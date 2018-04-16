package com.starthub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Created by Harrison on 03/03/2018.
 */

@Entity
@Table(name = "feeds")
public class Feed extends Auditable<String> {

    @Id
    @GeneratedValue
    private long id;
    @JoinColumn
    @OneToOne
    private Idea idea;
    @Column
    @ColumnDefault("0")
    private int numberOfViewers;
    @Column
    @OneToMany
    @JsonIgnore
    @Null
    private List<User> viewers;
    @JoinColumn
    @OneToOne
    private Rank rank;
    @Column
    @ColumnDefault("0")
    private int upVote;
    @Column
    @ColumnDefault("0")
    private int downVote;

    public Feed() {
    }

    public Feed(Idea idea) {
        this.idea = idea;
    }

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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
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

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", idea=" + idea +
                ", numberOfViewers=" + numberOfViewers +
                ", viewers=" + viewers +
                ", rank=" + rank +
                ", upVote=" + upVote +
                ", downVote=" + downVote +
                '}';
    }
}
