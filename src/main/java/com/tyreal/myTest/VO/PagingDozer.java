package com.tyreal.myTest.VO;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PagingDozer<T,K> extends Paging{
    //TODO:比较复杂的泛型+元类的处理方法
    public PagingDozer(Page<T> pageT,Class<K> classK){
        this.initPageParameters(pageT);
        List<T> tList = pageT.getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> voList = new ArrayList<>();
        tList.forEach(s->{
            K vo = mapper.map(s,classK);
            voList.add(vo);
        });
        this.setItems(voList);
    }
}
