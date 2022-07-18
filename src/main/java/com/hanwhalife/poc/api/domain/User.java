package com.hanwhalife.poc.api.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String username;

    @Column(length = 1)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Married married;

    @Embedded
    private Age age;

    @Enumerated(EnumType.STRING)
    private JobGroup jobGroup;

    @Enumerated(EnumType.STRING)
    private JobPosition jobPosition;

    @Embedded
    private Address address;

    //private Point point;

    //private Set<Authority> authorities = new HashSet<>();
}
