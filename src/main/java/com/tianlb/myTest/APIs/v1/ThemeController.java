package com.tianlb.myTest.APIs.v1;

import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.VO.ThemePureVO;
import com.tianlb.myTest.model.Theme;
import com.tianlb.myTest.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/by/names")
    public List<ThemePureVO> getThemeGroupByNames(@RequestParam(name = "names") String names) {
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = themeService.getByNames(nameList);
        List<ThemePureVO> themePureVOList = new ArrayList<>();
        themes.forEach(s -> {
            ThemePureVO themePure = new ThemePureVO(s);
            themePureVOList.add(themePure);
        });
        return themePureVOList;
    }

    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeByNameWithSpu(@PathVariable(name = "name") String name) {
        Optional<Theme> optionalTheme = themeService.getByName(name);
        return optionalTheme.orElseThrow(()-> new NotFoundException(30011));
    }

}
