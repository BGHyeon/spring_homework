package com.sparta.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Notice extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String makerName;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @Column
    private String contents;

    @Column
    private String title;

    @ManyToMany(targetEntity = Member.class,fetch = FetchType.LAZY)
    private List<Member> likedMembers = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    public List<Comment> getReverseComments(){
        List<Comment> copy = comments;
        Collections.reverse(copy);
        return copy;
    }
    public void addComment(Comment comment){
        this.comments.add(comment);
    }
    public void removeComment(Comment comment){this.comments.remove(comment);}
    public int getLikeCount(){return this.likedMembers.size();}
    public boolean toggleLike(Member m){
        boolean ret = false;
        boolean isContain = this.likedMembers.stream().filter(e->e.getId()==m.getId()).count() != 0;
        if(isContain) {
            likedMembers = likedMembers.stream().filter(e->e.getId() != m.getId()).collect(Collectors.toList());
        }else {
            this.likedMembers.add(m);
            ret = true;
        }
        return ret;
    }
    public boolean isContainMember(Member m){
        return this.likedMembers.stream().filter(e->e.getId() == m.getId()).count() == 1;
    }
}
