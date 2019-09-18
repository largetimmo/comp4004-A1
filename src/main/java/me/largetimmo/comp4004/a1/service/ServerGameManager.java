package me.largetimmo.comp4004.a1.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.bo.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ServerGameManager {


    @Getter
    private List<PlayerBO> players;

    private ObjectMapper objectMapper;

    public ServerGameManager(ObjectMapper objectMapper) {
        players = new ArrayList<>();
        this.objectMapper = objectMapper;
    }

    public PlayerBO initPlayer(Socket socket){
        if (socket.isClosed()){
            log.error("Connection is already closed. Failed to init player. Server needs to restart");
            throw new RuntimeException("Failed to init player.");
        }
        PlayerBO playerBO = new PlayerBO();
        playerBO.setPlayerId(UUID.randomUUID().toString());
        Connection connection = new Connection();
        connection.setSocket(socket);
        try {
            connection.setWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            connection.setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        } catch (IOException e) {
            log.warn("Unable to initialize connection.",e);
        }
        playerBO.setConnection(connection);
        ScoreSheetBO scoreSheet = new ScoreSheetBO();
        scoreSheet.setLowerSection(new LowerSectionGameScore());
        scoreSheet.setUpperSection(new UpperSectionGameScore());
        playerBO.setScoreSheet(scoreSheet);
        players.add(playerBO);
        BasicDTO helloDTO = new BasicDTO();
        helloDTO.setAction(DTOAction.HELLO);
        helloDTO.setType("String");
        helloDTO.setData(playerBO.getPlayerId());
        try {
            connection.send(objectMapper.writeValueAsString(helloDTO));
        } catch (IOException e) {
            log.error("Failed to communicate with client",e);
        }
        return playerBO;
    }
}
