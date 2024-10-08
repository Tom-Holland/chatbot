package cn.bugstack.chatbot.api.test;

import cn.bugstack.chatbot.api.domain.ai.IOpenAI;
import cn.bugstack.chatbot.api.domain.zsxq.IZsxqApi;
import cn.bugstack.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author zhx
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);
//    从配置文件中注入值
//    @Value("${chatbot-api.group01.groupId}")
    @Value("${chatbot-api.groupId}")
    private String groupId;
//    @Value("${chatbot-api.group01.cookie}")
    @Value("${chatbot-api.cookie}")
    private String cookie;
//    @Value("${chatbot-api.group01.openAiKey}")
    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            logger.info("topicId：{} text：{}", topicId, text);
            // 回答@某位用户的问题
            if (text != null && text.contains("<e type=\"mention\" uid=\"415544512228128\"")){
                zsxqApi.answer(groupId,cookie,topicId,text,false);
                // zsxqApi.answer(groupId, cookie, topicId, openAI.doChatGPT(openAiKey, text), false);

            }
        }
    }

    @Test
    public void test_openAi() throws IOException {
        String response = openAI.doChatGPT(openAiKey, "帮我写一个java冒泡排序");
        logger.info("测试结果：{}", response);
    }

}
