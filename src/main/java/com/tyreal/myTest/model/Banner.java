package com.tyreal.myTest.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16)
    private String name;
    @Transient
    private String description;
    private String img;
    //图片地址
    private String title;


    @OneToMany(mappedBy = "banner")
    private List<BannerItem> items;
}
