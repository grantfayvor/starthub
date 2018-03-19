package com.starthub.models;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Created by Harrison on 03/03/2018.
 */

@Entity
@Table(name = "feeds")
public class Feed {

    @Id
    @GeneratedValue
    private long id;
    @JoinColumn
    @OneToOne
    private Idea idea;
    @Column
    private int numberOfViewers;
    @Column
    @OneToMany
    @Null
    private List<User> viewers;
    @Column
    private double rank;
    @Column
    private int upVote;
    @Column
    private int downVote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public int getNumberOfViewers() {
        return numberOfViewers;
    }

    public void setNumberOfViewers(int numberOfViewers) {
        this.numberOfViewers = numberOfViewers;
    }

    public List<User> getViewers() {
        return viewers;
    }

    public void setViewers(List<User> viewers) {
        this.viewers = viewers;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
    }
}
