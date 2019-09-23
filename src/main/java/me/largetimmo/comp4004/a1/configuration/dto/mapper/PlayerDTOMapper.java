package me.largetimmo.comp4004.a1.configuration.dto.mapper;

import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import org.springframework.stereotype.Service;

@Service
public class PlayerDTOMapper {

    public PlayerDTO map(PlayerBO bo){
        if(bo==null) {
            return null;
        }
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerId(bo.getPlayerId());
        playerDTO.setPlayerName(bo.getPlayerName());
        playerDTO.setScoreSheet(bo.getScoreSheet());
        playerDTO.setRound(bo.getRound());
        return playerDTO;
    }

    public PlayerBO map(PlayerDTO dto){
        if(dto == null){
            return null;
        }
        PlayerBO playerBO = new PlayerBO();
        playerBO.setPlayerId(dto.getPlayerId());
        playerBO.setScoreSheet(dto.getScoreSheet());
        playerBO.setPlayerName(dto.getPlayerName());
        playerBO.setRound(dto.getRound());
        return playerBO;
    }

}
