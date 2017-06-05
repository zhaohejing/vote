package com.efan.repository;


import com.efan.core.primary.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface IRecordRepository extends JpaRepository<Record,Long> {
        List<Record> findBySendKeyAndActorIdAndCreationTimeBetween(String sendkey, Long actorId, java.util.Date start, java.util.Date end);
}