package com.starthub.repositories;

import com.starthub.models.IdeaTags;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Harrison on 3/21/2018.
 */
public interface IdeaTagsRepository extends JpaRepository<IdeaTags, Long> {
}
