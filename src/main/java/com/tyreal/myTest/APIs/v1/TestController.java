package com.tyreal.myTest.APIs.v1;

import com.tyreal.myTest.sample.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
//@Scope("prototype")
public class TestController {
    @Autowired
    private Test test;

    @Autowired
    private ObjectFactory<Test> test1;

    @GetMapping("")
    public void getDetail(){
        System.out.println(this.test);
        System.out.println(this.test1.getObject());
    }

}
