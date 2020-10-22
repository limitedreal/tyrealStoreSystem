package com.tianlb.myTest.VO;

import com.tianlb.myTest.model.Theme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class ThemePureVO {
    private Long id;
    private String title;
    private String description;
    private String name;
    private String extend;
    private String entranceImg;
    private String internalTopImg;
    private Boolean online;
    private String titleImg;
    private String tplName;

    public ThemePureVO(Theme theme) {
        BeanUtils.copyProperties(theme, this);
    }

}
