package com.tianlb.myTest.service;

import com.tianlb.myTest.repository.SkuRepository;
import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.model.Sku;
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
