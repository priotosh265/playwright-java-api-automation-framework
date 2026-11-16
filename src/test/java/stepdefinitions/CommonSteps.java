package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        APIResponse response = context.getResponse();
        assertThat(response)
                .as("API response should not be null")
                .isNotNull();

        assertThat(response.status())
                .as("HTTP status code mismatch")
                .isEqualTo(expectedStatusCode);
    }

    @Then("the response code in body should be {int}")
    public void theResponseCodeInBodyShouldBe(int expectedCodeInBody) {
        JsonNode jsonNode = context.getJsonResponse();
        assertThat(jsonNode)
                .as("Response body JSON should not be null")
                .isNotNull();

        assertThat(jsonNode.has("responseCode"))
                .as("Response JSON should contain 'responseCode' property")
                .isTrue();

        assertThat(jsonNode.get("responseCode").asInt())
                .as("Expected responseCode in JSON body")
                .isEqualTo(expectedCodeInBody);
    }

    @Then("the response message should be {string}")
    public void theResponseMessageShouldBe(String expectedMessage) {
        JsonNode jsonNode = context.getJsonResponse();
        assertThat(jsonNode)
                .as("Response body JSON should not be null")
                .isNotNull();

        assertThat(jsonNode.has("message"))
                .as("Response JSON should contain 'message' property")
                .isTrue();

        assertThat(jsonNode.get("message").asText())
                .as("Expected message in response body")
                .isEqualTo(expectedMessage);
    }
}
