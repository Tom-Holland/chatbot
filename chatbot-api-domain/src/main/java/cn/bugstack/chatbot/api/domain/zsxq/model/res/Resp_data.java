package cn.bugstack.chatbot.api.domain.zsxq.model.res;

import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * @author zhx
 * @description 请求返回的结果数据，这里是一些topics数据
 */

public class Resp_data
{
    private List<Topics> topics;

    public void setTopics(List<Topics> topics){
        this.topics = topics;
    }
    public List<Topics> getTopics(){
        return this.topics;
    }
}

