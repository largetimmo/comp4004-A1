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
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
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

    private static File inputFile1;

    private static File inputFile2;

    private static File inputFile3;

    @BeforeClass
    public static void setupInput(){
        inputFile1 = TestUtil.createTempFileWithContent(Arrays.asList("Kyle1", "y","","1","0 1 2 3 4"));
        inputFile2 = TestUtil.createTempFileWithContent(Arrays.asList("Kyle2", "y"));
        inputFile3 = TestUtil.createTempFileWithContent(Arrays.asList("Kyle3", "y"));
    }


    @TestConfiguration
    static class Config {

        @Primary
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Primary
        @Bean("serverController")
        public ServerMessageIOController serverMessageIOController(ServerGameManager serverGameManager) throws IOException {
            return Mockito.spy(new ServerMessageIOController(36666, serverGameManager));
        }

        @Primary
        @Bean
        public ServerGameManager serverGameManager(ObjectMapper mapper, PlayerDTOMapper playerDTOMapper) {
            return Mockito.spy(new ServerGameManager(mapper, playerDTOMapper));
        }

        @Primary
        @Bean(name = "client-controller1")
        @DependsOn("serverController")
        public ClientMessageIOController clientMessageIOController(@Qualifier("client1") ClientGameManager clientGameManager) throws Exception {
            return Mockito.spy(new ClientMessageIOController("localhost", 36666, clientGameManager));
        }

        @Primary
        @Bean(name = "client1")
        @DependsOn("serverController")
        public ClientGameManager clientGameManager(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper) throws Exception {
            return Mockito.spy(new ClientGameManager(objectMapper, playerDTOMapper, new FileInputStream(inputFile1)));
        }

        @Primary
        @Bean(name = "client-controller2")
        @DependsOn("serverController")
        public ClientMessageIOController clientMessageIOController2(@Qualifier("client2")ClientGameManager clientGameManager) throws Exception {
            return new ClientMessageIOController("localhost", 36666, clientGameManager);
        }

        @Primary
        @Bean(name = "client2")
        @DependsOn("serverController")
        public ClientGameManager clientGameManager2(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper) throws Exception {
            return new ClientGameManager(objectMapper, playerDTOMapper,new FileInputStream(inputFile2));
        }

        @Primary
        @Bean(name = "client-controller3")
        @DependsOn("serverController")
        public ClientMessageIOController clientMessageIOController3(@Qualifier("client3")ClientGameManager clientGameManager) throws Exception {
            return new ClientMessageIOController("localhost", 36666, clientGameManager);
        }

        @Primary
        @Bean(name = "client3")
        @DependsOn("serverController")
        public ClientGameManager clientGameManager3(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper) throws Exception {
            return new ClientGameManager(objectMapper, playerDTOMapper,new FileInputStream(inputFile3));
        }
    }

    @Test
    public void testGame() throws Exception{
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
