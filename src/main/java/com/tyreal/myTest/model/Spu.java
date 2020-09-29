package com.tyreal.myTest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Spu extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private Long rootCategoryId;
    private Boolean online;
    private String price;
    private Long sketchSpecId;
    private Integer defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
    private String forThemeImg;
    //private Object spuThemeImg;

    @OneToMany()
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;

    @OneToMany()
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany()
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;

}
