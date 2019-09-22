package me.largetimmo.comp4004.a1;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AutoTestContext.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class GameAutoTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ServerMessageIOController serverMessageIOController;

    @Autowired
    private ServerGameManager serverGameManager;

    @Autowired
    @Qualifier("client-controller1")
    private ClientMessageIOController clientMessageIOController1;

    @Autowired
    @Qualifier("client-controller2")
    private ClientMessageIOController clientMessageIOController2;

    @Autowired
    @Qualifier("client-controller3")
    private ClientMessageIOController clientMessageIOController3;

    @Autowired
    @Qualifier("client1")
    private ClientGameManager clientGameManager1;

    @Autowired
    @Qualifier("client2")
    private ClientGameManager clientGameManager2;

    @Autowired
    @Qualifier("client3")
    private ClientGameManager clientGameManager3;


    @Test
    public void testGame() throws Exception{
        Thread.sleep(1000);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleReady(Mockito.any());
        Assert.assertEquals(clientGameManager1.getPlayers().size(),3);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleSyncPlayer(Mockito.any());
        Mockito.verify(clientGameManager1,Mockito.times(3)).printScoreBoard(Mockito.any());
        Thread.sleep(1000);
        String firstPlayerId = serverGameManager.getPlayers().get(0).getPlayerId();
        Mockito.verify(serverGameManager,Mockito.times(1)).tellPlayerRoundStart(firstPlayerId);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleStartRound(Mockito.any());
        Mockito.verify(serverGameManager,Mockito.times(1)).handleRollDice(Mockito.eq(firstPlayerId),Mockito.any());
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleRollDice(Mockito.any());
        Mockito.verify(clientGameManager1,Mockito.times(1)).printMenu();
        ArgumentCaptor<List> dices = ArgumentCaptor.forClass(List.class);
        Mockito.verify(clientGameManager1,Mockito.times(1)).printDice(dices.capture());
        for (int i =0; i < 5;i++){
            //Make sure both server and client have the same position and the same value for each dice
            Assert.assertEquals(dices.getValue().get(i), serverGameManager.getPlayers().get(0).getCurrentDice().get(i));
        }
        Mockito.verify(clientGameManager1,Mockito.times(1)).printInstructionForSectionOne();
        ArgumentCaptor<BasicDTO> dtoArgumentCaptor  = ArgumentCaptor.forClass(BasicDTO.class);
        Mockito.verify(serverGameManager,Mockito.times(1)).handleKeepDice(Mockito.eq(firstPlayerId),dtoArgumentCaptor.capture());
        Assert.assertEquals("0,1,2,3,4",dtoArgumentCaptor.getValue().getData());



    }

}
