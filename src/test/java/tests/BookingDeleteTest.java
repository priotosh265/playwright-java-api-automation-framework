package tests;

import base.BaseApiTest;
import client.ApiClient;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Delete Booking API Tests")
public class BookingDeleteTest extends BaseApiTest {

    @Test
    @DisplayName("Should successfully delete booking and return 404 on subsequent get")
    public void testDeleteBookingSuccess() {
        // 1. Create a booking to delete
        Booking sampleBooking = buildSampleBooking();
        Booking.BookingResponse created = createBookingOnServer(sampleBooking);
        int bookingId = created.getBookingid();

        // 2. Delete the booking
        String token = getAuthToken();
        APIResponse deleteResponse = ApiClient.delete(
                ApiEndpoints.getBookingByIdPath(bookingId),
                token
        );

        assertThat(deleteResponse.status())
                .as("DELETE response should return 201 Created in restful-booker")
                .isEqualTo(201);

        // 3. Confirm booking no longer exists
        APIResponse getResponse = ApiClient.get(ApiEndpoints.getBookingByIdPath(bookingId));
        assertThat(getResponse.status())
                .as("GET request for deleted booking should return 404 Not Found")
                .isEqualTo(404);
    }
}
