package com.ruubypay;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest{

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("perfect_codes@163.com");
        message.setSubject("test");
        message.setText("just test info2");
        message.setTo("perfect_codes@126.com");
        javaMailSender.send(message);
    }

    @Test
    public void testHtml() throws MessagingException, IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File("D:\\dev\\git\\xpf\\test\\ruubypay-test\\javamail\\src\\test\\java\\com\\ruubypay"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        Template template = cfg.getTemplate("mail.ftl");
        Writer writer = new StringWriter();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username","perfect");
        map.put("url","http://www.baidu.com");
        map.put("urlName","遥控飞机");
        template.process(map,writer);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
        mimeMessageHelper.setFrom("perfect_codes@163.com");
        mimeMessageHelper.setTo("perfect_codes@126.com");
        mimeMessageHelper.setSubject("测试模板邮件");
        mimeMessageHelper.setText(writer.toString(),true);
        javaMailSender.send(message);
    }
}
