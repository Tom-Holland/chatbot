package cn.bugstack.chatbot.api.application.job;

import cn.bugstack.chatbot.api.domain.ai.IOpenAI;
import cn.bugstack.chatbot.api.domain.zsxq.IZsxqApi;
import cn.bugstack.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @author zhx
 * @description 任务体
 */
public class ChatbotSchedule implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    private String groupName;
    private String groupId;
    private String cookie;
    private String openAiKey;
    private boolean silenced;

    private IZsxqApi zsxqApi;
    private IOpenAI openAI;

    public ChatbotSchedule(String groupName, String groupId, String cookie, String openAiKey, IZsxqApi zsxqApi, IOpenAI openAI, boolean silenced) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.cookie = cookie;
        this.openAiKey = openAiKey;
        this.zsxqApi = zsxqApi;
        this.openAI = openAI;
        this.silenced = silenced;
    }

    @Override
    public void run() {
        try {
            //规律的调用曲线有被识别封控的风险，因此添加一个随机函数让调用不再规律
            if (new Random().nextBoolean()) {
                logger.info("{} 随机打烊中...", groupName);
                return;
            }

            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
                logger.info("{} 打烊时间不工作，AI 下班了！", groupName);
                return;
            }

            // 1. 检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            logger.info("{} 检索结果：{}", groupName, JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
                logger.info("{} 本次检索未查询到待会答问题", groupName);
                return;
            }

            // 2. AI 回答@某位用户的问题,用户uid为415544512228128
            Topics topic = topics.get(topics.size() - 1);
            String topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            if (text != null && text.contains("<e type=\"mention\" uid=\"415544512228128\"")){
                String answer = openAI.doChatGPT(openAiKey, text.trim());
                // 3. 问题回复
                boolean status = zsxqApi.answer(groupId, cookie, topicId, answer, silenced);
                logger.info("{} 编号：{} 问题：{} 回答：{} 状态：{}", groupName, topic.getTopic_id(), topic.getTalk().getText(), answer, status);
            }else {
                logger.info("{} 本次检索未查询到待会答问题", groupName);
            }

        } catch (Exception e) {
            logger.error("{} 自动回答问题异常", groupName, e);
        }
    }

}
