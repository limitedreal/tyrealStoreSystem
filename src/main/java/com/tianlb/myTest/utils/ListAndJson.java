package com.tianlb.myTest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianlb.myTest.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class ListAndJson implements AttributeConverter<List<Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            return mapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();//这里我们先就不定义新异常了
            throw new ServerErrorException(9999);
            //最好不直接抛出Exception，不然调用栈就要层层处理这个异常，把强制处理的编译期Exception转换为RuntimeException
        }
    }

    @Override
    public List<Object> convertToEntityAttribute(String s) {
        if(s==null){
            return null;
        }
        try {
            //这里会发生有的sku数据不完整，这个该有json数据的字段没有数据，这是数据源的问题，导致这里可能为null，恰恰这个null的错误不是JsonProcessingException，无法被捕获到
            List<Object> list = mapper.readValue(s, List.class);
            return list;
            //第二个参数是要反序列化到的类型的元类，这里不能写Map，Map是接口，要写实实在在的类
        } catch (JsonProcessingException e) {//这里不建议用Exception的异常接收，太宽泛了
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
