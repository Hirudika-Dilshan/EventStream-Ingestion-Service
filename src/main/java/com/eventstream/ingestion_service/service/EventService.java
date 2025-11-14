package com.eventstream.ingestion_service.service;

import com.eventstream.ingestion_service.dto.EventRequest;
import com.eventstream.ingestion_service.entity.EventLog;
import com.eventstream.ingestion_service.repository.EventLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // මේක 'Service' class එකක් කියලා Spring වලට කියනවා
public class EventService {

    // Logger එකක් හදාගන්නවා console එකේ log-messages print කරන්න
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    // Database 'Repository' එක 'inject' කරගන්නවා
    private final EventLogRepository eventLogRepository;

    // Constructor Injection: Spring Boot වලින් automatically EventLogRepository එකක් හදලා දෙනවා
    public EventService(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    /**
     * මේ method එක Controller එකෙන් call කරන්නේ.
     * මේක ඉක්මනට log එකක් දාලා, 'saveEventAsync' කියන async method එකට call කරනවා.
     */
    public void processEvent(EventRequest eventRequest) {
        log.info("Received event: {}. Handing over to async processor.", eventRequest.eventType());

        // අනිත් method එකට call කරනවා. මේ call එක දාලා client ට response එක යවනවා.
        // අනිත් method එක background එකේ run වෙනවා.
        saveEventAsync(eventRequest);
    }


    /**
     * Assignment එකේ core requirement එක.
     * මේ method එක වෙනම 'thread' එකක (background එකේ) run වෙන්නේ.
     */
    @Async // <-- මේකෙන් තමයි Asynchronous කරන්නේ
    @Transactional // <-- Database operation එක 'transaction' එකක් විදියට කරන්නේ
    public void saveEventAsync(EventRequest eventRequest) {
        log.info("Async processing started for event: {}", eventRequest.eventType());
        try {
            // 1. DTO (EventRequest) එක Entity (EventLog) එකකට convert කරනවා
            EventLog eventLog = new EventLog(
                    eventRequest.eventType(),
                    eventRequest.userId(),
                    eventRequest.timestamp(),
                    eventRequest.eventData().ipAddress(),
                    eventRequest.eventData().device()
            );

            // 2. Database එකේ save කරනවා
            eventLogRepository.save(eventLog);

            log.info("Successfully saved event {} to database.", eventRequest.eventType());

        } catch (Exception e) {
            // Database එකට save කරනකොට error එකක් ආවොත් (DB down, etc.)
            // මෙතන log කරනවා. Error handling පස්සේ improve කරමු.
            log.error("Failed to save event asynchronously: {}", eventRequest.eventType(), e);
            // @RestControllerAdvice එකෙන් මේ වගේ errors handle වෙන්නේ නෑ
            // (mokada client ta 202 response eka kalinma giya nisa)
            // Assignment eke bonus point (Retry) eka onama me error handling walata.
        }
    }
}