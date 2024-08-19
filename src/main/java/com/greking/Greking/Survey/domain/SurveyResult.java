package com.greking.Greking.Survey.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SurveyResult {
    @NotNull
    private int questionAnswer1;
    @NotNull
    private int questionAnswer2;
    @NotNull
    private int questionAnswer3;
    @NotNull
    private int questionAnswer4;
    @NotNull
    private int questionAnswer5;

}
