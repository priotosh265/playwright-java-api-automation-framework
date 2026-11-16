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

@DisplayName("Create Booking API Tests")
public class BookingPostTest extends BaseApiTest {

    @Test
    @DisplayName("Should create a new booking using Java POJO payload")
    public void testCreateBookingUsingPojo() {
        Booking bookingPayload = new Booking(
                "Alice",
                "Wonderland",
                300,
                true,
                new Booking.BookingDates("2026-09-01", "2026-09-10"),
                "High floor room"
        );

        APIResponse response = ApiClient.post(ApiEndpoints.BOOKING, bookingPayload);

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking.BookingResponse bookingResponse = JsonUtils.fromJson(
                response.text(),
                Booking.BookingResponse.class
        );

        assertThat(bookingResponse.getBookingid())
                .as("Created booking ID should be positive")
                .isNotNull()
                .isPositive();

        Booking createdBooking = bookingResponse.getBooking();
        assertThat(createdBooking.getFirstname()).isEqualTo(bookingPayload.getFirstname());
        assertThat(createdBooking.getLastname()).isEqualTo(bookingPayload.getLastname());
        assertThat(createdBooking.getTotalprice()).isEqualTo(bookingPayload.getTotalprice());
        assertThat(createdBooking.getDepositpaid()).isEqualTo(bookingPayload.getDepositpaid());
        assertThat(createdBooking.getBookingdates().getCheckin()).isEqualTo(bookingPayload.getBookingdates().getCheckin());
        assertThat(createdBooking.getBookingdates().getCheckout()).isEqualTo(bookingPayload.getBookingdates().getCheckout());
        assertThat(createdBooking.getAdditionalneeds()).isEqualTo(bookingPayload.getAdditionalneeds());
    }

    @Test
    @DisplayName("Should create a new booking using JSON file payload")
    public void testCreateBookingUsingJsonFile() {
        Booking fileBooking = JsonUtils.readJsonFromFile(TEST_DATA_BOOKING_PATH, Booking.class);

        APIResponse response = ApiClient.post(ApiEndpoints.BOOKING, fileBooking);

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking.BookingResponse bookingResponse = JsonUtils.fromJson(
                response.text(),
                Booking.BookingResponse.class
        );

        assertThat(bookingResponse.getBookingid())
                .as("Booking ID should be positive")
                .isNotNull()
                .isPositive();

        Booking createdBooking = bookingResponse.getBooking();
        assertThat(createdBooking.getFirstname()).isEqualTo(fileBooking.getFirstname());
        assertThat(createdBooking.getLastname()).isEqualTo(fileBooking.getLastname());
        assertThat(createdBooking.getTotalprice()).isEqualTo(fileBooking.getTotalprice());
        assertThat(createdBooking.getDepositpaid()).isEqualTo(fileBooking.getDepositpaid());
        assertThat(createdBooking.getAdditionalneeds()).isEqualTo(fileBooking.getAdditionalneeds());
    }
}
