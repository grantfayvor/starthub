package com.starthub.services;

import com.starthub.models.Feed;
import com.starthub.repositories.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Harrison on 04/03/2018.
 */

@Service
public class FeedService extends AbstractService<Feed, Long> {

    @Autowired
    private FeedRepository repository;

    public FeedService(FeedRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
