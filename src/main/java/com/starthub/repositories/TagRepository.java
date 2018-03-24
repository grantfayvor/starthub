package com.starthub.repositories;

import com.starthub.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harrison on 04/03/2018.
 */

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

//    @Query("SELECT t FROM Tag t WHERE t.id=(SELECT it.tag FROM IdeaTags it WHERE it.idea=?1)")
//    List<Tag> findByIdea(long ideaId);
}
