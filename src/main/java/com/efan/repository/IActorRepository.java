package com.efan.repository;
import com.efan.core.primary.Actor;
import com.efan.core.primary.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IActorRepository extends JpaRepository<Actor,Long> {

    /*  @Query("select u from activity u where u.orderNum=:orderId  ")
    Activity findOrderByFilter(@Param("orderId") String orderId);*/

    Page<Actor> findAllByActivityIdAndActorNameContains(Long activityId, String actorName, Pageable pageable);
    List<Actor> findAllByActivityId(Long activityId);
}



