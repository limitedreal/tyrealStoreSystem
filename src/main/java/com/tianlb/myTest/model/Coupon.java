package com.tianlb.myTest.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Coupon extends BaseEntity{
    @Id
    private Long id;
    private Long activityId;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;

    private BigDecimal fullMoney;
    //满减的“满”
    private BigDecimal minus;
    //满减的“减”
    private BigDecimal rate;
    //折扣

    private String remark;
    //对于优惠券的进一步说明，比如在前端显示的该券适用范围
    private Boolean wholeStore;
    //是不是全场券
    private Integer type;
    //优惠券的类型: 1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "couponList")
    private List<Category> categoryList;

}
