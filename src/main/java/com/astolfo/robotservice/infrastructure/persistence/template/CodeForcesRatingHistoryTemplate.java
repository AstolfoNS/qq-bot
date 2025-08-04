package com.astolfo.robotservice.infrastructure.persistence.template;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesRatingHistory;
import com.astolfo.robotservice.infrastructure.common.utils.MessagesUtil;
import com.astolfo.robotservice.infrastructure.common.utils.TimeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeForcesRatingHistoryTemplate {

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

    public Messages bodyToMessages(CodeForcesRatingHistory codeForcesRatingHistory) throws JsonProcessingException {
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
                codeForcesRatingHistory.getContestId(),
                codeForcesRatingHistory.getContestName(),
                codeForcesRatingHistory.getHandle(),
                codeForcesRatingHistory.getRank(),
                timeConverter.secondsToDateString(codeForcesRatingHistory.getRatingUpdateTimeSeconds()),
                codeForcesRatingHistory.getOldRating(),
                codeForcesRatingHistory.getNewRating()
        ));
    }

    public Messages contextToMessages(List<CodeForcesRatingHistory> codeForcesRatingHistoryList, int skipCount) {
            return MessagesUtil.merge(
                    codeForcesRatingHistoryList
                            .stream()
                            .skip(skipCount)
                            .map(codeForcesRatingHistory -> {
                                try {
                                    return this.bodyToMessages(codeForcesRatingHistory);
                                } catch (JsonProcessingException exception) {
                                    throw new RuntimeException(exception);
                                }
                            })
                            .toArray(Messages[]::new)
            );
    }

    public Messages toMessages(
            List<CodeForcesRatingHistory> codeForcesRatingHistoryList,
            String handle,
            int skipCount
    ) {
        return MessagesUtil.merge(titleToMessages(handle, codeForcesRatingHistoryList.size()), contextToMessages(codeForcesRatingHistoryList, skipCount));
    }

}
