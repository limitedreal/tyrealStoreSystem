package com.tianlb.myTest.sample;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class TyrealConfigurationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{HeroConfiguration.class.getName(),DatabaseConfiguration.class.getName()};
    }
}
