package stepdefinitions;

import client.ApiClient;
import constants.ApiEndpoints;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSteps {

    private final TestContext context;

    public ProductSteps(TestContext context) {
        this.context = context;
    }

    @When("I send a GET request to get all products")
    public void iSendAGetRequestToGetAllProducts() {
        APIResponse response = ApiClient.get(ApiEndpoints.PRODUCTS_LIST);
        context.setResponse(response);
    }

    @And("the response should contain product details")
    public void theResponseShouldContainProductDetails() {
        JsonNode jsonNode = context.getJsonResponse();
        assertThat(jsonNode).isNotNull();
        assertThat(jsonNode.has("products"))
                .as("Response JSON must contain 'products' array")
                .isTrue();

        JsonNode productsArray = jsonNode.get("products");
        assertThat(productsArray.isArray()).isTrue();
        assertThat(productsArray.size())
                .as("Products array should not be empty")
                .isGreaterThan(0);
    }

    @And("the product response should have valid fields")
    public void theProductResponseShouldHaveValidFields() {
        JsonNode productsArray = context.getJsonResponse().get("products");
        assertThat(productsArray).isNotNull();

        // Validate structure on first 5 sample products
        int sampleSize = Math.min(5, productsArray.size());
        for (int i = 0; i < sampleSize; i++) {
            JsonNode product = productsArray.get(i);
            assertThat(product.has("id")).as("Product must have 'id'").isTrue();
            assertThat(product.has("name")).as("Product must have 'name'").isTrue();
            assertThat(product.has("price")).as("Product must have 'price'").isTrue();
            assertThat(product.has("brand")).as("Product must have 'brand'").isTrue();
            assertThat(product.has("category")).as("Product must have 'category'").isTrue();

            assertThat(product.get("id").asInt()).isPositive();
            assertThat(product.get("name").asText()).isNotBlank();
        }
    }

    @When("I send a POST request to search for product {string}")
    public void iSendAPostRequestToSearchForProduct(String searchTerm) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("search_product", searchTerm);

        APIResponse response = ApiClient.postForm(ApiEndpoints.SEARCH_PRODUCT, formParams);
        context.setResponse(response);
        context.set("searchTerm", searchTerm);
    }

    @And("the response should contain searched products")
    public void theResponseShouldContainSearchedProducts() {
        JsonNode jsonNode = context.getJsonResponse();
        assertThat(jsonNode).isNotNull();
        assertThat(jsonNode.has("products")).isTrue();

        JsonNode productsArray = jsonNode.get("products");
        assertThat(productsArray.size())
                .as("Search results should contain at least 1 product")
                .isGreaterThan(0);
    }

    @And("each product in the search results should be related to {string}")
    public void eachProductInTheSearchResultsShouldBeRelatedTo(String expectedTerm) {
        JsonNode productsArray = context.getJsonResponse().get("products");
        assertThat(productsArray).isNotNull();

        for (JsonNode product : productsArray) {
            String name = product.get("name").asText().toLowerCase();
            String brand = product.has("brand") ? product.get("brand").asText().toLowerCase() : "";
            String category = product.has("category") ? product.get("category").toString().toLowerCase() : "";

            boolean matches = name.contains(expectedTerm.toLowerCase())
                    || brand.contains(expectedTerm.toLowerCase())
                    || category.contains(expectedTerm.toLowerCase());

            assertThat(matches)
                    .as("Product '" + product.get("name").asText() + "' should relate to '" + expectedTerm + "'")
                    .isTrue();
        }
    }

    @When("I send a POST request to all products list")
    public void iSendAPostRequestToAllProductsList() {
        APIResponse response = ApiClient.post(ApiEndpoints.PRODUCTS_LIST, "");
        context.setResponse(response);
    }
}
