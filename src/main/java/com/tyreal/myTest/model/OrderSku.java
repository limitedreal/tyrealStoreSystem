package com.tyreal.myTest.model;

import com.tyreal.myTest.DTO.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderSku {
    private Long id;
    private Long spuId;
    private BigDecimal finalPrice;
    //singlePrice*数量

    private BigDecimal singlePrice;
    //一个sku的单价

    private List<String> specValues;
    //sku的规格值

    private Integer count;
    private String img;
    private String title;

    public OrderSku(Sku sku,SkuInfoDTO skuInfoDTO) {
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.singlePrice = sku.getActualPrice();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
        this.count = skuInfoDTO.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValueList();
    }
}
