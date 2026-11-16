package base;

import client.ApiClient;
import config.ConfigManager;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import models.Booking;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utils.JsonUtils;

public abstract class BaseApiTest {

    protected static final String TEST_DATA_BOOKING_PATH = "src/test/java/testdata/booking.json";
    protected static String authToken;

    @BeforeAll
    public static void setUp() {
        ApiClient.initialize();
    }

    @AfterAll
    public static void tearDown() {
        ApiClient.close();
    }

    /**
     * Authenticates and returns a session auth token.
     */
    protected static synchronized String getAuthToken() {
        if (authToken == null || authToken.isBlank()) {
            Booking.AuthCredentials credentials = new Booking.AuthCredentials(
                    ConfigManager.getUsername(),
                    ConfigManager.getPassword()
            );

            APIResponse response = ApiClient.post(ApiEndpoints.AUTH, credentials);
            if (response.status() == 200) {
                Booking.TokenResponse tokenResponse = JsonUtils.fromJson(response.text(), Booking.TokenResponse.class);
                authToken = tokenResponse.getToken();
            } else {
                throw new RuntimeException("Failed to generate auth token. Status: " + response.status() + ", Body: " + response.text());
            }
        }
        return authToken;
    }

    /**
     * Helper to build a sample in-memory Booking object.
     */
    protected Booking buildSampleBooking() {
        return new Booking(
                "James",
                "Bond",
                150,
                true,
                new Booking.BookingDates("2026-07-01", "2026-07-10"),
                "Martini (shaken, not stirred)"
        );
    }

    /**
     * Helper to create a booking on the remote server and return the response.
     */
    protected Booking.BookingResponse createBookingOnServer(Booking booking) {
        APIResponse response = ApiClient.post(ApiEndpoints.BOOKING, booking);
        if (response.status() != 200) {
            throw new RuntimeException("Failed to create booking on server: " + response.text());
        }
        return JsonUtils.fromJson(response.text(), Booking.BookingResponse.class);
    }
}
