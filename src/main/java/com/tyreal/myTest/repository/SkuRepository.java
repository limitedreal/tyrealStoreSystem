package com.tyreal.myTest.repository;

import com.tyreal.myTest.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku,Long> {
    List<Sku> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query("update Sku \n" +
            "s set s.stock = s.stock - :quantity\n" +
            "where s.id=:sid and s.stock >=:quantity\n")
    int reduceStock(Long sid,Long quantity);
    //利用了sql语句操作的原子性
    //这个返回值会返回受影响的行数

    //@Query("select stock,version from Sku s\n" +
    //        "where true ;\n" +
    //        "\n" +
    //        "update sku set s.stock = newValue,version=version+1\n" +
    //        "where version=x;\n")
    //int test(Long sid,Long quantity);
    //乐观锁就是利用这种修改次数version在不加锁的情况下检测是否出现了并发的问题
    //也就是乐观锁里面并没有真正的锁

}
