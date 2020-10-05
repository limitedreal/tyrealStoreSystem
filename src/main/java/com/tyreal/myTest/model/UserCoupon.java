package com.tyreal.myTest.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_coupon", schema = "sleeve", catalog = "")
public class UserCoupon{
    //作为第三章表，唯独usercoupon是建立了model，因为有特殊的意义
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Long orderId;
    private Date createTime;

}
