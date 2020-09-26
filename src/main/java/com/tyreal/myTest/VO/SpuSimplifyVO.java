package com.tyreal.myTest.VO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpuSimplifyVO {
    private Long id;
    private String title;
    private String subtitle;
    private String price;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private String forThemeImg;
}
