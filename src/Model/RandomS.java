package Model;

import Cards.Cards;
import Controller.Dice;
import Controller.Misc;
import View.PhaseView;

import java.util.*;


public class RandomS extends Player implements Strategy {


    @Override
    public void sReinforce(int playerIndex, int extraarmies) {

        showSummary();

        ArrayList<Integer> playercountries = new ArrayList<Integer>();
        ArrayList<Integer> playerarmies = new ArrayList<Integer>();
        Random rand = new Random();

        showCountryWithArmy(playerIndex);

        playercountries = player_country.get(playerIndex);

        if (playercountries.size() == 0){
            System.out.println("Sorry but size is 0");
        }

        else{
            playerarmies = country_army.get(playerIndex);

            int numarmy = playercountries.size() / 3;

            if (numarmy < 3)
                numarmy = 3;

            // numarmy = numarmy + extraarmies; ignore card exchange
            // boolean flag = false;

            while (numarmy > 0) {

                System.out.println("Number of armies you have is =>" + numarmy);

            /*System.out.println("Enter the country # you want to put armies in : ");
              int countryno = read.nextInt();*/
                //get a random country # under the player.
                // double rd = Math.random() * playercountries.size();
                int rd = rand.nextInt(playercountries.size());
                int countryno = playercountries.get(rd);

                /*System.out.println("Enter the number of armies you want to put : ");*/
                //get random number of armies
                int rd2 = rand.nextInt(numarmy)+1;
                int enteredarmy =  rd2;
                System.out.println("Selected country is " + countryno + " and selected armies are = "+ enteredarmy);
                numarmy = doreinforce(playerIndex, countryno, numarmy, enteredarmy);

            }

            refreshList(2);
        }

        showSummary();

        wannaSave(playerIndex, "REINFORCEMENT");
        // setCurrentStatus(GamePhase.ATTACK);
    }

    public void sAttack(int playerIndex){
        refreshList(3);
        Random rand = new Random();
        int randattacktimes = rand.nextInt(3) + 1;
        int randno = rand.nextInt(player_country.get(playerIndex).size());
        int attacker = player_country.get(playerIndex).get(randno);
        int atarmy = country_to_army.get(attacker);

        System.out.println("Random number selected is = " + randno );
        System.out.println("Random country selected with armies is = "+ country_map.get(attacker) + "(" + country_to_army.get(attacker) + ")");
        System.out.println("Random attack times selected is  = " + randattacktimes);

        while (randattacktimes > 0){

            int defender = findCountrytoAttack(attacker);
            if (defender > 0 && atarmy>0){
                //doAttack(defender,attacker);
                doAttackR(attacker,defender);
                //checkforkick();
            }
            else
                break;

            randattacktimes--;
            atarmy = country_to_army.get(attacker);
        }

        showSummary();

        wannaSave(playerIndex, "ATTACK");
        //setCurrentStatus(GamePhase.FORTIFICATION);
    }
    /**
     * This is the implementation of fortify method.
     * This is a function with an arraylist of countries of a specified player and another arraylist
     * for him/her armies.
     * In this implementation, the player is given a number of armies that depends on the number of
     * countries he/she owns . If the player owns all the countries of an entire continent,
     * the player is given an amount of armies corresponding to the continentط·آ£ط¢آ¯ط·آ·ط¹ط›ط·آ¢ط¢آ½s control value.
     *
     * @param playerIndex
     * @author Zahra & Maral
     * @version 1.0
     */
    @Override
    public void sFortify(int playerIndex) {
        ArrayList<Integer> playercountries = new ArrayList<Integer>();
        ArrayList<Integer> playerarmies = new ArrayList<Integer>();

        playercountries.addAll(player_country.get(playerIndex));
        playerarmies.addAll(country_army.get(playerIndex));

        System.out.println("This is the countries that you have " + playercountries);
        System.out.println("This is the armies you have assigned in your countries " + playerarmies);

        int origincountry = -1;
        int destcountry = -1;
        int originarmycountry = -1;

        boolean randflag = true;
        int random = -1;
        while(randflag){
            random = (int)(Math.random() * playercountries.size() + 1);
            if(playerarmies.get(random - 1) != 0){
                randflag = false;
            }
        }


        System.out.println("This is your random country: " + playercountries.get(random-1));
        origincountry = playercountries.get(random-1);
        originarmycountry = playerarmies.get(random-1);

        setAdjNumber();
        boolean flagpath = false;
        //System.out.println("This is ADJ with num " + adj_map_num);
        visited = new boolean[adj_map_num.lastKey() + 1];
        visited[origincountry] = true;
        path.clear();
        flagFindPath = false;
        path.add(origincountry);
        for(int i=0; i<playercountries.size(); i++){
            if(playercountries.get(i) != origincountry){
                destcountry = playercountries.get(i);
                findPath(playercountries, origincountry, destcountry);
                if(flagFindPath){
                    System.out.println("$$$$$" );
                    flagpath = true;
                    break;
                }
                path.clear();
                flagFindPath = false;
                visited = new boolean[adj_map_num.lastKey() + 1];
                visited[origincountry] = true;
                path.add(origincountry);
            }
        }

        if(!flagpath){
        	System.out.println();
            System.out.println("---- There is no valid fortification ----");
            System.out.println();
        }
        else{
        	System.out.println();
            System.out.println("---- Fortification Success ----");
            System.out.println();
            for(int i = 0; i<playercountries.size(); i++){
                if(playercountries.get(i) == origincountry){
                    playerarmies.set(i, 0);
                }
                if(playercountries.get(i) == destcountry){
                    int army = playerarmies.get(i);
                    playerarmies.set(i, army + originarmycountry);
                }
            }

            country_army.set(playerIndex, playerarmies);
            refreshList(2);
        }

        showSummary();

        wannaSave(playerIndex, "FORTIFICATION");
        // setCurrentStatus(GamePhase.ENDTURN);
    }

    public void doAttackR(int attacker,int defender){
        refreshList(3);
        Dice dice = new Dice();
        Random rand = new Random();

        int tempatarmy = country_to_army.get(attacker);
        int tempdefarmy = country_to_army.get(defender);
        int deflosttimes = 0;
        int lastdiceroll = 0;

        if (tempdefarmy == 0){
            deflosttimes = 1;
        }else {
            deflosttimes = tempdefarmy;
        }

        for (int k = 0;k < 100; k++ ){

            int attackdicenum;
            int defenddicenum;

            // choosing number of dices for attacker
            if (tempatarmy >= 3){
                System.out.println("Enter the number of Attacker dices : ");
                attackdicenum = rand.nextInt(3) + 1;
            }
            else {
                System.out.println("Enter the number of Attacker dices : ");
                attackdicenum = rand.nextInt(tempatarmy) + 1;
            }

            //cooosing number of dices for defender
            if (tempatarmy >= 2){
                System.out.println("Enter the number of defender dices : ");
                defenddicenum = rand.nextInt(2) + 1;
            }
            else {
                System.out.println("The number of Dices have been selected to be 1");
                defenddicenum = dice.chooseDiceNum(1);
            }

            lastdiceroll = attackdicenum;

            System.out.println("The number of attacker dices are - " + attackdicenum);
            System.out.println("The number of defender dices are - " + defenddicenum);

            ArrayList<Integer> attackerrolls = dice.roll(attackdicenum);
            ArrayList<Integer> defenderrolls = dice.roll(defenddicenum);

            System.out.println("The attacker Rolls are - ");
            for (int i = 0;i < attackerrolls.size();i++){
                System.out.print(attackerrolls.get(i) + ", ");
            }
            System.out.println("");
            System.out.println("The defender rolls are - ");
            for (int i = 0;i < defenderrolls.size();i++){
                System.out.print(defenderrolls.get(i) + ", ");
            }
            System.out.println("");

            attackerrolls = dice.getDesc(attackerrolls);
            defenderrolls = dice.getDesc(defenderrolls);

            int attacker_first_roll;
            int attacker_second_roll;
            int defender_first_roll;
            int defender_second_roll;

            attacker_first_roll = dice.getBestRoll(attackerrolls);
            defender_first_roll = dice.getBestRoll(defenderrolls);

            System.out.print("Attacker best roll is -> " + attacker_first_roll + "\n");
            System.out.print("Defender best roll is -> " + defender_first_roll + "\n");

            if (attacker_first_roll > defender_first_roll){

                System.out.println("Attacker won the best dice roll");
                System.out.print("Defender army before -> " + tempdefarmy + "\n");
                tempdefarmy--;
                deflosttimes--;
                System.out.print("Defender army reduced to -> " + tempdefarmy + "\n");
            }else{
                System.out.println("Defender won the best dice roll");
                System.out.print("Attacker army before -> " + tempatarmy + "\n");
                tempatarmy--;
                System.out.print("Attacker army reduced to -> " + tempatarmy + "\n");
            }

            System.out.print("Attacker army now = " + tempatarmy + "\n");
            System.out.print("Defender army now = " + tempdefarmy + "\n");

            if ((tempatarmy <= 1) || deflosttimes == 0){
                break;
            }

            if (attackdicenum > 1 && defenddicenum > 1){
                attackerrolls.remove(0);
                defenderrolls.remove(0);

                attacker_second_roll = dice.getBestRoll(attackerrolls);
                defender_second_roll = dice.getBestRoll(defenderrolls);

                if (attacker_second_roll > defender_second_roll){
                    System.out.println("Attacker won the second dice roll");
                    System.out.print("Defender army before -> " + tempdefarmy + "\n");
                    tempdefarmy--;
                    deflosttimes--;
                    System.out.print("Defender army reduced to -> " + tempdefarmy + "\n");
                }else {
                    System.out.println("Defender won the second dice roll");
                    System.out.print("Attacker army before -> " + tempatarmy + "\n");
                    tempatarmy--;
                    System.out.print("Attacker army reduced to -> " + tempatarmy + "\n");
                }

                System.out.print("Attacker army now = " + tempatarmy + "\n");
                System.out.print("Defender army now = " + tempdefarmy + "\n");

                if ((tempatarmy <= 1) || deflosttimes == 0){
                    break;
                }
            }
        }

        if (tempatarmy <= 1 ){
            tempatarmy = 1;
            System.out.println("Seems like attack wasn't succesful...Better luck next time");
            country_to_army.replace(attacker,tempatarmy);
            country_to_army.replace(defender,tempdefarmy);

        }

        if (tempdefarmy <= 0) {

            System.out.println("Attacker won !!!");
            changeCountryOwner(defender, attacker);
            giveArmiesAfterAttack(defender, attacker, lastdiceroll, tempatarmy,1);

            Cards cards = new Cards();
            cards.addRandomCard(country_owner.get(attacker));
        }

        refreshList(3);
        refreshList(1);

        if(player_country.get(country_owner.get(attacker)).size() == num_of_countries){
            gameoverflag = true;
        }
        //setCurrentStatus(GamePhase.FORTIFICATION);
        //setChanged();
        //notifyObservers(this);
    }
}
