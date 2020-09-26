package com.tyreal.myTest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

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
