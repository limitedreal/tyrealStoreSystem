package com.tianlb.myTest.sample;

import com.tianlb.myTest.sample.database.MySQL;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {
    //@Value("${mysql.ip}")
    private String ip;

    //@Value("${mysql.port}")
    private Integer port;
    public IConnect mysql(){
        return new MySQL(this.ip,this.port);
    }
}
