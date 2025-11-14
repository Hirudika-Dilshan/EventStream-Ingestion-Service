package com.eventstream.ingestion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

// 'record' එකක් පාවිච්චි කරන්නේ DTO (Data Transfer Object) එකක් විදියට.
// define json fields
public record EventRequest(

        // Validation: eventType එක null හෝ empty ("") වෙන්න බෑ
        @NotBlank(message = "eventType cannot be null or empty")
        String eventType,

        // Validation: userId එක null හෝ empty ("") වෙන්න බෑ
        @NotBlank(message = "userId cannot be null or empty")
        String userId,

        @NotNull(message = "timestamp cannot be null")
        Instant timestamp,

        // eventData කියන්නේ nested JSON object එකක් නිසා,
        // අපි ඒකට වෙනම 'EventData' record එකක් පාවිච්චි කරනවා.
        @NotNull(message = "eventData cannot be null")
        EventData eventData
) {
    // Inner record for the nested eventData JSON object
    // { "ipAddress": "...", "device": "..." }
    // Map<String, Object> එකක් use කරාම ඕනම key-value pairs ටිකක් බාරගන්න පුළුවන්.
    // හැබැයි assignment එකේ විදියටම ipAddress සහ device fields define කරනවා නම් මෙහෙම දාන්න:
    // public record EventData(String ipAddress, String device) {}

    // Assignment එකේ eventData එකේ fields වෙනස් වෙන්න පුළුවන් නිසා Map එකක් පාවිච්චි කිරීම වඩා සුදුසුයි.
    // අපි සරලව තියාගන්න assignment එකේ තියෙන fields දෙක define කරමු.
    public record EventData(
            String ipAddress,
            String device
    ) {}
}