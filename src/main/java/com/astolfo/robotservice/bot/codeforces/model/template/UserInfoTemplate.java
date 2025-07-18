package com.astolfo.robotservice.bot.codeforces.model.template;

import com.astolfo.robotservice.bot.codeforces.model.dto.UserInfo;
import com.astolfo.robotservice.infrastructure.utils.TimeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import love.forte.simbot.message.OfflineURIImage;
import love.forte.simbot.message.Text;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class UserInfoTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages toMessages(UserInfo userInfo) throws JsonProcessingException {
        return Messages
                .builder()
                .add(OfflineURIImage.of(URI.create(userInfo.getAvatar())))
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
                        userInfo.getHandle(),
                        userInfo.getHandle(),
                        userInfo.getContribution(),
                        userInfo.getFriendOfCount(),
                        userInfo.getRank(),
                        userInfo.getRating(),
                        userInfo.getMaxRating(),
                        userInfo.getHandle(), timeConverter.secondsToDateString(userInfo.getLastOnlineTimeSeconds())
               )))
               .build();
    }

    public List<Messages> toMessages(List<UserInfo> userInfoList) {
        return userInfoList
                .stream()
                .map(userInfo -> {
                    try {
                        return this.toMessages(userInfo);
                    } catch (JsonProcessingException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .toList();
    }

}
