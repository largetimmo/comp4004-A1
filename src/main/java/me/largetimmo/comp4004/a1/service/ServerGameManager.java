package me.largetimmo.comp4004.a1.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.configuration.dto.BasicDTO;
import me.largetimmo.comp4004.a1.configuration.dto.DTOAction;
import me.largetimmo.comp4004.a1.configuration.dto.PlayerDTO;
import me.largetimmo.comp4004.a1.configuration.dto.mapper.PlayerDTOMapper;
import me.largetimmo.comp4004.a1.service.bo.*;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ServerGameManager {

    private Random random = new Random();

    @Getter
    private List<PlayerBO> players;

    private ObjectMapper objectMapper;

    private PlayerDTOMapper playerDTOMapper;

    private final Object scoreBoardLock = null;

    private int currentPlayer = 0;

    private final List<ScoreCategory> upperSection = new ArrayList<>(Arrays.asList(ScoreCategory.ONES,
            ScoreCategory.TWOS, ScoreCategory.THREES, ScoreCategory.FOURS, ScoreCategory.FIVES, ScoreCategory.SIXES));

    public ServerGameManager(ObjectMapper objectMapper, PlayerDTOMapper playerDTOMapper) {
        players = new ArrayList<>();
        this.objectMapper = objectMapper;
        this.playerDTOMapper = playerDTOMapper;
    }

    public PlayerBO initPlayer(Socket socket) {
        if (socket.isClosed()) {
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
            log.warn("Unable to initialize connection.", e);
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
            log.error("Failed to communicate with client", e);
        }
        listenToClient(playerBO.getConnection().getReader(), playerBO.getPlayerId());
        return playerBO;
    }

    public void calculateScoreForPlayer(PlayerBO player, List<Integer> dices, ScoreCategory category) {
        Integer points = calculateScore(dices, category);
        if (category == ScoreCategory.YAHTZEE && player.getScoreSheet().getLowerSection().getYahtzee() == 50 && points != 0) {
            player.getScoreSheet().getLowerSection().setYahtzeeBonus(player.getScoreSheet().getLowerSection().getYahtzeeBonus() + 1);
        } else {
            switch (category) {
                case ONES:
                    player.getScoreSheet().getUpperSection().setAces(points);
                    break;
                case TWOS:
                    player.getScoreSheet().getUpperSection().setTwos(points);
                    break;
                case THREES:
                    player.getScoreSheet().getUpperSection().setThrees(points);
                    break;

                case FOURS:
                    player.getScoreSheet().getUpperSection().setFours(points);
                    break;

                case FIVES:
                    player.getScoreSheet().getUpperSection().setFives(points);
                    break;

                case SIXES:
                    player.getScoreSheet().getUpperSection().setSixes(points);
                    break;

                case THREE_OF_A_KIND:
                    player.getScoreSheet().getLowerSection().setThreeOfAKind(points);
                    break;

                case FOUR_OF_A_KIND:
                    player.getScoreSheet().getLowerSection().setFourOfAKind(points);
                    break;

                case FULL_HOUSE:
                    player.getScoreSheet().getLowerSection().setFullHouse(points);
                    break;

                case SMALL_STRAIGHT:
                    player.getScoreSheet().getLowerSection().setSmallStraight(points);
                    break;

                case LARGE_STRAIGHT:
                    player.getScoreSheet().getLowerSection().setLargeStraight(points);
                    break;

                case YAHTZEE:
                    player.getScoreSheet().getLowerSection().setYahtzee(points);
                    break;

                case CHANCE:
                    player.getScoreSheet().getLowerSection().setChance(points);
                    break;

                default:
                    return;
            }
        }
        player.getScoreSheet().calculateTotal();
    }

    public Integer calculateScore(List<Integer> dices, ScoreCategory category) {
        switch (category) {
            case ONES:
                return (int) dices.stream().filter(d -> d == 1).count();
            case TWOS:
                return (int) dices.stream().filter(d -> d == 2).count() * 2;
            case THREES:
                return (int) dices.stream().filter(d -> d == 3).count() * 3;
            case FOURS:
                return (int) dices.stream().filter(d -> d == 4).count() * 4;
            case FIVES:
                return (int) dices.stream().filter(d -> d == 5).count() * 5;
            case SIXES:
                return (int) dices.stream().filter(d -> d == 6).count() * 6;
            case THREE_OF_A_KIND:
                List<Integer> sortedDice = dices.stream().sorted().collect(Collectors.toList());
                for (int i = 0; i < 3; i++) {
                    List<Integer> subArr = sortedDice.subList(i, i + 3);
                    boolean valid = true;
                    for (int j = 0; j < 2; j++) {
                        if (subArr.get(j) != subArr.get(j + 1)) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        return sum(dices);
                    }
                }
                return 0;
            case FOUR_OF_A_KIND:
                sortedDice = dices.stream().sorted().collect(Collectors.toList());
                for (int i = 0; i < 2; i++) {
                    List<Integer> subArr = sortedDice.subList(i, i + 4);
                    boolean valid = true;
                    for (int j = 0; j < 3; j++) {
                        if (subArr.get(j) != subArr.get(j + 1)) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        return sum(dices);
                    }
                }
                return 0;
            case FULL_HOUSE:
                sortedDice = dices.stream().sorted().collect(Collectors.toList());
                List<Integer> threeDices = sortedDice.subList(0, 3);
                List<Integer> twoDices = sortedDice.subList(3, 5);
                if (threeDices.stream().distinct().count() == 1 && twoDices.stream().distinct().count() == 1) {
                    return 25;
                }
                twoDices = sortedDice.subList(0, 2);
                threeDices = sortedDice.subList(2, 5);
                if (threeDices.stream().distinct().count() == 1 && twoDices.stream().distinct().count() == 1) {
                    return 25;
                }
                return 0;
            case SMALL_STRAIGHT:
                sortedDice = dices.stream().sorted().distinct().collect(Collectors.toList());
                if (sortedDice.size() < 4) {
                    return 0;
                }
                for (int i = 0; i < sortedDice.size() - 3; i++) {
                    boolean valid = true;
                    for (int j = i; j < 3 + i; j++) {
                        if (sortedDice.get(j) != sortedDice.get(j + 1) - 1) {
                            valid = false;
                        }
                    }
                    if (valid) {
                        return 30;
                    }
                }
            case LARGE_STRAIGHT:
                sortedDice = dices.stream().sorted().distinct().collect(Collectors.toList());
                if (sortedDice.size() != 5) {
                    return 0;
                }
                for (int i = 0; i < 4; i++) {
                    if (sortedDice.get(i) != sortedDice.get(i + 1) - 1) {
                        return 0;
                    }
                }
                return 40;
            case YAHTZEE:
                if (dices.stream().distinct().count() == 1) {
                    return 50;
                } else {
                    return 0;
                }
            case CHANCE:
                return sum(dices);
            default:
                return 0;

        }
    }

    public void handleMessage(String playerId, String msg) throws IOException {
        BasicDTO dto = objectMapper.readValue(msg, BasicDTO.class);
        switch (dto.getAction()) {
            case READY:
                handleReady(playerId, dto);
                break;
            case START_ROUND:
                handleRollDice(playerId,dto);
                break;
            case HOLD_DICE:
                handleKeepDice(playerId,dto);
                break;
            case FILL:
                handleFillScore(playerId,dto);
        }

    }

    public void handleReady(String playerId, BasicDTO dto) throws IOException {
        PlayerBO player = players.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get();
        player.setReady(true);
        player.setPlayerName(dto.getData());
        Long playerNotReady = players.stream().filter(p -> !p.getReady()).count();
        if (playerNotReady == 0 && players.size() == 3) {
            sendScoreBoardToAllPlayer();
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAction(DTOAction.READY);
            sendDataToAll(objectMapper.writeValueAsString(dto));
            tellPlayerRoundStart(players.get(0).getPlayerId());
        }

    }
    public void tellPlayerRoundStart(String playerId) throws IOException {
        PlayerBO player = players.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get();
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setAction(DTOAction.START_ROUND);
        player.getConnection().send(objectMapper.writeValueAsString(basicDTO));
    }

    public void handleRollDice(String playerId, BasicDTO dto) throws IOException{
        PlayerBO player = players.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get();
        List<Integer> dices = new ArrayList<>();
        for(int i = 0; i< 5;i++){
            dices.add(random.nextInt(6)+1);
        }
        player.setCurrentDice(dices);
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setAction(DTOAction.ROLL_DICE);
        basicDTO.setType("String");
        StringBuilder diceSB = new StringBuilder();
        for(Integer dice: dices){
            diceSB.append(dice);
            diceSB.append(",");
        }
        diceSB.deleteCharAt(diceSB.lastIndexOf(","));
        basicDTO.setData(diceSB.toString());
        player.getConnection().send(objectMapper.writeValueAsString(basicDTO));
    }
    public void handleKeepDice(String playerId, BasicDTO dto) throws IOException{
        //dto data = {}  ==> reroll all
        PlayerBO player = players.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get();
        String holdDiceStr = dto.getData();
        List<Integer> holdIdx;
        if(holdDiceStr == null || "".equals(holdDiceStr)){
            holdIdx = new ArrayList<>();
        }else{
            holdIdx = Arrays.stream(holdDiceStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }
        for(int i =0; i< player.getCurrentDice().size();i++){
            if(!holdIdx.contains(i)){
                Integer newDice = random.nextInt(6)+1;
                player.getCurrentDice().set(i,newDice);
            }
        }
        BasicDTO basicDTO = new BasicDTO();
        basicDTO.setAction(DTOAction.HOLD_DICE);
        basicDTO.setType("String");
        StringBuilder diceSB = new StringBuilder();
        for(Integer dice: player.getCurrentDice()){
            diceSB.append(dice);
            diceSB.append(",");
        }
        diceSB.deleteCharAt(diceSB.lastIndexOf(","));
        basicDTO.setData(diceSB.toString());
        player.getConnection().send(objectMapper.writeValueAsString(basicDTO));

    }
    public void handleFillScore(String playerId, BasicDTO dto) throws IOException{
        PlayerBO player = players.stream().filter(p -> p.getPlayerId().equals(playerId)).findAny().get();
        ScoreCategory category = ScoreCategory.valueOf(dto.getData());
        calculateScoreForPlayer(player,player.getCurrentDice(),category);
        player.setRound(player.getRound()+1);
        currentPlayer = ((currentPlayer + 1) % (players.size()));//Next one
        if(currentPlayer == players.size() -1 && player.getRound() == 13){
            //TODO: finish game
        }else{
            sendScoreBoardToAllPlayer();
            tellPlayerRoundStart(players.get(currentPlayer).getPlayerId());
        }
    }



    private void sendScoreBoardToAllPlayer() {
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

    private void sendDataToAll(String data) {
        for (PlayerBO playerBO : players) {
            try {
                playerBO.getConnection().send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void listenToClient(BufferedReader br, String playerId) {
        new Thread(() -> {
            while (true){
                try {
                    String msg = br.readLine();
                    handleMessage(playerId, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private Integer sum(List<Integer> nums) {
        if (nums == null) {
            return 0;
        }
        int total = 0;
        for (Integer i : nums) {
            total += i;
        }
        return total;
    }
}
