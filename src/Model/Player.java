package Model;

import Cards.Cards;
import Controller.Dice;
import Controller.Misc;
import View.PhaseView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * This is one of the model classes. This class has all instances and functions of players.
 * @author Niki
 * @version 2.0
 */
public class Player extends Observable {

    PhaseView phase_view = new PhaseView();

    static boolean gameoverflag = false;
    /**
     * public GamePhase currentGamePhase will take one of the phases mentioned in
     * GamePhase.
     */
    public GamePhase currentGamePhase;

    private static String player_name;
    private static int player_index;

    private static int num_of_players;

    public static int num_of_countries;

    static TreeMap<Integer, String> country_map = new TreeMap<>();
    static TreeMap<Integer, ArrayList> adj_map = new TreeMap<>();

    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Integer> playerstrategy = new ArrayList<>();

    static ArrayList<ArrayList<Integer>> player_country = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer>> country_army = new ArrayList<ArrayList<Integer>>();

    static boolean[] visited;
    static boolean flagFindPath = false;
    static ArrayList<Integer> path = new ArrayList<Integer>();
    static TreeMap<Integer, ArrayList> adj_map_num = new TreeMap<>();

    static TreeMap<Integer,Integer> country_to_army = new TreeMap<Integer, Integer>();
    static TreeMap<Integer,Integer> country_owner = new TreeMap<Integer, Integer>();

    static ArrayList<Double> map_percentages = new ArrayList<Double>(num_of_players);
    static ArrayList<ArrayList<Double>> player_continents = new ArrayList<ArrayList<Double>>(num_of_players);
    static ArrayList<Integer> total_armies = new ArrayList<>(num_of_players);

    static private int file_counter = 0;


    /**
     * The game Phase that can be used by a player in the game
     *
     */
    public enum GamePhase {

        /**
         * This is the world domination phase
         */
        WORLD,
        /**
         * This is a REINFORCEMENT phase in the game.
         */
        REINFORCEMENT,
        /**
         * This is a ATTACK phase in the game.
         */
        ATTACK,
        /**
         * This is a FORTIFICATION phase in the game.
         */
        FORTIFICATION,

        /**
         * This is when each player's turn is over
         */

        ENDTURN;
    }

    /**
     * This method set current status for the game. It follows the observer pattern.
     * @param currentPhase an object of GamePhase
     */

    public void setCurrentStatus(GamePhase currentPhase){
        // System.out.println("Current status is  = " + currentPhase);
        this.currentGamePhase = currentPhase;
        //  System.out.println("Current game phase is = " + currentGamePhase);
        setChanged();
        notifyObservers(this);

    }

    public void initializectow(){

        for(int country : country_map.keySet()){
            country_owner.put(country, 0);
        }
    }


    /**
     * The initialize method runs when user enters the game.</br>
     * It shows the total number of countries involved, </br>
     * and based on the number of players that user chose create players in an player ArrayList
     *
     * @param cmap Referring to country map
     * @param amap referring to Adjacency map
     * @see #players
     */

    public void initialize(TreeMap<Integer, String> cmap, TreeMap<Integer, ArrayList> amap, int numcountry) {
        country_map = cmap;
        adj_map = amap;
        num_of_countries = numcountry;
    }

    /**
     * This is setter method and sets player name and number of player and calls playerCountries
     * @param numplayer refers to number of players
     * @param playersname refers to name of player
     * @author swetha
     * @version 2.0
     */

    public void setPlayers(int numplayer, ArrayList<String> playersname,  ArrayList<Integer> strategies, int tour){
    	if(tour == 1){
    		 num_of_players = numplayer;
    	     players.addAll(playersname);
    	     playerstrategy.addAll(strategies);

    	     playerCountries();
    	     printPlayerCountries();
    	     printPlayerArmies();
    	}
    	else{
    		num_of_players = numplayer;

            for (int i = 0; i < num_of_players; i++) {

                try {
                    players.set(i,playersname.get(i));
                }catch (Exception e){
                    players.addAll(playersname);
                }

                try {
                    playerstrategy.set(i,strategies.get(i));
                }catch (Exception e){
                 playerstrategy.addAll(strategies);
                }

                playerCountries();
                printCtoaOwnerMap();
                printPlayerCountries();
                printPlayerArmies();
            }
    	}
    }

    /**
     * giveCountry assigns countries to players randomly,and stores these info in player_country,<br>
     * meanwhile initialize country_army, setting its value as all 0.
     *
     * @see #player_country
     * @see #country_army
     * @author paras
     * @version 1.0
     */

    public void playerCountries() {

        int window = num_of_countries / num_of_players;
        int start = 0;
        int end = window;

        Set<Integer> countrynums = country_map.keySet();
        ArrayList<Integer> countrydivider = new ArrayList<>(num_of_countries);

        for (int key : countrynums) {
            countrydivider.add(key);
        }

        for (int key: countrynums){
            country_to_army.put(key,0);
        }

        Collections.shuffle(countrydivider);
        System.out.println(countrydivider);

        for (int i = 0; i < num_of_players; i++) {

            ArrayList<Integer> temp = new ArrayList<Integer>();
            ArrayList<Integer> army = new ArrayList<Integer>(window);

            for (int j = start; j < end; j++) {
                temp.add(countrydivider.get(j));
                army.add(0);

                country_owner.put(countrydivider.get(j),i);
            }

            start = start + window;
            end = end + window;

            player_country.add(temp);
            country_army.add(army);
        }

        setAdjNumber();
    }

    /**
     * This function is to give armies at the beginning of attack phase
     * @author niki
     * @version 2.0
     */

    public void giveInitialArmies(){

        int armiesgiven = chooseInitialArmies();

        while (armiesgiven > 0) {

            for (int i = 0; i < num_of_players; i++) {

                System.out.println("The Countries of player " + (i + 1) + " are : ");
                for (int country : player_country.get(i)) {
                    System.out.println(country + ". " + country_map.get(country));
                }

                System.out.println("Enter the country you wanna put army in");
                Scanner read = new Scanner(System.in);

                int chosencountry = read.nextInt();

                country_to_army.replace(chosencountry,country_to_army.get(chosencountry)+1);

            }

            armiesgiven--;

            refreshList(1);
        }
    }

    /**
     * This function is used to give armies based on number of players and is done before reinforcement
     * @return armiesgiven returns armies
     * @author niki
     * @version 2.0
     */

    public int chooseInitialArmies(){

        int armiesgiven = 0;

        switch (num_of_players){
            case 2:
                armiesgiven = 5;
                break;

            case 3:
                armiesgiven = 4;
                break;

            case 4:
                armiesgiven = 3;
                break;
        }
        return armiesgiven;
    }

    /**
     * This function is used to give initial armies to all players
     * @author paras
     * @version 2.0
     */

    public void simulateInitial(){

        Random rand = new Random();

        for (int i = 0;i < players.size(); i++){

            ArrayList<Integer> countrynums = new ArrayList<Integer>();
            countrynums.addAll(country_to_army.keySet());

            int armiesgiven = 10;
            while (armiesgiven > 0)
            {
                int position = rand.nextInt(countrynums.size());
                int chosencountry = countrynums.get(position);
                int choosearmies = rand.nextInt(7) + 1;

                country_to_army.replace(chosencountry,country_to_army.get(chosencountry)+choosearmies);

                armiesgiven = armiesgiven - choosearmies;
            }
        }

        refreshList(1);

    }

    /**
     * The tusetPlayersrn method implements the main play phase,
     * in which all players are given a turn in a round-robin fashion.<br>
     * Each playerط·آ·ط¢آ£ط·آ¢ط¢آ¢ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬ط¹â€کط·آ¢ط¢آ¬ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬أ¢â‚¬ع†ط·آ¢ط¢آ¢s turn is itself divided into three phases:
     * 1) reinforcement phase ;2) attack phase; 3)fortifications phase <br>
     * Once a player is finished with these three phases, the next player turn starts.<br>
     * @author ying&Swetha
     * @version 2.0
     */
    public int doreinforce(int index,int countryno,int numarmy,int enteredarmy){

        //store army_num in the  ArrayList<ArrayList<Integer>> country_army
        //search the position of the country
        int position = player_country.get(index).indexOf(countryno);

        System.out.println("THIS is position " + position);
        System.out.println("THIS is country army " + country_army.get(index).get(position));
        //give the value to this position

        int prevarmy = country_army.get(index).get(position);
        country_army.get(index).set(position,enteredarmy + prevarmy);
        //System.out.println(army);
        //System.out.println("armies left:"+num_army);

        numarmy = numarmy - enteredarmy;

        refreshList(2);
        //setCurrentStatus(GamePhase.ATTACK);

        // setChanged();
        // notifyObservers(this);
        return numarmy;


    }

    /**
     * This function shows the list of countries with armies that can be used to attack
     * @param index refers to index of countries
     * @author swetha
     * @version 2.0
     */

    public void showAttackable(int index){

        System.out.println("Following are the list of your countries that you own with their respective armies : ");

        ArrayList<Integer> temppc  = player_country.get(index);

        for (int i:temppc){

            int countryindex = i;
            System.out.println(countryindex + ". " + country_map.get(countryindex)+"(" + country_to_army.get(countryindex)+")");
        }

        System.out.println("Following are the countries that can be used to attack (with armies > 1) to other countries : ");

        for (int i:temppc){

            int countryindex = i;

            if (country_to_army.get(countryindex) > 0){
                System.out.println("=> " + countryindex + ". " + country_map.get(countryindex) + "(" + country_to_army.get(countryindex) + ")");

                ArrayList<Integer> countryadj = adj_map_num.get(countryindex);
                for ( Integer k : countryadj){
                    if(!(country_owner.get(k) == country_owner.get(countryindex)))
                        System.out.println("\t\t X " + k + "."  + country_map.get(k) + "(" + country_to_army.get(k) + ")");
                }
            }
            else {
                continue;
            }
        }

    }


    public void doAttackAllOut(int attacker,int defender){
        refreshList(3);
        Dice dice = new Dice();
        Misc msc = new Misc();

        Scanner read = new Scanner(System.in);

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

            boolean verifychoice1;
            boolean verifychoice2;

            int attackdicenum;
            int defenddicenum;

            // choosing number of dices for attacker
            if (tempatarmy >= 3){
                System.out.println("Enter the number of Attacker dices : ");
                attackdicenum = dice.chooseDiceNum(3);
            }
            else {
                System.out.println("Enter the number of Attacker dices : ");
                attackdicenum = dice.chooseDiceNum(tempatarmy);
            }

            //cooosing number of dices for defender
            if (tempatarmy >= 2){
                System.out.println("Enter the number of defender dices : ");
                defenddicenum = dice.chooseDiceNum(2);
            }
            else {
                System.out.println("The number of Dices have been selected to be 1");
                defenddicenum = dice.chooseDiceNum(1);
            }

            System.out.println("The number of attacker dices are - " + attackdicenum);
            System.out.println("The number of defender dices are - " + defenddicenum);

            lastdiceroll = attackdicenum;

            ArrayList<Integer> attackerrolls = dice.roll(attackdicenum);
            ArrayList<Integer> defenderrolls = dice.roll(defenddicenum);

            System.out.println("The attacker Rolls are - ");
            for (int i = 0;i < attackerrolls.size();i++){
                System.out.print(attackerrolls.get(i) + ", ");
            }
            System.out.println("The defender rolls are - ");
            for (int i = 0;i < defenderrolls.size();i++){
                System.out.print(defenderrolls.get(i) + ", ");
            }
            System.out.println("");

            attackerrolls = dice.getDesc(attackerrolls);
            defenderrolls = dice.getDesc(defenderrolls);

            int attackerfirstroll;
            int attackersecondroll;
            int defenderfirstroll;
            int defendersecondroll;

            attackerfirstroll = dice.getBestRoll(attackerrolls);
            defenderfirstroll = dice.getBestRoll(defenderrolls);

            System.out.print("Attacker best roll is -> " + attackerfirstroll + "\n");
            System.out.print("Defender best roll is -> " + defenderfirstroll + "\n");

            if (attackerfirstroll > defenderfirstroll){

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

                attackersecondroll = dice.getBestRoll(attackerrolls);
                defendersecondroll = dice.getBestRoll(defenderrolls);

                if (attackersecondroll > defendersecondroll){
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
            giveArmiesAfterAttack(defender, attacker, lastdiceroll, tempatarmy,2);

            Cards cards = new Cards();
            cards.addRandomCard(country_owner.get(attacker));

        }
        refreshList(3);
        refreshList(1);
        if(player_country.get(country_owner.get(attacker)).size() == num_of_countries){
            gameoverflag = true;
        }

        int playerindex = country_owner.get(attacker);

    }

    /**
     * This function changes the previous owner of the country
     * @param defender index of the attacking player
     * @param attacker index of the defending player
     */

    public void changeCountryOwner(int defender,int attacker){

        System.out.println("Attacker successfully conqured the country -> " + country_map.get(defender));
        System.out.println("Chanding owner of " + country_map.get(defender) + " from :");
        System.out.println(players.get(country_owner.get(defender)) + " to ==> " + players.get(country_owner.get(attacker)));

        ArrayList atpc = player_country.get(country_owner.get(attacker));
        ArrayList atca = country_army.get(country_owner.get(attacker));
        ArrayList defpc = player_country.get(country_owner.get(defender));
        ArrayList defca = country_army.get(country_owner.get(defender));

        int indexindefender = defpc.indexOf(defender);

        atpc.add(defender);
        atca.add(0);
        defpc.remove(indexindefender);
        defca.remove(indexindefender);
    }

    /**
     * This function is used to assign armies after each attack
     * @param defender index of the defending player
     * @param attacker index of the attacking player
     * @param lastdiceroll the number of the the last dice
     * @param atarmy the number of attacker armies
     * @author maral
     * @version 2.0
     */

    public void giveArmiesAfterAttack(int defender,int attacker,int lastdiceroll,int atarmy,int option){

        Misc msc = new Misc();
        boolean check = false;

        int minimumtogive;
        int armiesgiven;

        Scanner read = new Scanner(System.in);

        if (lastdiceroll < atarmy){
            minimumtogive = lastdiceroll;
        }else {
            minimumtogive = atarmy;
        }

        armiesgiven = minimumtogive;

        System.out.println("<" + armiesgiven + "> armies given from " + country_map.get(attacker) + " to " + country_map.get(defender));
        country_to_army.replace(defender,armiesgiven);
        country_to_army.replace(attacker,atarmy - armiesgiven);

        System.out.println("Armies of " + country_map.get(attacker) + "are :" + country_to_army.get(attacker));
        System.out.println("Armies of " + country_map.get(defender) + "are :" + country_to_army.get(defender));

    }

    public void checkforkick(){

        ArrayList<String> toberemoved = new ArrayList<>();

        System.out.println("Current players  = " + players);
        System.out.println("Current strategy  = " + playerstrategy);

        for (int i = 0; i < players.size(); i++) {
            ArrayList temppc = player_country.get(i);
            if (temppc.size() == 0){
                System.out.println("The player with name = " + players.get(i) + " has 0 countries and will be removed");
                toberemoved.add(players.get(i));
            }
        }

        System.out.println("to be removed = "+ toberemoved);

        if (toberemoved.size() > 0){
            for (int i = 0; i < toberemoved.size(); i++) {
                int getindex = players.indexOf(toberemoved.get(i));

                System.out.println("Index at where player is selected to be removed is ...");
                System.out.println(getindex);

                players.remove(getindex);
                playerstrategy.remove(getindex);
                player_country.remove(getindex);
                country_army.remove(getindex);
            }
        }

        System.out.println("After removing the players is  = " + players);
        System.out.println("After removing the strategy is  = " + playerstrategy);
    }

    /**
     * This is the implementation of findPath method.
     * This is a function with an arraylist of countries of a specified player, his/her current country
     * and the destination country.
     * In this implementation, it is going to find a path between the current and destination countries
     * to do the fortification.
     *
     * @param playercountries a set of the countries of the player
     * @param curcou          the number of the current country (it is given by the player)
     * @param descou          the number of the destination country (it is given by the player)
     * @author Zahra & Maral
     * @version 1.0
     */

    public void findPath(ArrayList<Integer> playercountries, int curcou, int descou) {
    	System.out.println();
        System.out.println("The current country " + curcou);
        System.out.println("The destination country " + descou);
        ArrayList<Integer> tempadjacent = new ArrayList<Integer>();
        tempadjacent.addAll(adj_map_num.get(curcou));
       // System.out.println("this is temp ADJ " + tempadjacent);


        for (int i = 0; i < tempadjacent.size(); i++) {
            if (visited[tempadjacent.get(i)] == false && playercountries.contains(tempadjacent.get(i))) {
                //System.out.println("this is temp.get " + tempadjacent.get(i));
                visited[tempadjacent.get(i)] = true;
                path.add(tempadjacent.get(i));
                System.out.println("The path till now: " + path);
                if (tempadjacent.get(i) == descou) {
                    System.out.println("BREAKED");
                    flagFindPath = true;
                    break;
                } else {
                    findPath(playercountries, tempadjacent.get(i), descou);
                    //System.out.println("***** Before pop " + path);
                    if (path.size() != 0)
                        path.remove(path.size() - 1);
                   // System.out.println("***** After pop " + path);
                }
            }
        }
    }

    /**
     * This is the implementation of setAdjNumber method.
     * Countries are stored in A-map qand country_map as a String. In this implementaion, it is
     * going to make an array (adj_map_num) to transform the country,s name to a sepecific number.
     * The outline of this function is adj_map_num arraylist.
     * @author Zahra & Maral
     * @version 1.0
     */

    public void setAdjNumber() {

        Set<Integer> adjacentmapkeys = adj_map.keySet();
        Set<Integer> cntrymapkeys = country_map.keySet();

        for (Integer adjacentkey : adjacentmapkeys) {
            ArrayList<String> adjacentname = adj_map.get(adjacentkey);
            ArrayList<Integer> adjacentnum = new ArrayList<Integer>();

            for (int i = 0; i < adjacentname.size(); i++) {
                for (Integer C_key : cntrymapkeys) {
                    if (adjacentname.get(i).equals(country_map.get(C_key))) {
                        adjacentnum.add(C_key);
                        break;
                    }
                }
            }
            adj_map_num.put(adjacentkey, adjacentnum);
        }
    }

    /**
     * This method refreshes the tree maps according to the player's option.
     * @param option player's option
     */
    public void refreshList(int option){

        // option 1 = treemap country_to_army updates list country_army
        if (option == 1) {
            for (int i = 0; i < players.size(); i++) {

                System.out.println("Players size = " + players.size());

                ArrayList<Integer> pc = player_country.get(i);
                ArrayList<Integer> ca = country_army.get(i);
                int armies = 0;

                for (int j = 0;j < pc.size(); j++){
                    armies = country_to_army.get(pc.get(j));
                    try {
                        ca.set(j,armies);
                    }catch (Exception e){
                        ca.add(j,armies);
                    }
                }
                country_army.set(i,ca);
            }
        }

        //option 2 = country_army list updates treemap country_to_army
        if (option == 2){

            for (int i = 0;i < players.size();i++){

                ArrayList<Integer> pc = player_country.get(i);
                ArrayList<Integer> ca = country_army.get(i);
                int armies = 0;

                for (int j = 0;j < pc.size(); j++){
                    armies = ca.get(j);
                    country_to_army.put(pc.get(j),armies);
                }
            }
        }

        //option 3 = Treemap country_owner updates player_country

        if (option == 3){

            for (int index = 0; index < player_country.size(); index++) {

                ArrayList<Integer> temppc = player_country.get(index);

                for (int i = 0; i < temppc.size(); i++) {
                    country_owner.replace(temppc.get(i), index);

                }
            }
        }
    }

    /**
     *  This method calculates the percentage of the map controlled by every player.
     * @param playerindex
     * @return percentage
     * @author Zahra
     */
    private int mapPercentage(int playerindex){

        int numcountry = 0;
        ArrayList<Integer> playercountries = player_country.get(playerindex);
        for (int i = 0; i< playercountries.size(); i++){
            if(playercountries.get(i) != 0){
                numcountry++;
            }
        }
        int percentage = (numcountry / num_of_countries) * 100;
        return percentage;
    }

    /**
     *  This method shows the continents controlled by every player.
     * @param playerIndex
     * @author Zahra
     */
    public void continentOfPlayer(int playerIndex, String continent) throws Exception {

        double count = 0;
        double percentage = 0;

        ArrayList<Integer> playercountries = player_country.get(playerIndex);

        Setup sup = new Setup();
        sup.loadMap();
        sup.continentControl();

        int countrychoice = 0;

        switch (continent) {
            case "ASIA":
                for (int i = 0; i < playercountries.size(); i++) {
                    if (playercountries.get(i) > 0 && playercountries.get(i) <= 20) {
                        count++;
                    }
                }
                countrychoice = 0;
                percentage = count/sup.getAsiaCountries().size() *100;

                break;

            case "NAMERICA":
                for (int i = 0; i < playercountries.size(); i++) {
                    if (playercountries.get(i) > 20 && playercountries.get(i) <= 30) {
                        count++;
                    }
                }
                countrychoice = 1;
                percentage = (count / (double)sup.getNamericaCountries().size()) * 100;
                break;

            case "SAMERICA":
                for (int i = 0; i < playercountries.size(); i++) {
                    if (playercountries.get(i) > 30 && playercountries.get(i) <= 40) {
                        count++;
                    }
                }
                countrychoice = 2;
                percentage = (count / (double)sup.getSamericaCountries().size()) * 100;
                break;

            case "EUROPE":
                for (int i = 0; i < playercountries.size(); i++) {
                    if (playercountries.get(i) > 40 && playercountries.get(i) <= 60) {
                        count++;
                    }
                }
                countrychoice = 3;
                percentage = (count / (double)sup.getEuropeCountries().size()) * 100;
                break;

            case "AFRICA":
                for (int i = 0; i < playercountries.size(); i++) {
                    if (playercountries.get(i) > 60 && playercountries.get(i) <= 80) {
                        count++;
                    }
                }
                countrychoice = 4;
                percentage = (count / (double)sup.getAfricaCountries().size()) * 100;
                break;

            case "AUSTRALIA":
                for (int i = 0; i < playercountries.size(); i++) {
                    if (playercountries.get(i) > 80 && playercountries.get(i) <= 90) {
                        count++;
                    }
                }
                countrychoice = 5;
                percentage = (count / (double)sup.getAustraliaCountries().size()) * 100;
                break;
        }

        player_continents.get(playerIndex).set(countrychoice,percentage);
    }

    /**
     * This function gets  list of percentage of maps owned by player
     * @return map_percentages percentages of maps
     * @author ying
     * @version 2.0
     */

    public ArrayList<Double> getMapPercentages() {
        return map_percentages;
    }

    /**
     * This function is used to get list of continens owned by player
     * @return player_continents
     * @author ying
     * @version 2.0
     */

    public ArrayList<ArrayList<Double>> getPlayerContinents() {
        return player_continents;
    }

    /**
     * This function refers to list of total armies
     * @return total_armies
     * @author maral
     * @version 2.0
     */

    public ArrayList<Integer> getTotalArmies() {
        return total_armies;
    }

    /**
     * This function refers to set domination view
     * @author maral
     * @version 2.0
     */

    public void setupDomination(){

        for (int i = 0; i < num_of_players; i++) {

            map_percentages.add(i,0.0);
            total_armies.add(i,0);

            ArrayList<Double> temppercent = new ArrayList<>();

            for (int j = 0;j < 6;j++){
                temppercent.add((double) 0);
            }

            player_continents.add(temppercent);
        }
    }

    /**
     *  This method calculates  the total number of armies owned by every player.
     * @param playerIndex
     * @return totalno
     * @author Zahra
     */
    public void setPlayerTotalArmies(int playerIndex){

        total_armies.set(playerIndex,country_army.get(playerIndex).size());
        setPlayerIndex(playerIndex);
        setCurrentStatus(GamePhase.WORLD);
        //setChanged();
        //notifyObservers(this);
    }

    public void wannaSave(int playerindex,String phase){
        System.out.println("Do you want to save the current status of game in a file");
        System.out.println("1> Save game");
        System.out.println("2> Don't save the game");

        Scanner read = new Scanner(System.in);
        Misc msc = new Misc();

        int choice = read.nextInt();
        boolean flag = false;

        do{
            flag = msc.checkChoice(choice,1,2);
        }while (!flag);

        if (choice == 2){
        	System.out.println();
            System.out.println("Game Continued ...");
            System.out.println();
        }else {
            saveGame(playerindex,phase);
            System.exit(0);
        }
    }

    public void saveGame(int playerindex,String phase){

        /*
        the current structure is -
        currentplayerwhosaved
        currentphase
        numberofplayers currently active
        pc for each
        ca for each
        names seperated with lines
        strategies seperated with lines

         */


        String filepath = ".//saves/";
        String filename = "Save_state" + Integer.toString(file_counter) + ".txt";

        FileWriter fr = null;
        try {
            fr = new FileWriter(filepath + filename);
        } catch (IOException e) {}


        BufferedWriter br = new BufferedWriter(fr);
        PrintWriter out = new PrintWriter(br);

        int size = players.size();

        out.write(Integer.toString(playerindex));
        out.write(System.lineSeparator());
        out.write(phase);
        out.write(System.lineSeparator());
        out.write(Integer.toString(players.size()));
        out.write(System.lineSeparator());

        for (int i = 0; i < players.size(); i++) {

            //printing country armies
            String firstpc = Integer.toString(player_country.get(i).get(0));
            out.write(firstpc);
            for (int j = 1;j < player_country.get(i).size(); j++){
                String s = "," + player_country.get(i).get(j);
                out.write(s);
            }
            out.write(System.lineSeparator());

            //printing country armies
            String firstca = Integer.toString(country_army.get(i).get(0));
            out.write(firstca);
            for (int j = 1; j < country_army.get(i).size(); j++) {
                String s = "," + country_army.get(i).get(j);
                out.write(s);
            }
            out.write(System.lineSeparator());
        }

        for (int i = 0; i < size; i++) {
            out.write(players.get(i));
            out.write(System.lineSeparator());
        }
        for (int i = 0; i < size; i++) {
            System.out.print(playerstrategy.get(i) + ", ");
        }

        for (int i = 0; i < size; i++) {
            out.write(playerstrategy.get(i).toString());
            out.write(System.lineSeparator());
        }

        out.close();
    }


    /**
     * This function refers to percentage of map owned by each player
     * @param playerIndex
     * @author ying
     * @version 2.0
     */

    public void playerMapPercentage(int playerIndex){

        double numcountry = player_country.get(playerIndex).size();
        double percentage = (numcountry / num_of_countries) * 100;

        map_percentages.set(playerIndex,percentage);
        //return percentage;
    }


    /**
     * This function checks if the game is over or not.
     * @return gameoverflag
     */
    public static boolean gameOver(){

        if(players.size() == 1){
            return true;
        }
        else{
            return false;
        }
    }


    public int findCountrytoAttack(int attacker) {

        int defender = 0;

        ArrayList<Integer> tempadjattacker = adj_map_num.get(attacker);

        for (int i = 0; i < tempadjattacker.size(); i++) {
            if (country_owner.get(tempadjattacker.get(i)) != country_owner.get(attacker)) {
                defender = tempadjattacker.get(i);
            }
        }
        return defender;

    }

    //Getter and Setter methods

    /**
     * This function assign an index to every player.
     * @param i
     */

    public void setPlayerIndex(int i){
        player_index = i;
    }

    /**
     *This is a getter method.
     * @return player_index
     */

    public int getPlayerIndex(){
        return player_index;
    }
    /**
     *This is a getter method.
     * @return player name
     */

    public String getPlayer(int i) {
        return players.get(i);
    }

    /**
     *This is a getter method.
     * @return player strategy
     */

    public int getStrategy(String playername) {

        int getindex = players.indexOf(playername);
        return playerstrategy.get(getindex);
    }


    /**
     * This function is referring to getter method for num_of_players
     * @return num_of_players returning the number of players
     * @author paras
     * @version 1.0
     */
    public int getNumofPlayers() {
        return num_of_players;
    }

    /**
     * This function refers to getter method for list of players
     * @return players returns list of players
     * @author swetha
     * @version 2.0
     */

    public ArrayList<String> getPlayers() {
        return players;
    }

    /**
     * This function refers to getter method for list of countries
     * @return country_map returns list of players
     * @author swetha
     * @version 2.0
     */

    public TreeMap<Integer, String> getCountryMap() {
        return country_map;
    }

    /**
     * This function refers to getter method for list of armies for each player.
     * @return country_army returns list of armies
     * @author swetha
     * @version 2.0
     */

    public ArrayList<Integer> getPlayerArmies(int playerIndex){
        return country_army.get(playerIndex);
    }

    /**
     * This function refers to getter method for list of countries for each player.
     * @return player_country returns list of countries
     * @author swetha
     * @version 2.0
     */

    public ArrayList<Integer> getPlayerCountries (int playerIndex){
        return player_country.get(playerIndex);
    }


    public ArrayList<Integer> getCountryArmy(int i) {
        return country_army.get(i);
    }

    public int getArmiesAtCountry(int i) {
        return country_to_army.get(i);
    }

    /**
     * This function is a getter method.
     * @return country_to_army a tree map
     * @author swetha
     * @version 2.0
     */

    public TreeMap<Integer,Integer> getCountryToArmy(){
        return country_to_army;
    }
    /**
     * This function is a getter method.
     * @return printCtoaOwnerMap a tree map
     * @author swetha
     * @version 2.0
     */

    public TreeMap<Integer,Integer> getCountryOwner(){return country_owner;}

    //Printing methods//

    /**
     * This function prints armies for each player.
     * @author swetha
     * @version 2.0
     */

    public void printPlayerArmies(){
        for(int i = 0;i < players.size();i++){
            System.out.println("");
            for (int j = 0;j < country_army.get(i).size();j++){
                System.out.print(country_army.get(i).get(j) + ", ");
            }
        }
    }



    /**
     * This function prints countrie for each player.
     * @author swetha
     * @version 2.0
     */

    public void printPlayerCountries(){
        for(int i = 0;i < players.size();i++) {
            System.out.println("");
            for (int j = 0; j < player_country.get(i).size(); j++) {
                System.out.print(player_country.get(i).get(j) + ", ");
            }
        }
    }

    public void printCtoaOwnerMap(){

        System.out.println("Printing country to army map....");
        for (int country : country_to_army.keySet()) {
            System.out.println(country  + ". " + country_map.get(country) + " =>" + country_to_army.get(country));
        }

        System.out.println("Printing country_owner map.....");
        for (int country:country_owner.keySet()) {
            System.out.println(country  + ". " + country_map.get(country) + " =>" + players.get(country_owner.get(country)));
        }
    }

    /**
     * This function prints maps
     *
     */

    public void printMaps(){
        System.out.println("Country map....");
        for(int key:country_map.keySet()){
            System.out.println(key + " : " + country_map.get(key));
        }
        System.out.println("adj map....");
        for(int key:adj_map.keySet()){
            System.out.println(key + " : " + adj_map.get(key));
        }
    }


    /**
     * This function shows sountries and its armies.
     * @param i index of the player
     */

    public void showCountryWithArmy(int i){

        System.out.println("Following are your countries with their armies : ");
        System.out.println("player index is " + i);
        System.out.println("Player name is " + players.get(i));
        System.out.println("Strategy is " + playerstrategy.get(i));


        ArrayList<Integer> temppc = player_country.get(i);

        for(int countrynum : temppc){
            System.out.println(countrynum + ". " + country_map.get(countrynum) + "(" + country_to_army.get(countrynum) + ")");
        }
    }

    public void showSummary(){

        System.out.println("Following is the detailed summary");
        System.out.println("There are players = " + players.size() + " => " + players);

        for (int i = 0; i < players.size(); i++) {
            System.out.println("This is player with index = " + i + " name = " + players.get(i) + " Strategy =>" + playerstrategy.get(i));
        }

        System.out.println();
        System.out.println("These are player countries");
        printPlayerCountries();

        System.out.println();
        System.out.println("These are player amies");
        printPlayerArmies();

    }

    public void loadPlayers(int numplayer, ArrayList<String> playersname,  ArrayList<Integer> strategies,
                            ArrayList<ArrayList<Integer>> pcountry,  ArrayList<ArrayList<Integer>> parmy){
        num_of_players = numplayer;
        players.addAll(playersname);
        playerstrategy.addAll(strategies);

        player_country.addAll(pcountry);
        country_army.addAll(parmy);

        printPlayerCountries();
        printPlayerArmies();
    }

    public void changeToEndTurn() {
        setCurrentStatus(GamePhase.ENDTURN);

    }

    public void changeToAttack() {
        setCurrentStatus(GamePhase.ATTACK);
    }

    public void changeToFortify() {
        setCurrentStatus(GamePhase.FORTIFICATION);
    }
}