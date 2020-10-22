package com.tianlb.myTest.lib;

import com.tianlb.myTest.exception.http.ServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TyrealWXNotify {

    public static String readNotify(InputStream s) {
        //流处理函数
        BufferedReader reader = new BufferedReader(new InputStreamReader(s));
        //这里也涉及不到什么线程问题，用builder
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServerErrorException(9999);
            }
        }
        String xml = builder.toString();
        return xml;

    }

    public static String fail() {
        return "fail";//对于失败的消息没有要求
    }

    public static String success() {
        //微信认定的成功消息的格式
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
