package me.largetimmo.comp4004.a1.service.bo;

import lombok.Data;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerBO {

    private String playerId;

    private String playerName;

    private ScoreSheetBO scoreSheet;

    private Connection connection;

    private Integer round = 0;

    private Boolean ready = false;

    private List<Integer> currentDice = new ArrayList<>();

}
