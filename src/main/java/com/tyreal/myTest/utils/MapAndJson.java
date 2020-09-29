package com.tyreal.myTest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyreal.myTest.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

//不要忘记打上这个注解
@Converter
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {
//这里的泛型指定两个泛型，也就是是待转换的两个参数，第一个是要转换到的Java实体的类型，第二个是其在数据库中的类型

    //mapper的参数可以在配置文件里进行配置

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        //序列化
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();//这里我们先就不定义新异常了
            throw new ServerErrorException(9999);
            //最好不直接抛出Exception，不然调用栈就要层层处理这个异常，把强制处理的编译期Exception转换为RuntimeException
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        if(s==null){
            return null;
        }
        try {
            //这里会发生有的sku数据不完整，这个该有json数据的字段没有数据，这是数据源的问题，导致这里可能为null，恰恰这个null的错误不是JsonProcessingException，无法被捕获到
            Map<String, Object> map = mapper.readValue(s, HashMap.class);
            return map;
            //第二个参数是要反序列化到的类型的元类，这里不能写Map，Map是接口，要写实实在在的类
        } catch (JsonProcessingException e) {//这里不建议用Exception的异常接收，太宽泛了
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
