package cn.jarod.bluecat.core.api.pojo;

import cn.jarod.bluecat.core.security.pojo.DataConditionDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 通用查询类
 * @author jarod.jin 2019/9/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageRequestDTO extends RequestDTO{

    @NotNull
    private Integer pageNo;

    @NotNull
    private Integer pageSize;

    @NotNull
    private Boolean isAsc;

    private String[] orderProperty;

    public PageRequestDTO(int pageNo, int size) {
        if (pageNo < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }

        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.pageNo = pageNo;
        this.pageSize = size;
    }


    public PageRequestDTO(){
        pageNo = 1;
        pageSize = 10;
        isAsc = true;
        if (orderProperty==null || orderProperty.length<1){
            orderProperty = new String[]{"gmtCreate"};
        }
    }
    

    public PageRequest getPageRequest(){
        Sort sort = Sort.by(isAsc? Sort.Direction.ASC:Sort.Direction.DESC, orderProperty);
        return PageRequest.of(this.getPageNo() - 1, this.getPageSize(), sort);
    }


}
