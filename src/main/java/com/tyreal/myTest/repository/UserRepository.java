package com.tyreal.myTest.repository;

import com.tyreal.myTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);//因为这里是jdk8，jdk8的optional还不太适合我们的业务逻辑，于是弃用
    Optional<User> findByOpenid(String openid);
    User findFirstById(Long id);
    User findByUnifyUid(String unifyUid);
}
