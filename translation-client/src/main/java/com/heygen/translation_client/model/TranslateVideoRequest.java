package com.heygen.translation_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class TranslateVideoRequest {
    // Getters and Setters
    @JsonProperty("videoName")
    private String videoName;
    @JsonProperty("videoLanguage")
    private String videoLanguage;
    @JsonProperty("translateTo")
    private String translateTo;

    public TranslateVideoRequest(String videoName, String videoLanguage, String translateTo) {
        this.videoName = videoName;
        this.videoLanguage = videoLanguage;
        this.translateTo = translateTo;
    }
}
