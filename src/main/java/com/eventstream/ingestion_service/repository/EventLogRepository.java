package com.eventstream.ingestion_service.repository;

import com.eventstream.ingestion_service.entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // මේක 'Repository' component එකක් කියලා Spring වලට කියනවා
public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    // මෙතන කිසිම code එකක් ලියන්න ඕනේ නෑ!
    // 'JpaRepository' එක 'extend' කරපුවාම
    // .save(..), .findById(..), .findAll() වගේ
    // database methods ඔක්කොම අපිට automatically හම්බවෙනවා.

}