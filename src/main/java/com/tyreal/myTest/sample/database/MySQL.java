package com.tyreal.myTest.sample.database;

import com.tyreal.myTest.sample.IConnect;

public class MySQL implements IConnect {
    private  String ip="localhost";
    private Integer port=3306;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public MySQL(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        System.out.println("MySQL启动");
    }

    public MySQL() {

    }
    @Override
    public void connect() {
        System.out.println(this.ip+" | "+ this.port);
    }
}
