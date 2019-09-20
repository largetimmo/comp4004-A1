package me.largetimmo.comp4004.a1.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.controller.ServerMessageIOController;
import me.largetimmo.comp4004.a1.service.bo.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class ServerGameManager {


    @Getter
    private List<PlayerBO> players;

    private ObjectMapper objectMapper;

    private PlayerDTOMapper playerDTOMapper;

    private final Object scoreBoardLock = null;

    public ServerGameManager(ObjectMapper objectMapper,PlayerDTOMapper playerDTOMapper) {
        players = new ArrayList<>();
        this.objectMapper = objectMapper;
        this.playerDTOMapper = playerDTOMapper;
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
        listenToClient(playerBO.getConnection().getReader(),playerBO.getPlayerId());
        return playerBO;
    }
    public void handleMessage(String playerId, String msg) throws IOException {
        BasicDTO dto = objectMapper.readValue(msg,BasicDTO.class);
        switch (dto.getAction()){
            case READY:
                handleReady(playerId,dto);
                break;
        }

    }
    public void handleReady(String playerId, BasicDTO dto) throws JsonProcessingException {
        PlayerBO player = players.stream().filter(p->p.getPlayerId().equals(playerId)).findAny().get();
        player.setReady(true);
        Long playerNotReady = players.stream().filter(p->!p.getReady()).count();
        sendScoreBoardToAllPlayer();
        if (playerNotReady == 0){
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAction(DTOAction.READY);
            sendDataToAll(objectMapper.writeValueAsString(dto));
        }

    }
    private void sendScoreBoardToAllPlayer(){
        BasicDTO dto = new BasicDTO();
        dto.setAction(DTOAction.SYNC_PLAYER);
        dto.setType(ArrayList.class.getName());
        List<PlayerDTO> allPlayerDTOs = players.stream().map(playerDTOMapper::map).collect(Collectors.toList());
        try {
            dto.setData(objectMapper.writeValueAsString(allPlayerDTOs));
            sendDataToAll(objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private void sendDataToAll(String data){
        for (PlayerBO playerBO : players){
            try {
                playerBO.getConnection().send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void listenToClient(BufferedReader br, String playerId){
        new Thread(()->{
            try {
                String msg = br.readLine();
                handleMessage(playerId,msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
