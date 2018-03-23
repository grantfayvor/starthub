package com.starthub.services;

import com.starthub.models.Idea;
import com.starthub.models.IdeaTags;
import com.starthub.models.Tag;
import com.starthub.repositories.IdeaRepository;
import com.starthub.repositories.IdeaTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Harrison on 03/03/2018.
 */

@Service
public class IdeaService extends AbstractService<Idea, Long> {

    @Autowired
    private IdeaRepository repository;
    @Autowired
    private TagService tagService;
    @Autowired
    private IdeaTagsService ideaTagsService;

    public IdeaService(IdeaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Idea save(Idea idea) {
        System.out.println(idea);
        try {
            List<Tag> tags = idea.getTags();
            idea = repository.save(idea);
            for(Tag tag : tags) {
                tag = tagService.findByName(tag.getName());
                if(tag == null) tag = tagService.save(tag); // this should save or update
                ideaTagsService.save(new IdeaTags(idea, tag));
            }
            return idea;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

//    @Override
//    public Idea findOne(Long id) throws Exception {
//        Idea idea = super.findOne(id);
//        idea.setTags(tagService.findByIdea(idea.getId()));
//        return idea;
//    }
}
