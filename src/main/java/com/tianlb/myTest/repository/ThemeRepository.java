package com.tianlb.myTest.repository;

import com.tianlb.myTest.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository  extends JpaRepository<Theme,Long> {
    //JPQL JavaPersistence Query Languag
    //利用@Param使得参数能够代入jpql里面，但是如果参数名是相同的话是可以省略的

    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findByNames(@Param("names") List<String> names);

    Optional<Theme> findByName(String name);
}
