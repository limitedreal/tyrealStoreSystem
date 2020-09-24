package com.tyreal.myTest.model;

import javax.persistence.*;

@Entity
public class BannerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;
    private String keyword;
    //跳转到的SPU的id或者专题的标识
    private Short type;
    //用type标注应该跳转到什么样的页面去，区分SPU和专题这些
    private String name;
    private Long bannerId;
    // 双向情况下此字段不能显式地写除非在下面JoinColumn(insertable = false,updatable = false)这样子指定，而会自动生成

    //导航属性

    @ManyToOne
    @JoinColumn(insertable = false,updatable = false,name = "bannerId")//指明外键的名称
    private Banner banner;
}
