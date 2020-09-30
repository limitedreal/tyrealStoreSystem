package com.tyreal.myTest.APIs.v1;

import com.tyreal.myTest.model.Theme;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/theme")
public class ThemeController {

    @GetMapping("/by/names")
    public void getThemeGroupByNames(){

    }

    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeByNameWithSpu(){

    }

}
