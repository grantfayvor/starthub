package com.starthub.repositories;

import com.starthub.models.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Harrison on 04/03/2018.
 */

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query("UPDATE Feed f SET f.upVote = f.upVote + 1 WHERE f.id=?1")
    @Transactional
    @Modifying
    int upVote(long feedId);

    @Query("UPDATE Feed f SET f.downVote = f.downVote + 1 WHERE f.id=?1")
    @Transactional
    @Modifying
    int downVote(long feedId);
}
