package Map;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class is for checking the new map that is going to be created.
 * @author Niki
 * @version 2.0
 */
public class CheckMap {

    private TreeMap<Integer, ArrayList<String>> new_map = new TreeMap<>();
    private TreeMap<Integer, ArrayList<Integer>> new_map_num = new TreeMap<>();
    private TreeMap<Integer,String> country_map = new TreeMap<>();


    boolean explored [];
    boolean visited [];


    /**
     * This method is checking the map validity
     * @author Maral Ghatie
     * @return true if the map is correct and false other wise
     */

    public boolean checkCorrectivity(TreeMap<Integer, ArrayList<String>> map){

        new_map.putAll(map);
        setCountryMap();
        setAdjNum();
        explored = new boolean [new_map_num.lastKey() + 1];
        setExplored();

        System.out.println("This is adjmap or new map " + new_map);
        System.out.println("This is country map "  + country_map);
        System.out.println("This is adj num map " + new_map_num);
        boolean flagcorrect = false;


        boolean flagconnect = isConnected();

        if(flagconnect)
            System.out.println("CONNECTED");
        else
            System.out.println("NOT CONNECTED");

        boolean flagbidirectional = isBidirectional();

        if(flagbidirectional)
            System.out.println("Bidirectional OK");
        else
            System.out.println("Bidirectional FAILED");

        boolean flagsingelcontinent = isSingleContinent();

        if(flagsingelcontinent)
            System.out.println("No duplications ^_^");
        else
            System.out.println("One country has been duplicated in 2 continents");


        if(flagconnect && flagbidirectional && flagsingelcontinent){
            flagcorrect = true;
        }

        return flagcorrect;
    }

    /**
     * This function is to check if the countries have bidirectional adjacencies
     * @return flag which is true or false .false means the map doesn't has bidirectional adjacencies
     * @author paras
     * @version 1.0
     */
    public boolean isBidirectional(){

        boolean flag = true;

        for(int key : new_map_num.keySet()){

            int firstcountry = key;
            int secondcountry;

            ArrayList<Integer> adjacentarray = new_map_num.get(key);

            for(int i = 0;i<adjacentarray.size();i++){

                secondcountry = adjacentarray.get(i);
                ArrayList<Integer> secondarr = new_map_num.get(secondcountry);
                if (secondarr.contains(firstcountry)){
                    continue;
                }
                else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * This function checks if one country is in one continent only
     * @return flag which is boolean .false means one country is present in two continents
     * @author paras
     * @version 1.0
     */

    public boolean isSingleContinent(){

        boolean flag = true;

        ArrayList<String> temp = new ArrayList<String>();
        temp.add("nothing");

        for (int key : country_map.keySet()){

            String cname = country_map.get(key);
            if (temp.contains(cname)){
                flag = false;
            }
            else{
                temp.add(cname);
            }
        }

        return flag;
    }

    /**
     * This method checks if thep is connected or not.
     * @return boolean it's true if it was connected
     * @aurhor Niki
     */
    public boolean isConnected(){
        int initialcountry = new_map_num.firstKey();

        explored[initialcountry] = true;

        Set<Integer> mapkey = new_map_num.keySet();
        for(Integer key: mapkey){
            if(explored[key] ==  false){
                ArrayList<Integer> path = new ArrayList<Integer>();
                path.add(initialcountry);
                visited = new boolean [new_map_num.lastKey() + 1];
                visited[initialcountry] = true;
                findPath(initialcountry,key,path);
            }
        }

        boolean flagconnect = true;
        for(int i = 0; i<explored.length; i++){
            if(!explored[i]){
                flagconnect = false;
                break;
            }
        }

        return flagconnect;
    }

    /**
     * This method checks if the map is a connected map by finding a path between two giving countries
     * @author Maral Ghatie
     * @param curCou the initial country
     * @param desCou the destination country
     * @param path a valid path between initial country adn destination country
     */

    private void findPath(int curCou, int desCou, ArrayList<Integer> path) {
        ArrayList<Integer> tempadjacent = new ArrayList<Integer>();
        tempadjacent.addAll(new_map_num.get(curCou));

        for (int i = 0; i < tempadjacent.size(); i++) {
            if(visited[tempadjacent.get(i)] == false){
                path.add(tempadjacent.get(i));
                explored[tempadjacent.get(i)] = true;
                visited[tempadjacent.get(i)] = true;


                if(tempadjacent.get(i) == desCou){
                    break;
                }
                else{
                    findPath(tempadjacent.get(i), desCou, path);
                    if(path.size() != 0)
                        path.remove(path.size() - 1);
                }

            }

        }
    }

    /**
     * This method set true to explored array indexes which are not assigned as a key in tree map
     * @author Maral Ghatie
     */
    private void setExplored() {
        Set<Integer> mapkey = new_map_num.keySet();
        for(int i = 0; i<explored.length; i++){
            boolean flaghas = false;
            for(Integer key: mapkey){
                if(i == key){
                    flaghas = true;
                    break;
                }
            }
            if(!flaghas){
                explored[i] = true;
            }
        }

    }

    /**
     * This method make an adjacency map with country's numbers only
     * @author Maral Ghatie
     */

    private void setAdjNum() {

        Set<Integer> mapkey1 = new_map.keySet();
        Set<Integer> mapkey2 = new_map.keySet();

        for(Integer key1: mapkey1){
            ArrayList<String> adjacentname1 = new_map.get(key1);
            ArrayList<Integer> adjacentnum = new ArrayList<Integer>();
            for(int i = 1; i< adjacentname1.size(); i++){
                for(Integer key2: mapkey2){
                    ArrayList<String> adjName2 = new_map.get(key2);
                    String countryName = adjName2.get(0);
                    if(adjacentname1.get(i).equalsIgnoreCase(countryName)){
                        adjacentnum.add(key2);
                        break;
                    }
                }
            }
            new_map_num.put(key1, adjacentnum);
        }
    }

    /**
     * This method sets the arraylist of countryMap.
     */
    private void setCountryMap() {
        Set<Integer> mapkey1 = new_map.keySet();

        for(Integer key1: mapkey1){
            ArrayList<String> temp = new_map.get(key1);
            System.out.println("key in set country map " + key1);
            System.out.println("temp in set country map " + temp);
            country_map.put(key1, temp.get(0));
        }
    }
}
