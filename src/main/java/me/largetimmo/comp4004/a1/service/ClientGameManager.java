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
import me.largetimmo.comp4004.a1.service.bo.ScoreCategory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Getter
    private List<ScoreCategory> filled = new ArrayList<>();

    private int holdCount = 0;

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
            case ROLL_DICE:
            case HOLD_DICE:
                handleRollDice(dto);
                break;
            case FINISH:
                handleFinish(dto);
                break;
            default:
                break;
        }
    }

    public void handleFinish(BasicDTO dto){
        StringBuilder sb = new StringBuilder();
        PlayerBO playerBO = players.stream().filter(p->p.getPlayerId().equals(dto.getData())).findAny().get();
        sb.append("Congratulations, ");
        sb.append(playerBO.getPlayerName());
        sb.append(" has won this game with score ");
        sb.append(playerBO.getScoreSheet().getTotal());
        sb.append(" points!!!!!!!!!");
        sb.append("\n");
        sb.append("Great game everyone, and thanks for playing. Goodbye.");
        System.out.print(sb);

    }

    public void handleSyncPlayer(BasicDTO dto) throws IOException {
        List<PlayerDTO> playerDTOS = objectMapper.readValue(dto.getData(), new TypeReference<ArrayList<PlayerDTO>>() {
        });
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
        holdCount = 0;
        currentPlayer.getConnection().send(objectMapper.writeValueAsString(startDTO));
    }
    public void handleRollDice(BasicDTO dto) throws IOException{
        String diceStr = dto.getData();
        List<Integer> dices = Arrays.stream(diceStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        printDice(dices);
        printMenuAndGetInput();
    }

    public void printMenuAndGetInput() throws IOException {
        printMenu();
        String selection = "";
        while (true){
            selection = sysInput.readLine();
            if(!(selection.equals("1") || selection.equals("2") || selection.equals("3"))){
                System.out.println("Invalid input. Please try again.");

            }
            if ((selection.equals("1") || selection.equals("2")) && holdCount == 2) {
                System.out.println("You have used all the chance to re-roll dices. Please try again.");
            } else {
                break;
            }


        }
        if (selection.equals("1")) {
            List<Integer> holdDices;
            while (true) {
                printInstructionForSectionOne();
                String input = sysInput.readLine();
                if (input == null || input.isEmpty()) {
                    holdDices = new ArrayList<>();
                    break;
                }
                holdDices = Arrays.stream(input.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
                boolean invalidIndex = holdDices.stream().anyMatch(i -> i > 4 || i < 0);
                if (invalidIndex || holdDices.size() > 5 || holdDices.size() != holdDices.stream().distinct().count()) {
                    System.out.println("Invalid index(es). Please try again.");
                } else {
                    break;
                }
            }
            BasicDTO holdDTO = new BasicDTO();
            holdDTO.setAction(DTOAction.HOLD_DICE);
            holdDTO.setType("String");
            StringBuilder sb = new StringBuilder();
            for (Integer num : holdDices) {
                sb.append(num);
                sb.append(",");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            holdDTO.setData(sb.toString());
            currentPlayer.getConnection().send(objectMapper.writeValueAsString(holdDTO));
            holdCount++;
        } else if (selection.equals("2")) {
            BasicDTO holdDTO = new BasicDTO();
            holdDTO.setAction(DTOAction.HOLD_DICE);
            holdDTO.setType("String");
            holdDTO.setData("");
            currentPlayer.getConnection().send(objectMapper.writeValueAsString(holdDTO));
            holdCount++;
        } else if (selection.equals("3")) {
            System.out.println("What category do you want put your score in ?");
            Integer categoryInt;
            ScoreCategory scoreCategory = null;
            while (true) {
                String category = sysInput.readLine();
                if (category == null || category.isEmpty()) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }
                char[] chars = category.toCharArray();
                boolean valid = true;
                for (char c : chars) {
                    if (c < '0' || c > '9') {
                        System.out.println("Invalid input. Please try again.");
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    continue;
                }
                categoryInt = Integer.parseInt(category);
                if (categoryInt > 13 || categoryInt < 0) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }
                switch (categoryInt) {
                    case 1:
                        scoreCategory = ScoreCategory.ONES;
                        break;
                    case 2:
                        scoreCategory = ScoreCategory.TWOS;
                        break;
                    case 3:
                        scoreCategory = ScoreCategory.THREES;
                        break;
                    case 4:
                        scoreCategory = ScoreCategory.FOURS;
                        break;
                    case 5:
                        scoreCategory = ScoreCategory.FIVES;
                        break;
                    case 6:
                        scoreCategory = ScoreCategory.SIXES;
                        break;
                    case 7:
                        scoreCategory = ScoreCategory.THREE_OF_A_KIND;
                        break;
                    case 8:
                        scoreCategory = ScoreCategory.FOUR_OF_A_KIND;
                        break;
                    case 9:
                        scoreCategory = ScoreCategory.FULL_HOUSE;
                        break;
                    case 10:
                        scoreCategory = ScoreCategory.SMALL_STRAIGHT;
                        break;
                    case 11:
                        scoreCategory = ScoreCategory.LARGE_STRAIGHT;
                        break;
                    case 12:
                        scoreCategory = ScoreCategory.YAHTZEE;
                        break;
                    case 13:
                        scoreCategory = ScoreCategory.CHANCE;
                        break;
                    default:
                        break;
                }
                if (!filled.contains(scoreCategory)) {
                    break;
                }else{
                    System.out.println("This category has already filled with score. Please try another one.");
                }
            }
            filled.add(scoreCategory);
            BasicDTO basicDTO = new BasicDTO();
            basicDTO.setAction(DTOAction.FILL);
            basicDTO.setData("String");
            basicDTO.setData(scoreCategory.toString());
            currentPlayer.getConnection().send(objectMapper.writeValueAsString(basicDTO));
        }
    }


    public void listenToServer(BufferedReader br) {
        new Thread(() -> {
            while (true) {
                try {
                    String line = br.readLine();
                    messageHandler(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void printScoreBoard(PlayerBO player) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("", Collections.nCopies(150, "-")));
        sb.append("\n");
        sb.append("|");
        sb.append(" ");
        sb.append("Name: ");
        sb.append(player.getPlayerName());
        sb.append(String.join("", Collections.nCopies(30 - player.getPlayerName().length(), " ")));
        sb.append("|");
        sb.append(" Current Score: ");
        sb.append(player.getScoreSheet().getTotal());
        sb.append(String.join("", Collections.nCopies(30 - Integer.toString(player.getScoreSheet().getTotal()).length(), " ")));
        sb.append("|");
        sb.append(" Current round :");
        sb.append(player.getRound());
        sb.append(String.join("", Collections.nCopies(47 - Integer.toString(player.getRound()).length(), " ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("", Collections.nCopies(148, "-")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(" (1) Ones: ");
        sb.append(player.getScoreSheet().getUpperSection().getAces() == -1 ? "" : player.getScoreSheet().getUpperSection().getAces());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getUpperSection().getAces() == -1 ? "" : Integer.toString(player.getScoreSheet().getUpperSection().getAces())).length(), " ")));
        sb.append("|");
        sb.append(" (2) Twos: ");
        sb.append(player.getScoreSheet().getUpperSection().getTwos() == -1 ? "" : player.getScoreSheet().getUpperSection().getTwos());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getUpperSection().getTwos() == -1 ? "" : Integer.toString(player.getScoreSheet().getUpperSection().getTwos())).length(), " ")));
        sb.append("|");
        sb.append(" (3) Threes: ");
        sb.append(player.getScoreSheet().getUpperSection().getThrees() == -1 ? "" : player.getScoreSheet().getUpperSection().getThrees());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getUpperSection().getThrees() == -1 ? "" : Integer.toString(player.getScoreSheet().getUpperSection().getThrees())).length(), " ")));
        sb.append("|");
        sb.append(" (4) Fours: ");
        sb.append(player.getScoreSheet().getUpperSection().getFours() == -1 ? "" : player.getScoreSheet().getUpperSection().getFours());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getUpperSection().getFours() == -1 ? "" : Integer.toString(player.getScoreSheet().getUpperSection().getFours())).length(), " ")));
        sb.append("|");
        sb.append(" (5) Fives: ");
        sb.append(player.getScoreSheet().getUpperSection().getFives() == -1 ? "" : player.getScoreSheet().getUpperSection().getFives());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getUpperSection().getFives() == -1 ? "" : Integer.toString(player.getScoreSheet().getUpperSection().getFives())).length(), " ")));
        sb.append("|");
        sb.append(" (6) Sixes: ");
        sb.append(player.getScoreSheet().getUpperSection().getSixes() == -1 ? "" : player.getScoreSheet().getUpperSection().getSixes());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getUpperSection().getSixes() == -1 ? "" : Integer.toString(player.getScoreSheet().getUpperSection().getSixes())).length(), " ")));
        sb.append("|");
        sb.append(" Bonus: ");
        sb.append(player.getScoreSheet().getUpperSection().getUpperTotal() >= 98 ? 35 : 0);
        sb.append(String.join("", Collections.nCopies(27 - Integer.toString(player.getScoreSheet().getUpperSection().getUpperTotal() >= 98 ? 35 : 0).length(), " ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("", Collections.nCopies(148, "-")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(" (7) Three of a kind : ");
        sb.append(player.getScoreSheet().getLowerSection().getThreeOfAKind() == -1 ? "" : player.getScoreSheet().getLowerSection().getThreeOfAKind());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getThreeOfAKind() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getThreeOfAKind())).length(), " ")));
        sb.append("|");
        sb.append(" (8) Four of a kind : ");
        sb.append(player.getScoreSheet().getLowerSection().getFourOfAKind() == -1 ? "" : player.getScoreSheet().getLowerSection().getFourOfAKind());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getFourOfAKind() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getFourOfAKind())).length(), " ")));
        sb.append("|");
        sb.append(" (9) Full House: ");
        sb.append(player.getScoreSheet().getLowerSection().getFullHouse() == -1 ? "" : player.getScoreSheet().getLowerSection().getFullHouse());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getFullHouse() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getFullHouse())).length(), " ")));
        sb.append("|");
        sb.append(" (10) Small Straight: ");
        sb.append(player.getScoreSheet().getLowerSection().getSmallStraight() == -1 ? "" : player.getScoreSheet().getLowerSection().getSmallStraight());
        sb.append(String.join("", Collections.nCopies(6 - (player.getScoreSheet().getLowerSection().getSmallStraight() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getSmallStraight())).length(), " ")));
        sb.append("|");
        sb.append(" (11) Large Straight: ");
        sb.append(player.getScoreSheet().getLowerSection().getLargeStraight() == -1 ? "" : player.getScoreSheet().getLowerSection().getLargeStraight());
        sb.append(String.join("", Collections.nCopies(14 - (player.getScoreSheet().getLowerSection().getLargeStraight() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getLargeStraight())).length(), " ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("", Collections.nCopies(148, "-")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(" (12) YAHTZEE : ");
        sb.append(player.getScoreSheet().getLowerSection().getYahtzee() == -1 ? "" : player.getScoreSheet().getLowerSection().getYahtzee());
        sb.append(String.join("", Collections.nCopies(40 - (player.getScoreSheet().getLowerSection().getYahtzee() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getYahtzee())).length(), " ")));
        sb.append("|");
        sb.append(" YAHTZEE Bonus count: ");
        sb.append(player.getScoreSheet().getLowerSection().getYahtzeeBonus());
        sb.append(String.join("", Collections.nCopies(20 - Integer.toString(player.getScoreSheet().getLowerSection().getYahtzeeBonus()).length(), " ")));
        sb.append("|");
        sb.append(" (13) Chance : ");
        sb.append(player.getScoreSheet().getLowerSection().getChance() == -1 ? "" : player.getScoreSheet().getLowerSection().getChance());
        sb.append(String.join("", Collections.nCopies(33 - (player.getScoreSheet().getLowerSection().getChance() == -1 ? "" : Integer.toString(player.getScoreSheet().getLowerSection().getChance())).length(), " ")));
        sb.append("|");
        sb.append("\n");
        sb.append("|");
        sb.append(String.join("", Collections.nCopies(148, "-")));
        sb.append("|");
        System.out.println(sb);
    }

    public void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("What action would you like to perform next?");
        sb.append("\n");
        sb.append("(1) Select dice to hold, and then re-roll the other dices?");
        sb.append("\n");
        sb.append("(2) Reroll all the dices?");
        sb.append("\n");
        sb.append("(3) Score this round?\n");
        System.out.print(sb);
    }

    public void printInstructionForSectionOne() {
        System.out.println("Please enter in the dice position (Start with 0 ^.^) the you want to hold. Please seperate each number with a <<SPACE>>: ");
    }

    public void printDice(List<Integer> dices) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("", Collections.nCopies(20, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append("\n");
        sb.append("You rolled: ");
        sb.append(String.join("", Collections.nCopies(8, " ")));
        for (Integer dice : dices) {
            sb.append("| ");
            sb.append(dice);
            sb.append(" |");
            sb.append(String.join("", Collections.nCopies(5, " ")));
        }
        sb.append("\n");
        sb.append(String.join("", Collections.nCopies(20, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        sb.append(String.join("", Collections.nCopies(5, "-")));
        sb.append(String.join("", Collections.nCopies(5, " ")));
        System.out.println(sb);

    }


}
