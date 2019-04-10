package Controller;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * test class for Dice
 * @author raoying
 * @version 2.0
 */
public class DiceTest {

    Dice dice = new Dice();

    @Test
    public void chooseDiceNumTest() {

        int dicecount = dice.chooseDiceNum(1);
        Assert.assertEquals(1,dicecount);

    }

    @Test
    public void rollTest() {

        ArrayList<Integer> dicerolls = dice.roll(3);

        Assert.assertEquals(dicerolls.size(),3);
    }

    @Test
    public void getDescTest() {

        ArrayList<Integer> dicerolls = new ArrayList<>();

        dicerolls.add(4);
        dicerolls.add(6);
        dicerolls.add(2);

        dicerolls = dice.getDesc(dicerolls);

        Assert.assertEquals(dicerolls.indexOf(6), 0);
        Assert.assertEquals(dicerolls.indexOf(4), 1);
    }

    @Test
    public void getBestRollTest() {

        ArrayList<Integer> dicerolls = new ArrayList<>();

        dicerolls.add(4);
        dicerolls.add(6);
        dicerolls.add(2);

        dicerolls = dice.getDesc(dicerolls);

        Assert.assertEquals(dice.getBestRoll(dicerolls),6);

    }
    
    /**
     * checkLessThanSix() verifies the number that roll() method returns is less than 6.
     */
    @Test
    public void checkLessthanSix(){

        ArrayList<Integer> dicerolls = dice.roll(3);

        Assert.assertTrue(dicerolls.get(0) <= 6);
        Assert.assertTrue(dicerolls.get(1) <= 6);
        Assert.assertTrue(dicerolls.get(2) <= 6);


    }

}