package com.starthub.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

/**
 * Created by Harrison on 03/03/2018.
 */

@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String body;
    @Column
    @OneToMany
    @Null
    private List<Tag> tags;
    @Column
    @DateTimeFormat
    private Date createdAt;
    @Column
    @DateTimeFormat
    private Date updatedAt;

    public Idea () {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
        this.updatedAt = new Date();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

}
