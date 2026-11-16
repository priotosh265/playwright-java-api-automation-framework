package tests;

import base.BaseApiTest;
import client.ApiClient;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Negative Scenario API Tests")
public class NegativeApiTest extends BaseApiTest {

    @Test
    @DisplayName("Should return 404 Not Found when retrieving non-existent booking ID")
    public void testGetNonExistentBookingId() {
        int nonExistentId = 999999999;
        APIResponse response = ApiClient.get(ApiEndpoints.getBookingByIdPath(nonExistentId));

        assertThat(response.status())
                .as("Should return 404 Not Found for invalid booking ID")
                .isEqualTo(404);
    }

    @Test
    @DisplayName("Should return 403 Forbidden when attempting PUT without auth token")
    public void testPutWithoutAuthentication() {
        Booking sampleBooking = buildSampleBooking();
        Booking.BookingResponse created = createBookingOnServer(sampleBooking);
        int bookingId = created.getBookingid();

        // Perform PUT without auth token
        APIResponse response = ApiClient.put(
                ApiEndpoints.getBookingByIdPath(bookingId),
                RequestOptions.create().setData("{\"firstname\":\"Hacker\"}")
        );

        assertThat(response.status())
                .as("Status should be 403 Forbidden when unauthenticated")
                .isEqualTo(403);
    }

    @Test
    @DisplayName("Should return 403 Forbidden when attempting DELETE without auth token")
    public void testDeleteWithoutAuthentication() {
        Booking sampleBooking = buildSampleBooking();
        Booking.BookingResponse created = createBookingOnServer(sampleBooking);
        int bookingId = created.getBookingid();

        // Perform DELETE without auth token
        APIResponse response = ApiClient.delete(
                ApiEndpoints.getBookingByIdPath(bookingId),
                RequestOptions.create()
        );

        assertThat(response.status())
                .as("Status should be 403 Forbidden when unauthenticated")
                .isEqualTo(403);
    }

    @Test
    @DisplayName("Should return 500 or 400 when creating booking with invalid/empty payload")
    public void testCreateBookingWithEmptyPayload() {
        APIResponse response = ApiClient.post(
                ApiEndpoints.BOOKING,
                RequestOptions.create().setData("{}")
        );

        assertThat(response.status())
                .as("API should reject invalid payload")
                .isIn(400, 500);
    }
}
