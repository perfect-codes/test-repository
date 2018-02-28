package com.perfectcodes.component;

import com.perfectcodes.util.GJJsonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@EnableScheduling
public class SchedueConfig {

    private Logger logger = LoggerFactory.getLogger(SchedueConfig.class);

    @Autowired
    private PropConfig propConfig;

    @Scheduled(cron = "0 0/90 * * * ?")
    public void getToken() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpGet httpGet = new HttpGet(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", propConfig.getAppid(), propConfig.getSecret()));
        try {
            httpResponse = httpClient.execute(httpGet);
            String content = EntityUtils.toString(httpResponse.getEntity());
            logger.debug(String.format("access_token:%s",content));
            HashMap<String,Object> hashMap = GJJsonUtil.fromJSON(content, HashMap.class);
            propConfig.setAccessToken((String) hashMap.get("access_token"));
        } catch (IOException e) {
            logger.error("获取access_token失败，网络请求错误", e);
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                logger.error("获取access_token网络请求关闭错误");
            }
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void test() {
//        logger.debug("----------------------------------------");
    }
}
