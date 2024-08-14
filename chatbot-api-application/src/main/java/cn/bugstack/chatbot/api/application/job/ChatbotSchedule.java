package cn.bugstack.chatbot.api.application.job;

import cn.bugstack.chatbot.api.domain.ai.IOpenAI;
import cn.bugstack.chatbot.api.domain.zsxq.IZsxqApi;
import cn.bugstack.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author zhx
 * @description 任务体
 */
@EnableScheduling
@Configuration
public class ChatbotSchedule implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

//    private String groupName;
//    private String groupId;
//    private String cookie;
//    private String openAiKey;
//    private boolean silenced;
//
//    private IZsxqApi zsxqApi;
//    private IOpenAI openAI;
//
//    public ChatbotSchedule(String groupName, String groupId, String cookie, String openAiKey, IZsxqApi zsxqApi, IOpenAI openAI, boolean silenced) {
//        this.groupName = groupName;
//        this.groupId = groupId;
//        this.cookie = cookie;
//        this.openAiKey = openAiKey;
//        this.zsxqApi = zsxqApi;
//        this.openAI = openAI;
//        this.silenced = silenced;
//    }
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;
    private boolean silenced;
    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

//    public ChatbotSchedule(String groupId, String cookie, String openAiKey, IZsxqApi zsxqApi, IOpenAI openAI, boolean silenced) {
//
//        this.groupId = groupId;
//        this.cookie = cookie;
//        this.openAiKey = openAiKey;
//        this.zsxqApi = zsxqApi;
//        this.openAI = openAI;
//        this.silenced = silenced;
//    }
//    @Override
    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        try {
            //规律的调用曲线有被识别封控的风险，因此添加一个随机函数让调用不再规律
            if (new Random().nextBoolean()) {
//                logger.info("{} 随机打烊中...", groupName);
                logger.info("{} 随机打烊中...");
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
//                logger.info("{} 打烊时间不工作，AI 下班了！", groupName);
                logger.info("{} 打烊时间不工作，AI 下班了！");
                return;
            }
            // 1. 检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
//            logger.info("{} 检索结果：{}", groupName, JSON.toJSONString(unAnsweredQuestionsAggregates));
            logger.info("{} 检索结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
//                logger.info("{} 本次检索未查询到待会答问题", groupName);
                logger.info("{} 本次检索未查询到待会答问题");
                return;
            }
            List<Topics> deal_topics =new ArrayList<>();
            for (Topics topic : topics){
                String text = topic.getTalk().getText();
                String mentionPattern = "<e type=\"mention\" uid=\"415544512228128\".*?>"; // 匹配 mention 部分的正则表达式
                Pattern pattern = Pattern.compile(mentionPattern);
                if (text != null && text.contains("<e type=\"mention\" uid=\"415544512228128\"")){
                    // 使用正则表达式替换掉 mention 部分
                    String updatedText = pattern.matcher(text).replaceAll("");
                    // 更新 topic 的 text
                    topic.getTalk().setText(updatedText.trim());  // 删除可能留下的空白字符
                    deal_topics.add(topic);
                }
            }
            if (null == deal_topics || deal_topics.isEmpty()) {
//                logger.info("{} 本次检索未查询到待会答问题", groupName);
                logger.info("{} 本次检索未查询到待会答问题");
                return;
            }
            for (Topics topic : deal_topics){
                // 2. AI 回答@某位用户的问题,用户uid为415544512228128
                String topicId = topic.getTopic_id();
                String text = topic.getTalk().getText();
                String answer = openAI.doChatGPT(openAiKey, text.trim());
                // 3. 问题回复
                boolean status = zsxqApi.answer(groupId, cookie, topicId, answer, silenced);
//                logger.info("{} 编号：{} 问题：{} 回答：{} 状态：{}", groupName, topic.getTopic_id(), topic.getTalk().getText(), answer, status);
                logger.info("{} 编号：{} 问题：{} 回答：{} 状态：{}", topic.getTopic_id(), topic.getTalk().getText(), answer, status);

            }
        } catch (Exception e) {
//            logger.error("{} 自动回答问题异常", groupName, e);
            logger.error("{} 自动回答问题异常", e);
        }
    }

}
