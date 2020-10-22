package com.tianlb.myTest.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class GridCategory extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String title;
    private Long categoryId;
    private Long rootCategoryId;
    private String img;

}
