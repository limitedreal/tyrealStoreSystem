package com.tianlb.myTest.repository;

import com.tianlb.myTest.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    //理想情况是{List,List,List}

    List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);

}
