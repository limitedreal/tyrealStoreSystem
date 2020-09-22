package com.tyreal.myTest.APIs.v1;

import com.tyreal.myTest.DTO.PersonDTO;
import com.tyreal.myTest.sample.ISkill;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
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
    public PersonDTO test1(@PathVariable(name="id1") @Range(min = 15,max=19,message = "不可以超过15哦哦") Integer id
            ,@RequestParam String name
            ){
        iSkill.r();
        PersonDTO dto = PersonDTO.builder()
                .name("tlb")
                .age(20)
                .build();
        return dto;
    }

    @PostMapping("/test")
    public PersonDTO test3(@RequestBody @Validated PersonDTO person){
        //缺点，需要转型，需要装箱，应使用单独创建的类进行处理
        iSkill.r();
        //PersonDTO dto = new PersonDTO();
        PersonDTO dto = PersonDTO.builder()
                .name("tlb")
                .age(20)
                .build();
        return dto;
        //throw new ForbiddenException(10001);
    }



}
