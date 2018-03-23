package com.starthub.services;

import com.starthub.models.IdeaTags;
import com.starthub.repositories.IdeaTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Harrison on 3/22/2018.
 */

@Service
public class IdeaTagsService extends AbstractService<IdeaTags, Long> {

    @Autowired
    private IdeaTagsRepository repository;

    public IdeaTagsService(IdeaTagsRepository repository) {
        super(repository);
    }
}
