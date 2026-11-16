package tests;

import base.BaseApiTest;
import client.ApiClient;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Health Check API Tests")
public class HealthCheckTest extends BaseApiTest {

    @Test
    @DisplayName("Should return 201 Created for Ping / Health check endpoint")
    public void testHealthCheckPing() {
        APIResponse response = ApiClient.get(ApiEndpoints.PING);

        assertThat(response.status())
                .as("Check Ping API response status code is 201")
                .isEqualTo(201);

        assertThat(response.text())
                .as("Check Ping API response body contains Created")
                .containsIgnoringCase("Created");
    }
}
