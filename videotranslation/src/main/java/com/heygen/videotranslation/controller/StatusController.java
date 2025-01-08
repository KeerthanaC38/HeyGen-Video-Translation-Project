package com.heygen.videotranslation.controller;



import com.heygen.videotranslation.model.StatusResponse;
import com.heygen.videotranslation.model.TranslateVideoRequest;
import com.heygen.videotranslation.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @Autowired
    private StatusService statusService;

    private final ConcurrentMap<String, String> videoStatusQueue = new ConcurrentHashMap<>();
    private final BlockingQueue<TranslateVideoRequest> videoQueue = new LinkedBlockingQueue<>();
    private final ConcurrentMap<String, String[]> videoLanguageDetails = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Instant> videoStartTimeMap = new ConcurrentHashMap<>();

    @PostMapping("/translate-video-bulk")
    public String startBulkTranslation(@RequestBody TranslateVideoRequest[] requests) {
        for (TranslateVideoRequest request : requests) {
            videoQueue.add(request);
            videoStatusQueue.put(request.getVideoName(), "In queue");
            videoLanguageDetails.put(request.getVideoName(), new String[]{request.getVideoLanguage(), request.getTranslateTo()});
            videoStartTimeMap.put(request.getVideoName(), Instant.now());
        }
        processBulkVideos(requests);
        return "Bulk video translation started.";
    }

    @GetMapping("/bulk-status")
    public List<StatusResponse> getBulkStatus() {
        List<StatusResponse> bulkStatusResponse = new ArrayList<>();

        // Iterate through all videos in videoStatusQueue
        videoStatusQueue.forEach((videoName, status) -> {
            String[] languageDetails = videoLanguageDetails.get(videoName);
            String videoLanguage = languageDetails != null ? languageDetails[0] : "en-US"; // Default to "en-US"
            String translateTo = languageDetails != null ? languageDetails[1] : "espanol"; // Default to "espanol"
            Instant startTime = videoStartTimeMap.get(videoName);

            // Fetch the detailed status using the service
            StatusResponse response = statusService.getBulkStatus(videoName, videoLanguage, translateTo, status, startTime);
            bulkStatusResponse.add(response);
        });

        return bulkStatusResponse;
    }


    @GetMapping("/status")
    public StatusResponse getStatus(@RequestParam String videoName) {
        String status = videoStatusQueue.get(videoName);
        String[] languageDetails = videoLanguageDetails.get(videoName);
        String videoLanguage = languageDetails != null ? languageDetails[0] : "en-US"; // Default to "en-US"
        String translateTo = languageDetails != null ? languageDetails[1] : "espanol"; // Default to "espanol"
        Instant startTime = videoStartTimeMap.get(videoName);

        if (status == null) {
            return new StatusResponse(videoName, videoLanguage, translateTo, "error", "Video not found.",
                    0, "N/A", "N/A", null);
        }
        return statusService.getBulkStatus(videoName, videoLanguage, translateTo, status, startTime);
    }

    private void processBulkVideos(TranslateVideoRequest[] requests) {
        logVideoSet("Initial status", requests);

        new Thread(() -> {
            while (!videoQueue.isEmpty()) {
                try {
                    TranslateVideoRequest request = videoQueue.take();
                    Instant videoStartTime = Instant.now(); // Set start time for the video
                    videoStartTimeMap.put(request.getVideoName(), videoStartTime);
                    videoStatusQueue.put(request.getVideoName(), "Processing");

                    for (int i = 1; i <= 3; i++) {
                        Thread.sleep(statusService.processingTime*1000/ 3); // Simulate processing for 33%, 66%, 100%
                        int progress = i * 33;
                        videoStatusQueue.put(request.getVideoName(), "Processing");
                        logVideoSet("Processing " + progress + "% of " + request.getVideoName(), requests);
                    }

                    videoStatusQueue.put(request.getVideoName(), "Completed");
                    logVideoSet("After completing " + request.getVideoName(), requests);
                    videoStartTimeMap.remove(request.getVideoName());

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Processing interrupted for video", e);
                }
            }
        }).start();
    }


    private void logVideoSet(String logHeader, TranslateVideoRequest[] requests) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("====================VideoSet============================\n");
        for (TranslateVideoRequest request : requests) {
            String status = videoStatusQueue.get(request.getVideoName());
            StatusResponse response = statusService.getBulkStatus(request.getVideoName(), request.getVideoLanguage(), request.getTranslateTo(), status, videoStartTimeMap.get(request.getVideoName()));
            logBuilder.append(String.format(
                    "videoName: \"%s\", videoLanguage: \"%s\", translateTo: \"%s\", result: \"%s\", message: \"%s\", percentage: \"%d\", elapsedTime: \"%s\", remainingTime: \"%s\", totalTime: \"%s\"\n",
                    response.getVideoName(), response.getVideoLanguage(), response.getTranslateTo(),
                    response.getResult(), response.getMessage(), response.getPercentage(),
                    response.getElapsedTime(), response.getRemainingTime(), response.getTotalTime()
            ));
        }
        logBuilder.append("============================================================\n");
        logger.info("\n{}\n{}", logHeader, logBuilder);
    }
}
