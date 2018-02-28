package com.perfectcodes.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class GJHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(GJHttpUtil.class);
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    /**
     * @author xpf
     * @description 通用GET请求
     * @date 2018/2/9 15:26
     */
    public static HashMap<String,Object> get(HttpGet httpGet){
        CloseableHttpResponse httpResponse = null;
        logger.debug(String.format("GET请求开始,请求地址为:%s",httpGet.getURI().toString()));
        try {
            httpResponse = httpClient.execute(httpGet);
            String content = EntityUtils.toString(httpResponse.getEntity(), Charset.defaultCharset());
            logger.debug(String.format("GET请求结束,返回结果为:%s",content));
            return GJJsonUtil.fromJSON(content, HashMap.class);
        } catch (IOException e) {
            logger.error("GET请求错误", e);
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                logger.error("GET请求关闭错误");
            }
        }
        return null;
    }
}
