package com.sparta.entitys;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
public class Member extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nickName;

    @Column
    private String name;

    @Column(unique = true)
    private String loginId;

    @Column
    private String loginPw;

    public boolean build(){
        return (nickName.matches("^[a-zA-Z0-9]{3,}$") && !loginPw.contains(nickName) && loginPw.length() > 3);
    }
}
