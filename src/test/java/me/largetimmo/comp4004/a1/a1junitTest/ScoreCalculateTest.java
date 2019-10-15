package me.largetimmo.comp4004.a1.a1junitTest;

import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreCalculateTest {

    private ServerGameManager serverGameManager = new ServerGameManager(null,null);

    private PlayerBO playerBO;

    private ScoreSheetBO scoreSheetBO;

    @Before
    public void setup(){
        playerBO = new PlayerBO();
        scoreSheetBO = new ScoreSheetBO();
        playerBO.setScoreSheet(scoreSheetBO);
    }

    @Test
    public void calculateUpperSectionBonus(){
        UpperSectionGameScore upperSectionGameScore = new UpperSectionGameScore();
        Assert.assertEquals(0, (int) upperSectionGameScore.getUpperTotal());
        upperSectionGameScore.setSixes(36);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(36, (int) upperSectionGameScore.getUpperTotal());
        upperSectionGameScore.setFives(25);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(61, (int) upperSectionGameScore.getUpperTotal());
        upperSectionGameScore.setAces(2);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(98, (int) upperSectionGameScore.getUpperTotal());
        upperSectionGameScore.setFours(8);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(106, (int) upperSectionGameScore.getUpperTotal());

    }

    @Test
    public void calculateLowerSectionBonus(){
        LowerSectionGameScore lowerSectionGameScore  = new LowerSectionGameScore();
        Assert.assertEquals(0, (int)lowerSectionGameScore.getLowerTotal());
        lowerSectionGameScore.setYahtzee(50);
        lowerSectionGameScore.calculateTotal();
        Assert.assertEquals(50,(int)lowerSectionGameScore.getLowerTotal());
        lowerSectionGameScore.setYahtzeeBonus(2);
        lowerSectionGameScore.calculateTotal();
        Assert.assertEquals(250,(int)lowerSectionGameScore.getLowerTotal());
        lowerSectionGameScore.setFourOfAKind(40);
        lowerSectionGameScore.setThreeOfAKind(30);
        lowerSectionGameScore.setFullHouse(25);
        lowerSectionGameScore.setSmallStraight(30);
        lowerSectionGameScore.calculateTotal(); 
        Assert.assertEquals(375,(int)lowerSectionGameScore.getLowerTotal());



    }

    @Test
    public void calculateScoreSheetTotal(){
        ScoreSheetBO scoreSheetBO = new ScoreSheetBO();
        Assert.assertEquals(0,(int)scoreSheetBO.getTotal());
        Assert.assertEquals(0, (int) scoreSheetBO.getUpperSection().getUpperTotal());
        scoreSheetBO.getUpperSection().setSixes(36);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(36, (int) scoreSheetBO.getUpperSection().getUpperTotal());
        scoreSheetBO.getUpperSection().setFives(25);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(61, (int) scoreSheetBO.getUpperSection().getUpperTotal());
        scoreSheetBO.getUpperSection().setAces(2);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(98, (int) scoreSheetBO.getUpperSection().getUpperTotal());
        scoreSheetBO.getUpperSection().setFours(8);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(106, (int) scoreSheetBO.getUpperSection().getUpperTotal());
        Assert.assertEquals(0, (int)scoreSheetBO.getLowerSection().getLowerTotal());
        scoreSheetBO.getLowerSection().setYahtzee(50);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(50,(int)scoreSheetBO.getLowerSection().getLowerTotal());
        scoreSheetBO.getLowerSection().setYahtzeeBonus(2);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(250,(int)scoreSheetBO.getLowerSection().getLowerTotal());
        scoreSheetBO.getLowerSection().setFourOfAKind(40);
        scoreSheetBO.getLowerSection().setThreeOfAKind(30);
        scoreSheetBO.getLowerSection().setFullHouse(25);
        scoreSheetBO.getLowerSection().setSmallStraight(30);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(375,(int)scoreSheetBO.getLowerSection().getLowerTotal());
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(106+375,(int)scoreSheetBO.getTotal());

    }

    @Test
    public void testONES_1(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,1,1,1,1), ScoreCategory.ONES);
        Assert.assertEquals(5,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testONES_2(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,1,1,2,2), ScoreCategory.ONES);
        Assert.assertEquals(3,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testONES_3(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,4,5), ScoreCategory.ONES);
        Assert.assertEquals(1,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testTWO_1(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,4,5), ScoreCategory.TWOS);
        Assert.assertEquals(2,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testTWO_2(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.TWOS);
        Assert.assertEquals(10,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testTWO_3(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,3,3,3), ScoreCategory.TWOS);
        Assert.assertEquals(2,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testTHREE(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,3,3,3), ScoreCategory.THREES);
        Assert.assertEquals(12,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testFOURS(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,3,4,3), ScoreCategory.FOURS);
        Assert.assertEquals(4,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testFIVE(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,4,5,5), ScoreCategory.FIVES);
        Assert.assertEquals(10,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testSIX(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,6,6,6), ScoreCategory.SIXES);
        Assert.assertEquals(18,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testThreeOfKind(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,6,6,6), ScoreCategory.THREE_OF_A_KIND);
        Assert.assertEquals(2+3+6+6+6,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testFourOFKind(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,6,6,6,6), ScoreCategory.FOUR_OF_A_KIND);
        Assert.assertEquals(2+6+6+6+6,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testFullHouse_1(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,6,6,6), ScoreCategory.FULL_HOUSE);
        Assert.assertEquals(25,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testFullHouse_2(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,3,6,6,6), ScoreCategory.FULL_HOUSE);
        Assert.assertEquals(0,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testSmallStraight_1(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,5,6), ScoreCategory.SMALL_STRAIGHT);
        Assert.assertEquals(0,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testSmallStraight_2(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,4,6), ScoreCategory.SMALL_STRAIGHT);
        Assert.assertEquals(30,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testLargeStraight_1(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,4,6), ScoreCategory.LARGE_STRAIGHT);
        Assert.assertEquals(0,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testLargeStraight_2(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,4,5), ScoreCategory.LARGE_STRAIGHT);
        Assert.assertEquals(40,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testYAHTZEE_1(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(1,2,3,4,5), ScoreCategory.YAHTZEE);
        Assert.assertEquals(0,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testYAHTZEE_2(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.YAHTZEE);
        Assert.assertEquals(50,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testYAHTZEE_3(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.YAHTZEE);
        Assert.assertEquals(50,(int)scoreSheetBO.getTotal());
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.YAHTZEE);
        Assert.assertEquals(150,(int)scoreSheetBO.getTotal());
    }
    @Test
    public void testYAHTZEE_4(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.YAHTZEE);
        Assert.assertEquals(50,(int)scoreSheetBO.getTotal());
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.YAHTZEE);
        Assert.assertEquals(150,(int)scoreSheetBO.getTotal());
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,2,2,2,2), ScoreCategory.YAHTZEE);
        Assert.assertEquals(250,(int)scoreSheetBO.getTotal());
    }

    @Test
    public void testChance(){
        serverGameManager.calculateScoreForPlayer(playerBO,createDiceArray(2,4,5,3,2), ScoreCategory.CHANCE);
        Assert.assertEquals(2+4+5+3+2,(int)scoreSheetBO.getTotal());
    }



    private List<Integer> createDiceArray(Integer... dices){
        if (dices.length != 5){
            System.out.println("Incorrect dice quantity. Check your code.");
        }
        return new ArrayList<>(Arrays.asList(dices));
    }

}
