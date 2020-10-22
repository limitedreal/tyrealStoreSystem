package com.tianlb.myTest.service;

import com.tianlb.myTest.model.Spu;
import com.tianlb.myTest.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<Spu> getByCategoryId(Long cid,Boolean isRoot,Integer pageNum,Integer size){
        Pageable pageable = PageRequest.of(pageNum,size);
        Page<Spu> spuPage;
        if(isRoot){
            spuPage = this.spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(cid,pageable);
        }else {
            spuPage = this.spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid,pageable);
        }
        return spuPage;
    }
}
