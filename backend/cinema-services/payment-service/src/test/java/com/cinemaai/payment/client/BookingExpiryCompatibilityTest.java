package com.cinemaai.payment.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingExpiryCompatibilityTest {
    @Test
    void additionalEpochDoesNotChangeExistingPaymentBookingContract() throws Exception {
        var mapper = new ObjectMapper().findAndRegisterModules();
        String json = """
                {"id":1,"status":"HOLDING","holdExpiresAt":"2026-11-27T04:27:14",
                 "holdExpiresAtEpochMs":1795753634000}
                """;
        var booking = mapper.readValue(json, BookingClient.BookingInfo.class);
        assertEquals(LocalDateTime.of(2026, 11, 27, 4, 27, 14), booking.holdExpiresAt());
    }
}
