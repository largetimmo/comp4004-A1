package me.largetimmo.comp4004.a1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.service.bo.Connection;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientGameManager {

    @Getter
    private List<PlayerBO> players;

    @Getter
    private PlayerBO currentPlayer;

    private BufferedReader sysInput;

    private ObjectMapper objectMapper;

    public ClientGameManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void initPlayer(Socket socket) throws IOException {
        players = new ArrayList<>();
        currentPlayer = new PlayerBO();
        Connection connection = new Connection();
        connection.setSocket(socket);
        connection.setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        connection.setWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        currentPlayer.setConnection(connection);
        sysInput = new BufferedReader(new InputStreamReader(System.in));
        BasicDTO dto = objectMapper.readValue(connection.receive(), BasicDTO.class);
        currentPlayer.setPlayerId(dto.getData());
        System.out.println("Welcome Player " + currentPlayer.getPlayerId() + ". What's your name?");
        String name = sysInput.readLine();
        currentPlayer.setPlayerName(name);
        String readyRes = "N";
        while (true) {
            System.out.println("Are you ready? (y/N)");
            readyRes = sysInput.readLine();
            if (readyRes.toLowerCase().equals("y")){
                break;
            }
        }
        BasicDTO playerReadyDTO = new BasicDTO();
        playerReadyDTO.setAction(DTOAction.READY);
        playerReadyDTO.setType("String");
        playerReadyDTO.setData(currentPlayer.getPlayerId());
        connection.send(objectMapper.writeValueAsString(playerReadyDTO));
    }

    public void messageHandler(BasicDTO dto){
        switch (dto.getAction()){
        }
    }
}
