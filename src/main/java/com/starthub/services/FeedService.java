package com.starthub.services;

import com.starthub.messages.FeedMessage;
import com.starthub.models.Feed;
import com.starthub.models.Rank;
import com.starthub.repositories.FeedRepository;
import com.starthub.repositories.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by Harrison on 04/03/2018.
 */

@Service
public class FeedService extends AbstractService<Feed, Long> {

    private FeedRepository repository;
    @Autowired
    private RankRepository rankRepository;

    public FeedService(@Autowired FeedRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Page<Feed> findAll(int pageNumber, int pageSize) {
        return repository.findAllByOrderByUpdatedAtDesc(new PageRequest(pageNumber, pageSize));
    }

    public Page<Feed> findByCreatedBy(String username, int pageNumber, int pageSize) {
        return repository.findAllByCreatedBy(username, new PageRequest(pageNumber, pageSize));
    }

    public Feed vote(FeedMessage message) throws Exception {
        if (message.isUpVote()) upVote(message.getFeedId());
        else downVote(message.getFeedId());
        return super.findOne(message.getFeedId());
    }

    private int upVote(long feedId) {
        return this.repository.upVote(feedId);
    }

    private int downVote(long feedId) {
        return this.repository.downVote(feedId);
    }

    public Rank saveRank(Rank rank) {
        return this.rankRepository.save(rank);
    }

    public Page<Feed> findFeedOrderByRank(int pageNumber, int pageSize) {
        return repository.findAllOrderByRank(new PageRequest(pageNumber, pageSize));
    }
}
