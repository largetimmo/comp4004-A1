package me.largetimmo.comp4004.a1;

import me.largetimmo.comp4004.a1.service.bo.LowerSectionGameScore;
import me.largetimmo.comp4004.a1.service.bo.UpperSectionGameScore;
import org.junit.Assert;
import org.junit.Test;

public class ScoreCalculateTest {

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

}
