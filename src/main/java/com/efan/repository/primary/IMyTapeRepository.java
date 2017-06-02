package com.efan.repository.primary;

import com.efan.core.primary.MyTape;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 45425 on 2017/5/8.
 */
@Repository
public interface IMyTapeRepository extends JpaRepository<MyTape,Long> {
    Page<MyTape> findMyTapeByUserKey(String userKey,Pageable pageable);
    Page<MyTape> findMyTapeByUserKeyAndState(String userKey,Boolean state,Pageable pageable);
}
