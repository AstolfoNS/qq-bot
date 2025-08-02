package com.astolfo.robotservice.robot.domain.codeforces.model.dto;

import lombok.Data;

@Data
public class UserInfo {

    private String handle;

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String organization;

    private int contribution;

    private int rating;

    private int maxRating;

    private String rank;

    private String maxRank;

    private long lastOnlineTimeSeconds;

    private long registrationTimeSeconds;

    private int friendOfCount;

    private String avatar;

    private String titlePhoto;
}
