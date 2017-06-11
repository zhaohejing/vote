package com.efan.repository;

import com.efan.core.primary.Gift;
import com.efan.core.primary.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * Created by 45425 on 2017/6/2.
 */

@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {
 void  deleteByActivityId(Long activityId);
 List<Image> findAllByActivityId(Long activityId);
 List<Image> findAllByActivityIdOrderBySort(Long activityId);
}