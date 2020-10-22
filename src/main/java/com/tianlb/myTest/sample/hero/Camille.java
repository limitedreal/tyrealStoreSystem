package com.tianlb.myTest.sample.hero;

import com.tianlb.myTest.sample.ISkill;

public class Camille implements ISkill {
    private String name = "Camille";
    private Integer age;
    private String skillName="Camille R";

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Camille(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Camille() {
        System.out.println("Camille");
    }

    @Override
    public void q() {
        System.out.println("Camille q");
    }

    @Override
    public void w() {
        System.out.println("Camille w");
    }

    @Override
    public void e() {
        System.out.println("Camille e");
    }

    @Override
    public void r() {
        System.out.println("Camille r");
    }
}
