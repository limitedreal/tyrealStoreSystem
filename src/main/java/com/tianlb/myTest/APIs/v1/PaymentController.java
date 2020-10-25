package com.tianlb.myTest.APIs.v1;

import com.tianlb.myTest.core.interceptors.ScopeLevel;
import com.tianlb.myTest.lib.TyrealWXNotify;
import com.tianlb.myTest.service.WXPaymentNotifyService;
import com.tianlb.myTest.service.WXPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RequestMapping("payment")
@RestController
@Validated
public class PaymentController {

    @Autowired
    private WXPaymentService wxPaymentService;

    @Autowired
    private WXPaymentNotifyService wxPaymentNotifyService;

    //@Autowired
    //private WxPaymentNotifyService wxPaymentNotifyService;


    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String, String> preWxOrder(@PathVariable(name = "id") @Positive Long oid) {
        Map<String, String> miniPayParams = this.wxPaymentService.preOrder(oid);

        return miniPayParams;
        //return miniPayParams;
    }


    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request,
                              HttpServletResponse response) {
        //微信的数据需要我们使用原生的request和response进行接收和返回
        //他不是按照restful规范设计的，不是json数据

        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return TyrealWXNotify.fail();
        }
        String xml;
        xml = TyrealWXNotify.readNotify(s);
        try{
            this.wxPaymentNotifyService.processPayNotify(xml);
        }
        catch (Exception e){
            return TyrealWXNotify.fail();
        }
        return TyrealWXNotify.success();
    }
}
