package com.efan.repository;
import com.efan.core.primary.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface IActorRepository extends JpaRepository<Actor,Long> {
  /*  @Query("select u from activity u where u.boxId=:boxId and u.creationTime>:a and u.creationTime<=:b ")
    List<Activity> findOrders(@Param("boxId")String boxId,@Param("a") Date start,@Param("b") Date end);
    @Query("select u from activity u where u.orderNum=:orderId  ")
    Activity findOrderByFilter(@Param("orderId") String orderId);*/

    Page<Actor> findAllByActivityIdAndActorNameLike(Long activityId, String actorName, Pageable pageable);
    Page<Actor> findAllByActivityIdAndActorNameContains(Long activityId, String actorName, Pageable pageable);

}



