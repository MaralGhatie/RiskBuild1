package Controller;

import Cards.Cards;
import Model.*;
import View.CardExchangeView;
import javafx.scene.transform.Scale;
import sun.reflect.generics.tree.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class Tournament {

    Scanner read = new Scanner(System.in);

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

    ArrayList<playonce> objects = new ArrayList<>();
    int objectcounter = 0;

    public void startTournament() {

        System.out.println("Enter the number of players (2 to 4) => ");
        numplayers = read.nextInt();
        addplayers(numplayers);


        System.out.println("Enter the number of maps (1 to 5) => ");
        nummaps = read.nextInt();
        loadMaps(nummaps);

        System.out.println("Enter the number of games to play (1 to 5) => ");
        numgames = read.nextInt();


        System.out.println("Enter the number of turns (1 to 30) => ");
        numturns = read.nextInt();

        setplay(numgames);
    }


    public void setall(){
        numplayers = 4;
        players.add("maral");players.add("paras");players.add("Swetha");players.add("ying");
        playerstrategy.add(2);playerstrategy.add(3);playerstrategy.add(5);playerstrategy.add(4);
        card_model.initializeCards(0);card_model.initializeCards(1);card_model.initializeCards(2);card_model.initializeCards(3);

        nummaps = 3;
        loadMaps(3);

        numgames = 3;

        numturns = 4;

        int numobjects = nummaps*numgames;

        for (int i = 0; i < numobjects; i++) {
            playonce obj = new playonce();
            objects.add(obj);
        }

        setplay(numgames);

    }

    public void setplay(int numgames) {

        for (int i = 0 ; i < numgames;i++) {

            System.err.println("It is game => " + numgames);

            for (int j = 0; j < cmaps.size(); i++) {

                System.out.println(cmaps.size());

                playonce obj = objects.get(objectcounter);

                objectcounter++;

                System.err.println("It is map " + (j+1));

                obj.setmaps(numturns,cmaps.get(j),amaps.get(j),numofcountries.get(j));
                obj.establishplayers(numplayers,players,playerstrategy);

            }
            numgames--;
        }
    }

    public void addplayers(int numplayers) {

        Scanner read = new Scanner(System.in);

        for (int i = 0; i < numplayers; i++) {
            System.out.println("Enter player name -> ");
            String name = read.nextLine();
            players.add(name);
        }
        System.out.println(players);

        for (int i = 0; i < numplayers; i++) {

            System.out.println("Enter player strategy -> ");
            System.out.println("Options are -->\n1.Human\n2.Aggressive\n3.Benevolent\n4.Random\n5.Cheater");
            playerstrategy.add(read.nextInt());
        }
        System.out.println(playerstrategy);

        for (int i = 0; i < numplayers; i++) {
            card_model.initializeCards(i);
            card_model.getCardArtilery();
            card_model.getCardCavalry();
            card_model.getCardArtilery();
        }

    }


    public void loadMaps(int nummaps){

        Setup smodel = new Setup();

        switch (nummaps) {
            case 1:
                try {
                    smodel.loadMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());
                break;

            case 2:

                try {
                    smodel.loadMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                    smodel.tLoadMap("conquest1.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                break;

            case 3:

                try {
                    smodel.loadMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                   smodel.tLoadMap("conquest1.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());


                try {
                   smodel.tLoadMap("conquest2.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                break;

            case 4:

                try {
                    smodel.loadMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                    smodel.tLoadMap("conquest1.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());


                try {
                    smodel.tLoadMap("conquest2.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                   // smodel.loadMap("conquest3.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                break;


            case 5:

                try {
                    smodel.loadMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                    //smodel.loadMap("conquest1.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());


                try {
                   // smodel.loadMap("conquest2.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                    smodel.tLoadMap("conquest3.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());

                try {
                    smodel.tLoadMap("conquest4.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cmaps.add(smodel.getCountryMap());
                amaps.add(smodel.getAdjMap());
                numofcountries.add(smodel.getNumCountry());


                break;


        }
    }

}








