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

@RestController 
@RequestMapping("/api/v1") 
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Assignment requirement: POST /api/v1/event
    @PostMapping("/event")
    public ResponseEntity<Void> handleEvent(

            
            @Valid @RequestBody EventRequest eventRequest) {

        log.info("Received event ingestion request: {}", eventRequest.eventType());

        
        eventService.processEvent(eventRequest);

        
        return ResponseEntity.accepted().build();
    }
}
