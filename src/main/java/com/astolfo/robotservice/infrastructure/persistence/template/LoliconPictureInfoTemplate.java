package com.astolfo.robotservice.infrastructure.persistence.template;

import com.astolfo.robotservice.infrastructure.common.utils.MessagesUtil;
import com.astolfo.robotservice.infrastructure.common.utils.TimeConverter;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconPictureInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoliconPictureInfoTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages bodytoMessages(LoliconPictureInfo loliconPictureInfo) throws JsonProcessingException {
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
                        loliconPictureInfo.getLoliconUrls().getOriginal(),
                        loliconPictureInfo.getR18(),
                        loliconPictureInfo.getTitle(),
                        loliconPictureInfo.getAuthor(),
                        String.join(",\n    ", loliconPictureInfo.getTags()),
                        timeConverter.millisToDateString(loliconPictureInfo.getUploadDate())
                )))
                .build();
    }

    public Messages toMessages(List<LoliconPictureInfo> loliconPictureInfoList) throws JsonProcessingException {
         return MessagesUtil.merge(
                 loliconPictureInfoList
                         .stream()
                         .map(loliconPictureInfo -> {
                             try {
                                 return this.bodytoMessages(loliconPictureInfo);
                             } catch (JsonProcessingException exception) {
                                 throw new RuntimeException(exception);
                             }
                         })
                         .toArray(Messages[]::new)
         );
    }

}
