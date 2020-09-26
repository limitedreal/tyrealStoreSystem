package com.tyreal.myTest.service;

import com.tyreal.myTest.model.Spu;
import com.tyreal.myTest.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuService {
    @Autowired
    SpuRepository spuRepository;

    public Spu getSpu(Long id){
        return this.spuRepository.findOneById(id);
    }
    public List<Spu> getLatestPagingSpu(){
        return this.spuRepository.findAll();
    }
}
