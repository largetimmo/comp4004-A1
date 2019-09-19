package me.largetimmo.comp4004.a1.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.service.bo.Connection;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientGameManager {

    @Getter
    private List<PlayerBO> players;

    @Getter
    private PlayerBO currentPlayer;

    private BufferedReader sysInput;

    private ObjectMapper objectMapper;

    private PlayerDTOMapper playerDTOMapper;

    private InputStream inputStream;

    public ClientGameManager(ObjectMapper objectMapper,PlayerDTOMapper playerDTOMapper,InputStream inputStream) {
        this.objectMapper = objectMapper;
        this.playerDTOMapper = playerDTOMapper;
        this.inputStream = inputStream;
    }

    public void initPlayer(Socket socket) throws IOException {
        players = new ArrayList<>();
        currentPlayer = new PlayerBO();
        Connection connection = new Connection();
        connection.setSocket(socket);
        connection.setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        connection.setWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        currentPlayer.setConnection(connection);
        sysInput = new BufferedReader(new InputStreamReader(inputStream));
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

    public void messageHandler(String str) throws IOException {
        BasicDTO dto = objectMapper.readValue(str,BasicDTO.class);
        switch (dto.getAction()){
            case SYNC_PLAYER:
                handleSyncPlayer(dto);
                break;
        }
    }

    public void handleSyncPlayer(BasicDTO dto) throws IOException {
        List<PlayerDTO> playerDTOS = objectMapper.readValue(dto.getData(),new TypeReference<ArrayList<PlayerDTO>>(){});
        players = playerDTOS.stream().map(playerDTOMapper::map).collect(Collectors.toList());
    }


}
