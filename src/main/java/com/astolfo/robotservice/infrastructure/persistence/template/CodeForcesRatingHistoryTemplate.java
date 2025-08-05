package com.astolfo.robotservice.infrastructure.persistence.template;

import com.astolfo.robotservice.infrastructure.persistence.model.dto.CodeForcesRatingHistoryDTO;
import com.astolfo.robotservice.infrastructure.common.utils.EventUtil;
import com.astolfo.robotservice.infrastructure.common.utils.TimeConverterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import love.forte.simbot.message.Messages;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeForcesRatingHistoryTemplate {

    @Resource
    private TimeConverterUtil timeConverterUtil;


    public Messages titleToMessages(String handle, int total) {
        return StringTemplate.toMessages(String.format(
                """
                
                %s, total number of contests joined: %d
                """,
                handle, total
        ));
    }

    public Messages bodyToMessages(CodeForcesRatingHistoryDTO codeForcesRatingHistoryDTO) throws JsonProcessingException {
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
                codeForcesRatingHistoryDTO.getContestId(),
                codeForcesRatingHistoryDTO.getContestName(),
                codeForcesRatingHistoryDTO.getHandle(),
                codeForcesRatingHistoryDTO.getRank(),
                timeConverterUtil.secondsToDateString(codeForcesRatingHistoryDTO.getRatingUpdateTimeSeconds()),
                codeForcesRatingHistoryDTO.getOldRating(),
                codeForcesRatingHistoryDTO.getNewRating()
        ));
    }

    public Messages contextToMessages(List<CodeForcesRatingHistoryDTO> codeForcesRatingHistoryDTOList, int skipCount) {
            return EventUtil.merge(
                    codeForcesRatingHistoryDTOList
                            .stream()
                            .skip(skipCount)
                            .map(codeForcesRatingHistoryDTO -> {
                                try {
                                    return this.bodyToMessages(codeForcesRatingHistoryDTO);
                                } catch (JsonProcessingException exception) {
                                    throw new RuntimeException(exception);
                                }
                            })
                            .toArray(Messages[]::new)
            );
    }

    public Messages toMessages(
            List<CodeForcesRatingHistoryDTO> codeForcesRatingHistoryDTOList,
            String handle,
            int skipCount
    ) {
        return EventUtil.merge(this.titleToMessages(handle, codeForcesRatingHistoryDTOList.size()), this.contextToMessages(codeForcesRatingHistoryDTOList, skipCount));
    }

}
