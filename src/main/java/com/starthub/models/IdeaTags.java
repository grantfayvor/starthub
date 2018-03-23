package com.starthub.models;

import javax.persistence.*;

/**
 * Created by Harrison on 3/21/2018.
 */

@Entity
@Table(name = "ideas_tags")
public class IdeaTags {

    @Id
    @GeneratedValue
    private long id;
    @JoinColumn(name = "idea_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Idea idea;
    @JoinColumn(name = "tags_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Tag tag;

    public IdeaTags() {

    }

    public IdeaTags(Idea idea, Tag tag) {
        this.idea = idea;
        this.tag = tag;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
