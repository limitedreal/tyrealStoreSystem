package com.tianlb.myTest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianlb.myTest.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateAndJson {

    private static ObjectMapper mapper;

    //注意这种写法，为静态变量注入依赖

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenerateAndJson.mapper = mapper;
    }

    public static <T> String objectToJson(T o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();//这里我们先就不定义新异常了
            throw new ServerErrorException(9999);
            //最好不直接抛出Exception，不然调用栈就要层层处理这个异常，把强制处理的编译期Exception转换为RuntimeException
        }
    }

    //TypeReference传参的方法也是可以适用于这里的，所以下面的那个可以注释掉了
    public static <T> T jsonToObject(String s, TypeReference<T> tr) {
        if (s == null) {
            return null;
        }
        try {
            T o = GenerateAndJson.mapper.readValue(s, tr);
            return o;
        } catch (JsonProcessingException e) {//这里不建议用Exception的异常接收，太宽泛了
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    ////把List<S>中的S当做泛型，但是并没有在运行时推断出T的类型，而是将Spec转化成了HashMap，故废弃
    //public static <T> List<T> jsonToList(String s) {
    //    if (s == null) {
    //        return null;
    //    }
    //    try {
    //        List<T> list = GenerateAndJson.mapper.readValue(s, new TypeReference<List<T>>() {
    //        });
    //        return list;
    //    } catch (JsonProcessingException e) {
    //        e.printStackTrace();
    //        throw new ServerErrorException(9999);
    //    }
    //}

    //// 被上面的jsonToObject取代
    ////把List<S>整体当做泛型
    //public static <T> T jsonToList(String s, TypeReference<T> tr){
    //    if (s == null) {
    //        return null;
    //    }
    //    try {
    //        return GenerateAndJson.mapper.readValue(s, tr);
    //    } catch (JsonProcessingException e) {
    //        e.printStackTrace();
    //        throw new ServerErrorException(9999);
    //    }
    //}
}
