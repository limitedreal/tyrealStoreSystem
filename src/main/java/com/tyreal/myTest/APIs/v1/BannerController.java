package com.tyreal.myTest.APIs.v1;

import com.tyreal.myTest.DTO.PersonDTO;
import com.tyreal.myTest.core.interceptors.ScopeLevel;
import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.model.Banner;
import com.tyreal.myTest.sample.ISkill;
import com.tyreal.myTest.service.BannerService;
import com.tyreal.myTest.service.BannerServiceImpl;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    @ScopeLevel()
    public Banner getByName(@PathVariable String name) {
        Banner b = bannerService.getByName(name);
        if(b==null){
            throw new NotFoundException(30005);
        }
        return b;
    }

    // 自描述性
    // host:port/v1/banner/test
    // ResponseBody取代HttpResponse

    // URL参数、问号参数、POST参数

    //@GetMapping("/test/{id1}")
    //public PersonDTO test1(@PathVariable(name="id1") @Range(min = 15,max=19,message = "不可以超过15哦哦") Integer id
    //        , @RequestParam String name
    //        ){
    //    //System.out.println("get-ok");
    //    PersonDTO dto = PersonDTO.builder()
    //            .name("tlb")
    //            .age(20)
    //            .build();
    //    return dto;
    //}
    //
    //@PostMapping("/test/{id1}")
    //public PersonDTO test3(@PathVariable(name="id1") @Range(min = 1,max=10,message = "不可以超过15哦哦") Integer id
    //        , @RequestParam String name
    //        , @RequestBody @Validated PersonDTO person){
    //    //缺点，需要转型，需要装箱，应使用单独创建的类进行处理
    //    //System.out.println("ok");
    //    iSkill.r();
    //    //PersonDTO dto = new PersonDTO();
    //    PersonDTO dto = PersonDTO.builder()
    //            .name("tlb")
    //            .age(20)
    //            .build();
    //    return dto;
    //    //throw new ForbiddenException(10001);
    //}
}
