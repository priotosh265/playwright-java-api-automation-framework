package tests;

import base.BaseApiTest;
import client.ApiClient;
import config.ConfigManager;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Authentication API Tests")
public class AuthenticationTest extends BaseApiTest {

    @Test
    @DisplayName("Should successfully authenticate and generate token with valid credentials")
    public void testAuthenticationWithValidCredentials() {
        Booking.AuthCredentials credentials = new Booking.AuthCredentials(
                ConfigManager.getUsername(),
                ConfigManager.getPassword()
        );

        APIResponse response = ApiClient.post(ApiEndpoints.AUTH, credentials);

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking.TokenResponse tokenResponse = JsonUtils.fromJson(response.text(), Booking.TokenResponse.class);

        assertThat(tokenResponse.getToken())
                .as("Auth token should not be null or blank")
                .isNotNull()
                .isNotBlank();
    }

    @Test
    @DisplayName("Should fail authentication with invalid credentials")
    public void testAuthenticationWithInvalidCredentials() {
        Booking.AuthCredentials credentials = new Booking.AuthCredentials("invalidUser", "invalidPass");

        APIResponse response = ApiClient.post(ApiEndpoints.AUTH, credentials);

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        Booking.TokenResponse tokenResponse = JsonUtils.fromJson(response.text(), Booking.TokenResponse.class);

        assertThat(tokenResponse.getToken())
                .as("Token should be null for invalid credentials")
                .isNull();

        assertThat(tokenResponse.getReason())
                .as("Reason should indicate Bad credentials")
                .isEqualTo("Bad credentials");
    }
}
