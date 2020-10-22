package cn.jarod.bluecat.core.common.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Properties;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/19
 */
@Slf4j
@Setter
@Getter
public class CustomMailDTO extends AbstractMailDTO {

    /**
     * 邮件模板对应内容
     */
    private String mailContext;

    /**
     * 发送者邮箱
     */
    private String senderEmail;

    /**
     * 发送者密码
     */
    private String emailPassword;

    private Map<String, byte[]> attachmentMap;

    private Boolean isValidate;

    private Properties properties;

    private CustomMailDTO(String[] recipients, String[] copyRecipients, String subject, String sender, String mailContext,
                          MultipartFile[] attachments, String senderEmail, String emailPassword, Map<String, byte[]> attachmentMap) {
        super(recipients, copyRecipients, subject, sender, null,attachments);
        this.mailContext = mailContext;
        this.senderEmail = senderEmail;
        this.emailPassword = emailPassword;
        this.attachmentMap = attachmentMap;
        this.isValidate = StringUtils.hasText(senderEmail) && StringUtils.hasText(emailPassword);

    }

    public static CustomMailDTO getInstance(String[] recipients, String[] copyRecipients, String subject, String sender,
                                            String mailContext, MultipartFile[] attachments, String senderEmail, String emailPassword,
                                            Map<String, byte[]> attachmentMap) {
        return new CustomMailDTO(recipients, copyRecipients, subject, sender,
                mailContext, attachments, senderEmail, emailPassword, attachmentMap);
    }

    public void setDefaultProperties() {
        properties = new Properties();
        properties.put("mail.smtp.host", "mail.dahuatech.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.auth.mechanisms", "NTLM");
        properties.put("mail.smtp.auth.ntlm.domain", "dahuatech.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.transport.protocol", "smtp");
//        properties.put("mail.debug","true");
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
