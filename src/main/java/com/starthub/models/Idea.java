package com.starthub.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Harrison on 03/03/2018.
 */

@Entity
@Table(name = "ideas")
public class Idea{

    @Id
    @GeneratedValue
    private long id;
    @Column(columnDefinition = "longtext not null")
    private String body;
    @Transient
    private MultipartFile document;
    @Column
    private String documentLocation;
    @Column(insertable = false, updatable = false)
    @ManyToMany(fetch = FetchType.EAGER)
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

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString(){
        return "body= " + body + " & tags= " + tags.toString() + " & tagsSize= " + tags.size() + " tags [\n" + tags + "\n ]";
    }
}
