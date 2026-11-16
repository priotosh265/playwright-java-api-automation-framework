package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private JsonUtils() {
        // Prevent instantiation
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON to " + clazz.getSimpleName(), e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON using TypeReference", e);
        }
    }

    private static InputStream getResourceStream(String filePath) {
        InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream(filePath);
        if (is != null) {
            return is;
        }
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
    }

    public static <T> T readJsonFromFile(String filePath, Class<T> clazz) {
        try {
            InputStream resourceStream = getResourceStream(filePath);
            if (resourceStream != null) {
                try (InputStream is = resourceStream) {
                    return objectMapper.readValue(is, clazz);
                }
            }

            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return objectMapper.readValue(path.toFile(), clazz);
            }

            throw new RuntimeException("JSON file not found at: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + filePath, e);
        }
    }

    public static String readJsonFileAsString(String filePath) {
        try {
            InputStream resourceStream = getResourceStream(filePath);
            if (resourceStream != null) {
                try (InputStream is = resourceStream) {
                    return new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
            }

            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return Files.readString(path, StandardCharsets.UTF_8);
            }

            throw new RuntimeException("File not found at: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file to string: " + filePath, e);
        }
    }
}
