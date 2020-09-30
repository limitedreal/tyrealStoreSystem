package com.tyreal.myTest.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Where(clause = "delete_time is null")
public class Banner extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="bannerId")//这个是一定要加滴！！！
    private List<BannerItem> items;
}
