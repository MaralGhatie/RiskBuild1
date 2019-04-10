package Controller;

import Cards.Cards;
import Model.*;
import View.CardExchangeView;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class playonce {

    int numplayers;
    int nummaps;
    int numgames;
    int numturns;

    private ArrayList players = new ArrayList();
    private ArrayList playerstrategy = new ArrayList();

    Player p_model = new Player();
    Aggresive aggresive = new Aggresive();
    Benevolent benevolent = new Benevolent();
    Human human = new Human();
    RandomS rndm = new RandomS();
    Cheater cheater = new Cheater();

    Cards card_model = new Cards();
    CardExchangeView card_view = new CardExchangeView();

    ArrayList<TreeMap<Integer, String>> cmaps = new ArrayList<>();
    ArrayList<TreeMap<Integer, ArrayList>> amaps = new ArrayList<>();
    ArrayList<Integer> numofcountries = new ArrayList<>();

    private int whoseturn = 0;

   public void setmaps(int doturns,TreeMap cmap,TreeMap amap,int numofcountries) {

       //p_model.initialize(cmap, amap, numofcountries, true);

       numturns = doturns;

   }

   public void establishplayers(int numplayers,ArrayList players,ArrayList playerstrategy){

       System.out.println(players);
       System.out.println(playerstrategy);

       p_model.setPlayers(numplayers, players, playerstrategy,2);

       p_model.printPlayerCountries();
       p_model.printPlayerArmies();

       intializecards();

       for (int k = 0; k < numturns; k++) {

           System.err.println("It is turn " + (k+1));

           int size = p_model.getPlayers().size();
           System.out.println("++++++++++++++++++++ now changing turn +++++++++++++++++++++++++");
           System.out.println(p_model.getPlayers());

           Scanner read = new Scanner(System.in);
           read.nextInt();

           turn(size);
       }
   }

   public void intializecards(){

       for (int i = 0; i < numplayers; i++) {
           card_model.initializeCards(i);
       }
   }


    public void turn(int size) {

        for (int i = whoseturn; i < size; i++) {

            System.out.println(p_model.getPlayers());
            //System.out.println("Player name is - " + p_model.getPlayer(i));

            //System.out.println("size = " + size);

            p_model.setPlayerIndex(i);


            /*
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
            */

            int extraarmies = 0;
            reinforceControl(i, extraarmies);

            attackControl(i);

            fortifyControl(i);

            //p_model.checkforkick();

            String currentplayer = p_model.getPlayer(i);
            p_model.checkforkick();
            int newsize = p_model.getPlayers().size();
            System.out.println("%%%% New Size " + newsize);
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
                System.out.println("Next turn is: " + whoseturn + ": " + p_model.getPlayer(whoseturn));
                System.out.println();
                break;
            }
            else{
                if(i+1 == size){
                    whoseturn = 0;
                }
            }

        }

    }

    public void reinforceControl(int index, int extraarmies) {

        //int strategy = p_model.getStrategy(index);

        System.out.println("Index = " + index);
        String playername = p_model.getPlayer(index);
        System.out.println("name  = " + playername);


        int strategy = p_model.getStrategy(playername);

        switch (strategy) {
            case 1:
                human.sReinforce(index, extraarmies);
                p_model.changeToAttack();
                break;
            case 2:
                aggresive.sReinforce(index,extraarmies);
                p_model.changeToAttack();
                break;
            case 3:
                benevolent.sReinforce(index,extraarmies);
                p_model.changeToAttack();
                break;
            case 4:
                rndm.sReinforce(index,extraarmies);
                p_model.changeToAttack();
                break;
            case 5:
                cheater.sReinforce(index,extraarmies);
                p_model.changeToAttack();
                break;
        }
    }

    public void attackControl(int index){

        //int strategy = p_model.getStrategy(index);

        System.out.println("Index = " + index);
        String playername = p_model.getPlayer(index);
        System.out.println("name  = " + playername);

        int strategy = p_model.getStrategy(playername);
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

    public void fortifyControl(int index){

        // ph_view.fortifyPhaseView(players.get(index));

        // p_model.showCountryWithArmy(index);

        System.out.println("Index = " + index);
        String playername = p_model.getPlayer(index);
        System.out.println("name  = " + playername);


        int strategy = p_model.getStrategy(playername);
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
}
