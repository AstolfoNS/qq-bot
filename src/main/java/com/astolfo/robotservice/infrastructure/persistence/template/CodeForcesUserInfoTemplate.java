package com.astolfo.robotservice.infrastructure.persistence.template;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesUserInfoDTO;
import com.astolfo.robotservice.infrastructure.common.utils.TimeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.OfflineURIImage;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class CodeForcesUserInfoTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages toMessages(CodeForcesUserInfoDTO codeForcesUserInfoDTO) throws JsonProcessingException {
        return Messages
                .builder()
                .add(OfflineURIImage.of(URI.create(codeForcesUserInfoDTO.getAvatar())))
                .add(Text.of(String.format(
                        """
                        
                        >>>https://codeforces.com/profile/%s<<<
                        - username: %s
                        - contribution: %d
                        - friendOfCount: %d
                        - rank: %s
                        - rating: %d
                        - maxRating: %d
                        %s, last logged in %s.
                        """,
                        codeForcesUserInfoDTO.getHandle(),
                        codeForcesUserInfoDTO.getHandle(),
                        codeForcesUserInfoDTO.getContribution(),
                        codeForcesUserInfoDTO.getFriendOfCount(),
                        codeForcesUserInfoDTO.getRank(),
                        codeForcesUserInfoDTO.getRating(),
                        codeForcesUserInfoDTO.getMaxRating(),
                        codeForcesUserInfoDTO.getHandle(), timeConverter.secondsToDateString(codeForcesUserInfoDTO.getLastOnlineTimeSeconds())
               )))
               .build();
    }

    public List<Messages> toMessages(List<CodeForcesUserInfoDTO> codeForcesUserInfoDTOList) {
        return codeForcesUserInfoDTOList
                .stream()
                .map(codeForcesUserInfoDTO -> {
                    try {
                        return this.toMessages(codeForcesUserInfoDTO);
                    } catch (JsonProcessingException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .toList();
    }

}
