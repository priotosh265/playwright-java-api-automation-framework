package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                // Fallback to thread context classloader
                try (InputStream threadInput = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
                    if (threadInput != null) {
                        properties.load(threadInput);
                    }
                }
            } else {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        // System property takes precedence over config file
        String systemProp = System.getProperty(key);
        if (systemProp != null && !systemProp.isBlank()) {
            return systemProp;
        }
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://automationexercise.com");
    }

    public static String getRestfulBookerBaseUrl() {
        return getProperty("restful.booker.url", "https://restful-booker.herokuapp.com");
    }

    public static String getUsername() {
        return getProperty("auth.username", "admin");
    }

    public static String getPassword() {
        return getProperty("auth.password", "password123");
    }
}
