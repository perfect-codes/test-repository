package com.ruubypay;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void logic() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://qr.liantu.com/api.php?text=xupengfei");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        System.out.println(httpEntity.getContentType());
        InputStream inputStream = httpEntity.getContent();
        System.out.println(httpEntity.isStreaming());
        File file = new File("D:\\dev\\zz_affix\\xpf.png");
        FileUtils.copyInputStreamToFile(inputStream,file);
    }

    public static void main( String[] args )
    {
//        SpringApplication.run(App.class,args);
        try {
            logic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
