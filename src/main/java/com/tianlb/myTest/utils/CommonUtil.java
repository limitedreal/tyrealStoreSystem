package com.tianlb.myTest.utils;

import com.tianlb.myTest.BO.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public static String yuanToFenPlainString(BigDecimal p) {
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }
    public static String timestamp10(){
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }

    public static String toPlain(BigDecimal p) {
        return p.stripTrailingZeros().toPlainString();
    }

    public static Boolean isOutOfDate(Date expiredTime) {
        long now = Calendar.getInstance().getTimeInMillis();
        long expiredTimeStamp = expiredTime.getTime();
        return now > expiredTimeStamp;
    }

    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        //TODO：这里会不会出现前端数据不规范导致这个结果有余数的情况呢？
        PageCounter pageCounter =
                PageCounter.builder()
                        .page(pageNum)
                        .count(count)
                        .build();
        return pageCounter;
    }

    public static Boolean isInTimeLine(Date date, Date start, Date end) {
        Long time = date.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        return time >= startTime && time < endTime;
    }
}
