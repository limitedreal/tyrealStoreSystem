package com.tianlb.myTest.repository;

import com.tianlb.myTest.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Long> {

    Banner findOneById(Long id);
    Banner findOneByName(String name);
}
