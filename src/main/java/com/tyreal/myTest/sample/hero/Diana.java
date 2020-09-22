package com.tyreal.myTest.sample.hero;

import com.tyreal.myTest.sample.ISkill;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

public class Diana implements ISkill {
    public Diana() {
        System.out.println("Diana");
    }

    @Override
    public void q(){
        System.out.println("Diana q");
    }
    @Override
    public void w(){
        System.out.println("Diana w");
    }
    @Override
    public void e(){
        System.out.println("Diana e");
    }
    @Override
    public void r(){
        System.out.println("Diana r");
    }

}
