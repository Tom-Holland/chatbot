package cn.bugstack.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author zhx
 * @description ChatGPT open ai 接口：https://beta.openai.com/account/api-keys
 */
public interface IOpenAI {

    String doChatGPT(String openAiKey, String question) throws IOException;

}
