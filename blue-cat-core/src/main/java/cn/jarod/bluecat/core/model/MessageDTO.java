package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jarod.jin 2019/9/4
 */
@Getter
@Setter
@ToString
public class MessageDTO extends RdsModel {

    private String message;

    public MessageDTO(String msg){
        this.message = msg;
    }
}
