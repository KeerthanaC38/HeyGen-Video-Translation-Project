package com.heygen.translation_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heygen.translation_client.model.StatusResponse;
import com.heygen.translation_client.model.TranslateVideoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VideoTranslationClientTest {

    private static final String BASE_URL = "http://localhost:8080"; // Replace with your server URL
    private final VideoTranslationClient client = new VideoTranslationClient(BASE_URL);

    @Test
    void testBulkTranslationRequest() {
        List<TranslateVideoRequest> requests = List.of(
                new TranslateVideoRequest("video1.mp4", "en-US", "espanol"),
                new TranslateVideoRequest("video2.mp4", "en-US", "hindi"),
                new TranslateVideoRequest("video3.mp4", "en-US", "chinese"),
                new TranslateVideoRequest("video4.mp4", "en-US", "french")
        );
        client.postBulkTranslateVideos(requests);
//        try {
//            var bulkStatus = client.fetchBulkStatus();
//            assertNotNull(bulkStatus, "Bulk status response should not be null");
//
//            bulkStatus.forEach(status -> {
//                assertNotNull(status.getVideoName(), "Video name should not be null");
//                assertNotNull(status.getResult(), "Result should not be null");
//                // Additional assertions for fields
//            });
//
//            bulkStatus.forEach(status -> {
//                assertNotNull(status.getVideoName());
//                assertNotNull(status.getResult());
//                assertNotNull(status.getMessage());
//                assertNotNull(status.getElapsedTime());
//
//
//                // Print all fields of the status response
//                System.out.println("Bulk Status:");
//                System.out.println("Video Name: " + status.getVideoName());
//                System.out.println("Video Language: " + status.getVideoLanguage());
//                System.out.println("Translate To: " + status.getTranslateTo());
//                System.out.println("Result: " + status.getResult());
//                System.out.println("Message: " + status.getMessage());
//                System.out.println("Percentage: " + status.getPercentage() + "%");
//                System.out.println("Elapsed Time: " + status.getElapsedTime());
//                System.out.println("Remaining Time: " + status.getRemainingTime());
//                System.out.println("Total Time: " + status.getTotalTime());
//            });
//        } catch (Exception e) {
//            fail("Bulk translation request failed: " + e.getMessage());
//        }
        int iterations = 10;
        int intervalInSeconds = 10;
        for (int i = 0; i < iterations; i++) {
            try {
                System.out.println("Fetching bulk status - Attempt " + (i + 1));
                var bulkStatus = client.fetchBulkStatus();
                assertNotNull(bulkStatus, "Bulk status should not be null");

                bulkStatus.forEach(status -> {
                    System.out.println("Video Name: " + status.getVideoName());
                    System.out.println("Video Language: " + status.getVideoLanguage());
                    System.out.println("Translate To: " + status.getTranslateTo());
                    System.out.println("Result: " + status.getResult());
                    System.out.println("Message: " + status.getMessage());
                    System.out.println("Percentage: " + status.getPercentage() + "%");
                    System.out.println("Elapsed Time: " + status.getElapsedTime());
                    System.out.println("Remaining Time: " + status.getRemainingTime());
                    System.out.println("Total Time: " + status.getTotalTime());
                    System.out.println("---------------");
                });

                if (i < iterations - 1) {
                    // Wait for the specified interval before the next iteration
                    Thread.sleep(intervalInSeconds * 1000L);
                }
            } catch (Exception e) {
                fail("Fetching bulk status failed at iteration " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    @Test
    void testFetchBulkStatus() {
        try {
            var bulkStatus = client.fetchBulkStatus();
            assertNotNull(bulkStatus, "Bulk status should not be null");
        } catch (Exception e) {
            fail("Fetching bulk status failed: " + e.getMessage());
        }
    }

    @Test
    void testIndividualVideoStatus() {
        String videoName = "video1.mp4";
        try {
            var videoStatus = client.getVideoStatus(videoName);
            assertNotNull(videoStatus, "Video status should not be null");
            assertEquals(videoName, videoStatus.getVideoName(), "Video name mismatch");
        } catch (Exception e) {
            fail("Fetching individual video status failed: " + e.getMessage());
        }
    }
}



