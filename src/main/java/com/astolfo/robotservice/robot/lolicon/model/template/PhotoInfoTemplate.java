package com.astolfo.robotservice.robot.lolicon.model.template;

import com.astolfo.robotservice.infrastructure.utils.TimeConverter;
import com.astolfo.robotservice.robot.lolicon.model.dto.PhotoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.OfflineURIImage;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class PhotoInfoTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages toMessages(PhotoInfo photoInfo) throws JsonProcessingException {
        return Messages
                .builder()
                .add(OfflineURIImage.of(URI.create(photoInfo.getUrls().getOriginal())))
                .add(Text.of(String.format("""
                        - r18: %s
                        - title: %s
                        - author: %s
                        - tags: [
                            %s
                        ]
                        - uploadDate: %s
                        """,
                        photoInfo.getR18(),
                        photoInfo.getTitle(),
                        photoInfo.getAuthor(),
                        String.join(",\n    ", photoInfo.getTags()),
                        timeConverter.millisToDateString(photoInfo.getUploadDate())
                )))
                .build();
    }

}
