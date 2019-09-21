package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.service.ServerGameManager;
import me.largetimmo.comp4004.a1.service.bo.LowerSectionGameScore;
import me.largetimmo.comp4004.a1.service.bo.PlayerBO;
import me.largetimmo.comp4004.a1.service.bo.ScoreSheetBO;
import me.largetimmo.comp4004.a1.service.bo.UpperSectionGameScore;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreCalculateTest {

    private ServerGameManager serverGameManager = new ServerGameManager(null,null);

    @Test
    public void calculateUpperSectionBonus(){
        UpperSectionGameScore upperSectionGameScore = new UpperSectionGameScore();
        Assert.assertEquals(0, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setSixes(36);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(36, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setFives(25);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(61, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setAces(2);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(98, (int) upperSectionGameScore.getTotal());
        upperSectionGameScore.setFours(8);
        upperSectionGameScore.calculateTotal();
        Assert.assertEquals(106, (int) upperSectionGameScore.getTotal());

    }

    @Test
    public void calculateLowerSectionBonus(){
        LowerSectionGameScore lowerSectionGameScore  = new LowerSectionGameScore();
        Assert.assertEquals(0, (int)lowerSectionGameScore.getTotal());
        lowerSectionGameScore.setYahtzee(50);
        lowerSectionGameScore.calculateTotal();
        Assert.assertEquals(50,(int)lowerSectionGameScore.getTotal());
        lowerSectionGameScore.setYahtzeeBonus(2);
        lowerSectionGameScore.calculateTotal();
        Assert.assertEquals(250,(int)lowerSectionGameScore.getTotal());
        lowerSectionGameScore.setFourOfAKind(40);
        lowerSectionGameScore.setThreeOfAKind(30);
        lowerSectionGameScore.setFullHouse(25);
        lowerSectionGameScore.setSmallStraight(30);
        lowerSectionGameScore.calculateTotal(); 
        Assert.assertEquals(375,(int)lowerSectionGameScore.getTotal());



    }

    @Test
    public void calculateScoreSheetTotal(){
        ScoreSheetBO scoreSheetBO = new ScoreSheetBO();
        Assert.assertEquals(0,(int)scoreSheetBO.getTotal());
        Assert.assertEquals(0, (int) scoreSheetBO.getUpperSection().getTotal());
        scoreSheetBO.getUpperSection().setSixes(36);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(36, (int) scoreSheetBO.getUpperSection().getTotal());
        scoreSheetBO.getUpperSection().setFives(25);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(61, (int) scoreSheetBO.getUpperSection().getTotal());
        scoreSheetBO.getUpperSection().setAces(2);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(98, (int) scoreSheetBO.getUpperSection().getTotal());
        scoreSheetBO.getUpperSection().setFours(8);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(106, (int) scoreSheetBO.getUpperSection().getTotal());
        Assert.assertEquals(0, (int)scoreSheetBO.getLowerSection().getTotal());
        scoreSheetBO.getLowerSection().setYahtzee(50);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(50,(int)scoreSheetBO.getLowerSection().getTotal());
        scoreSheetBO.getLowerSection().setYahtzeeBonus(2);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(250,(int)scoreSheetBO.getLowerSection().getTotal());
        scoreSheetBO.getLowerSection().setFourOfAKind(40);
        scoreSheetBO.getLowerSection().setThreeOfAKind(30);
        scoreSheetBO.getLowerSection().setFullHouse(25);
        scoreSheetBO.getLowerSection().setSmallStraight(30);
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(375,(int)scoreSheetBO.getLowerSection().getTotal());
        scoreSheetBO.calculateTotal();
        Assert.assertEquals(106+375,(int)scoreSheetBO.getTotal());

    }

}
