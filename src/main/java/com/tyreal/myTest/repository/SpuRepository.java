package com.tyreal.myTest.repository;

import com.tyreal.myTest.model.Spu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpuRepository extends JpaRepository<Spu,Long> {
    Spu findOneById(Long id);
}
