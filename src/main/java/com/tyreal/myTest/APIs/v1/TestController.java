package com.tyreal.myTest.APIs.v1;

import com.tyreal.myTest.model.Sku;
import com.tyreal.myTest.repository.SkuRepository;
import com.tyreal.myTest.sample.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
//@Scope("prototype")
public class TestController {
    @Autowired
    private Test test;

    @Autowired
    private ObjectFactory<Test> test1;

    @Autowired
    private SkuRepository skuRepository;

    @GetMapping("")
    public void getDetail(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(55L);
        List<Sku> skulist = skuRepository.findAllByIdIn(list);
        System.out.println(skulist);
    }

}
