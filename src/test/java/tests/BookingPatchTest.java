package tests;

import base.BaseApiTest;
import client.ApiClient;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Partial Update Booking (PATCH) API Tests")
public class BookingPatchTest extends BaseApiTest {

    @Test
    @DisplayName("Should partially update booking fields (firstname and totalprice)")
    public void testPartialUpdateBooking() {
        // 1. Create a booking
        Booking initialBooking = buildSampleBooking();
        Booking.BookingResponse created = createBookingOnServer(initialBooking);
        int bookingId = created.getBookingid();

        // 2. Prepare partial update payload
        Map<String, Object> patchPayload = new HashMap<>();
        patchPayload.put("firstname", "PatchedName");
        patchPayload.put("totalprice", 888);

        // 3. Send PATCH request
        String token = getAuthToken();
        APIResponse patchResponse = ApiClient.patch(
                ApiEndpoints.getBookingByIdPath(bookingId),
                patchPayload,
                token
        );

        assertThat(patchResponse.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking patchedBooking = JsonUtils.fromJson(patchResponse.text(), Booking.class);
        assertThat(patchedBooking.getFirstname()).isEqualTo("PatchedName");
        assertThat(patchedBooking.getTotalprice()).isEqualTo(888);
        // Original fields should remain intact
        assertThat(patchedBooking.getLastname()).isEqualTo(initialBooking.getLastname());
        assertThat(patchedBooking.getDepositpaid()).isEqualTo(initialBooking.getDepositpaid());
    }
}
