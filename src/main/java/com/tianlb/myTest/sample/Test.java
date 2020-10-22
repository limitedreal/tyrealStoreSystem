package com.tianlb.myTest.sample;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Test {
    private String name="tlb";
}
