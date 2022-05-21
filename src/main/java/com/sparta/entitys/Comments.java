package com.sparta.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comments extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String makerName;

    @Column(updatable = false)
    private String password;

    @Column
    private String contents;

    @Column
    private String title;
}
