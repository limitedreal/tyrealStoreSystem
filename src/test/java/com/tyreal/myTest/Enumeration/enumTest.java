package com.tyreal.myTest.Enumeration;

import com.tyreal.myTest.core.enumeration.LoginType;
import com.tyreal.myTest.model.Sku;
import com.tyreal.myTest.model.User;
import com.tyreal.myTest.repository.SkuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
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
