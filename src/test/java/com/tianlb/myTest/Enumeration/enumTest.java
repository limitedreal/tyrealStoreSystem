package com.tianlb.myTest.Enumeration;

import com.tianlb.myTest.model.Sku;
import com.tianlb.myTest.model.User;
import com.tianlb.myTest.repository.SkuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class enumTest {

    @Autowired
    SkuRepository skuRepository;
    @Test
    public void test(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(55L);
        List<Sku> skulist = skuRepository.findAllByIdIn(list);
        System.out.println(skulist);
    }

    public Optional<User> testEnum(){
        return null;
    }
}
