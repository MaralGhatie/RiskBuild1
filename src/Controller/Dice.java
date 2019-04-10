package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Dice class creates dice objects that will be used in attack phase.
 * @author raoying
 * @version 2.0
 */
public class Dice {

    private boolean verify_choice1 = true;
    /**
     * chooseDiceNum method returns the player's choice of rolls he/she is going to play.
     *  @param flr int the minimal choice given to the player. 
     *  @param top int the maximum choice given to the player.
     *  @return dicenum int the number of dices that the player chooses.
     */
    public int chooseDiceNum(int flr,int top){

        int dicenum;
        Scanner read = new Scanner(System.in);
        Misc msc = new Misc();

        do {
            System.out.print("Value - ");
            dicenum = read.nextInt();

            verify_choice1 = msc.checkChoice(dicenum,flr,top);
        }while (!verify_choice1);

        return dicenum;
    }
    
    public int chooseDiceNum(int dicenum){
        return dicenum;
    }
    /**
     * roll method returns a list of random numbers as the player rolls.
     * All numbers should be less than 6. 
     * @param dicenum int the number of dices that the player chooses.
     * @return dicerolls  ArrayList<Integer> the list of numbers the player gets after rolling dices.
     */
    public ArrayList<Integer> roll(int dicenum){

        ArrayList<Integer> dicerolls = new ArrayList<>();
        int dicecount;

        Random rand = new Random();

        for (int i = 0; i < dicenum ;i++ ){
            dicecount = rand.nextInt(6) + 1;
            dicerolls.add(dicecount);
        }

        return dicerolls;
    }
    /**
     * getDesc method arranges the numbers the player gets when rolling dices in a descending order.
     * @param dicerolls ArrayList<Integer> the original list of numbers the player gets after rolling dices.
     * @return  dicerolls ArrayList<Integer> the new descending list of numbers after reordering.
     */
    public ArrayList<Integer> getDesc(ArrayList<Integer> dicerolls){
        Collections.sort(dicerolls, Collections.reverseOrder());
        return dicerolls;
    }
    
    /**
     * getBestRoll method returns the best roll of the player.
     * @param attackerrolls ArrayList<Integer> 
     * @return int the best roll. 
     */
    public int getBestRoll(ArrayList<Integer> attackerrolls) {
        return attackerrolls.get(0);
    }
}

