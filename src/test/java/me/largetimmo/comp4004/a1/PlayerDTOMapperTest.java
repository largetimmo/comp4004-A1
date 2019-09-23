package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.service.bo.Connection;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;
import org.junit.Assert;
import org.junit.Test;

public class PlayerDTOMapperTest {

    private PlayerDTOMapper mapper = new PlayerDTOMapper();

    @Test
    public void testBoToDto() throws Exception{
        PlayerBO playerBO = new PlayerBO();
        playerBO.setPlayerId("111");
        playerBO.setPlayerName("name");
        playerBO.setScoreSheet(new ScoreSheetBO());
        playerBO.setRound(11);
        PlayerDTO dto = mapper.map(playerBO);
        Assert.assertEquals(dto.getPlayerId(), playerBO.getPlayerId());
        Assert.assertEquals(dto.getPlayerName(), playerBO.getPlayerName());
        Assert.assertEquals(dto.getScoreSheet(), playerBO.getScoreSheet());
        Assert.assertEquals(dto.getRound(),playerBO.getRound());

    }
    @Test
    public void testDtoToBo() throws Exception{
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerId("111");
        playerDTO.setPlayerName("name");
        playerDTO.setScoreSheet(new ScoreSheetBO());
        playerDTO.setRound(12);
        PlayerBO bo = mapper.map(playerDTO);
        Assert.assertEquals(bo.getPlayerId(), playerDTO.getPlayerId());
        Assert.assertEquals(bo.getPlayerName(), playerDTO.getPlayerName());
        Assert.assertEquals(bo.getScoreSheet(), playerDTO.getScoreSheet());
        Assert.assertNull(bo.getConnection());
        Assert.assertEquals(playerDTO.getRound(),bo.getRound());

    }

}
