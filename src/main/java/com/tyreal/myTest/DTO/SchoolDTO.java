package com.tyreal.myTest.DTO;

import org.hibernate.validator.constraints.Length;

public class SchoolDTO {
    @Length(min = 1)
    String name;
}
