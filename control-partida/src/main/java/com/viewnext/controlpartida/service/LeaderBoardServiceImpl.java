package com.viewnext.controlpartida.service;

import org.springframework.stereotype.Service;

import com.viewnext.controlpartida.domain.LeaderBoardRow;
import com.viewnext.controlpartida.repository.ScoreCardRepository;

import java.util.List;

@Service
class LeaderBoardServiceImpl implements LeaderBoardService {

    private ScoreCardRepository scoreCardRepository;

    LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return scoreCardRepository.findFirst10();
    }
}
