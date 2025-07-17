package com.astolfo.robotservice.lolicon.template;

import com.astolfo.robotservice.common.infrastructure.utils.TimeConverter;
import com.astolfo.robotservice.lolicon.model.dto.PhotoInfoV1;
import com.astolfo.robotservice.lolicon.model.dto.PhotoInfoV2;
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


    public Messages toMessages(PhotoInfoV1 photoInfo) {
        return Messages
                .builder()
                .add(OfflineURIImage.of(URI.create(photoInfo.getUrl())))
                .add(Text.of(String.format("""
                        - r18: %s
                        - title: %s
                        - author: %s
                        - tags: [
                            %s
                        ]
                        """,
                        photoInfo.getR18(),
                        photoInfo.getTitle(),
                        photoInfo.getAuthor(),
                        String.join(",\n    ", photoInfo.getTags())
                )))
                .build();
    }

    public Messages toMessages(PhotoInfoV2 photoInfo) throws JsonProcessingException {
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
