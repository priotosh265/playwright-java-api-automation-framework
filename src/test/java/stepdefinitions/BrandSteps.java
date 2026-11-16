package stepdefinitions;

import client.ApiClient;
import constants.ApiEndpoints;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandSteps {

    private final TestContext context;

    public BrandSteps(TestContext context) {
        this.context = context;
    }

    @When("I send a GET request to get all brands")
    public void iSendAGetRequestToGetAllBrands() {
        APIResponse response = ApiClient.get(ApiEndpoints.BRANDS_LIST);
        context.setResponse(response);
    }

    @And("the response should contain brand details")
    public void theResponseShouldContainBrandDetails() {
        JsonNode jsonNode = context.getJsonResponse();
        assertThat(jsonNode).isNotNull();
        assertThat(jsonNode.has("brands"))
                .as("Response JSON must contain 'brands' array")
                .isTrue();

        JsonNode brandsArray = jsonNode.get("brands");
        assertThat(brandsArray.isArray()).isTrue();
        assertThat(brandsArray.size())
                .as("Brands array should not be empty")
                .isGreaterThan(0);
    }

    @And("each brand should have a valid id and brand name")
    public void eachBrandShouldHaveAValidIdAndBrandName() {
        JsonNode brandsArray = context.getJsonResponse().get("brands");
        assertThat(brandsArray).isNotNull();

        for (JsonNode brand : brandsArray) {
            assertThat(brand.has("id")).as("Brand must have 'id'").isTrue();
            assertThat(brand.has("brand")).as("Brand must have 'brand' name").isTrue();
            assertThat(brand.get("id").asInt()).isPositive();
            assertThat(brand.get("brand").asText()).isNotBlank();
        }
    }

    @When("I send a PUT request to all brands list")
    public void iSendAPutRequestToAllBrandsList() {
        APIResponse response = ApiClient.put(ApiEndpoints.BRANDS_LIST, "", "");
        context.setResponse(response);
    }
}
