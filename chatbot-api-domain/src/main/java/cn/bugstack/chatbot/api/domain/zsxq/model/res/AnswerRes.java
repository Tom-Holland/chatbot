package cn.bugstack.chatbot.api.domain.zsxq.model.res;

/**
 * @author zhx
 * @description 请求问答接口结果
 */
public class AnswerRes
{
    private boolean succeeded;

    private Resp_data resp_data;

//    public void setSucceeded(boolean succeeded){
//        this.succeeded = succeeded;
//    }
    public boolean isSucceeded(){
        return succeeded;
    }
    public boolean getSucceeded(){
        return this.succeeded;
    }
    public void setResp_data(Resp_data resp_data){
        this.resp_data = resp_data;
    }
    public Resp_data getResp_data(){
        return this.resp_data;
    }
}
