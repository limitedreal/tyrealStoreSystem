package com.tyreal.myTest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spec {//此model并不是实体，而是试图接收json字符串构造的model，不打entity
    private Long keyId;
    private String key;
    private Long valueId;
    private String value;
}
