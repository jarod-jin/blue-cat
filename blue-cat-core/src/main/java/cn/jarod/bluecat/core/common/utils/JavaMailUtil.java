package cn.jarod.bluecat.core.common.utils;


import cn.jarod.bluecat.core.common.pojo.CustomMailDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static javax.mail.internet.MimeUtility.*;
import static javax.mail.internet.MimeUtility.encodeText;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/27
 */
@Slf4j
public class JavaMailUtil {

    public static Session getMailSession(CustomMailDTO mailboxDTO) {
        if (mailboxDTO.getProperties() == null) {
            mailboxDTO.setDefaultProperties();
        }
        if (mailboxDTO.getIsValidate()) {
            return Session.getDefaultInstance(mailboxDTO.getProperties(),
                    new Authenticator() {
                        @Override
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailboxDTO.getSender(), mailboxDTO.getEmailPassword());
                        }
                    });
        }
        return Session.getDefaultInstance(mailboxDTO.getProperties());
    }

    /**
     * 发送文本邮件
     *
     * @param mailboxDTO
     * @return
     */
    public static boolean sendTextMail(CustomMailDTO mailboxDTO) {

        Session sendMailSession = getMailSession(mailboxDTO);
        try {
            Message mailMessage = getMimeMessage(sendMailSession, mailboxDTO);
            // 保存邮件(可选)
            mailMessage.saveChanges();
            mailMessage.setText(mailboxDTO.getMailContext());
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    /**
     * 发送Html邮件 可带附件
     *
     * @param mailboxDTO
     * @return
     */
    public static boolean sendHtmlMail(CustomMailDTO mailboxDTO) {

        Session sendMailSession = getMailSession(mailboxDTO);
        try {
            Message mailMessage = getMimeMessage(sendMailSession, mailboxDTO);
            // 保存邮件(可选)
            mailMessage.saveChanges();
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            MimeMultipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(mailboxDTO.getMailContext(), "text/html; charset=utf-8");

            mainPart.addBodyPart(html);

            // 有多个附件，多次添加
            setBodyPart(mailboxDTO.getAttachment(), mainPart);
            setBodyPart(mailboxDTO.getAttachmentMap(), mainPart);
            // 混合关系
            mainPart.setSubType("mixed");
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException | IOException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    private static void setBodyPart(Map<String, byte[]> attachmentMap, MimeMultipart mainPart) throws MessagingException, UnsupportedEncodingException {
        if (!CollectionUtils.isEmpty(attachmentMap)) {
            for (Map.Entry<String, byte[]> entry : attachmentMap.entrySet()) {
                String[] strs = entry.getKey().split(":");
                addBodyPart(entry.getValue(), strs[1], strs[0], mainPart);
            }
        }
    }

    private static void addBodyPart(byte[] bytes, String contentType, String fileName, MimeMultipart mainPart) throws MessagingException, UnsupportedEncodingException {
        MimeBodyPart attachment = new MimeBodyPart();
        DataSource dataSource = new ByteArrayDataSource(bytes, contentType);
        DataHandler dh = new DataHandler(dataSource);
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh);
        attachment.setDisposition(MimeBodyPart.ATTACHMENT);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(encodeText(fileName));
        mainPart.addBodyPart(attachment);

    }

    private static void setBodyPart(MultipartFile[] array, MimeMultipart mainPart) throws IOException, MessagingException {
        if (array != null && array.length > 0) {
            for (MultipartFile att : array) {
                addBodyPart(att.getBytes(), att.getContentType(), att.getOriginalFilename(), mainPart);
            }
        }
    }


    /**
     * 创建一封邮件的实例对象
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session, CustomMailDTO mailboxDTO) throws MessagingException, UnsupportedEncodingException {

        // 根据session创建一个邮件消息
        MimeMessage mailMessage = new MimeMessage(session);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailboxDTO.getSenderEmail());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        List<InternetAddress> list = Lists.newArrayList();
        for (String e : mailboxDTO.getRecipients()) {
            InternetAddress internetAddress = new InternetAddress(e.trim());
            list.add(internetAddress);
        }
        InternetAddress[] to = list.toArray(new InternetAddress[0]);
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipients(Message.RecipientType.TO, to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailboxDTO.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // 抄送人员不存在则不抄送
        if (null != mailboxDTO.getCopyRecipients() && mailboxDTO.getCopyRecipients().length > 0) {
            List<InternetAddress> listC = Lists.newArrayList();
            for (String e : mailboxDTO.getCopyRecipients()) {
                InternetAddress internetAddress = new InternetAddress(e.trim());
                listC.add(internetAddress);
            }
            InternetAddress[] copyR = listC.toArray(new InternetAddress[0]);
            mailMessage.addRecipients(Message.RecipientType.CC, copyR);
        }
        return mailMessage;
    }

}
