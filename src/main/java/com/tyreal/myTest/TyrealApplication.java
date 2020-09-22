package com.tyreal.myTest;

import com.tyreal.myTest.sample.EnableTyrealConfiguration;
import com.tyreal.myTest.sample.HeroConfiguration;
import com.tyreal.myTest.sample.ISkill;
import com.tyreal.myTest.sample.TyrealConfigurationSelector;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//@ComponentScan
@EnableTyrealConfiguration
public class TyrealApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(TyrealApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);
        ISkill iSkill =(ISkill)context.getBean("irelia");
        iSkill.r();
    }
}
