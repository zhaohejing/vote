package com.efan.repository;


import com.efan.core.primary.Giving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGivingRepository extends JpaRepository<Giving,Long> {
    Page<Giving> findAllByActorId(Long actorId, Pageable pageable);
}