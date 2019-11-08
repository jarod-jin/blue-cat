package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther jarod.jin 2019/9/4
 */
@Getter
@Setter
public class MessageBO extends BaseBO {

    private String message;

    public MessageBO(String msg){
        this.message = msg;
    }
}
