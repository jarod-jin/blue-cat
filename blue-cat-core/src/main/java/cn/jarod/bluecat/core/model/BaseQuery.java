package cn.jarod.bluecat.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

/**
 * @author jarod.jin 2019/9/4
 */
@Setter
@Getter
public class BaseQuery {

    @NotNull
    private Integer pageNum;

    @NotNull
    private Integer pageSize;

    @NotNull
    private Boolean isAsc;

    private String[] orderProperty;

    public BaseQuery(){
        pageNum = 1;
        pageSize = 10;
        isAsc = true;
        if (orderProperty==null || orderProperty.length<1){
            orderProperty = new String[]{"gmtCreate"};
        }
    }

    public PageRequest getPageRequest(){
        Sort sort = Sort.by(isAsc? Sort.Direction.ASC:Sort.Direction.DESC, orderProperty);
        return PageRequest.of(this.getPageNum() - 1, this.getPageSize(), sort);
    }


}
