package Model;

import View.PhaseView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;



public class Cheater extends Player implements Strategy{

    @Override
    public void sReinforce(int playerIndex, int extraarmies) {

        showSummary();

        ArrayList<Integer> playerarmies = country_army.get(playerIndex);

        showCountryWithArmy(playerIndex);

        //  doubles the number of armies on all its countries
        for (int i = 1; i < playerarmies.size(); i++) {
            playerarmies.set(i,playerarmies.get(i)*2);
        }

        country_army.set(playerIndex,playerarmies);

        refreshList(2);

        wannaSave(playerIndex, "REINFORCEMENT");
        // setCurrentStatus(GamePhase.ATTACK);
    }

    public void sAttack(int playerIndex){
        refreshList(3);
        Random rand = new Random();

        ArrayList<Integer> temppc = new ArrayList<>();
        ArrayList<Integer> tempca = new ArrayList<>();
        ArrayList<Integer> adjforcountry = new ArrayList<>();
        ArrayList<Integer> listowners = new ArrayList<>();

        int cheaterindex = playerIndex;

        temppc = player_country.get(playerIndex);
        tempca = country_army.get(playerIndex);

        int size = temppc.size();

        for (int i = 0; i < size;i++) {

            int attackercountry = temppc.get(i);

            adjforcountry = adj_map_num.get(attackercountry);

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("Attacker country = " + attackercountry);
            System.out.println("adj for attacker = "+ adjforcountry);

            //addd all countries in adjforc to this player
            for (int country : adjforcountry) {

                if (!temppc.contains(country)) {
                    int originalowner = country_owner.get(country);
                    if (originalowner == cheaterindex) {
                        System.out.println(" its his own country");
                        continue;
                    }


                    System.out.println("pc changed from ...");
                    System.out.println(temppc);
                    System.out.println(tempca);

                    temppc.add(country);
                    tempca.add(0);

                    System.out.println("to...");
                    System.out.println(temppc);
                    System.out.println(tempca);

                    ArrayList pcfororiginal = player_country.get(originalowner);
                    ArrayList cafororiginal = country_army.get(originalowner);

                    System.out.println("original pc = " + pcfororiginal);
                    System.out.println("original ca = " + cafororiginal);

                    int indexinoriginal = pcfororiginal.indexOf(country);
                    System.out.println("chosen index is = " + indexinoriginal);
                    System.out.println("Country at original  = " + pcfororiginal.get(indexinoriginal));
                    pcfororiginal.remove(indexinoriginal);
                    cafororiginal.remove(indexinoriginal);

                    System.out.println("afterwards original pc = " + pcfororiginal);
                    System.out.println("afterwards orgial ca = " + cafororiginal);

                    player_country.set(originalowner, pcfororiginal);
                    country_army.set(originalowner, cafororiginal);

                }
            }

        }

        player_country.set(cheaterindex,temppc);
        country_army.set(cheaterindex,tempca);

//        checkforkick();

        System.out.println("Summary before refreshing");
        showSummary();

        refreshList(3);
        refreshList(1);

        System.out.println("Summary after refreshing");
        showSummary();

        wannaSave(playerIndex, "ATTACK");
        // setCurrentStatus(GamePhase.FORTIFICATION);
    }

    @Override
    public void sFortify(int playerIndex) {
        ArrayList<Integer> playercountries = new ArrayList<Integer>();
        ArrayList<Integer> playerarmies = new ArrayList<Integer>();

        playercountries.addAll(player_country.get(playerIndex));
        playerarmies.addAll(country_army.get(playerIndex));

        System.out.println("This is the countries that you have " + playercountries);
        System.out.println("This is the armies you have assigned in your countries " + playerarmies);


        Set<Integer> adjacentmapkeys = adj_map_num.keySet();
        for(Integer adjacentkey : adjacentmapkeys){
            if(playercountries.contains(adjacentkey)){
                ArrayList<Integer> neighbours = adj_map_num.get(adjacentkey);
                boolean flag = false;
                for(int i = 0; i< neighbours.size(); i++){
                    if(!playercountries.contains(neighbours.get(i))){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    for(int j =0; j<playercountries.size(); j++){
                        if(playercountries.get(j) == adjacentkey){
                            int army = playerarmies.get(j);
                            playerarmies.set(j, army*2);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println();
        System.out.println("---- Fortification Success ----");
        System.out.println();
        country_army.set(playerIndex, playerarmies);
        refreshList(2);
        wannaSave(playerIndex, "FORTIFICATION");
        //  setCurrentStatus(GamePhase.ENDTURN);
    }
}
