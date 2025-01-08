package com.heygen.translation_client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heygen.translation_client.model.StatusResponse;
import com.heygen.translation_client.model.TranslateVideoRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class VideoTranslationClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public VideoTranslationClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl;
    }

    // Step 1: POST to /translate-video-bulk
    public void postBulkTranslateVideos(List<TranslateVideoRequest> requests) {
        try {
            String requestBody = objectMapper.writeValueAsString(requests);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/translate-video-bulk"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response from the POST request
            System.out.println("Bulk Translation Response: " + response.body());

            // Step 2: Once the request is made, fetch the status from /bulk-status
            fetchBulkStatus();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error while making bulk translate request.", e);
        }
    }

    // Step 2: GET from /bulk-status to retrieve the status of bulk translation
    public List<StatusResponse> fetchBulkStatus() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/bulk-status"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Assuming the response is an array of StatusResponse objects
            List<StatusResponse> statusResponses = Arrays.asList(objectMapper.readValue(response.body(), StatusResponse[].class));
            return statusResponses;

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching bulk status.", e);
        }

    }

    public StatusResponse getVideoStatus(String videoName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/status?videoName=" + videoName))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), StatusResponse.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize response.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching video status.", e);
        }
    }
}
