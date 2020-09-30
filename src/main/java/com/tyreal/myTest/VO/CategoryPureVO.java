package com.tyreal.myTest.VO;

//使用精简的类，避免循环序列化

import com.tyreal.myTest.model.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class CategoryPureVO {
    private Long id;
    private String name;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;

    public CategoryPureVO(Category category){
        BeanUtils.copyProperties(category,this);
    }

}
