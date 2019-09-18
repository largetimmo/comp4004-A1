package me.largetimmo.comp4004.a1;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"server.port=39989","app.mode=server"})
public class ServerGameManagerTest {

    @Autowired
    ServerGameManager serverGameManager;

    @After
    public void clean() throws Exception {
        serverGameManager.getPlayers().clear();
    }

    @Test
    public void testInitPlayer() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost",39989));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread.sleep(1000);
        Assert.assertTrue(socket.isConnected());
        PlayerBO playerBO = serverGameManager.initPlayer(socket);
        Assert.assertNotNull(playerBO.getConnection());
        Assert.assertNotNull(playerBO.getPlayerId());
        Assert.assertNotNull(playerBO.getScoreSheet());
        for (int i =0;i<5;i++){
            if (br.ready()){
                break;
            }
            if (i == 4){
                System.out.print("Failed to receive data");
            }
            Thread.sleep(1000);
        }
        Assert.assertTrue(br.ready());
        BasicDTO dto = new ObjectMapper().readValue(br.readLine(),BasicDTO.class);
        Assert.assertEquals(dto.getAction(), DTOAction.HELLO);
        Assert.assertNotNull(dto.getData());
        Assert.assertEquals("String",dto.getType());
    }

    @Test
    public void testInitTwoPlayers() throws Exception{
        Socket socket1 = new Socket();
        Socket socket2 = new Socket();
        socket1.connect(new InetSocketAddress("localhost",39989));
        socket2.connect(new InetSocketAddress("localhost",39989));
        PlayerBO player1 = serverGameManager.initPlayer(socket1);
        PlayerBO player2 = serverGameManager.initPlayer(socket2);
        Assert.assertNotEquals(player1.getPlayerId(),player2.getPlayerId());
    }
}
