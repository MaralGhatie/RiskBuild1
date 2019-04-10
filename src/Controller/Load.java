package Controller;

import Cards.Cards;
import Map.Loadexmap;
import Map.Mapper;
import Model.Aggresive;
import Model.Benevolent;
import Model.Cheater;
import Model.Human;
import Model.Player;
import Model.RandomS;
import Model.Setup;
import Model.Player.GamePhase;
import View.CardExchangeView;
import View.MainView;
import View.PhaseView;
import View.WorldDominationView;
//import javafx.beans.InvalidationListener;

import java.io.*;
import java.util.*;

/**
 * Control class is the controller of MVC model in this project.
 * @author raoying
 * @version 2.0
 */
public class Load extends Object{

    private TreeMap<Integer,String> cmap_setup = new TreeMap<>();
    private TreeMap<Integer,ArrayList> amap_setup = new TreeMap<>();
    private TreeMap<Integer,ArrayList<Integer>> amap_num_setup = new TreeMap<>();

    private static int num_players;
    private static ArrayList<String> players = new ArrayList<String>();
    private static ArrayList<Integer> playerstrategy = new ArrayList<>();

    private static MainView m_view;
    private PhaseView ph_view;
    private WorldDominationView w_view;
    private CardExchangeView card_view;

    private static ArrayList<Integer> pc_setup = new ArrayList<Integer>();
    private static ArrayList<Integer> ca_setup = new ArrayList<>();

    static Scanner read = new Scanner(System.in);

    Player p_model;
    Cards card_model;

    private Aggresive aggresive = new Aggresive();
    private Benevolent benevolent =  new Benevolent();
    private Human human = new Human();
    private RandomS rndm = new RandomS();
    private Cheater cheater = new Cheater();
    
    private int whoseturn = 0;

    public Load(){

        p_model = new Player();
        ph_view = new PhaseView();
        w_view =  new WorldDominationView();
        p_model.addObserver(ph_view);
        p_model.addObserver(w_view);

        card_model = new Cards();
        card_view = new CardExchangeView();
        card_model.addObserver(card_view);
    }


    /**
     * turn method enables all players to play the game in a round-robin fashion.
     * Each playerط·آ·ط¢آ£ط·آ¢ط¢آ¢ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬ط¹â€کط·آ¢ط¢آ¬ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬أ¢â‚¬ع†ط·آ¢ط¢آ¢s turn starts with players exchanging their cards for armies
     * and then itself divided into three phases:
     * 1) reinforcement phase,
     * 2) attack phase,
     * 3) fortifications phase.
     * Once a player is finished with these three phases, the next playerط·آ·ط¢آ£ط·آ¢ط¢آ¢ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬ط¹â€کط·آ¢ط¢آ¬ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬أ¢â‚¬ع†ط·آ¢ط¢آ¢s turn starts.
     * @see {@link CardExchangeView}
     * @see #reinforceControl(int,int)
     * @see #attackControl(int)
     * @see #fortifyControl(int)
     *
     */
    public void turn(int size) {
        for (int i = whoseturn; i < size; i++) {
        	System.out.println();
        	System.out.println("~~~~~~~~~~~~~~~~~~~ " + p_model.getPlayer(i).toUpperCase() 
        			+ " Turn ~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println();
            //System.out.println("Player name is - "+ p_model.getPlayer(i));

            try {
                worldDominationControl(i);
            } catch (Exception e) {}

            //w_view.playerWorldDomination(i);
			/*
			w_view.playerWorldDomination(p_model.getPlayer(i));
			w_view.playerMapPercentage(p_model.getPlayerMapPercentage(i));
			w_view.playerContinents();
			w_view.playerArmiesNum(p_model.getPlayerTotalArmies(i));
			w_view.playerCountriesandArmies(p_model.getPlayerCountries(i), p_model.getPlayerArmies(i));
			*/
            p_model.setPlayerIndex(i);

            card_view.cardExchangevView(i);
            ArrayList<Integer> choices = card_model.availableChoices(i);
            int choice;

            if (choices.size() < 2){
                System.out.println("Option => 5 Exit card Exchange");
                choices.add(5);
            }

            do {
                System.out.println("Enter choice - ");
                choice = read.nextInt();
            }while (!choices.contains(choice));

            card_model.exchangeCardForArmies(i,choice);
            int extraarmies = card_model.getExchangedArmies(i);


            //ph_view.reinforcementPhaseView(players.get(i));
            reinforceControl(i,extraarmies);
            //ph_view.reinforcementPhaseView2();
            //p_model.showCountryWithArmy(i);


            // ph_view.attackPhaseView(p_model.getPlayer(i));
            attackControl(i);
            //p_model.initiateAttack(i);
            // p_model.printPlayerCountries();
            //p_model.printPlayerArmies();
            // ph_view.attackPhaseView2();



            //  ph_view.fortifyPhaseView(p_model.getPlayer(i));
            fortifyControl(i);
            //System.out.println("$$$$ out side of the fortify");
            String currentplayer = p_model.getPlayer(i);
            p_model.checkforkick();
            int newsize = p_model.getPlayers().size();
            System.out.println("$$$$$ Players New Size " + newsize);
            if(newsize != size){
                for(int j = 0; j< newsize; j++){
                    if(p_model.getPlayer(j).equalsIgnoreCase(currentplayer)){
                        if(j == newsize - 1){
                            whoseturn = 0;
                        }
                        else{
                            whoseturn = j + 1;
                        }
                    }
                }
               // System.out.println("Next turn is: " + whoseturn + ": " + p_model.getPlayer(whoseturn));
               // System.out.println();
                break;
            }
            else{
                if(i+1 == size){
                    whoseturn = 0;
                }
            }

        }

    }
    /**
     * Shows world domination view of current player
     * @param i
     * @throws Exception
     * @see {@link Player#playerMapPercentage(int)}
     * @see {@link Player#continentOfPlayer(int,String)}
     * @see {@link Player#setPlayerTotalArmies(int)}
     */
    public void worldDominationControl(int i) throws Exception {

        p_model.playerMapPercentage(i);

        p_model.continentOfPlayer(i,"ASIA");
        p_model.continentOfPlayer(i,"NAMERICA");
        p_model.continentOfPlayer(i,"SAMERICA");
        p_model.continentOfPlayer(i,"EUROPE");
        p_model.continentOfPlayer(i,"AFRICA");
        p_model.continentOfPlayer(i,"AUSTRALIA");

        //w_view.continentPercentages(naowned,saowned,europeowned,africaowned,asiaowned,ausowned);

        p_model.setPlayerTotalArmies(i);

        //w_view.playerCountriesandArmies(p_model.getPlayerCountries(i), p_model.getPlayerArmies(i));

    }

    /**
     * This is the controller for reinforcement phase.</br>
     * Each time entering the reinforcement phase,
     * the player is given a number of armies that depends on the number of countries he owns
     * (# of countries owned divided by 3, rounded down)</br>
     * Also player may get extra armies by exchanging cards.</br>
     * Once the total number of reinforcements is determined for the playerط·آ·ط¢آ£ط·آ¢ط¢آ¢ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬ط¹â€کط·آ¢ط¢آ¬ط·آ£ط¢آ¢ط£آ¢أ¢â€ڑآ¬أ¢â‚¬ع†ط·آ¢ط¢آ¢s turn,
     * the player may place the armies on any country he owns, divided as he wants. </br>
     * @param index index of current player
     * @param extraarmies extra armies from exchanging cards
     *
     */
    public void reinforceControl(int index,int extraarmies){

        ph_view.reinforcementPhaseView(players.get(index));
        String name = p_model.getPlayer(index);
        int strategy = p_model.getStrategy(name);
        switch(strategy){
            case 1:
                human.sReinforce(index, extraarmies);
                p_model.changeToAttack();
                break;
            case 2:
                aggresive.sReinforce(index, extraarmies);
                p_model.changeToAttack();
                break;
            case 3:
                benevolent.sReinforce(index, extraarmies);
                p_model.changeToAttack();
                break;
            case 4:
                rndm.sReinforce(index, extraarmies);
                p_model.changeToAttack();
                break;
            case 5:
                cheater.sReinforce(index, extraarmies);
                p_model.changeToAttack();
                break;
        }
    }

    /**
     * This is the controller for attack phase, enables current player to either do attack or stop attacking.</br>
     * When attacking, it provides current player with countries that can be attacked and then initialize attack.
     * @param index index of current player.
     * @see {@link Model.Player#showAttackable(int)}
     *
     */
    public void attackControl(int index){
        String name = p_model.getPlayer(index);
        int strategy = p_model.getStrategy(name);
        switch(strategy){
            case 1:
                human.sAttack(index);
                p_model.changeToFortify();
                break;
            case 2:
                aggresive.sAttack(index);
                p_model.changeToFortify();
                break;
            case 3:
                benevolent.sAttack(index);
                p_model.changeToFortify();
                break;
            case 4:
                rndm.sAttack(index);
                p_model.changeToFortify();
                break;
            case 5:
                cheater.sAttack(index);
                p_model.changeToFortify();
                break;
        }
    }



    /**
     * This is the controller for fortification phase.
     * @param index index of current player.
     *
     */
    public void fortifyControl(int index){

        // ph_view.fortifyPhaseView(players.get(index));

        // p_model.showCountryWithArmy(index);
        String name = p_model.getPlayer(index);
        int strategy = p_model.getStrategy(name);
        switch(strategy){
            case 1:
                human.sFortify(index);
                p_model.changeToEndTurn();
                break;
            case 2:
                aggresive.sFortify(index);
                p_model.changeToEndTurn();
                break;
            case 3:
                benevolent.sFortify(index);
                p_model.changeToEndTurn();
                break;
            case 4:
                rndm.sFortify(index);
                p_model.changeToEndTurn();
                break;
            case 5:
                cheater.sFortify(index);
                p_model.changeToEndTurn();
                break;
        }

    }

    /**
     * Add player's name
     * @param name
     */
    public static void addPlayerName(String name,int strategy){
        players.add(name);
        playerstrategy.add(strategy);

    }

    /**
     * initialize cards
     * @param index
     * @see {@link Cards#initializeCards(int)}
     */
    public void initializeCards(int index){
        card_model.initializeCards(index);
    }

    /**
     * returns player ArrayList.
     * @return
     */
    public static ArrayList<String> getPlayers(){return players;}

    public void loadState(String savefilename) throws IOException {

        BufferedReader read  = new BufferedReader(new FileReader(".//saves/" + savefilename));

        // int numofplayer = Integer.parseInt(read.readLine());

        ArrayList<ArrayList<Integer>> player_country = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> country_army = new ArrayList<ArrayList<Integer>>();

        ArrayList<String> playernames = new ArrayList<>();
        ArrayList<Integer> playerstrategies = new ArrayList<>();

        ArrayList<String> filearray = new ArrayList<String>();
        String s;
        int position = 0;

        while((s = read.readLine())!= null){
            filearray.add(position,s);
            position++;
        }

        int lastactiveplayer = Integer.parseInt(filearray.get(0));
        String phase = filearray.get(1);
        int activeplayers = Integer.parseInt(filearray.get(2));

        int linereader = 3;
        for (int i = 0;i < activeplayers;i++){

            StringTokenizer st = new StringTokenizer(filearray.get(linereader),",");

            int tokens = st.countTokens();
            ArrayList<Integer> temppc = new ArrayList<>();

            for (int k = 0; k < tokens; k++) {

                int countrynumber = Integer.parseInt(st.nextToken());
                temppc.add(k,countrynumber);
            }


            player_country.add(temppc);
            linereader++;

            //for ca
            st = new StringTokenizer(filearray.get(linereader),",");

            tokens = st.countTokens();
            ArrayList<Integer> tempca = new ArrayList<>();

            for (int k = 0; k < tokens; k++) {

                int countrynumber = Integer.parseInt(st.nextToken());
                tempca.add(k,countrynumber);
            }

            country_army.add(tempca);
            linereader++;

        }
        System.out.println(filearray);
        for (int i = 0; i < activeplayers; i++) {
            String name = filearray.get(linereader);
            playernames.add(name);
            linereader++;
        }
        for (int i = 0; i < activeplayers; i++) {
            int strategy = Integer.parseInt(filearray.get(linereader));
            playerstrategies.add(strategy);
            linereader++;
        }

        System.out.println("--------------------Load Succesfull-----------------------");

        System.out.println("Printing player countries");
        for (int i = 0; i < player_country.size(); i++) {
            for (int j = 0; j < player_country.get(i).size(); j++) {
                System.out.print(player_country.get(i).get(j) + ",");
            }
            System.out.println();
        }

        System.out.println("Printing country armies");
        for (int i = 0; i < country_army.size(); i++) {
            for (int j = 0; j < country_army.get(i).size(); j++) {
                System.out.print(country_army.get(i).get(j) + ",");
            }
            System.out.println();
        }

        System.out.println("Printing player names");
        for (int i = 0; i < playernames.size(); i++) {
            System.out.print(playernames.get(i) + ",");
        }
        System.lineSeparator();
        System.out.println();
        System.out.println("Printing player strategies");
        for (int i = 0; i < playerstrategies.size(); i++) {
            System.out.print(playerstrategies.get(i) + ",");
        }
        System.lineSeparator();

        System.out.println();
        System.out.println();

        Setup s_model = new Setup();
        m_view = new MainView();
        try {
            s_model.loadMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cmap_setup = s_model.getCountryMap();
        amap_setup = s_model.getAdjMap();

        p_model.initialize(cmap_setup, amap_setup, s_model.getNumCountry());

        for(int i = 0; i<activeplayers; i++){
            String name = playernames.get(i);
            int strategy = playerstrategies.get(i);
            addPlayerName(name, strategy);
            initializeCards(i);
        }

        // p_model.printMaps();
        p_model.loadPlayers(activeplayers, playernames, playerstrategies, player_country, country_army);
        //m_view.startUpPhaseView();
        //p_model.simulateInitial();
        //p_model.giveInitialArmies();


        p_model.setupDomination();

        //uncomment this block if u wanna jump to attack part....with 2 players playing
/*
        addPlayerName("paras");
        addPlayerName("varun");
        initializeCards(0);
        initializeCards(1);
        p_model.setPlayers(2,players);
        //p_model.print_ctoamap();
        p_model.simulateInitial();
        p_model.printPlayerArmies();
        p_model.printPlayerCountries();
*/
        p_model.initializectow();
        while(!p_model.gameOver()){
        	completeTurn(lastactiveplayer, phase);
            int size = p_model.getPlayers().size();
            System.out.println(p_model.getPlayers());

            turn(size);
        }
        String name = p_model.getPlayer(0);
        m_view.gameOverView(name);

    }


    private void completeTurn(int lastactiveplayer, String phase) {
        
    	System.out.println();
    	System.out.println("~~~~~~~~~~~~~~~~~~~ " + p_model.getPlayer(lastactiveplayer).toUpperCase() 
    			+ " Turn Continues ~~~~~~~~~~~~~~~~~~~~~~~");
    	System.out.println();
    	
        p_model.setPlayerIndex(lastactiveplayer);
        
        if(phase.equalsIgnoreCase("REINFORCEMENT")){
            p_model.changeToAttack();
            attackControl(lastactiveplayer);
        }
        else if(phase.equalsIgnoreCase("ATTACK")){
            p_model.changeToFortify();
            fortifyControl(lastactiveplayer);
        }
        else if(phase.equalsIgnoreCase("FORTIFICATION")){
            p_model.changeToEndTurn();
        }

        for (int i = lastactiveplayer + 1; i < players.size(); i++) {
            
            
            System.out.println();
        	System.out.println("~~~~~~~~~~~~~~~~~~~ " + p_model.getPlayer(i).toUpperCase() 
        			+ " Turn ~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println();
        	
            p_model.setPlayerIndex(i);


            try {
                worldDominationControl(i);
            } catch (Exception e) {}

            //w_view.playerWorldDomination(i);
			/*
			w_view.playerWorldDomination(p_model.getPlayer(i));
			w_view.playerMapPercentage(p_model.getPlayerMapPercentage(i));
			w_view.playerContinents();
			w_view.playerArmiesNum(p_model.getPlayerTotalArmies(i));
			w_view.playerCountriesandArmies(p_model.getPlayerCountries(i), p_model.getPlayerArmies(i));
			*/


            card_view.cardExchangevView(i);
            ArrayList<Integer> choices = card_model.availableChoices(i);
            int choice;

            if (choices.size() < 2){
                System.out.println("Option => 5 Exit card Exchange");
                choices.add(5);
            }

            do {
                System.out.println("Enter choice - ");
                choice = read.nextInt();
            }while (!choices.contains(choice));

            card_model.exchangeCardForArmies(i,choice);
            int extraarmies = card_model.getExchangedArmies(i);


            //ph_view.reinforcementPhaseView(players.get(i));
            reinforceControl(i,extraarmies);
            //ph_view.reinforcementPhaseView2();
            //p_model.showCountryWithArmy(i);


            // ph_view.attackPhaseView(p_model.getPlayer(i));
            attackControl(i);
            //p_model.initiateAttack(i);
            // p_model.printPlayerCountries();
            //p_model.printPlayerArmies();
            // ph_view.attackPhaseView2();


            //  ph_view.fortifyPhaseView(p_model.getPlayer(i));
            fortifyControl(i);

        }

    }

}
