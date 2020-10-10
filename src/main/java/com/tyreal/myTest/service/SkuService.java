package com.tyreal.myTest.service;

import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.model.Sku;
import com.tyreal.myTest.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuService {

    @Autowired
    SkuRepository skuRepository;

    public List<Sku> getSkuByIds(List<Long> ids){
        List<Sku> skuList = skuRepository.findAllByIdIn(ids);
        if(skuList.size()!=ids.size()){
            throw new NotFoundException(50001);
        }
        return skuList;
    }
}
