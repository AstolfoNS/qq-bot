package com.astolfo.robotservice.infrastructure.persistence.template;

import com.astolfo.robotservice.infrastructure.common.utils.EventUtils;
import com.astolfo.robotservice.infrastructure.common.utils.TimeConverterUtils;
import com.astolfo.robotservice.infrastructure.persistence.model.dto.LoliconPictureInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoliconPictureInfoTemplate {

    @Resource
    private TimeConverterUtils timeConverterUtils;


    public Messages bodytoMessages(LoliconPictureInfoDTO loliconPictureInfoDTO) throws JsonProcessingException {
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
                        loliconPictureInfoDTO.getUrls().getOriginal(),
                        loliconPictureInfoDTO.getR18(),
                        loliconPictureInfoDTO.getTitle(),
                        loliconPictureInfoDTO.getAuthor(),
                        String.join(",\n    ", loliconPictureInfoDTO.getTags()),
                        timeConverterUtils.millisToDateString(loliconPictureInfoDTO.getUploadDate())
                )))
                .build();
    }

    public Messages toMessages(List<LoliconPictureInfoDTO> loliconPictureInfoDTOList) throws JsonProcessingException {
         return EventUtils.merge(
                 loliconPictureInfoDTOList
                         .stream()
                         .map(loliconPictureInfoDTO -> {
                             try {
                                 return this.bodytoMessages(loliconPictureInfoDTO);
                             } catch (JsonProcessingException exception) {
                                 throw new RuntimeException(exception);
                             }
                         })
                         .toArray(Messages[]::new)
         );
    }

}
