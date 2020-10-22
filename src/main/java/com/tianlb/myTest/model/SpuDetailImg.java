package com.tianlb.myTest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "spu_detail_img", schema = "sleeve", catalog = "")
@Getter
@Setter
public class SpuDetailImg extends BaseEntity {
    @Id
    private Long id;

    private String img;

    private Long spuId;

    private Long index;

}
