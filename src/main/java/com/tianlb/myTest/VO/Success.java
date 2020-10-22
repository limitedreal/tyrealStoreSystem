package com.tianlb.myTest.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Success {
    private Integer code = 0;
    private Map data = new HashMap();
    private String msg = "成功";
    private Boolean result = true;

    public Success() {
    }

}
