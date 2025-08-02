package com.astolfo.robotservice.robot.domain.lolicon.model.template;

import com.astolfo.robotservice.infrastructure.utils.MessagesUtil;
import com.astolfo.robotservice.infrastructure.utils.TimeConverter;
import com.astolfo.robotservice.robot.domain.lolicon.model.dto.PhotoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.OfflineURIImage;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class PhotoInfoTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages bodytoMessages(PhotoInfo photoInfo) throws JsonProcessingException {
        return Messages
                .builder()
                .add(Text.of(String.format(
                        """
                        
                        - link: [%s]
                        - r18: %s
                        - title: %s
                        - author: %s
                        - tags: [
                            %s
                        ]
                        - uploadDate: %s
                        """,
                        photoInfo.getUrls().getOriginal(),
                        photoInfo.getR18(),
                        photoInfo.getTitle(),
                        photoInfo.getAuthor(),
                        String.join(",\n    ", photoInfo.getTags()),
                        timeConverter.millisToDateString(photoInfo.getUploadDate())
                )))
                .build();
    }

    public Messages toMessages(List<PhotoInfo> photoInfoList) throws JsonProcessingException {
         return MessagesUtil.merge(
                 photoInfoList
                         .stream()
                         .map(photoInfo -> {
                             try {
                                 return this.bodytoMessages(photoInfo);
                             } catch (JsonProcessingException exception) {
                                 throw new RuntimeException(exception);
                             }
                         })
                         .toArray(Messages[]::new)
         );
    }

}
