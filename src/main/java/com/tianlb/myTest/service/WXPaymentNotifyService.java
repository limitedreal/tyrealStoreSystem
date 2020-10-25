package com.tianlb.myTest.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.tianlb.myTest.core.enumeration.OrderStatus;
import com.tianlb.myTest.exception.http.ServerErrorException;
import com.tianlb.myTest.model.Order;
import com.tianlb.myTest.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WXPaymentNotifyService {

    @Autowired
    private WXPaymentService wxPaymentService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void processPayNotify(String xml){
        //微信的通知回调是不间断的过程，所以要防止重复的访问造成的可能问题
        Map<String ,String > data;
        try {
            data = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        WXPay wxPay = wxPaymentService.assembleWxPayConfig();
        boolean valid = false;
        try {
            valid = wxPay.isResponseSignatureValid(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        if(!valid){
            throw new ServerErrorException(9999);
        }
        String returnCode = data.get("return_code");
        String orderNo = data.get("out_trade_no");
        String resultCode = data.get("result_code");
        if(!returnCode.equals("SUCCESS")){
            throw new ServerErrorException(9999);
        }
        if(!resultCode.equals("SUCCESS")){
            throw new ServerErrorException(9999);
        }
        if (orderNo==null){
            throw new ServerErrorException(9999);
        }
        this.deal(orderNo);
    }

    private void deal(String orderNo){
        Optional<Order> orderOptional = orderRepository.findFirstByOrderNo(orderNo);
        Order order = orderOptional.orElseThrow(()-> new ServerErrorException(9999));
        //unpaid->paid
        //库存不需要处理(因为先行处理了)
        //这里要考虑什么样的状态呢？当然可能是UNPAID，但是也可能会有一点点可能性是CANCELED状态的
        //也就是说马上要过期前开始支付，此时延迟消息队列可能已经使得订单状态改变为CANCELED了
        int res = 0;
        if(order.getStatus().equals(OrderStatus.UNPAID.value())
        ||order.getStatus().equals(OrderStatus.CANCELED.value())){
            res = orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
        }
        //JPQL中无论这个数据是不是真的改变了，也就是改成了一个原值，也会返回说有记录收到影响
        if(res!=1){
            throw new ServerErrorException(9999);
        }
    }

}
