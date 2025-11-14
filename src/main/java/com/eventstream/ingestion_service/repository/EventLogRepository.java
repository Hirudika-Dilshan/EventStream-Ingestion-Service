package com.eventstream.ingestion_service.repository;

import com.eventstream.ingestion_service.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    
}
