package com.efan.repository;


import com.efan.core.primary.Prize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPrizeRepository extends JpaRepository<Prize,Long> {
  /*  @Query("select u from activity u where u.boxId=:boxId and u.creationTime>:a and u.creationTime<=:b ")
    List<Activity> findOrders(@Param("boxId")String boxId,@Param("a") Date start,@Param("b") Date end);
    @Query("select u from activity u where u.orderNum=:orderId  ")
    Activity findOrderByFilter(@Param("orderId") String orderId);*/

    Page<Prize> findAllByPrizeNameContains(String prizeName, Pageable pageable);
    List<Prize> findAllByActivityId(Long activityId);

}