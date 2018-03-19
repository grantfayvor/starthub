package com.starthub.models;

import javax.persistence.*;

/**
 * Created by Harrison on 04/03/2018.
 */

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
