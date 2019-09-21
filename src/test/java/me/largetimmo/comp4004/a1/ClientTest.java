package me.largetimmo.comp4004.a1;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"app.mode=client", "server.host=localhost", "server.port=37777"})
public class ClientTest {

    private static ServerSocket serverSocket;

    @Autowired
    private ClientMessageIOController controller;

    @Autowired
    private ClientGameManager clientGameManager;

    @Autowired
    private ObjectMapper objectMapper;

    private static File inputFile;

    private static List<Socket> clients = new ArrayList<>();

    @BeforeClass
    public static void setupServerSocket() throws Exception {
        inputFile= File.createTempFile("clientSimInput","txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile));
        bw.write("Kyle");
        bw.newLine();
        bw.write("Y");
        bw.newLine();
        bw.flush();
        bw.close();
        InputStream is = new FileInputStream(inputFile);
        System.setIn(is);
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 37777));
        new Thread(() -> {
            try {
                Socket s = serverSocket.accept();
                clients.add(s);
                BasicDTO dto = new BasicDTO();
                dto.setData("111");
                dto.setType("String");
                dto.setAction(DTOAction.HELLO);
                BufferedWriter bwClient = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                bwClient.write(new ObjectMapper().writeValueAsString(dto));
                bwClient.newLine();
                bwClient.flush();
                bwClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
    @Test
    public void testClientConnection() throws Exception {

        Assert.assertTrue(controller.getConnection().isConnected());
        Thread.sleep(1000);
        Assert.assertNotNull(clientGameManager.getCurrentPlayer());
        Assert.assertNotNull(clientGameManager.getCurrentPlayer().getPlayerId());
    }


    @Test
    public void testHandleMessage() throws Exception{
        List<PlayerDTO> players = new ArrayList<>();
        PlayerDTO player1 = new PlayerDTO();
        PlayerDTO player2 = new PlayerDTO();
        PlayerDTO player3 = new PlayerDTO();
        player1.setPlayerId("111");
        player2.setPlayerId("222");
        player3.setPlayerId("333");
        player1.setPlayerName("name1");
        player2.setPlayerName("name2");
        player3.setPlayerName("name3");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        ScoreSheetBO scoreSheetBO = new ScoreSheetBO();
        player1.setScoreSheet(scoreSheetBO);
        player2.setScoreSheet(scoreSheetBO);
        player3.setScoreSheet(scoreSheetBO);

        BasicDTO dto = new BasicDTO();
        dto.setAction(DTOAction.SYNC_PLAYER);
        dto.setType("String");
        dto.setData(objectMapper.writeValueAsString(players));
        clientGameManager.messageHandler(objectMapper.writeValueAsString(dto));
        Assert.assertEquals(clientGameManager.getPlayers().size(),3);

    }
}
