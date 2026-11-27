package com.cinemaai.booking.dto;

import com.cinemaai.booking.dto.response.BookingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HoldExpirySerializationTest {
    @Test
    void holdsIncludeAnUnambiguousEpochInServerTimezone() throws Exception {
        TimeZone original = TimeZone.getDefault();
        try {
            for (String zone : new String[]{"UTC", "Asia/Ho_Chi_Minh"}) {
                TimeZone.setDefault(TimeZone.getTimeZone(zone));
                LocalDateTime expiry = LocalDateTime.of(2026, 11, 27, 4, 27, 14);
                long expected = expiry.atZone(ZoneId.of(zone)).toInstant().toEpochMilli();
                ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
                var booking = BookingResponse.builder().holdExpiresAt(expiry).build();
                assertEquals(expected, mapper.readTree(mapper.writeValueAsString(booking)).get("holdExpiresAtEpochMs").asLong());
            }
        } finally {
            TimeZone.setDefault(original);
        }
    }
}
