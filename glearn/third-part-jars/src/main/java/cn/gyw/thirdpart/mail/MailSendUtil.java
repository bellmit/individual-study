package cn.gyw.thirdpart.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 邮件工具类
 * @author guanyw
 *
 */
public class MailSendUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    //发件人
    private static String sendEmailAccount;
    private static String sendEmailPassword;
    private static String EmailSMTPHost;
    private static String receiveEmailAccount;

    //简单邮件发送
    public static void sendSimpleEmail(Map<String,String> map){
        mapToParam(map);
        String theme = map.get("theme");
        String content = map.get("content");

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", EmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        MimeMessage message = null;
        Transport transport = null;
        try {
            message = createSimpleMessage(session, sendEmailAccount, receiveEmailAccount,theme,content);
            transport = session.getTransport();
            transport.connect(sendEmailAccount,sendEmailPassword);

            transport.sendMessage(message, message.getAllRecipients());

         // 8. 将该邮件保存到本地
            File outFile = new File("E:"+File.separator+"files"+File.separator+"mySimpleEmail"+"^"+sdf.format(new Date()) +".eml");
            OutputStream out = new FileOutputStream(outFile);
            message.writeTo(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException("邮件传输对象异常关闭!");
            }
        }
    }

    //创建并发送一封包含图片和附件的复杂邮件。
    public static void sendComplexEmail(Map<String,String> map){
    	mapToParam(map);
    	String theme = map.get("theme");

    	Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", EmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        MimeMessage message = null;
        Transport transport = null;
        try {
            message = createComplexMessage(session, sendEmailAccount, receiveEmailAccount,theme);
            transport = session.getTransport();
            transport.connect(sendEmailAccount,sendEmailPassword);

            transport.sendMessage(message, message.getAllRecipients());

         // 8. 将该邮件保存到本地
            File outFile = new File("E:"+File.separator+"files"+File.separator+"myComplexEmail"+"^"+sdf.format(new Date()) +".eml");
            OutputStream out = new FileOutputStream(outFile);
            message.writeTo(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException("邮件传输对象异常关闭!");
            }
        }
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    private static MimeMessage createSimpleMessage(Session session, String sendMail, String receiveMail,
            String theme, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject(theme, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 创建并发送一封包含文本、图片、附件的复杂邮件
     * @param session
     * @param sendMail
     * @param receiveMail
     * @return
     * @throws Exception
     */
    private static MimeMessage createComplexMessage(Session session, String sendMail, String receiveMail
            ,String theme) throws Exception {
    	MimeMessage message = new MimeMessage(session);
    	message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));
    	message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));
    	message.setSubject(theme, "UTF-8");

    	//创建邮件内容
    	//1.创建图片节点
    	MimeBodyPart image = new MimeBodyPart();
    	File imgFile = new File("E:\\Pictures\\20170508113921.png");
    	DataHandler dh = new DataHandler(new FileDataSource(imgFile));  //读取本地图片
    	image.setDataHandler(dh);                   // 将图片数据添加到“节点”
        image.setContentID("image_fairy_tail");     // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）

        //2. 创建文本“节点”
        MimeBodyPart text = new MimeBodyPart();
        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("这是一张图片<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=UTF-8");

        //3. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // 关联关系

        //4. 将 文本+图片 的混合“节点”封装成一个普通“节点”
        //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

     // 9. 创建附件“节点”
        MimeBodyPart attachment = new MimeBodyPart();
        File textFile = new File("E:\\files\\myEmail^20170516034813.eml");
        DataHandler dh2 = new DataHandler(new FileDataSource(textFile));  // 读取本地文件
        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));              // 设置附件的文件名（需要编码）

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);

        // 12. 设置发件时间
        message.setSentDate(new Date());

        // 13. 保存上面的所有设置
        message.saveChanges();

    	return message;
    }

    private static void mapToParam(Map<String,String> map){
        sendEmailAccount = map.get("sendEmailAccount");
        sendEmailPassword = map.get("sendEmailPassword");
        receiveEmailAccount = map.get("receiveEmailAccount");
        EmailSMTPHost = map.get("EmailSMTPHost");
    }
}
