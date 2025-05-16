package com.website.loveconnect.service;

import com.website.loveconnect.dto.request.MatchRequestDTO;
import com.website.loveconnect.dto.response.MatchBySenderResponse;
import com.website.loveconnect.dto.response.MatchMatchIdResponse;
import com.website.loveconnect.dto.response.MatchResponse;
import com.website.loveconnect.dto.response.UserMatchedResponse;
import com.website.loveconnect.entity.User;
import com.website.loveconnect.enumpackage.MatchStatus;

import java.util.List;

public interface MatchService {
    MatchResponse createMatch(MatchRequestDTO matchRequestDTO);
    List<MatchResponse> getMatchesByUser(Integer userId);
    MatchResponse updateMatchStatus(Integer matchId, MatchStatus status);
    void createMatchByLike(User sender, User receiver);
    List<MatchBySenderResponse> getAllMatchBySenderId(int userId,int page,int size);
    MatchMatchIdResponse getMatchMatchId(int matchId);
    List<UserMatchedResponse> getAllUserMatched(int userId,int page,int size);
}
