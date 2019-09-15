package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"server.port=39999","app.mode=server"})
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
        socket.connect(new InetSocketAddress("localhost",39999));
        Assert.assertTrue(socket.isConnected());
        PlayerBO playerBO = serverGameManager.initPlayer(socket);
        Assert.assertNotNull(playerBO.getConnection());
        Assert.assertNotNull(playerBO.getPlayerId());
        Assert.assertNotNull(playerBO.getScoreSheet());
    }

    @Test
    public void testInitTwoPlayers() throws Exception{
        Socket socket1 = new Socket();
        Socket socket2 = new Socket();
        socket1.connect(new InetSocketAddress("localhost",39999));
        socket2.connect(new InetSocketAddress("localhost",39999));
        PlayerBO player1 = serverGameManager.initPlayer(socket1);
        PlayerBO player2 = serverGameManager.initPlayer(socket2);
        Assert.assertNotEquals(player1.getPlayerId(),player2.getPlayerId());
    }
}
