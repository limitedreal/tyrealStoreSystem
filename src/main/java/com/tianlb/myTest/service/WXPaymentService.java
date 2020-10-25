package com.tianlb.myTest.service;

import com.github.wxpay.sdk.TyrealWxPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.tianlb.myTest.core.LocalUser;
import com.tianlb.myTest.exception.http.ForbiddenException;
import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.exception.http.ParameterException;
import com.tianlb.myTest.exception.http.ServerErrorException;
import com.tianlb.myTest.utils.CommonUtil;
import com.tianlb.myTest.model.Order;
import com.tianlb.myTest.repository.OrderRepository;
import com.tianlb.myTest.utils.HttpRequestProxy;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@ComponentScan({"com.github.wxpay.sdk", "com.tyreal.myTest"})
public class WXPaymentService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TyrealWxPayConfig wxPayConfig;
    //= new TyrealWxPayConfig();

    @Value("${wx.pay-callback-host}")
    private String payCallbackHost;

    @Value("${wx.pay-callback-path}")
    private String payCallbackPath;

    @Value("${wx.appid}")
    private String appId;

    @Autowired
    private OrderRepository orderRepository;

    public Map<String, String> preOrder(Long oid) {
        Long uid = LocalUser.getUser().getId();
        Optional<Order> orderOptional = orderRepository.findFirstByUserIdAndId(uid, oid);
        Order order = orderOptional.orElseThrow(() -> new NotFoundException(50009));
        if (order.needCancel()) {
            throw new ForbiddenException(50010);
        }
        WXPay wxPay = this.assembleWxPayConfig();
        Map<String, String> params = this.makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo());
        Map<String, String> wxOrder;
        try {
            wxOrder = wxPay.unifiedOrder(params);
        } catch (Exception e) {
            throw new ServerErrorException(9999);
        }
        if (this.unifiedOrderSuccess(wxOrder)) {
            //更新prepayId的
            this.orderService.updateOrderPrepayId(order.getId(), wxOrder.get("prepay_id"));
        }

        //把最终参数返回小程序
        return this.makePaySignature(wxOrder);
    }

    private Map<String ,String > makePaySignature(Map<String ,String > wxOrder) {
        //签名，构建用于返回小程序中的数据
        Map<String ,String > wxPayMap = new HashMap<>();

        String packages = "prepay_id="+wxOrder.get("prepay_id");

        wxPayMap.put("appId", this.wxPayConfig.getAppID());//但是返回小程序的时候是不包含appId的，最后还要删除此字段
        wxPayMap.put("timeStamp", CommonUtil.timestamp10());//这里的时间戳要求10位，而不是默认的13位的，所以进行了转换
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));//32位随机字符串
        wxPayMap.put("package", packages);//统一下单的pre_id
        wxPayMap.put("signType", "HMAC-SHA256");//签名算法

        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap ,this.wxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);//最后的是加密算法的枚举
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        Map<String,String > miniPayParams = new HashMap<>();
        miniPayParams.put("sign",sign);
        miniPayParams.putAll(wxPayMap);
        miniPayParams.remove("appId");

        return miniPayParams;
    }


    private boolean unifiedOrderSuccess(Map<String, String> wxOrder) {
        if (!wxOrder.get("return_code").equals("SUCCESS")
                || !wxOrder.get("result_code").equals("SUCCESS")) {
            throw new ParameterException(10007);
        }
        return true;
    }

    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice,String orderNo) {
        //组装微信支付preOrder的相关数据
        String payCallbackUrl=this.payCallbackHost+this.payCallbackPath;
        Map<String, String> data = new HashMap<>();
        data.put("body", "Sleeve");//商品名称
        data.put("out_trade_no", orderNo);//我们定义的orderId
        data.put("device_info", "Sleeve");//自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        data.put("fee_type", "CNY");//货币单位
        data.put("trade_type", "JSAPI");//JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付，不同trade_type决定了调起支付的方式，请根据支付产品正确上传

        data.put("total_fee", CommonUtil.yuanToFenPlainString(serverFinalPrice));//微信接收的价格要以分为单位，但是我们的是以元为单位的
        data.put("openid", LocalUser.getUser().getOpenid());//openId
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());//用户的ip地址

        data.put("notify_url", payCallbackUrl);//微信服务器调用我们api以通知支付结果

        return data;
    }

    WXPay wxPay = null;

    public WXPay assembleWxPayConfig() {
        if(wxPay!=null){
            return wxPay;
        }
        try {
            wxPay = new WXPay(this.wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }

}
