package com.starthub.repositories;

import com.starthub.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Harrison on 04/03/2018.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
