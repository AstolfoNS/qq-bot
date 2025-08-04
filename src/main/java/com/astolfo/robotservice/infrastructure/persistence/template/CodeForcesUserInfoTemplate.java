package com.astolfo.robotservice.infrastructure.persistence.template;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesUserInfo;
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


    public Messages toMessages(CodeForcesUserInfo codeForcesUserInfo) throws JsonProcessingException {
        return Messages
                .builder()
                .add(OfflineURIImage.of(URI.create(codeForcesUserInfo.getAvatar())))
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
                        codeForcesUserInfo.getHandle(),
                        codeForcesUserInfo.getHandle(),
                        codeForcesUserInfo.getContribution(),
                        codeForcesUserInfo.getFriendOfCount(),
                        codeForcesUserInfo.getRank(),
                        codeForcesUserInfo.getRating(),
                        codeForcesUserInfo.getMaxRating(),
                        codeForcesUserInfo.getHandle(), timeConverter.secondsToDateString(codeForcesUserInfo.getLastOnlineTimeSeconds())
               )))
               .build();
    }

    public List<Messages> toMessages(List<CodeForcesUserInfo> codeForcesUserInfoList) {
        return codeForcesUserInfoList
                .stream()
                .map(codeForcesUserInfo -> {
                    try {
                        return this.toMessages(codeForcesUserInfo);
                    } catch (JsonProcessingException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .toList();
    }

}
