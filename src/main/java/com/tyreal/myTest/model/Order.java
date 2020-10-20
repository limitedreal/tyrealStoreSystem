package com.tyreal.myTest.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tyreal.myTest.DTO.OrderAddressDTO;
import com.tyreal.myTest.utils.GenerateAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
//这个指定table是必不可少的
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    //数字自增虽然快，但是在数据库集群的情况下就不太合适了,他不唯一了
    //所以又使用了orderNo来标识
    //如果要做分布式集群，就需要有全局的id生成器

    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private Date expiredTime;
    private Date placedTime;

    private String snapItems;

    private String snapAddress;

    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public List<OrderSku> getSnapItems() {
        List<OrderSku> list = GenerateAndJson.jsonToObject(this.snapItems,
                new TypeReference<List<OrderSku>>() {
                });
        return list;
    }

    public void setSnapItems(List<OrderSku> orderSkuList) {
        if(orderSkuList.isEmpty()){
            return;
        }
        this.snapItems = GenerateAndJson.objectToJson(orderSkuList);
    }

    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        OrderAddressDTO o = GenerateAndJson.jsonToObject(this.snapAddress,
                new TypeReference<OrderAddressDTO>() {
                });
        return o;
    }

    public void setSnapAddress(OrderAddressDTO orderAddressDTO) {
        this.snapAddress = GenerateAndJson.objectToJson(orderAddressDTO);
    }
}
