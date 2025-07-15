package com.astolfo.robotservice.codeforces.template;

import com.astolfo.robotservice.codeforces.model.dto.RatingHistory;
import com.astolfo.robotservice.common.infrastructure.utils.MessagesUtil;
import com.astolfo.robotservice.common.infrastructure.utils.TimeConverter;
import com.astolfo.robotservice.common.template.StringTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingHistoryTemplate {

    @Resource
    private TimeConverter timeConverter;


    public Messages titleToMessages(String handle, int total) {
        return StringTemplate.toMessages(String.format(
                """
                %s, total number of contests joined: %d
                
                """,
                handle, total
        ));
    }

    public Messages bodyToMessages(RatingHistory ratingHistory) throws JsonProcessingException {
        return StringTemplate.toMessages(String.format(
                """
                >>>https://codeforces.com/contest/%d<<<
                - contestName: %s
                - username: %s
                - rank: %d
                - ratingUpdateTime: %s
                - oldRating: %d
                - newRating: %d
               
                """,
                ratingHistory.getContestId(),
                ratingHistory.getContestName(),
                ratingHistory.getHandle(),
                ratingHistory.getRank(),
                timeConverter.secondsToDateString(ratingHistory.getRatingUpdateTimeSeconds()),
                ratingHistory.getOldRating(),
                ratingHistory.getNewRating()
        ));
    }

    public Messages contextToMessages(List<RatingHistory> ratingHistoryList, int skipCount) {
            return MessagesUtil.merge(
                    ratingHistoryList
                            .stream()
                            .skip(skipCount)
                            .map(ratingHistory -> {
                                try {
                                    return this.bodyToMessages(ratingHistory);
                                } catch (JsonProcessingException exception) {
                                    throw new RuntimeException(exception);
                                }
                            })
                            .toArray(Messages[]::new)
            );
    }

    public Messages toMessages(List<RatingHistory> ratingHistoryList, String handle, int skipCount) {
        return MessagesUtil.merge(titleToMessages(handle, ratingHistoryList.size()), contextToMessages(ratingHistoryList, skipCount));
    }

}
