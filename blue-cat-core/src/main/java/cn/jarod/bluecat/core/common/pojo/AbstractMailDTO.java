package cn.jarod.bluecat.core.common.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/19
 */
@Slf4j
@Data
public class AbstractMailDTO {

    /**
     * 接收人邮箱地址数组
     */
    @NotNull
    private String[] recipients;

    /**
     * 抄送人员
     */
    private String[] copyRecipients;

    /**
     * 邮件主题
     */
    @NotNull
    private String subject;

    /**
     * 发送主体（如，工号或者某个系统）
     */
    @NotNull
    private String sender;

    /**
     * 被代表人邮箱
     */
    private String behalfOf;


    /**
     * 附件
     */
    private MultipartFile[] attachment;


    protected AbstractMailDTO(String[] recipients,
                              String[] copyRecipients,
                              String subject,
                              String sender,
                              String behalfOf,
                              MultipartFile[] attachments){
        this.copyRecipients = copyRecipients;
        this.recipients = recipients;
        this.subject = subject;
        this.sender= sender;
        this.behalfOf = behalfOf;
        this.attachment = attachments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append(field.getName()).append(":").append(field.get(this)).append(",");
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        // 去掉多于的逗号
        if(sb.length() > 1){
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append("]");
        return sb.toString();
    }

}
