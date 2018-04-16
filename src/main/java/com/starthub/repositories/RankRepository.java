package com.starthub.repositories;

import com.starthub.models.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Harrison on 4/13/2018.
 */

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {
}
