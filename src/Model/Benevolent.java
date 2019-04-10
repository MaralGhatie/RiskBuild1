package Model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import View.PhaseView;

public class Benevolent extends Player implements Strategy {


    @Override
    public void sReinforce(int playerIndex, int extraarmies) {

        showSummary();

        ArrayList<Integer> playercountries = new ArrayList<Integer>();
        ArrayList<Integer> playerarmies = new ArrayList<Integer>();

        playercountries = player_country.get(playerIndex);
        playerarmies = country_army.get(playerIndex);

        showCountryWithArmy(playerIndex);

        int numarmy = getPlayerCountries(playerIndex).size() / 3;

        if (numarmy < 3)
            numarmy = 3;

        // numarmy = numarmy + extraarmies; ignore card exchange for now.
        boolean flag = false;

        System.out.println("Number of armies you have is =>" + numarmy);

        //get the weakest country #
        int weakest = playerarmies.get(0);
        int weakestindex = 0;

        for (int i = 1; i < playerarmies.size(); i++) {
            if (playerarmies.get(i) < weakest) {
                weakest = playerarmies.get(i);
                weakestindex = i;
            }
            //++if they were equal
        }
        System.out.println("This is your weakest country: " + playercountries.get(weakestindex));

        // put all the armies to strongest country
        playerarmies.set(weakestindex,playerarmies.get(weakestindex) + numarmy);
        country_army.set(playerIndex,playerarmies);

        refreshList(2);

        showSummary();

        wannaSave(playerIndex, "REINFORCEMENT");
        // setCurrentStatus(GamePhase.ATTACK);

    }

    public void sAttack(int playerIndex){
        System.out.println("------ This player does not attack :D -------");
        wannaSave(playerIndex, "ATTACK");
        //setCurrentStatus(GamePhase.FORTIFICATION);
    }

    @Override
    public void sFortify(int playerIndex) {
        ArrayList<Integer> playercountries = new ArrayList<Integer>();
        ArrayList<Integer> playerarmies = new ArrayList<Integer>();

        playercountries.addAll(player_country.get(playerIndex));
        playerarmies.addAll(country_army.get(playerIndex));

        TreeMap<Integer, ArrayList<Integer>> armysum = new TreeMap<>();

        System.out.println("This is the countries that you have " + playercountries);
        System.out.println("This is the armies you have assigned in your countries " + playerarmies);

        int origincountry = -1;
        int destcountry = -1;
        int originarmycountry = -1;

        int weakest = playerarmies.get(0);
        int weakestIndex = 0;
        for(int i = 1; i<playerarmies.size(); i++){
            if(playerarmies.get(i) < weakest){
                weakest = playerarmies.get(i);
                weakestIndex = i;
            }
            //++if they were equal
        }
        System.out.println("This is your weakest country: " + playercountries.get(weakestIndex));
        origincountry = playercountries.get(weakestIndex);
        originarmycountry = playerarmies.get(weakestIndex);

        setAdjNumber();
        //System.out.println("This is ADJ with num " + adj_map_num);
        visited = new boolean[adj_map_num.lastKey() + 1];
        visited[origincountry] = true;
        path.clear();
        flagFindPath = false;
        path.add(origincountry);
        for(int i=0; i<playercountries.size(); i++){
            if(playercountries.get(i) != origincountry && playerarmies.get(i) != 0){
                destcountry = playercountries.get(i);
                findPath(playercountries, origincountry, destcountry);
                if(flagFindPath){
                    System.out.println("$$$$$" );
                    //pathflag = true;
                    int sum = originarmycountry + playerarmies.get(i);
                    ArrayList<Integer> points = new ArrayList<Integer>();
                    points.add(origincountry);
                    points.add(destcountry);
                    armysum.put(sum, points);
                }
                path.clear();
                flagFindPath = false;
                visited = new boolean[adj_map_num.lastKey() + 1];
                visited[origincountry] = true;
                path.add(origincountry);
            }
        }

        if(armysum.isEmpty()){
        	System.out.println();
            System.out.println("---- There is no valid fortification ----");
            System.out.println();
        }
        else{
        	System.out.println();
            System.out.println("---- Fortification Success ----");
            System.out.println();
            Set<Integer> keys = armysum.keySet();
            int min = armysum.firstKey();
            for(Integer key: keys){
                if(key < min){
                    min = key;
                }
            }
            for(int i = 0; i<playercountries.size(); i++){
                if(playercountries.get(i) == armysum.get(min).get(0)){
                    playerarmies.set(i, min);
                }
                if(playercountries.get(i) == armysum.get(min).get(1)){
                    playerarmies.set(i, 0);
                }
            }
            country_army.set(playerIndex, playerarmies);
            refreshList(2);
        }

        showSummary();

        wannaSave(playerIndex, "FORTIFICATION");
        // setCurrentStatus(GamePhase.ENDTURN);
    }
}
