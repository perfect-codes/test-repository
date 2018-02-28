package com.perfectcodes.listener;

import com.perfectcodes.component.SchedueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author xpf
 * @description 应用监听器
 * @date 2018/2/9 12:10
 */
public class TokenListener implements ApplicationListener {

    private static Logger logger = LoggerFactory.getLogger(TokenListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationReadyEvent){//启动完成
            logger.debug("******启动成功******");
            //TODO 为了避免不断请求微信，开发期间可通过注释以下内容关闭启动获取access_token
            SchedueConfig schedueConfig = ((ApplicationReadyEvent) applicationEvent).getApplicationContext().getBean(SchedueConfig.class);
            schedueConfig.getToken();
        }
    }
}
