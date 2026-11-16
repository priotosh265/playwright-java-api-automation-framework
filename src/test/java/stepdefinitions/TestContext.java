package stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static final ThreadLocal<TestContext> threadLocalContext = ThreadLocal.withInitial(TestContext::new);

    private APIResponse response;
    private JsonNode jsonResponse;
    private final Map<String, Object> sessionData = new HashMap<>();

    public static TestContext getContext() {
        return threadLocalContext.get();
    }

    public APIResponse getResponse() {
        return response;
    }

    public void setResponse(APIResponse response) {
        this.response = response;
        if (response != null && response.text() != null && !response.text().isBlank()) {
            try {
                this.jsonResponse = JsonUtils.getObjectMapper().readTree(response.text());
            } catch (Exception ignored) {
                this.jsonResponse = null;
            }
        } else {
            this.jsonResponse = null;
        }
    }

    public JsonNode getJsonResponse() {
        return jsonResponse;
    }

    public void set(String key, Object value) {
        sessionData.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) sessionData.get(key);
    }

    public void reset() {
        this.response = null;
        this.jsonResponse = null;
        this.sessionData.clear();
    }
}
