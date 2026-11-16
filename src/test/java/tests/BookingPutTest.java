package tests;

import base.BaseApiTest;
import client.ApiClient;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Update Booking (PUT) API Tests")
public class BookingPutTest extends BaseApiTest {

    @Test
    @DisplayName("Should fully update existing booking with valid auth token")
    public void testUpdateBookingSuccess() {
        // 1. Create a booking first
        Booking initialBooking = buildSampleBooking();
        Booking.BookingResponse created = createBookingOnServer(initialBooking);
        int bookingId = created.getBookingid();

        // 2. Prepare updated data
        Booking updatedPayload = new Booking(
                "UpdatedFirstName",
                "UpdatedLastName",
                555,
                false,
                new Booking.BookingDates("2026-10-01", "2026-10-15"),
                "Late check-out"
        );

        // 3. Perform PUT request with auth token
        String token = getAuthToken();
        APIResponse putResponse = ApiClient.put(
                ApiEndpoints.getBookingByIdPath(bookingId),
                updatedPayload,
                token
        );

        assertThat(putResponse.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking updatedResult = JsonUtils.fromJson(putResponse.text(), Booking.class);
        assertThat(updatedResult.getFirstname()).isEqualTo("UpdatedFirstName");
        assertThat(updatedResult.getLastname()).isEqualTo("UpdatedLastName");
        assertThat(updatedResult.getTotalprice()).isEqualTo(555);
        assertThat(updatedResult.getDepositpaid()).isFalse();
        assertThat(updatedResult.getBookingdates().getCheckin()).isEqualTo("2026-10-01");
        assertThat(updatedResult.getBookingdates().getCheckout()).isEqualTo("2026-10-15");
        assertThat(updatedResult.getAdditionalneeds()).isEqualTo("Late check-out");

        // 4. Verify via GET
        APIResponse getResponse = ApiClient.get(ApiEndpoints.getBookingByIdPath(bookingId));
        assertThat(getResponse.status()).isEqualTo(200);
        Booking fetched = JsonUtils.fromJson(getResponse.text(), Booking.class);
        assertThat(fetched.getFirstname()).isEqualTo("UpdatedFirstName");
    }
}
