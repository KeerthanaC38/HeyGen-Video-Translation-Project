package com.heygen.videotranslation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class JobStatus {

        private String result;

        public JobStatus(String result) {
            System.out.println("JobStatus result: " + result);  // Add this for debugging

            this.result = result;
        }

}


