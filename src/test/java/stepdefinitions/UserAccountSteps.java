package stepdefinitions;

import client.ApiClient;
import constants.ApiEndpoints;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAccountSteps {

    private final TestContext context;

    public UserAccountSteps(TestContext context) {
        this.context = context;
    }

    @When("I create a new user account with unique email")
    public void iCreateANewUserAccountWithUniqueEmail() {
        JsonNode usersData = JsonUtils.readJsonFromFile("testdata/users.json", JsonNode.class);
        JsonNode template = usersData.get("newUserRegistration");

        String uniqueEmail = template.get("emailPrefix").asText() + System.currentTimeMillis() + template.get("emailDomain").asText();
        String password = template.get("password").asText();
        String name = template.get("name").asText();

        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", uniqueEmail);
        formParams.put("password", password);
        formParams.put("title", template.get("title").asText());
        formParams.put("birth_date", template.get("birth_date").asText());
        formParams.put("birth_month", template.get("birth_month").asText());
        formParams.put("birth_year", template.get("birth_year").asText());
        formParams.put("firstname", template.get("firstname").asText());
        formParams.put("lastname", template.get("lastname").asText());
        formParams.put("company", template.get("company").asText());
        formParams.put("address1", template.get("address1").asText());
        formParams.put("address2", template.get("address2").asText());
        formParams.put("country", template.get("country").asText());
        formParams.put("zipcode", template.get("zipcode").asText());
        formParams.put("state", template.get("state").asText());
        formParams.put("city", template.get("city").asText());
        formParams.put("mobile_number", template.get("mobile_number").asText());

        APIResponse response = ApiClient.postForm(ApiEndpoints.CREATE_ACCOUNT, formParams);
        context.setResponse(response);

        // Store for subsequent steps in scenario
        context.set("createdEmail", uniqueEmail);
        context.set("createdPassword", password);
        context.set("createdName", name);
        context.set("accountDeleted", false);
    }

    @When("I get user detail by the created email")
    public void iGetUserDetailByTheCreatedEmail() {
        String email = context.get("createdEmail");
        APIResponse response = ApiClient.get(
                ApiEndpoints.GET_USER_DETAIL_BY_EMAIL,
                RequestOptions.create().setQueryParam("email", email)
        );
        context.setResponse(response);
    }

    @When("I get user detail by email {string}")
    public void iGetUserDetailByEmail(String email) {
        APIResponse response = ApiClient.get(
                ApiEndpoints.GET_USER_DETAIL_BY_EMAIL,
                RequestOptions.create().setQueryParam("email", email)
        );
        context.setResponse(response);
    }

    @And("the user detail response should match the created user name")
    public void theUserDetailResponseShouldMatchTheCreatedUserName() {
        JsonNode jsonNode = context.getJsonResponse();
        assertThat(jsonNode).isNotNull();
        assertThat(jsonNode.has("user"))
                .as("Response must contain 'user' object")
                .isTrue();

        String expectedName = context.get("createdName");
        JsonNode userObject = jsonNode.get("user");
        assertThat(userObject.get("name").asText()).isEqualTo(expectedName);
    }

    @When("I delete the created user account")
    public void iDeleteTheCreatedUserAccount() {
        String email = context.get("createdEmail");
        String password = context.get("createdPassword");

        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);

        APIResponse response = ApiClient.deleteForm(ApiEndpoints.DELETE_ACCOUNT, formParams);
        context.setResponse(response);
        context.set("accountDeleted", true);
    }

    @When("I send a DELETE request to delete account with email {string} and password {string}")
    public void iSendADeleteRequestToDeleteAccountWithEmailAndPassword(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);

        APIResponse response = ApiClient.deleteForm(ApiEndpoints.DELETE_ACCOUNT, formParams);
        context.setResponse(response);
    }
}
