package com.astolfo.robotservice.infrastructure.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoliconPictureInfo {

    private long pid;

    private int p;

    private long uid;

    private String title;

    private String author;

    private String r18;

    private int width;

    private int height;

    private List<String> tags;

    private String ext;

    private int aiType;

    private long uploadDate;

    private LoliconUrls loliconUrls;
}
