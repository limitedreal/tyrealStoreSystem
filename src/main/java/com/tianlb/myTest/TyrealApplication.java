package com.tianlb.myTest;

import com.tianlb.myTest.sample.EnableTyrealConfiguration;
import com.tianlb.myTest.sample.ISkill;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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
