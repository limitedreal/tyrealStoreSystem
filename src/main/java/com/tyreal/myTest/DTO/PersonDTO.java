package com.tyreal.myTest.DTO;

import lombok.*;

// 数据传输对象

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
@Builder
public class PersonDTO {
    @NonNull
    private String name;
    private int age;
}
