package com.astolfo.robotservice.codeforces.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RatingHistory {

    private int contestId;

    private String contestName;

    private String handle;

    private int rank;

    private long ratingUpdateTimeSeconds;

    private int oldRating;

    private int newRating;


}
