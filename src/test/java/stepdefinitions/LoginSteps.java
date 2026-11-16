package stepdefinitions;

import client.ApiClient;
import constants.ApiEndpoints;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @When("I send a POST request to verify login with email {string} and password {string}")
    public void iSendAPostRequestToVerifyLoginWithEmailAndPassword(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);

        APIResponse response = ApiClient.postForm(ApiEndpoints.VERIFY_LOGIN, formParams);
        context.setResponse(response);
    }

    @When("I send a POST request to verify login with password {string} but missing email")
    public void iSendAPostRequestToVerifyLoginWithPasswordButMissingEmail(String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("password", password);

        APIResponse response = ApiClient.postForm(ApiEndpoints.VERIFY_LOGIN, formParams);
        context.setResponse(response);
    }

    @When("I send a POST request to verify login with email {string} but missing password")
    public void iSendAPostRequestToVerifyLoginWithEmailButMissingPassword(String email) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);

        APIResponse response = ApiClient.postForm(ApiEndpoints.VERIFY_LOGIN, formParams);
        context.setResponse(response);
    }

    @When("I send a DELETE request to verify login")
    public void iSendADeleteRequestToVerifyLogin() {
        APIResponse response = ApiClient.delete(ApiEndpoints.VERIFY_LOGIN, "");
        context.setResponse(response);
    }
}
