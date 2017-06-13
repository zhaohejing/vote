package com.efan.repository;


import com.efan.core.primary.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface IRecordRepository extends JpaRepository<Record,Long> {
        List<Record> findBySendKeyAndActorIdAndCreationTimeBetween(String senKey, Long actorId, String start, String end);
        List<Record> findAllBySendKeyAndActivityIdAndCreationTimeBetween(String senKey, Long activityId, String start, String end);

        List<Record> findAllBySendKeyAndActivityId(String senKey,Long activityId);

}