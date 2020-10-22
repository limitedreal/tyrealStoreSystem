package com.tianlb.myTest.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tianlb.myTest.utils.GenerateAndJson;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Where(clause = "delete_time is null and online = 1")
public class Sku extends BaseEntity {
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private Long categoryId;
    private Long rootCategoryId;

    //code为唯一标识，虽然也可以用id，但是code有更强的解析性
    private String code;

    //@Convert(converter = ListAndJson.class)
    //private List<Object> specs;
    private String specs;
    private Long stock;
    //库存量还有单位这个概念，如果要做可以再做一张表

    public List<String> getSpecValueList(){
        return this.getSpecs()
                .stream()
                .map(Spec::getValue)
                .collect(Collectors.toList());
    }

    public List<Spec> getSpecs() {
        if (this.specs == null) {
            return null;
        }
        return GenerateAndJson.jsonToObject(this.specs, new TypeReference<List<Spec>>() {
        });
    }

    public BigDecimal getActualPrice(){
        return discountPrice==null?this.price:this.discountPrice;
    }

    public void setSpecs(List<Spec> specs) {
        if (specs.isEmpty()) {
            return;
        }
        GenerateAndJson.objectToJson(specs);
    }


    //单体映射成map但是数组应该映射成list
    //@Convert(converter = MapAndJson.class)
    //private Map<String ,Object> test;
    ////测试学习用的字段，配置好之后标红也没关系的


    // 可以使用重设get和set方法一个一个分别处理
    //public List<Spec> getSpecs() {
    //    String specs = this.specs;
    ////    反序列化，然后返回
    //    return null;
    //}
    //public void setSpecs(List<Spec> specs) {
    //}
}
