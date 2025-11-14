package com.eventstream.ingestion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;


// define json fields
public record EventRequest(

        
        @NotBlank(message = "eventType cannot be null or empty")
        String eventType,

        
        @NotBlank(message = "userId cannot be null or empty")
        String userId,

        @NotNull(message = "timestamp cannot be null")
        Instant timestamp,

       
        @NotNull(message = "eventData cannot be null")
        EventData eventData
) {
    
    public record EventData(
            String ipAddress,
            String device
    ) {}
}
