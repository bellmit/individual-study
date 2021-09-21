package cn.gyw.thirdpart.mail;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by guanyw on 2019/2/27.
 */
@Ignore("mail send test ignore")
public class MailSendUtilTest {

    @Test
    public void shouldSuccessSendEmail() {
        Map<String, String> paramMap = new HashMap<>();
        Properties props = new Properties();
        InputStream in = MailSendUtilTest.class.getClassLoader().getResourceAsStream("email.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Object> keySet = props.keySet();
        for (Object key : keySet) {
            String skey = key.toString();
            paramMap.put(skey, props.getProperty(skey));
        }
//        MailSendUtil.sendSimpleEmail(paramMap);  //发送简单文本邮件
        MailSendUtil.sendComplexEmail(paramMap);  //发送复杂带附件邮件
    }
}
