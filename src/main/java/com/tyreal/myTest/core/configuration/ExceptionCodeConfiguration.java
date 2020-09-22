package com.tyreal.myTest.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tyreal")
@PropertySource(value="classpath:config/exceptionCode.properties")
public class ExceptionCodeConfiguration {

    private Map<Integer,String> codes = new HashMap<>();

    public String getMessage(int code){
        return codes.get(code);
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public Map<Integer, String> getCodes() {
        return codes;
    }
}
