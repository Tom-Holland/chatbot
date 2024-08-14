package cn.bugstack.chatbot.api.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * @description 单元测试
 */
public class ApiTest {

//    @Test
//    public void base64(){
//        String cronExpression = new String(Base64.getDecoder().decode("MC81MCAqICogKiAqID8="), StandardCharsets.UTF_8);
//        System.out.println(cronExpression);
//    }
//
    private static final String COOKIE = "tfstk=fb_eogaf-yUFBxyIcp8y0pC53K8pyEebLa9WZ_fkOpvnR4saq_fHO32pRaWNHsIQw3Y3aTWcL36PwWdkETXyFMZ_l6CpyU2jzraf9wS08I6E-LcgZIR-t64vzOfpyU21nJ9XA6BjM1fDUUVwSQArxUAHr5-MGd0HZ2AoI5RJzVCKD8lHb-fDINTyeSPQahpZrqlvTHx4kd0oaNAF_Kf3m40l7B-hzPUcjM-CqsKARTUrWe1VjFAPySmyUnSlRFb3Irx9qgjX4GM3OtXPsTLCS7mHnM6BSwjqZ4fw8MxJThkUQKbdsZLMfrVVsNBC9N5SZzA1hdfd-Uz0Me8HKFSydYdGKQ_RYYmyxCdwh5PwacEDLX-KGrn-2HI9_KNL90nJxCdwh5PZ20KpWCJb9WC..; zsxq_access_token=41F5AD64-A101-2818-D0AE-D59C1291D520_2434DE71EB4E1E1B; abtest_env=product; zsxqsessionid=786a909b08b4e165a202a7f587227e14; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415544512228128%22%2C%22first_id%22%3A%2218e4f6f519c4c3-06830e1deb59f8-4c657b58-1327104-18e4f6f519dd0b%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.yuque.com%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNGY2ZjUxOWM0YzMtMDY4MzBlMWRlYjU5ZjgtNGM2NTdiNTgtMTMyNzEwNC0xOGU0ZjZmNTE5ZGQwYiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjQxNTU0NDUxMjIyODEyOCJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415544512228128%22%7D%2C%22%24device_id%22%3A%2218e4f6f519c4c3-06830e1deb59f8-4c657b58-1327104-18e4f6f519dd0b%22%7D";
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

//        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88888248222552/topics?scope=all&count=20");
        get.addHeader("cookie", "tfstk=fb_eogaf-yUFBxyIcp8y0pC53K8pyEebLa9WZ_fkOpvnR4saq_fHO32pRaWNHsIQw3Y3aTWcL36PwWdkETXyFMZ_l6CpyU2jzraf9wS08I6E-LcgZIR-t64vzOfpyU21nJ9XA6BjM1fDUUVwSQArxUAHr5-MGd0HZ2AoI5RJzVCKD8lHb-fDINTyeSPQahpZrqlvTHx4kd0oaNAF_Kf3m40l7B-hzPUcjM-CqsKARTUrWe1VjFAPySmyUnSlRFb3Irx9qgjX4GM3OtXPsTLCS7mHnM6BSwjqZ4fw8MxJThkUQKbdsZLMfrVVsNBC9N5SZzA1hdfd-Uz0Me8HKFSydYdGKQ_RYYmyxCdwh5PwacEDLX-KGrn-2HI9_KNL90nJxCdwh5PZ20KpWCJb9WC..; zsxq_access_token=41F5AD64-A101-2818-D0AE-D59C1291D520_2434DE71EB4E1E1B; abtest_env=product; zsxqsessionid=786a909b08b4e165a202a7f587227e14; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415544512228128%22%2C%22first_id%22%3A%2218e4f6f519c4c3-06830e1deb59f8-4c657b58-1327104-18e4f6f519dd0b%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.yuque.com%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNGY2ZjUxOWM0YzMtMDY4MzBlMWRlYjU5ZjgtNGM2NTdiNTgtMTMyNzEwNC0xOGU0ZjZmNTE5ZGQwYiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjQxNTU0NDUxMjIyODEyOCJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415544512228128%22%7D%2C%22%24device_id%22%3A%2218e4f6f519c4c3-06830e1deb59f8-4c657b58-1327104-18e4f6f519dd0b%22%7D");
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
//
    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/88888248222552/comments");
        post.addHeader("cookie", "tfstk=fb_eogaf-yUFBxyIcp8y0pC53K8pyEebLa9WZ_fkOpvnR4saq_fHO32pRaWNHsIQw3Y3aTWcL36PwWdkETXyFMZ_l6CpyU2jzraf9wS08I6E-LcgZIR-t64vzOfpyU21nJ9XA6BjM1fDUUVwSQArxUAHr5-MGd0HZ2AoI5RJzVCKD8lHb-fDINTyeSPQahpZrqlvTHx4kd0oaNAF_Kf3m40l7B-hzPUcjM-CqsKARTUrWe1VjFAPySmyUnSlRFb3Irx9qgjX4GM3OtXPsTLCS7mHnM6BSwjqZ4fw8MxJThkUQKbdsZLMfrVVsNBC9N5SZzA1hdfd-Uz0Me8HKFSydYdGKQ_RYYmyxCdwh5PwacEDLX-KGrn-2HI9_KNL90nJxCdwh5PZ20KpWCJb9WC..; zsxq_access_token=41F5AD64-A101-2818-D0AE-D59C1291D520_2434DE71EB4E1E1B; abtest_env=product; zsxqsessionid=786a909b08b4e165a202a7f587227e14; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415544512228128%22%2C%22first_id%22%3A%2218e4f6f519c4c3-06830e1deb59f8-4c657b58-1327104-18e4f6f519dd0b%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fwww.yuque.com%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThlNGY2ZjUxOWM0YzMtMDY4MzBlMWRlYjU5ZjgtNGM2NTdiNTgtMTMyNzEwNC0xOGU0ZjZmNTE5ZGQwYiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjQxNTU0NDUxMjIyODEyOCJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415544512228128%22%7D%2C%22%24device_id%22%3A%2218e4f6f519c4c3-06830e1deb59f8-4c657b58-1327104-18e4f6f519dd0b%22%7D");
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去百度！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    @Test
    public void findAndCommentOnMentionedTalks() throws IOException {
        // 1. 创建 HTTP 客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 2. 发起 GET 请求以获取话题列表
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88888248222552/topics?scope=all&count=20");
        get.addHeader("cookie", COOKIE);
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        String res = EntityUtils.toString(response.getEntity());

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println("Response JSON: " + res);

            // 3. 解析响应数据
            JSONObject jsonResponse = JSONObject.parseObject(res);
            JSONObject respData = jsonResponse.getJSONObject("resp_data");
            JSONArray topics = respData.getJSONArray("topics");

            if (topics != null && !topics.isEmpty()) {
                for (int i = 0; i < topics.size(); i++) {
                    JSONObject topic = topics.getJSONObject(i);
                    JSONObject talk = topic.getJSONObject("talk");
                    String text = talk.getString("text");
                    String topicId = topic.getString("topic_id");

                    // 4. 检查文本中是否有 @ 用户A 的内容
                    if (text != null && text.contains("<e type=\"mention\" uid=\"415544512228128\"")) {
                        // 找到被@的用户ID
                        String mentionedUid = extractMentionedUserId(text);

                        // 5. 发出评论
                        if (mentionedUid != null) {
                            postComment(httpClient, topicId, mentionedUid);
                        }
                    }
                }
            }
        } else {
            System.out.println("Failed to fetch topics: " + response.getStatusLine().getStatusCode());
        }

        httpClient.close();
    }

    // 从文本中提取被@的用户ID
    private String extractMentionedUserId(String text) {
        String mentionTagStart = "<e type=\"mention\" uid=\"";
        int startIndex = text.indexOf(mentionTagStart);
        if (startIndex != -1) {
            int uidStart = startIndex + mentionTagStart.length();
            int uidEnd = text.indexOf("\"", uidStart);
            if (uidEnd != -1) {
                return text.substring(uidStart, uidEnd);
            }
        }
        return null;
    }

    // 发出评论
    private void postComment(CloseableHttpClient httpClient, String topicId, String mentionedUserId) throws IOException {
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/groups/88888248222552/topics/" + topicId + "/comments");
        post.addHeader("cookie", COOKIE);
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String commentJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"感谢@您，我来解答：...\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": [\"" + mentionedUserId + "\"]\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(commentJson, ContentType.create("application/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println("Comment Response: " + res);
        } else {
            System.out.println("Failed to post comment: " + response.getStatusLine().getStatusCode());
        }
    }


//    private void postComment(CloseableHttpClient httpClient, String topicId, String talkId, String mentionedUserId, String commentText) throws IOException {
//        HttpPost post = new HttpPost("https://api.zsxq.com/v2/groups/88888248222552/topics/" + topicId + "/talks/" + talkId + "/comments");
//        post.addHeader("cookie", COOKIE);
//        post.addHeader("Content-Type", "application/json;charset=utf8");
//
//        // Formulate the comment text with @mention if needed
//        String paramJson = String.format(
//                "{\n  \"req_data\": {\n    \"text\": \"%s \\n\",\n    \"image_ids\": [],\n    \"silenced\": false\n  }\n}",
//                commentText);
//
//        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));
//        post.setEntity(stringEntity);
//
//        CloseableHttpResponse response = httpClient.execute(post);
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            String res = EntityUtils.toString(response.getEntity());
//            System.out.println("Comment posted: " + res);
//        } else {
//            System.out.println("Error posting comment: " + response.getStatusLine().getStatusCode());
//        }
//    }
//
//    @Test
//    public void test_chatGPT() throws IOException {
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
//        post.addHeader("Content-Type", "application/json");
//        post.addHeader("Authorization", "Bearer 自行申请 https://beta.openai.com/overview");
//
//        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";
//
//        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
//        post.setEntity(stringEntity);
//
//        CloseableHttpResponse response = httpClient.execute(post);
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            String res = EntityUtils.toString(response.getEntity());
//            System.out.println(res);
//        } else {
//            System.out.println(response.getStatusLine().getStatusCode());
//        }
//
//    }

}
