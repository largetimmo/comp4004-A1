package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.Socket;

public class ServerGameManagerTest {

    ServerGameManager serverGameManager;

    @Before
    public void setup(){
        serverGameManager = new ServerGameManager(null);
    }

    @Test
    public void testInitPlayer(){
        Socket socket = Mockito.mock(Socket.class);
        Mockito.when(socket.isClosed()).thenReturn(true);
        PlayerBO playerBO = serverGameManager.initPlayer(socket);
        Assert.assertNotNull(playerBO.getClient());
        Assert.assertNotNull(playerBO.getPlayerId());
        Assert.assertNotNull(playerBO.getScoreSheet());
    }

    @Test
    public void testInitTwoPlayers(){
        Socket socket1 = Mockito.mock(Socket.class);
        Socket socket2 = Mockito.mock(Socket.class);
        PlayerBO player1 = serverGameManager.initPlayer(socket1);
        PlayerBO player2 = serverGameManager.initPlayer(socket2);
        Assert.assertNotEquals(player1.getPlayerId(),player2.getPlayerId());
    }
}
