package Model;

import Cards.Cards;
import Controller.Dice;
import Controller.Misc;
import View.PhaseView;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.*;

/**
 * This is one of the model classes. This class has all instances and functions of players.
 * @author Niki
 * @version 2.0
 */
public class Human extends Player implements Strategy {


    public void sReinforce(int playerIndex, int extraarmies){
        // ca_setup = getPlayerArmies(i);
        Scanner read = new Scanner(System.in);
        ArrayList<Integer> pc_setup = getPlayerCountries(playerIndex);

        showCountryWithArmy(playerIndex);

        int numarmy = pc_setup.size()/3;

        if (numarmy < 3)
            numarmy = 3;

        //  numarmy = numarmy + extraarmies;
        boolean flag = false;

        while (numarmy > 0){

            System.out.println("Number of armies you have is =>" + numarmy);

            System.out.println("Enter the country # you want to put armies in : ");
            int countryno = read.nextInt();

            if (pc_setup.contains(countryno)){

                System.out.println("Enter the number of armies you want to put : ");
                int enteredarmy = read.nextInt();

                if (numarmy < enteredarmy){
                    System.out.println("OOPS....not enough army!");
                }
                else {
                    flag = true;
                    numarmy = doreinforce(playerIndex,countryno,numarmy,enteredarmy);
                }
            }
            else {
                System.out.println("Country not in your list....ERROR!!!");
                System.out.println("Enter again");
            }
        }
        if(flag && numarmy == 0){
            wannaSave(playerIndex, "REINFORCEMENT");
        }
    }


    public void sAttack(int playerIndex){
        Scanner read = new Scanner(System.in);
        Misc msc = new Misc();
        boolean check = true;
        int choice = 0;
        boolean flag = true;

        do {
            do {

                System.out.println("Enter the choice :-");
                System.out.println("1> Do Attack");
                System.out.println("2> STOP ATTACK.... Please have mercy");

                choice = read.nextInt();
                check = msc.checkChoice(choice,1,2);
            }while (!check);

            if (choice == 1){
                showAttackable(playerIndex);
                initiateAttack(playerIndex);
            }
            else {
                break;
            }
        }while (flag);

    }


    public void sFortify(int playerIndex) {
        ArrayList<Integer> playercountries = new ArrayList<Integer>();
        ArrayList<Integer> playerarmies = new ArrayList<Integer>();

        playercountries.addAll(player_country.get(playerIndex));
        playerarmies.addAll(country_army.get(playerIndex));

        Scanner fortifyscanner = new Scanner(System.in);
        boolean pathflag = false;

        System.out.println("This is the countries that you have " + playercountries);
        System.out.println("This is the armies you have assigned in your countries " + playerarmies);

        while (!pathflag) {
            boolean originflag = false;
            boolean destflag = false;
            boolean armyflag = false;
            int origincountry = -1;
            int destcountry = -1;
            int originarmycountry = -1;
            while (!originflag) {
                System.out.println("Enter the origin country#:");
                origincountry = fortifyscanner.nextInt();
                for (int k = 0; k < playercountries.size(); k++) {
                    if (origincountry == playercountries.get(k)) {
                        originarmycountry = playerarmies.get(k);
                        if (originarmycountry != 0) {
                            originflag = true;
                        } else {
                            System.out.println("This country has no army");
                        }
                    }
                }
                if (!originflag && originarmycountry == -1) {
                    System.out.println("Origin country not exist in your list");
                }
            }
            while (!destflag) {
                System.out.println("Enter the destination country#:");
                destcountry = fortifyscanner.nextInt();
                if(origincountry == destcountry){
                    System.out.println("The origin country and destination country should not be the same!");
                }
                else {
                    for (int k = 0; k < playercountries.size(); k++) {
                        if (destcountry == playercountries.get(k)) {
                            destflag = true;
                        }
                    }
                    if (!destflag) {
                        System.out.println("Destination country not exist in your list");
                    }
                }
            }

            setAdjNumber();
            
            System.out.println("This is ADJ with num " + adj_map_num);
            visited = new boolean[adj_map_num.lastKey() + 1];
            visited[origincountry] = true;
            path.add(origincountry);
            findPath(playercountries, origincountry, destcountry);
            System.out.println("the path from recursive method " + path);

            if (path.contains(destcountry)) {
                //System.out.println("OK");
                pathflag = true;
                while (!armyflag) {
                    System.out.println("Enter the number of armies you want to move from country number " + origincountry + " to country number " + destcountry);
                    int armyCount = fortifyscanner.nextInt();
                    if (armyCount <= originarmycountry) {
                        armyflag = true;
                        // move armies to destination country
                        int indexorgncntry =  playercountries.indexOf(origincountry);
                        int oldarmiesOfOrgn = playerarmies.get(indexorgncntry);
                        int indexdestcntry = playercountries.indexOf(destcountry);
                        int oldarmiesOfdest = playerarmies.get(indexdestcntry);
                        int newarmiesoforgn = oldarmiesOfOrgn - armyCount;
                        playerarmies.set(indexorgncntry, newarmiesoforgn);
                        int newarmiesofdest = oldarmiesOfdest + armyCount;
                        playerarmies.set(indexdestcntry, newarmiesofdest);

                        refreshList(2);

                        System.out.println();
                        System.out.println("---- Fortification Success ----");
                        System.out.println();
                        //System.out.println("************************");
                        //System.out.println("NEW " + playercountries);
                        //System.out.println("NEW " + playerarmies);


                    } else {
                        System.out.println("You can not move this number of armies");
                    }
                }
            } else {
                path.clear();
                System.out.println("NOPE, there is no path! Try again");
            }
        }
    }

    /**
     * This function is used to choose attacking, defending countries and also strategy
     * @param index refers to index of countries
     * @author swetha
     * @version 2.0
     */

    public void initiateAttack(int index){

        Scanner read = new Scanner(System.in);

        int attacker;
        int defender;

        do {
            System.out.println("Enter your country (Attacking counrty #) ");
            attacker = read.nextInt();

            if (!player_country.get(index).contains(attacker)){
                System.out.println("ERROR !!... Enter Again");
            }
        }while (!((country_to_army.get(attacker)) > 0) && !(player_country.get(index).contains(attacker)));

        do {
            System.out.println("Enter the country to be attacked (Defending country #) ");
            defender = read.nextInt();

            if(country_owner.get(defender).equals(country_owner.get(attacker))){
                System.out.println("ERROR !!!... Enter Again");
            }
        }while (!adj_map_num.get(attacker).contains(defender) || country_owner.get(defender).equals(country_owner.get(attacker)));

        System.out.println("You choose Attacking country - "+country_map.get(attacker));
        System.out.println("You chose Defending country - " + country_map.get(defender));

        System.out.println("Select Attack strategy :- "  );
        System.out.println("1> Normal Attack");
        System.out.println("2> All-out Attack mode");

        boolean flag = false;
        int attackchoice = 0;

        Misc msc = new Misc();

        do {
            attackchoice = read.nextInt();
            flag = msc.checkChoice(attackchoice,1,2);
        }while (!flag);

        if (attackchoice == 1){
            doAttack(attacker,defender);
        }else {
            doAttackAllOut(attacker,defender);
        }
    }

    /**
     * This function is used to roll dice,print dice in sorted order
     * @param attacker refers to index of attacking player
     * @param defender refers to index of defending player
     * @author paras
     * @version 2.0
     */

    public void doAttack(int attacker,int defender){

        Dice dice = new Dice();
        Misc msc = new Misc();

        Scanner read = new Scanner(System.in);

        int tempatarmy = country_to_army.get(attacker);
        int tempdefarmy = country_to_army.get(defender);
        int deflosttimes = 0;
        boolean retreat = false;
        int lastdiceroll = 0;

        if (tempdefarmy == 0){
            deflosttimes = 1;
        }else {
            deflosttimes = tempdefarmy;
        }

        for (int k = 0;k < 100; k++ ){

            boolean verifychoice1;
            boolean verifychoice2;

            int attackdicenum;
            int defenddicenum;

            // choosing number of dices for attacker
            if (tempatarmy >= 3){
                System.out.println("Enter the number of Attacker dices : ");
                attackdicenum = dice.chooseDiceNum(1,3);
            }
            else {
                System.out.println("Enter the number of Attacker dices : ");
                attackdicenum = dice.chooseDiceNum(1,tempatarmy);
            }

            //cooosing number of dices for defender
            if (tempatarmy >= 2){
                System.out.println("Enter the number of defender dices : ");
                defenddicenum = dice.chooseDiceNum(1,2);
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

            if ((tempatarmy == 1) || deflosttimes == 0){
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

                if ((tempatarmy == 1) || deflosttimes == 0){
                    break;
                }

                System.out.println("Do you wanna retreat(0-No/1-Yes)...Enter the number");
                if (read.nextInt() == 1){
                    retreat = true;
                    break;
                }
            }
        }

        if (retreat == true){
            System.out.println("The armies after Retreat are being updated");
            country_to_army.replace(attacker,tempatarmy);
            country_to_army.replace(defender,tempdefarmy);
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
    }
}
