package me.largetimmo.comp4004.a1;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"server.port=39989","app.mode=server"})
public class ServerGameManagerTest {

    @Autowired
    private ServerGameManager serverGameManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerDTOMapper playerDTOMapper;

    private ClientMessageIOController clientMessageIOController;

    private ClientGameManager clientGameManager;


    @After
    public void clean() throws Exception {
        serverGameManager.getPlayers().clear();
    }

    @Test
    public void handleMessage() throws Exception{
        File f = TestUtil.createTempFileWithContent(Arrays.asList("kyle","y"));
        clientGameManager = Mockito.spy(new ClientGameManager(objectMapper,playerDTOMapper,new FileInputStream(f)));
        clientMessageIOController = Mockito.spy(new ClientMessageIOController("localhost", 39989, clientGameManager));
        Thread.sleep(1000);
        Mockito.verify(clientGameManager,Mockito.times(1)).handleReady(Mockito.any());
        Assert.assertEquals(clientGameManager.getPlayers().size(),1);
        Mockito.verify(clientGameManager,Mockito.times(1)).printScoreBoard(Mockito.any());

    }

}
