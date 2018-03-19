package com.starthub.services;

import com.starthub.models.Idea;
import com.starthub.repositories.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Harrison on 03/03/2018.
 */

@Service
public class IdeaService extends AbstractService<Idea, Long> {

    @Autowired
    private IdeaRepository repository;

    public IdeaService(IdeaRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
