package com.efan.repository;

import com.efan.core.primary.Gift;
import com.efan.core.primary.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 45425 on 2017/6/2.
 */

@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {

}