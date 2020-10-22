package com.tianlb.myTest.model;

import com.tianlb.myTest.utils.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Integer unifyUid;
    private String email;
    private String password;
    //密码这种私密信息，其实宜再建一张专门处理安全信息的表进行处理
    private String mobile;
    //private String group;
    // 如果需要会员系统，则应该再来一个会员分级的标识

    @Convert(converter = MapAndJson.class)
    private Map<String ,Objects> wxProfile;

}
