package com.tianlb.myTest.sample;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(TyrealConfigurationSelector.class)
public @interface EnableTyrealConfiguration {

}
