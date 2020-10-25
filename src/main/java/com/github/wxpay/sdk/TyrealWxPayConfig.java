package com.github.wxpay.sdk;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@NoArgsConstructor
public class TyrealWxPayConfig extends WXPayConfig {

    @Value("${wx.appid}")
    private String AppID;
    //="wx0dc4063b0624495e";

    @Value("${wx.MchID}")
    private String MchID;
    //="abc";

    @Value("${wx.MchKey}")
    private String MchKey;
    //="def";

    @Override
    public String getAppID() {
        //appid
        return this.AppID;
    }

    @Override
    public String getMchID() {
        //商户号
        return this.MchID;
    }

    @Override
    public String getKey() {
        //商户平台的mch key
        return this.MchKey;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
    
    @Override
    InputStream getCertStream() {
        //证书流，我们没有用到微信的证书
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

}
