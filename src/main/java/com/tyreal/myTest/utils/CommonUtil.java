package com.tyreal.myTest.utils;

import com.tyreal.myTest.BO.PageCounter;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start , Integer count) {
        int pageNum = start/count;
        //TODO：这里会不会出现前端数据不规范导致这个结果有余数的情况呢？
        PageCounter pageCounter =
                PageCounter.builder()
                .page(pageNum)
                .count(count)
                .build();
        return pageCounter;
    }
}
