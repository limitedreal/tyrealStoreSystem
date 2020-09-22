package com.tyreal.myTest.sample.hero;

import com.tyreal.myTest.sample.ISkill;
import org.springframework.stereotype.Component;

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
