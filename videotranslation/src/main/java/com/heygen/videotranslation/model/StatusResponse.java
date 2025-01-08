package com.heygen.videotranslation.model;

public class StatusResponse {
    private String videoName;
    private String videoLanguage;
    private String translateTo;
    private String result;
    private String message;
    private int percentage;
    private String elapsedTime;
    private String remainingTime;
    private String totalTime;

    // Constructor
    public StatusResponse(String videoName, String videoLanguage, String translateTo, String result, String message,
                          int percentage, String elapsedTime, String remainingTime, String totalTime) {
        this.videoName = videoName;
        this.videoLanguage = videoLanguage;
        this.translateTo = translateTo;
        this.result = result;
        this.message = message;
        this.percentage = percentage;
        this.elapsedTime = elapsedTime;
        this.remainingTime = remainingTime;
        this.totalTime = totalTime;
    }

    // Getters and Setters
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoLanguage() {
        return videoLanguage;
    }

    public void setVideoLanguage(String videoLanguage) {
        this.videoLanguage = videoLanguage;
    }

    public String getTranslateTo() {
        return translateTo;
    }

    public void setTranslateTo(String translateTo) {
        this.translateTo = translateTo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "videoName='" + videoName + '\'' +
                ", videoLanguage='" + videoLanguage + '\'' +
                ", translateTo='" + translateTo + '\'' +
                ", result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", percentage=" + percentage +
                ", elapsedTime='" + elapsedTime + '\'' +
                ", remainingTime='" + remainingTime + '\'' +
                ", totalTime='" + totalTime + '\'' +
                '}';
    }
}