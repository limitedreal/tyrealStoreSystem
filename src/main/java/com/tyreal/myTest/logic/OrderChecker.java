package com.tyreal.myTest.logic;

import com.tyreal.myTest.DTO.OrderDTO;
import com.tyreal.myTest.DTO.SkuInfoDTO;
import com.tyreal.myTest.exception.http.ParameterException;
import com.tyreal.myTest.model.Sku;
import lombok.Value;

import java.util.List;

public class OrderChecker {
    // orderService->orderChecker->CouponChecker
    // 这么做的目的是为了让orderService和可能更改的CouponChecker分离
    // 这是设计模式中的facade外观模式

    // 1. 校验order的相关信息
    // 2. 校验Coupon相关信息(CouponChecker)
    // 3. 返回Order的一个模型，使得其写入数据库,即写入数据库的数据必须来源于orderChecker而不是前端

    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;

    //没被托管，那么就无法注入配置文件的数据了
    private int maxSkuLimit;

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
    }

    public void isOK(){
        //在couponChecker中校验的是finalTotalPrice
        //在这里要校验的是orderTotalPrice 和 serverTotalPrice
        //每一件商品的原价、现在价格、是否下架、是否售罄，订单sku数量是否超出库存，是否超过每件sku最大购买数量，优惠券校验，都需要校验
        //但是不能依赖在这里检测售罄，因为检测必须和减少库存同时进行，不然会产生并发问题
        //虽然这样，但是仍然在这里进行检测，以使前端在早期就能提示错误
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(),this.serverSkuList.size());
        for (int i = 0;i<this.serverSkuList.size();i++){
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            //超卖


        }
    }
    private void beyondSkuStock(Sku sku,SkuInfoDTO skuInfoDTO){
        //超卖
    }
    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO){
        if(skuInfoDTO.getCount() > this.maxSkuLimit){
            throw new ParameterException(50004);
        }
    }

    private void skuNotOnSale(int count1,int count2){
        //前端sku种类的量是不是和服务端查询出来的数量相等(因为有些找不到的sku(比如下架、下线等)就不会返回，所以我们判断数量)
        //list添加null是会增加size的，但是查出来如果是不存在的，不会写入null而是会跳过去，所以不用担心这个地方鉴别失效
        if(count1!=count2){
            throw new ParameterException(50002);
        }
    }
    private void containsSoldOutSku(Sku sku){
        //售罄
        if(sku.getStock() == 0){
            throw new ParameterException(50001);
        }
    }
}
