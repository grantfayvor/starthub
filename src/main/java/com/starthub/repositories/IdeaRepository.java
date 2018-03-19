package com.starthub.repositories;

import com.starthub.models.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harrison on 03/03/2018.
 */

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
