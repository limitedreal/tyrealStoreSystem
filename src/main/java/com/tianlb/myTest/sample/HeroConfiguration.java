package com.tianlb.myTest.sample;

import com.tianlb.myTest.sample.hero.Camille;
import com.tianlb.myTest.sample.hero.Diana;
import com.tianlb.myTest.sample.hero.Irelia;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfiguration {

    @Bean
    @ConditionalOnProperty(value="hero.condition",havingValue = "diana",matchIfMissing = true)
    //matchIfMissing是指如果没有在配置文件中找到对应配置项，则使用此bean，并不是说条件都不成立的default项
    public ISkill diana() {
        return new Diana();
    }

    @Bean
    @ConditionalOnProperty(value="hero.condition",havingValue = "irelia")
    //@Conditional(IreliaCondition.class)
    public ISkill irelia() {
        return new Irelia();
    }

    @Bean
    @ConditionalOnProperty(value="hero.condition",havingValue = "camille")
    public ISkill camille() {
        return new Camille();
    }
}
