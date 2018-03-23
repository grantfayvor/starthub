package com.starthub.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Harrison on 03/03/2018.
 */

@Entity
@Table(name = "ideas")
public class Idea extends Auditable<String>{

    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = "longtext not null")
    private String body;
    @Column(insertable = false, updatable = false)
    @OneToMany
    private List<Tag> tags;

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

    @Override
    public String toString(){
        return "body= " + body + " & tags= " + tags.toString() + " & tagsSize= " + tags.size();
    }
}
