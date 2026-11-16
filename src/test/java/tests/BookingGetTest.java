package tests;

import base.BaseApiTest;
import client.ApiClient;
import constants.ApiEndpoints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Get Booking API Tests")
public class BookingGetTest extends BaseApiTest {

    @Test
    @DisplayName("Should retrieve all booking IDs successfully")
    public void testGetAllBookingIds() {
        APIResponse response = ApiClient.get(ApiEndpoints.BOOKING);

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        List<Booking.BookingId> bookingIds = JsonUtils.fromJson(
                response.text(),
                new TypeReference<List<Booking.BookingId>>() {}
        );

        assertThat(bookingIds)
                .as("Booking IDs list should not be empty")
                .isNotEmpty();

        assertThat(bookingIds.get(0).getBookingid())
                .as("Booking ID should be positive integer")
                .isPositive();
    }

    @Test
    @DisplayName("Should retrieve booking details by specific ID")
    public void testGetBookingById() {
        // Create a booking first to guarantee test data isolation
        Booking sampleBooking = buildSampleBooking();
        Booking.BookingResponse created = createBookingOnServer(sampleBooking);
        int bookingId = created.getBookingid();

        APIResponse response = ApiClient.get(ApiEndpoints.getBookingByIdPath(bookingId));

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking retrievedBooking = JsonUtils.fromJson(response.text(), Booking.class);

        assertThat(retrievedBooking.getFirstname()).isEqualTo(sampleBooking.getFirstname());
        assertThat(retrievedBooking.getLastname()).isEqualTo(sampleBooking.getLastname());
        assertThat(retrievedBooking.getTotalprice()).isEqualTo(sampleBooking.getTotalprice());
        assertThat(retrievedBooking.getDepositpaid()).isEqualTo(sampleBooking.getDepositpaid());
        assertThat(retrievedBooking.getBookingdates().getCheckin()).isEqualTo(sampleBooking.getBookingdates().getCheckin());
        assertThat(retrievedBooking.getBookingdates().getCheckout()).isEqualTo(sampleBooking.getBookingdates().getCheckout());
    }

    @Test
    @DisplayName("Should filter booking IDs by name query parameters")
    public void testFilterBookingsByName() {
        // Create booking with distinct name
        String uniqueFirstName = "FilterFirst" + System.currentTimeMillis();
        String uniqueLastName = "FilterLast";

        Booking booking = new Booking(
                uniqueFirstName,
                uniqueLastName,
                200,
                true,
                new Booking.BookingDates("2026-08-01", "2026-08-15"),
                "Extra pillow"
        );
        createBookingOnServer(booking);

        RequestOptions options = RequestOptions.create()
                .setQueryParam("firstname", uniqueFirstName)
                .setQueryParam("lastname", uniqueLastName);

        APIResponse response = ApiClient.get(ApiEndpoints.BOOKING, options);

        assertThat(response.status()).isEqualTo(200);

        List<Booking.BookingId> filteredIds = JsonUtils.fromJson(
                response.text(),
                new TypeReference<List<Booking.BookingId>>() {}
        );

        assertThat(filteredIds)
                .as("Filtered list should contain matching booking")
                .isNotEmpty();
    }
}
