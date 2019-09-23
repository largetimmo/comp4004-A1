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

import java.io.*;
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

    @Autowired
    @Qualifier("userSim1")
    private BufferedWriter bw1;

    @Autowired
    @Qualifier("userSim2")
    private BufferedWriter bw2;

    @Autowired
    @Qualifier("userSim3")
    private BufferedWriter bw3;


    @Test
    public void gameShouldStartWithFirstPlayer() throws Exception{
        Thread.sleep(1000);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleStartRound(Mockito.any());
        Mockito.verify(clientGameManager1,Mockito.times(0)).handleRollDice(Mockito.any());
        bw1.newLine();
        bw1.flush();
        Thread.sleep(1000);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleRollDice(Mockito.any());
    }

    @Test
    public void onlyOnePlayerWillReceiveStartRoundMessageAtATime()throws Exception{
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleStartRound(Mockito.any());
        Mockito.verify(clientGameManager2,Mockito.times(0)).handleStartRound(Mockito.any());
        Mockito.verify(clientGameManager3,Mockito.times(0)).handleStartRound(Mockito.any());
    }

    @Test
    public void player1CanFinishHisRound() throws Exception{
        bw1.write("\n3\n1\n");
        bw1.flush();
        Thread.sleep(1000);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleStartRound(Mockito.any());
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleRollDice(Mockito.any());
        Mockito.verify(serverGameManager,Mockito.times(1)).handleFillScore(Mockito.eq(clientGameManager1.getCurrentPlayer().getPlayerId()),Mockito.any());
    }

    @Test
    public void playerHoldDicesShallWorkingCorrectly() throws Exception{
        bw1.newLine();
        bw1.flush();
        Thread.sleep(1000);
        List<Integer> dices = serverGameManager.getPlayers().get(0).getCurrentDice();
        ArgumentCaptor<List> diceCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(clientGameManager1,Mockito.times(1)).printDice(diceCaptor.capture());
        for(int i = 0; i< dices.size();i++){
            Assert.assertEquals(dices.get(i),diceCaptor.getValue().get(i));
        }
        bw1.write("1\n0 1 2\n");
        bw1.flush();
        Thread.sleep(2000);
        Mockito.verify(clientGameManager1,Mockito.times(2)).printDice(diceCaptor.capture());
        for(int i =0; i< serverGameManager.getPlayers().get(0).getCurrentDice().size();i++){
            Assert.assertEquals(serverGameManager.getPlayers().get(0).getCurrentDice().get(i),diceCaptor.getAllValues().get(2).get(i));
        }
    }

    @Test
    public void firstPlayerCanPlaySecondRoundAfterAllThreePlayerFinished() throws Exception{
        bw1.newLine();
        bw1.write("3\n1\n");
        bw1.flush();
        Thread.sleep(2000);
        Mockito.verify(clientGameManager1,Mockito.times(1)).handleStartRound(Mockito.any());
        bw2.newLine();
        bw2.write("3\n1\n");
        bw2.flush();
        Thread.sleep(2000);
        Mockito.verify(clientGameManager2,Mockito.times(1)).handleStartRound(Mockito.any());
        bw3.newLine();
        bw3.write("3\n1\n");
        bw3.flush();
        Thread.sleep(2000);
        Mockito.verify(clientGameManager3,Mockito.times(1)).handleStartRound(Mockito.any());
        Mockito.verify(clientGameManager1,Mockito.times(2)).handleStartRound(Mockito.any());

    }

   


}
