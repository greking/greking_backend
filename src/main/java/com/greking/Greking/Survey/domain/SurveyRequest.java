package com.greking.Greking.Survey.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyRequest {

    @NotNull
    private Long userId;

    @NotNull
    private SurveyResult surveyResult;

}
