package com.tyreal.myTest.VO;

import com.tyreal.myTest.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
public class CategoryAllVo {
    private List<CategoryPureVO> roots;
    private List<CategoryPureVO> subs;
    //这里都是list，里面的Category是需要精简的，所以有一个CategoryPureVO类

    public CategoryAllVo(Map<Integer, List<Category>> map) {
        //转化成stream流，函数式编程方法
        this.roots = map.get(1).stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());
        this.subs = map.get(2).stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());
    }
}
