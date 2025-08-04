package com.astolfo.robotservice.infrastructure.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CodeForcesRatingHistory {

    private int contestId;

    private String contestName;

    private String handle;

    private int rank;

    private long ratingUpdateTimeSeconds;

    private int oldRating;

    private int newRating;


}
