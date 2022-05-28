package com.sparta.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment extends Timestamp {
    public Comment(){

    }
    public Comment(String comment){
        this.comment = comment;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column
    private String comment;

    public String calculateTime(){
        Duration d = Duration.between(getCreateTime(),LocalDateTime.now());
        if(d.toDaysPart() != 0)
            return d.toDaysPart()+"일";
        else if(d.toHoursPart() != 0)
            return d.toHoursPart()+"시간";
        else if(d.toMinutesPart() != 0)
            return d.toMinutesPart()+"분";
        return "방금";
    }
}
