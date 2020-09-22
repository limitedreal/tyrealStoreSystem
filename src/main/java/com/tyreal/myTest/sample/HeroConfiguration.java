package com.tyreal.myTest.sample;

import com.tyreal.myTest.sample.condition.DianaCondition;
import com.tyreal.myTest.sample.condition.IreliaCondition;
import com.tyreal.myTest.sample.hero.Camille;
import com.tyreal.myTest.sample.hero.Diana;
import com.tyreal.myTest.sample.hero.Irelia;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
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
