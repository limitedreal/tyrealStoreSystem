package com.tianlb.myTest.sample.hero;

import com.tianlb.myTest.sample.ISkill;

public class Irelia implements ISkill {
    public Irelia() {
        System.out.println("Irelia");
    }

    @Override
    public void q() {
        System.out.println("Irelia q");
    }

    @Override
    public void w() {
        System.out.println("Irelia w");
    }

    @Override
    public void e() {
        System.out.println("Irelia e");
    }

    @Override
    public void r() {
        System.out.println("Irelia r");
    }
}
