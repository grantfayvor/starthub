package com.starthub.services;

import com.starthub.models.Feed;
import com.starthub.repositories.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Harrison on 04/03/2018.
 */

@Service
public class FeedService extends AbstractService<Feed, Long> {

    private FeedRepository repository;

    public FeedService(@Autowired FeedRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Feed vote(boolean upVote, long feedId) throws Exception {
        if (upVote == true) upVote(feedId);
        else downVote(feedId);
        Feed feed = super.findOne(feedId);
//        System.out.println("the current feed is " + feed.toString());
        return feed;
    }

    private int upVote(long feedId) {
        return this.repository.upVote(feedId);
    }

    private int downVote(long feedId) {
        return this.repository.downVote(feedId);
    }
}
