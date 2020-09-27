package com.tyreal.myTest.service;

import com.tyreal.myTest.model.Spu;
import com.tyreal.myTest.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuService {
    @Autowired
    SpuRepository spuRepository;

    public Spu getSpu(Long id){
        return this.spuRepository.findOneById(id);
    }
    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer size){
        //还没有真正查询数据库呢
        Pageable page = PageRequest.of(pageNum,size, Sort.by("createTime").descending());
        return this.spuRepository.findAll(page);
    }
}
