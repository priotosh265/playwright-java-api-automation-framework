package stepdefinitions;

import client.ApiClient;
import constants.ApiEndpoints;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void beforeScenario() {
        ApiClient.initialize();
        context.reset();
    }

    @After
    public void afterScenario() {
        // Cleanup dynamically created user account if exists and not already deleted
        String createdEmail = context.get("createdEmail");
        String createdPassword = context.get("createdPassword");
        Boolean accountDeleted = context.get("accountDeleted");

        if (createdEmail != null && createdPassword != null && (accountDeleted == null || !accountDeleted)) {
            try {
                Map<String, String> deleteParams = new HashMap<>();
                deleteParams.put("email", createdEmail);
                deleteParams.put("password", createdPassword);
                ApiClient.deleteForm(ApiEndpoints.DELETE_ACCOUNT, deleteParams);
            } catch (Exception ignored) {
                // Ignore cleanup failures
            }
        }
    }
}
