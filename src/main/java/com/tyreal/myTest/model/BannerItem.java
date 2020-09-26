package com.tyreal.myTest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "banner_item", schema = "sleeve", catalog = "")
public class BannerItem extends BaseEntity{
    @Id
    private Long id;
    private String img;
    private String keyword;
    private Short type;
    private Long bannerId;
    private String name;
}
