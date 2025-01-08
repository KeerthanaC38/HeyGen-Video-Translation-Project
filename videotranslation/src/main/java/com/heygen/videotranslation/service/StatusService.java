package com.heygen.videotranslation.service;

import com.heygen.videotranslation.controller.StatusController;
import com.heygen.videotranslation.model.StatusResponse;
import io.micrometer.common.util.internal.logging.InternalLogger;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.Instant;

@Service
public class StatusService {
    private final Instant startTime = Instant.now();
    public final long processingTime = 20; // Example processing time in seconds
    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);


    public StatusResponse getBulkStatus(String videoName, String videoLanguage, String translateTo, String result, Instant startTime) {
        Instant currentTime = Instant.now();
        if (startTime == null) {
            logger.info("Processing complete for video {}", videoName);
            return new StatusResponse(videoName, videoLanguage, translateTo, "Completed", "Successfully processed", 100, "20s", "0s", "20s");
        }
        long elapsedTime = Duration.between(startTime, currentTime).getSeconds();
        if (elapsedTime < 0) elapsedTime = 0;
        if ("In queue".equals(result)) {
            return new StatusResponse(videoName, videoLanguage, translateTo, "In queue", "Video is in queue for processing.",
                    0, elapsedTime + "s", "N/A", "N/A");
        }

        if ("Processing".equals(result)) {
            int percentage = (int) Math.min(((double) elapsedTime / processingTime) * 100, 100); // Cap percentage at 100
            return new StatusResponse(videoName, videoLanguage, translateTo, "pending", "Processing video, please wait.",
                    percentage, elapsedTime + "s", Math.max((processingTime - elapsedTime), 0)+"s", "N/A");
        }

        if ("Completed".equals(result)) {
            return new StatusResponse(videoName, videoLanguage, translateTo, "completed", "Processing complete!",
                    100, elapsedTime + "s", "0s", processingTime + "s");
        }

        return new StatusResponse(videoName, videoLanguage, translateTo, "error", "An error occurred during processing.",
                0, elapsedTime + "s", "N/A", "N/A");
    }
}