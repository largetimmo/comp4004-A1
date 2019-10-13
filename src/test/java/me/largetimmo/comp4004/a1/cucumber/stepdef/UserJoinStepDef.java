package me.largetimmo.comp4004.a1.cucumber.stepdef;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.controller.ClientMessageIOController;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import org.junit.Assert;
import org.mockito.Mockito;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class UserJoinStepDef implements En {

    private static final Map<String, Object> context = new HashMap<>();

    public UserJoinStepDef() {

        Given(("server starts"), () -> {
            ServerGameManager serverGameManager = Mockito.spy(new ServerGameManager(new ObjectMapper(), new PlayerDTOMapper()));
            Integer port = new Random().nextInt(20000) + 40000;
            ServerMessageIOController serverMessageIOController = Mockito.spy(new ServerMessageIOController(port, serverGameManager));
            context.put("server", serverGameManager);
            context.put("serverIO", serverMessageIOController);
            context.put("port", port);
        });
        And("wait for player join", () -> {
            System.out.print(context.get("server"));

        });

        Then("{int} player joined server", (Integer playerCount) -> {
            for (int i = 0; i < playerCount; i++) {
                File f = File.createTempFile("testInput", "txt");
                PrintWriter printWriter = new PrintWriter(new FileWriter(f));
                printWriter.println("kyle" + i);
                printWriter.println("y");
                printWriter.println();
                printWriter.flush();

                ClientGameManager clientGameManager = new ClientGameManager(new ObjectMapper(), new PlayerDTOMapper(), new FileInputStream(f));
                ClientMessageIOController clientMessageIOController = new ClientMessageIOController("localhost", (Integer) context.get("port"), clientGameManager);
                context.put("client" + i, clientGameManager);
                context.put("clientIO" + i, clientMessageIOController);
            }
        });
        Then("game is not start", () -> {
            ServerGameManager serverGameManager = (ServerGameManager) context.get("server");
            Mockito.verify(serverGameManager, Mockito.times(0)).tellPlayerRoundStart(Mockito.any());
        });
        Then("game starts", () -> {
            ServerGameManager serverGameManager = (ServerGameManager) context.get("server");
            Mockito.verify(serverGameManager, Mockito.atLeast(1)).tellPlayerRoundStart(Mockito.any());
        });
        Then("{int} player cannot join server", (Integer playerCount) -> {
            for (int i = 0; i < playerCount; i++) {
                File f = File.createTempFile("testInput", "txt");
                PrintWriter printWriter = new PrintWriter(new FileWriter(f));
                printWriter.println("kyle" + i);
                printWriter.println("y");
                printWriter.println();
                printWriter.flush();

                ClientGameManager clientGameManager = Mockito.spy(new ClientGameManager(new ObjectMapper(), new PlayerDTOMapper(), new FileInputStream(f)));
                Thread t = new Thread(() -> {
                    try {
                        new ClientMessageIOController("localhost", (Integer) context.get("port"), clientGameManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                t.start();
                Thread.sleep(1000);
                ServerGameManager serverGameManager = (ServerGameManager) context.get("server");
                Assert.assertEquals(serverGameManager.getPlayers().size(), 3);
                t.stop();
            }
        });
    }
}
