package com.efan.repository;

import com.efan.core.primary.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<Activity,Long> {
  /*  @Query("select u from activity u where u.boxId=:boxId and u.creationTime>:a and u.creationTime<=:b ")
    List<Activity> findOrders(@Param("boxId")String boxId,@Param("a") Date start,@Param("b") Date end);*/
    Page<Activity> findAllByTitleContains(String title, Pageable pageable);
        List<Activity> findAllByIsPublic(Boolean isPublic);
}