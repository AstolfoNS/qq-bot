package com.astolfo.robotservice.robot.domain.lolicon.model.template;

import com.astolfo.robotservice.infrastructure.utils.MessagesUtil;
import com.astolfo.robotservice.infrastructure.utils.TimeConverter;
import com.astolfo.robotservice.robot.domain.lolicon.model.dto.PictureInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PictureInfoTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages bodytoMessages(PictureInfo pictureInfo) throws JsonProcessingException {
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
                        pictureInfo.getUrls().getOriginal(),
                        pictureInfo.getR18(),
                        pictureInfo.getTitle(),
                        pictureInfo.getAuthor(),
                        String.join(",\n    ", pictureInfo.getTags()),
                        timeConverter.millisToDateString(pictureInfo.getUploadDate())
                )))
                .build();
    }

    public Messages toMessages(List<PictureInfo> pictureInfoList) throws JsonProcessingException {
         return MessagesUtil.merge(
                 pictureInfoList
                         .stream()
                         .map(pictureInfo -> {
                             try {
                                 return this.bodytoMessages(pictureInfo);
                             } catch (JsonProcessingException exception) {
                                 throw new RuntimeException(exception);
                             }
                         })
                         .toArray(Messages[]::new)
         );
    }

}
