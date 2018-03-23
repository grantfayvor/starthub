package com.starthub.services;

import com.starthub.models.Tag;
import com.starthub.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Harrison on 3/20/2018.
 */

@Service
public class TagService extends AbstractService<Tag, Long>{

    @Autowired
    private TagRepository tagRepository;

    public TagService(TagRepository repository) {
        super(repository);
        this.tagRepository = repository;
    }

    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

//    public List<Tag> findByIdea(long ideaId) {
//        return tagRepository.findByIdea(ideaId);
//    }
}
