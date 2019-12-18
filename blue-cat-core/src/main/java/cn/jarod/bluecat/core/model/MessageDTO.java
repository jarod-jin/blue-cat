package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jarod.jin 2019/9/4
 */
@Getter
@Setter
public class MessageDTO extends BaseModel {

    private String message;

    public MessageDTO(String msg){
        this.message = msg;
    }
}
