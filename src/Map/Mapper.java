
package Map;
/**
 * This class makes a new map from scratch with client inputs
 * and check if the map is correct
 * @author Maral Ghatie
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class is used to make maps from scratch.
 * @author Maral Ghatie
 * @version 2.0
 *
 */
public class Mapper
{
    private TreeMap<Integer, ArrayList<String>> new_map = new TreeMap<>();
    private TreeMap<Integer,ArrayList<String>> adjacent_map = new TreeMap<>();

    /**
     * This method provides six continents with their number rage
     * and gets countries name and number and their neighbours name from the user, afterwards,
     * set the inputs to a tree map
     * @author Maral Ghatie
     * @return the final correct map in order to play
     */
    public void makeNew() {


        Scanner sc = new Scanner(System.in);
        boolean flagInput = true;

        while(flagInput){

            int continentchoice = 0;
            int numberofcountries = 0;
            int countrynum = 0;
            boolean flagcontinent = true;

            while(flagcontinent){
                System.out.println("Choose the continent you want to add counties for:");
                System.out.println("1. Asia \n2. N-America \n3. S-America \n4.Europe \n5.Africa \n6.Australia \n7.DONE");
                continentchoice = sc.nextInt();

                if(continentchoice <8 && continentchoice >0){
                    flagcontinent = false;
                }
                else{
                    System.out.println("The continent number is not valid!");
                }
            }

            if(continentchoice == 1){
                boolean flag1_1 = true;
                while(flag1_1){
                    System.out.println("How many countries do you wanna add to this continent?");
                    numberofcountries = sc.nextInt();
                    if(numberofcountries >= 1 && numberofcountries <=20){
                        flag1_1 = false;
                        for(int i = 1; i<=numberofcountries; i++){
                            boolean flag1_2 = true;
                            while(flag1_2){
                                System.out.println(i + ". Enter the country number");
                                System.out.println("NOTICE: Asia range of country number is between 1-20");
                                countrynum = sc.nextInt();
                                if(countrynum >=1 && countrynum <=20){
                                    flag1_2 = false;
                                    System.out.println("Enter the country's name");
                                    String countryname = sc.next();

                                    boolean flagneighbour = true;

                                    while(flagneighbour){
                                        System.out.println("How many neighbours " + countryname + " has?");
                                        int neighboursnum = sc.nextInt();
                                        if(neighboursnum <= 19 && neighboursnum >=1){
                                            flagneighbour = false;
                                            ArrayList<String> neighbours = new ArrayList<>();
                                            neighbours.add(countryname);
                                            for(int j = 1; j<= neighboursnum; j++){
                                                System.out.println(j + ". Enter the neighbour name");
                                                String neighbourname = sc.next();
                                                neighbours.add(neighbourname);
                                            }
                                            new_map.put(countrynum, neighbours);
                                        }
                                        else{
                                            System.out.println("1 neighbour minimum/19 neighbors maximum");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("The country number should be between 1-20");
                                }
                            }


                        }
                    }
                    else{
                        System.out.println("This continent can have a minimum of 1 and a maximum of 20 countries");
                    }
                }
            }

            else if(continentchoice == 2){
                boolean flag2_1 = true;
                while(flag2_1){
                    System.out.println("How many countries do you wanna add to this continent?");
                    numberofcountries = sc.nextInt();
                    if(numberofcountries >= 1 && numberofcountries <=10){
                        flag2_1 = false;
                        for(int i = 1; i<=numberofcountries; i++){
                            boolean flag2_2 = true;
                            while(flag2_2){
                                System.out.println(i + ". Enter the country number");
                                System.out.println("NOTICE: N-America range of country number is between 21-30");
                                countrynum = sc.nextInt();
                                if(countrynum >=21 && countrynum <=30){
                                    flag2_2 = false;
                                    System.out.println("Enter the country's name");
                                    String countryname = sc.next();

                                    boolean flagneighbour = true;

                                    while(flagneighbour){
                                        System.out.println("How many neighbours " + countryname + " has?");
                                        int neighboursNum = sc.nextInt();
                                        if(neighboursNum <= 9 && neighboursNum >=1){
                                            flagneighbour = false;
                                            ArrayList<String> neighbours = new ArrayList<>();
                                            neighbours.add(countryname);
                                            for(int j = 1; j<= neighboursNum; j++){
                                                System.out.println(j + ". Enter the neighbour name");
                                                String neighbourname = sc.next();
                                                neighbours.add(neighbourname);
                                            }
                                            new_map.put(countrynum, neighbours);
                                        }
                                        else{
                                            System.out.println("1 neighbour minimum/9 neighbors maximum");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("The country number should be between 21-30");
                                }
                            }


                        }
                    }
                    else{
                        System.out.println("This continent can have a minimum of 1 and a maximum of 10 countries");
                    }
                }
            }

            else if(continentchoice == 3){
                boolean flag3_1 = true;
                while(flag3_1){
                    System.out.println("How many countries do you wanna add to this continent?");
                    numberofcountries = sc.nextInt();
                    if(numberofcountries >= 1 && numberofcountries <=10){
                        flag3_1 = false;
                        for(int i = 1; i<=numberofcountries; i++){
                            boolean flag3_2 = true;
                            while(flag3_2){
                                System.out.println(i + ". Enter the country number");
                                System.out.println("NOTICE: S-America range of country number is between 31-40");
                                countrynum = sc.nextInt();
                                if(countrynum >=31 && countrynum <=40){
                                    flag3_2 = false;
                                    System.out.println("Enter the country's name");
                                    String countryname = sc.next();

                                    boolean flagneighbour = true;

                                    while(flagneighbour){
                                        System.out.println("How many neighbours " + countryname + " has?");
                                        int neighboursnum = sc.nextInt();
                                        if(neighboursnum <= 9 && neighboursnum >=1){
                                            flagneighbour = false;
                                            ArrayList<String> neighbours = new ArrayList<>();
                                            neighbours.add(countryname);
                                            for(int j = 1; j<= neighboursnum; j++){
                                                System.out.println(j + ". Enter the neighbour name");
                                                String neighbourname = sc.next();
                                                neighbours.add(neighbourname);
                                            }
                                            new_map.put(countrynum, neighbours);
                                        }
                                        else{
                                            System.out.println("1 neighbour minimum/9 neighbors maximum");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("The country number should be between 31-40");
                                }
                            }


                        }
                    }
                    else{
                        System.out.println("This continent can have a minimum of 1 and a maximum of 10 countries");
                    }
                }
            }

            else if(continentchoice == 4){
                boolean flag4_1 = true;
                while(flag4_1){
                    System.out.println("How many countries do you wanna add to this continent?");
                    numberofcountries = sc.nextInt();
                    if(numberofcountries >= 1 && numberofcountries <=20){
                        flag4_1 = false;
                        for(int i = 1; i<=numberofcountries; i++){
                            boolean flag4_2 = true;
                            while(flag4_2){
                                System.out.println(i + ". Enter the country number");
                                System.out.println("NOTICE: Europe range of country number is between 41-60");
                                countrynum = sc.nextInt();
                                if(countrynum >=41 && countrynum <=60){
                                    flag4_2 = false;
                                    System.out.println("Enter the country's name");
                                    String countryname = sc.next();

                                    boolean flagneighbour = true;

                                    while(flagneighbour){
                                        System.out.println("How many neighbours " + countryname + " has?");
                                        int neighboursnum = sc.nextInt();
                                        if(neighboursnum <= 19 && neighboursnum >=1){
                                            flagneighbour = false;
                                            ArrayList<String> neighbours = new ArrayList<>();
                                            neighbours.add(countryname);
                                            for(int j = 1; j<= neighboursnum; j++){
                                                System.out.println(j + ". Enter the neighbour name");
                                                String neighbourname = sc.next();
                                                neighbours.add(neighbourname);
                                            }
                                            new_map.put(countrynum, neighbours);
                                        }
                                        else{
                                            System.out.println("1 neighbour minimum/19 neighbors maximum");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("The country number should be between 41-60");
                                }
                            }


                        }
                    }
                    else{
                        System.out.println("This continent can have a minimum of 1 and a maximum of 20 countries");
                    }
                }
            }

            else if(continentchoice == 5){
                boolean flag5_1 = true;
                while(flag5_1){
                    System.out.println("How many countries do you wanna add to this continent?");
                    numberofcountries = sc.nextInt();
                    if(numberofcountries >= 1 && numberofcountries <=20){
                        flag5_1 = false;
                        for(int i = 1; i<=numberofcountries; i++){
                            boolean flag5_2 = true;
                            while(flag5_2){
                                System.out.println(i + ". Enter the country number");
                                System.out.println("NOTICE: Africa range of country number is between 61-80");
                                countrynum = sc.nextInt();
                                if(countrynum >=61 && countrynum <=80){
                                    flag5_2 = false;
                                    System.out.println("Enter the country's name");
                                    String countryname = sc.next();

                                    boolean flagneighbour = true;

                                    while(flagneighbour){
                                        System.out.println("How many neighbours " + countryname + " has?");
                                        int neighboursnum = sc.nextInt();
                                        if(neighboursnum <= 19 && neighboursnum >=1){
                                            flagneighbour = false;
                                            ArrayList<String> neighbours = new ArrayList<>();
                                            neighbours.add(countryname);
                                            for(int j = 1; j<= neighboursnum; j++){
                                                System.out.println(j + ". Enter the neighbour name");
                                                String neighbourname = sc.next();
                                                neighbours.add(neighbourname);
                                            }
                                            new_map.put(countrynum, neighbours);
                                        }
                                        else{
                                            System.out.println("1 neighbour minimum/19 neighbors maximum");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("The country number should be between 61-80");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("This continent can have a minimum of 1 and a maximum of 20 countries");
                    }
                }
            }

            else if(continentchoice == 6){
                boolean flag6_1 = true;
                while(flag6_1){
                    System.out.println("How many countries do you wanna add to this continent?");
                    numberofcountries = sc.nextInt();
                    if(numberofcountries >= 1 && numberofcountries <=10){
                        flag6_1 = false;
                        for(int i = 1; i<=numberofcountries; i++){
                            boolean flag6_2 = true;
                            while(flag6_2){
                                System.out.println(i + ". Enter the country number");
                                System.out.println("NOTICE: Australia range of country number is between 61-80");
                                countrynum = sc.nextInt();
                                if(countrynum >=81 && countrynum <=90){
                                    flag6_2 = false;
                                    System.out.println("Enter the country's name");
                                    String countryname = sc.next();

                                    boolean flagneighbour = true;

                                    while(flagneighbour){
                                        System.out.println("How many neighbours " + countryname + " has?");
                                        int neighboursnum = sc.nextInt();
                                        if(neighboursnum <= 10 && neighboursnum >=1){
                                            flagneighbour = false;
                                            ArrayList<String> neighbours = new ArrayList<>();
                                            neighbours.add(countryname);
                                            for(int j = 1; j<= neighboursnum; j++){
                                                System.out.println(j + ". Enter the neighbour name");
                                                String neighbourName = sc.next();
                                                neighbours.add(neighbourName);
                                            }
                                            new_map.put(countrynum, neighbours);
                                        }
                                        else{
                                            System.out.println("1 neighbour minimum/9 neighbors maximum");
                                        }
                                    }
                                }
                                else{
                                    System.out.println("The country number should be between 81-90");
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("This continent can have a minimum of 1 and a maximum of 10 countries");
                    }
                }
            }

            else{
                System.out.println("Done adding a new map, lets check its correctivity");

                System.out.println(new_map);
                //System.out.println("SIZE: " + new_map_num.size());
                CheckMap check = new CheckMap();
                boolean result = check.checkCorrectivity(new_map);
                if(result){
                    System.out.println("**************************");
                    System.out.println("Congrats!! your map is totally correct! Shall we play now? :D");
                    System.out.println("**************************");
                    flagInput = false;
                }
                else{
                    new_map.clear();
                }
            }
        }
    }


    /**
     * This method gets the new map only
     * @author Maral Ghatie
     * @return a tree map of new map
     */

    public TreeMap<Integer, ArrayList<String>> getNewMap(){
        Set<Integer> mapkey = new_map.keySet();

        for(Integer key: mapkey){
            ArrayList<String> temp = new_map.get(key);
            ArrayList<String> adjtemp = new ArrayList<String>();

            for(int i = 1; i< temp.size(); i++){
                adjtemp.add(temp.get(i));
            }
            adjacent_map.put(key, adjtemp);
        }

        return adjacent_map;
    }

}
