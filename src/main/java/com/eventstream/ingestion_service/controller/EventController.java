package com.eventstream.ingestion_service.controller;


import com.eventstream.ingestion_service.dto.EventRequest;
import com.eventstream.ingestion_service.service.EventService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // මේක 'REST Controller' එකක් කියලා Spring වලට කියනවා
@RequestMapping("/api/v1") // මේ controller එකේ තියෙන எல்லா endpoints වලටම common prefix එක
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    // 'Service' layer එක 'inject' කරගන්නවා
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Assignment requirement: POST /api/v1/event
    @PostMapping("/event")
    public ResponseEntity<Void> handleEvent(

            // @Valid: DTO එකේ තියෙන validation rules (@NotBlank) check කරන්න කියනවා
            // @RequestBody: JSON payload එක EventRequest object එකට convert කරන්න කියනවා
            @Valid @RequestBody EventRequest eventRequest) {

        log.info("Received event ingestion request: {}", eventRequest.eventType());

        // 1. Data එක Service එකට pass කරනවා
        // මේ method එක ඉක්මනට return වෙනවා (async නිසා)
        eventService.processEvent(eventRequest);

        // 2. Assignment requirement: HTTP 202 Accepted return කරනවා
        // "බාරගත්තා, process කරන්නම්"
        return ResponseEntity.accepted().build();
    }
}