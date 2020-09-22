package com.tyreal.myTest.APIs.v1;

import com.tyreal.myTest.DTO.PersonDTO;
import com.tyreal.myTest.exception.http.ForbiddenException;
import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.sample.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private ISkill iSkill;

    // 自描述性
    // host:port/v1/banner/test
    // ResponseBody取代HttpResponse

    // URL参数、问号参数、POST参数

    @GetMapping("/test/{id1}")
    public String test1(@PathVariable(name="id1") Integer id
            ,@RequestParam String name
            ){
        iSkill.r();
        throw new ForbiddenException(10001);
    }
    //@PostMapping("/test")
    //public String test2(@RequestBody Map<String,Object> person){
    //    //缺点，需要转型，需要装箱，应使用单独创建的类进行接收
    //    iSkill.r();
    //    throw new ForbiddenException(10001);
    //}

    @PostMapping("/test")
    public String test3(@RequestBody PersonDTO person){
        //缺点，需要转型，需要装箱，应使用单独创建的类进行处理
        iSkill.r();
        //PersonDTO dto = new PersonDTO();
        PersonDTO dto = PersonDTO.builder()
                .name("tlb")
                .age(20)
                .build();
        throw new ForbiddenException(10001);
    }



}
