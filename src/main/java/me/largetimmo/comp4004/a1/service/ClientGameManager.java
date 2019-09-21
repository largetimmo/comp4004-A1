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
import java.util.Collections;
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

    public ClientGameManager(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper, InputStream inputStream) {
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
            if (readyRes.toLowerCase().equals("y")) {
                break;
            }
        }
        BasicDTO playerReadyDTO = new BasicDTO();
        playerReadyDTO.setAction(DTOAction.READY);
        playerReadyDTO.setType("String");
        playerReadyDTO.setData(currentPlayer.getPlayerName());
        listenToServer(currentPlayer.getConnection().getReader());
        connection.send(objectMapper.writeValueAsString(playerReadyDTO));
    }

    public void messageHandler(String str) throws IOException {
        BasicDTO dto = objectMapper.readValue(str, BasicDTO.class);
        switch (dto.getAction()) {
            case SYNC_PLAYER:
                handleSyncPlayer(dto);
                break;
            case READY:
                handleReady(dto);
                break;
            case START_ROUND:
                handleStartRound(dto);
                break;
            default:
                break;
        }
    }

    public void handleSyncPlayer(BasicDTO dto) throws IOException {
        List<PlayerDTO> playerDTOS = objectMapper.readValue(dto.getData(), new TypeReference<ArrayList<PlayerDTO>>(){});
        players = playerDTOS.stream().map(playerDTOMapper::map).collect(Collectors.toList());
        System.out.print("\033[H\033[2J");
        for (PlayerBO player : players){
            printScoreBoard(player);
        }
    }

    public void handleReady(BasicDTO dto){
        System.out.println("All players are ready");
    }

    public void handleStartRound(BasicDTO dto) throws IOException {
        System.out.println("Press <<ENTER>> to start next round.");
        sysInput.readLine();
        BasicDTO startDTO = new BasicDTO();
        startDTO.setAction(DTOAction.START_ROUND);
        currentPlayer.getConnection().send(objectMapper.writeValueAsString(startDTO));
    }

    public void listenToServer(BufferedReader br){
        new Thread(()->{
            while (true){
                try {
                    String line = br.readLine();
                    messageHandler(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void printScoreBoard(PlayerBO player){
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("", Collections.nCopies(150,"-")));
        sb.append("\n");
        sb.append("|");
        sb.append(" ");
        sb.append("Name: ");
        sb.append(player.getPlayerName());
        sb.append(String.join("",Collections.nCopies(30-player.getPlayerName().length()," ")));
        sb.append("|");
        sb.append(" Current Score: ");
        sb.append(player.getScoreSheet().getTotal());
        sb.append(String.join("",Collections.nCopies(30-Integer.toString(player.getScoreSheet().getTotal()).length()," ")));
        sb.append("|");
        sb.append(" Current round :");
        sb.append(player.getRound());
        sb.append(String.join("",Collections.nCopies(47-Integer.toString(player.getRound()).length()," ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("",Collections.nCopies(148,"-")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(" (1) Ones: ");
        sb.append(player.getScoreSheet().getUpperSection().getAces() == -1? "" :player.getScoreSheet().getUpperSection().getAces() );
        sb.append(String.join("",Collections.nCopies(6-(player.getScoreSheet().getUpperSection().getAces() == -1? "" :Integer.toString(player.getScoreSheet().getUpperSection().getAces())).length()," ")));
        sb.append("|");
        sb.append(" (2) Twos: ");
        sb.append(player.getScoreSheet().getUpperSection().getTwos() == -1? "" :player.getScoreSheet().getUpperSection().getTwos());
        sb.append(String.join("",Collections.nCopies(6-(player.getScoreSheet().getUpperSection().getTwos() == -1? "" :Integer.toString(player.getScoreSheet().getUpperSection().getTwos())).length()," ")));
        sb.append("|");
        sb.append(" (3) Threes: ");
        sb.append(player.getScoreSheet().getUpperSection().getThrees() == -1? "" :player.getScoreSheet().getUpperSection().getThrees());
        sb.append(String.join("",Collections.nCopies(6-(player.getScoreSheet().getUpperSection().getThrees() == -1? "" :Integer.toString(player.getScoreSheet().getUpperSection().getThrees())).length()," ")));
        sb.append("|");
        sb.append(" (4) Fours: ");
        sb.append(player.getScoreSheet().getUpperSection().getFours() == -1? "" :player.getScoreSheet().getUpperSection().getFours());
        sb.append(String.join("",Collections.nCopies(6-(player.getScoreSheet().getUpperSection().getFours() == -1? "" :Integer.toString(player.getScoreSheet().getUpperSection().getFours())).length()," ")));
        sb.append("|");
        sb.append(" (5) Fives: ");
        sb.append(player.getScoreSheet().getUpperSection().getFives() == -1? "" :player.getScoreSheet().getUpperSection().getFives());
        sb.append(String.join("",Collections.nCopies(6-(player.getScoreSheet().getUpperSection().getFives() == -1? "" :Integer.toString(player.getScoreSheet().getUpperSection().getFives())).length()," ")));
        sb.append("|");
        sb.append(" (6) Sixes: ");
        sb.append(player.getScoreSheet().getUpperSection().getSixes() == -1? "" :player.getScoreSheet().getUpperSection().getSixes());
        sb.append(String.join("",Collections.nCopies(6-(player.getScoreSheet().getUpperSection().getSixes() == -1? "" :Integer.toString(player.getScoreSheet().getUpperSection().getSixes())).length()," ")));
        sb.append("|");
        sb.append(" Bonus: ");
        sb.append(player.getScoreSheet().getUpperSection().getTotal() >=98 ? 35 : 0);
        sb.append(String.join("",Collections.nCopies(27-Integer.toString(player.getScoreSheet().getUpperSection().getTotal() >=98 ? 35 : 0).length()," ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("",Collections.nCopies(148,"-")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(" (7) Three of a kind : ");
        sb.append(player.getScoreSheet().getLowerSection().getThreeOfAKind() == -1? "" :player.getScoreSheet().getLowerSection().getThreeOfAKind());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getThreeOfAKind() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getThreeOfAKind())).length(), " ")));
        sb.append("|");
        sb.append(" (8) Four of a kind : ");
        sb.append(player.getScoreSheet().getLowerSection().getFourOfAKind() == -1? "" :player.getScoreSheet().getLowerSection().getFourOfAKind());
        sb.append(String.join("",Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getFourOfAKind() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getFourOfAKind())).length(), " ")));
        sb.append("|");
        sb.append(" (9) Full House: ");
        sb.append(player.getScoreSheet().getLowerSection().getFullHouse() == -1? "" :player.getScoreSheet().getLowerSection().getFullHouse());
        sb.append(String.join("",Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getFullHouse() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getFullHouse())).length(), " ")));
        sb.append("|");
        sb.append(" (10) Small Straight: ");
        sb.append(player.getScoreSheet().getLowerSection().getSmallStraight() == -1? "" :player.getScoreSheet().getLowerSection().getSmallStraight());
        sb.append(String.join("",Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getSmallStraight() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getSmallStraight())).length(), " ")));
        sb.append("|");
        sb.append(" (11) Large Straight: ");
        sb.append(player.getScoreSheet().getLowerSection().getLargeStraight() == -1? "" :player.getScoreSheet().getLowerSection().getLargeStraight());
        sb.append(String.join("",Collections.nCopies(14 - (player.getScoreSheet().getLowerSection().getLargeStraight() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getLargeStraight())).length(), " ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("",Collections.nCopies(148,"-")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(" (12) YAHTZEE : ");
        sb.append(player.getScoreSheet().getLowerSection().getYahtzee() == -1? "" :player.getScoreSheet().getLowerSection().getYahtzee());
        sb.append(String.join("",Collections.nCopies(40-(player.getScoreSheet().getLowerSection().getYahtzee() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getYahtzee())).length(), " ")));
        sb.append("|");
        sb.append(" YAHTZEE Bonus count: ");
        sb.append(player.getScoreSheet().getLowerSection().getYahtzeeBonus());
        sb.append(String.join("",Collections.nCopies(20-Integer.toString(player.getScoreSheet().getLowerSection().getYahtzeeBonus()).length()," ")));
        sb.append("|");
        sb.append(" (13) Chance : ");
        sb.append(player.getScoreSheet().getLowerSection().getChance() == -1? "" :player.getScoreSheet().getLowerSection().getChance());
        sb.append(String.join("",Collections.nCopies(32-(player.getScoreSheet().getLowerSection().getChance() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getChance())).length(), " ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("",Collections.nCopies(148,"-")));
        sb.append("|");
        System.out.println(sb);
    }


}
