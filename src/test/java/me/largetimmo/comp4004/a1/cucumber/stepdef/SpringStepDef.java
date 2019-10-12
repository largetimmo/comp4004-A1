package me.largetimmo.comp4004.a1.cucumber.stepdef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;
import me.largetimmo.comp4004.a1.service.ClientGameManager;
import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SpringStepDef extends AbstractStepDef implements En {

    @Autowired
    @Qualifier("userSim1")
    private BufferedWriter bw1;

    @Autowired
    private ServerGameManager serverGameManager;

    @Autowired
    @Qualifier("client1")
    private ClientGameManager clientGameManager;

    @Autowired
    private ApplicationContext applicationContext;

    private boolean matched = false;

    public SpringStepDef() throws Exception {
        Thread.sleep(1000);


        Given("^user roll dice$", () -> {
            log.info("Player rolled dice");
            bw1.newLine();
            bw1.flush();
        });

        And("put {int} on :", (Integer points, DataTable table) -> {
            PlayerBO playerBO = serverGameManager.getPlayers().get(0);
            ScoreSheetBO scoreSheetBO = playerBO.getScoreSheet();
            Map<Field, Object> scoreFields = new HashMap<>();
            Arrays.stream(scoreSheetBO.getUpperSection().getClass().getDeclaredFields()).forEach(f -> scoreFields.put(f, scoreSheetBO.getUpperSection()));
            Arrays.stream(scoreSheetBO.getLowerSection().getClass().getDeclaredFields()).forEach(f -> scoreFields.put(f, scoreSheetBO.getLowerSection()));
            for (Field f : scoreFields.keySet()) {
                if (f.getName().equals(table.asList().get(0))) {
                    f.setAccessible(true);
                    f.set(scoreFields.get(f), points);
                }
            }

        });
        And("user has dices:", (DataTable dices) -> {
            Thread.sleep(1000);
            dices.asList().forEach(log::info);
            serverGameManager.getPlayers().get(0).setCurrentDice(dices.asList(Integer.class));
        });
        And("user score to {int}", (Integer scoreChoice) -> {
            bw1.write("3\n");
            bw1.write(scoreChoice.toString());
            bw1.newLine();
            bw1.flush();
        });
        Then("wait for {int}", (Integer time) -> {
            Thread.sleep(time * 1000);
        });
        Then("scoreboard section is {int} :", (Integer score, DataTable dataTable) -> {
            PlayerBO playerBO = serverGameManager.getPlayers().get(0);
            ScoreSheetBO scoreSheetBO = playerBO.getScoreSheet();
            Map<Field, Object> scoreFields = new HashMap<>();
            Arrays.stream(scoreSheetBO.getUpperSection().getClass().getDeclaredFields()).forEach(f -> scoreFields.put(f, scoreSheetBO.getUpperSection()));
            Arrays.stream(scoreSheetBO.getLowerSection().getClass().getDeclaredFields()).forEach(f -> scoreFields.put(f, scoreSheetBO.getLowerSection()));
            for (Field f : scoreFields.keySet()) {
                if (f.getName().equals(dataTable.asList().get(0))) {
                    matched = true;
                    f.setAccessible(true);
                    Assert.assertEquals(score, f.get(scoreFields.get(f)));
                }
            }
        });
        Then("set section to default:", (DataTable datatable) -> {
            PlayerBO playerBO = serverGameManager.getPlayers().get(0);
            ScoreSheetBO scoreSheetBO = playerBO.getScoreSheet();
            Map<Field, Object> scoreFields = new HashMap<>();
            Arrays.stream(scoreSheetBO.getUpperSection().getClass().getDeclaredFields()).forEach(f -> scoreFields.put(f, scoreSheetBO.getUpperSection()));
            Arrays.stream(scoreSheetBO.getLowerSection().getClass().getDeclaredFields()).forEach(f -> scoreFields.put(f, scoreSheetBO.getLowerSection()));
            for (String category : datatable.asList()) {
                for (Field f : scoreFields.keySet()) {
                    if (f.getName().equals(category)) {
                        f.setAccessible(true);
                        f.set(scoreFields.get(f), -1);
                    }
                }
            }
            serverGameManager.setCurrentPlayer(0);
            clientGameManager.getFilled().clear();
            serverGameManager.tellPlayerRoundStart(playerBO.getPlayerId());
            serverGameManager.getPlayers().get(0).setRound(0);
        });

        Then("reset user",()->{
            serverGameManager.setCurrentPlayer(0);
            clientGameManager.getFilled().clear();
            serverGameManager.tellPlayerRoundStart(serverGameManager.getPlayers().get(0).getPlayerId());
            serverGameManager.getPlayers().get(0).setRound(0);
        });

        And("user hold dices:" , (DataTable dataTable)->{
            bw1.write("1\n");
            List<String> dices = dataTable.asList();
            if (dices.size() == 1 && "".equals(dices.get(0))){
                bw1.newLine();
            }else{
                StringBuffer sb = new StringBuffer();
                for (String d : dices){
                    sb.append(d);
                    sb.append(" ");
                }
                sb.deleteCharAt(sb.lastIndexOf(" "));
                log.info("Parsed input : '{}'",sb.toString());
                bw1.write(sb.toString());
            }
            bw1.flush();
        });

        Then("category score is correct",()->{
            PlayerBO playerBO = serverGameManager.getPlayers().get(0);
            ScoreSheetBO scoreSheetBO = playerBO.getScoreSheet();
            Assert.assertTrue(scoreSheetBO.getUpperSection().getAces() > -1);
        });

        And("user reroll dices",()->{
            bw1.write("2\n");
            bw1.flush();
        });



    }
}
