package com.tyreal.myTest.BO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PageCounter {
    private Integer page;
    private Integer count;
}
