package cn.bugstack.chatbot.api.domain.ai.model.vo;

/**
 * @Author zhx
 * @description 模型使用参数封装
 */
public class Usage
{
    private int completion_tokens;

    private int prompt_tokens;

    private int total_tokens;

    public void setCompletion_tokens(int completion_tokens){
        this.completion_tokens = completion_tokens;
    }
    public int getCompletion_tokens(){
        return this.completion_tokens;
    }
    public void setPrompt_tokens(int prompt_tokens){
        this.prompt_tokens = prompt_tokens;
    }
    public int getPrompt_tokens(){
        return this.prompt_tokens;
    }
    public void setTotal_tokens(int total_tokens){
        this.total_tokens = total_tokens;
    }
    public int getTotal_tokens(){
        return this.total_tokens;
    }
}
