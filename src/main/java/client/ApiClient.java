package client;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import config.ConfigManager;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    private static Playwright playwright;
    private static APIRequestContext request;

    private ApiClient() {
        // Prevent instantiation
    }

    public static synchronized void initialize() {
        if (playwright == null) {
            Map<String, String> env = new HashMap<>(System.getenv());
            env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
            playwright = Playwright.create(new Playwright.CreateOptions().setEnv(env));
        }

        Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("Accept", "*/*");

        request = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(ConfigManager.getBaseUrl())
                        .setExtraHTTPHeaders(defaultHeaders)
        );
    }

    public static APIRequestContext getRequest() {
        if (request == null) {
            initialize();
        }
        return request;
    }

    // GET requests
    public static APIResponse get(String endpoint) {
        return getRequest().get(endpoint);
    }

    public static APIResponse get(String endpoint, RequestOptions options) {
        return getRequest().get(endpoint, options);
    }

    // POST requests (JSON)
    public static APIResponse post(String endpoint, Object body) {
        String jsonBody = (body instanceof String) ? (String) body : JsonUtils.toJson(body);
        return getRequest().post(endpoint, RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(jsonBody));
    }

    public static APIResponse post(String endpoint, RequestOptions options) {
        return getRequest().post(endpoint, options);
    }

    // POST requests (Form data)
    public static APIResponse postForm(String endpoint, Map<String, String> formParams) {
        FormData formData = FormData.create();
        if (formParams != null) {
            formParams.forEach(formData::set);
        }
        return getRequest().post(endpoint, RequestOptions.create().setForm(formData));
    }

    // PUT requests (JSON)
    public static APIResponse put(String endpoint, Object body, String token) {
        String jsonBody = (body instanceof String) ? (String) body : JsonUtils.toJson(body);
        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(jsonBody)
                .setHeader("Cookie", "token=" + token);
        return getRequest().put(endpoint, options);
    }

    public static APIResponse put(String endpoint, RequestOptions options) {
        return getRequest().put(endpoint, options);
    }

    // PUT requests (Form data)
    public static APIResponse putForm(String endpoint, Map<String, String> formParams) {
        FormData formData = FormData.create();
        if (formParams != null) {
            formParams.forEach(formData::set);
        }
        return getRequest().put(endpoint, RequestOptions.create().setForm(formData));
    }

    // PATCH requests (JSON)
    public static APIResponse patch(String endpoint, Object body, String token) {
        String jsonBody = (body instanceof String) ? (String) body : JsonUtils.toJson(body);
        RequestOptions options = RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(jsonBody)
                .setHeader("Cookie", "token=" + token);
        return getRequest().patch(endpoint, options);
    }

    public static APIResponse patch(String endpoint, RequestOptions options) {
        return getRequest().patch(endpoint, options);
    }

    // DELETE requests
    public static APIResponse delete(String endpoint, String token) {
        RequestOptions options = RequestOptions.create()
                .setHeader("Cookie", "token=" + token);
        return getRequest().delete(endpoint, options);
    }

    public static APIResponse delete(String endpoint, RequestOptions options) {
        return getRequest().delete(endpoint, options);
    }

    // DELETE requests (Form data)
    public static APIResponse deleteForm(String endpoint, Map<String, String> formParams) {
        FormData formData = FormData.create();
        if (formParams != null) {
            formParams.forEach(formData::set);
        }
        return getRequest().delete(endpoint, RequestOptions.create().setForm(formData));
    }

    public static synchronized void close() {
        if (request != null) {
            request.dispose();
            request = null;
        }

        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
