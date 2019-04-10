package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.*;
import Cards.Cards;

/**
 * This is one of the model classes which sets every thing up including maps.
 * @author Niki
 * @version 2.0
 */

public class Setup {
    private TreeMap<Integer,String> country_map = new TreeMap<>();
    private TreeMap<Integer, ArrayList> adjacency_map = new TreeMap<>();
    private String MapFile = "Countries.txt";
    public final int ASIA_VALUES = 20;
    public final int NAMERICA_VALUES = 10;
    public final int SAMERICA_VALUES = 10;
    public final int EUROPE_VALUES = 25;
    public final int AFRICA_VALUES = 25;
    public final int AUSTRALIA_VALUES = 10;

    private ArrayList<Integer> asia_countries = new ArrayList<>();
    private ArrayList<Integer> namerica_countries = new ArrayList<>();
    private ArrayList<Integer> samerica_countries = new ArrayList<>();
    private ArrayList<Integer> europe_countries = new ArrayList<>();
    private ArrayList<Integer> africa_countries = new ArrayList<>();
    private ArrayList<Integer> australia_countries = new ArrayList<>();


    private static int number_of_country;

    /**
     * This function is used to load the map from our countries file.
     *
     * @author Paras Sethi
     * @version 1.0
     * @see StringTokenizer,BufferedReader
     * @throws Exception
     */
    public void loadMap() throws Exception{

        BufferedReader read  = new BufferedReader(new FileReader(MapFile));

        String[] filearray = new String[36];
        String userinput;
        int positionofcountry = 0;

        while((userinput = read.readLine())!= null){
            filearray[positionofcountry] = userinput;
            positionofcountry++;
        }

        number_of_country = positionofcountry;


        for (int i = 0; i < filearray.length; i++){

            StringTokenizer st = new StringTokenizer(filearray[i],",");
            int countrynumber = Integer.parseInt(st.nextToken());
            String countryname =  st.nextToken();

            ArrayList<String> temp = new ArrayList<String>();

            while(st.hasMoreTokens()){
                temp.add(st.nextToken());
            }

            country_map.put(countrynumber,countryname);
            adjacency_map.put(countrynumber,temp);

        }
    }
    
    public void tLoadMap(String MapFile) throws Exception{

        BufferedReader read  = new BufferedReader(new FileReader(MapFile));

        ArrayList<String> filearray = new ArrayList<>();
        String userinput;
        int positionofcountry = 0;

        while((userinput = read.readLine())!= null){
            filearray.add(positionofcountry,userinput);
            positionofcountry++;
        }

        number_of_country = positionofcountry;


        for (int i = 0; i < filearray.size(); i++){

            StringTokenizer st = new StringTokenizer(filearray.get(i),",");
            int countrynumber = Integer.parseInt(st.nextToken());
            String countryname =  st.nextToken();

            ArrayList<String> temp = new ArrayList<String>();

            while(st.hasMoreTokens()){
                temp.add(st.nextToken());
            }

            country_map.put(countrynumber,countryname);
            adjacency_map.put(countrynumber,temp);

        }
    }

    /**
     * This method check if the number of countries is divisible to number of players
     * @param numOfPlayers
     * @return true if it was divisible and false other wise
     * @author Maral Ghatie
     */
    public boolean countriesDivisible(int numOfPlayers){
        if(number_of_country % numOfPlayers == 0){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * This function is used to return number of countries in our map.
     * @author Paras Sethi
     * @version 1.0
     * @return Num_country = Number of Countries in our map.
     */
    public static int getNumCountry(){
        return number_of_country;
    }

    /**
     * This function is used to return Country_map Treemap.
     * @author Paras Sethi
     * @version 1.0
     * @return Country_map = Tree map for Countries with numbers as keys and values as Countries.
     */
    public TreeMap<Integer, String> getCountryMap() {
        return country_map;
    }

    /**
     * This function is used to return Adj_Map_ Treemap.
     * @author Paras Sethi
     * @version 1.0
     * @return Adj_Map = Tree map for Countries with numbers as keys and Adjescent countries as values.
     */
    public TreeMap<Integer, ArrayList> getAdjMap() {
        return adjacency_map;
    }

    public void setEverything(TreeMap<Integer, ArrayList<String>> adjacentmap){
        adjacency_map.putAll(adjacentmap);
        //country_map.putAll(countrymap);
        Set<Integer> mapkey1 = adjacentmap.keySet();

        for(Integer key1: mapkey1){
            ArrayList<String> temp = adjacentmap.get(key1);
            country_map.put(key1, temp.get(0));
        }
        //number_of_country = countrynumber;
        number_of_country = adjacentmap.size();

    }

    /**
     * This function is to show number of countries each continent holds
     * returns list of countries in continent
     */
    public void continentControl(){
        Iterator it = country_map.keySet().iterator();
        while(it.hasNext()){
            int cn = (int)it.next();
            if(cn<=20){
                asia_countries.add(cn);
            }
            else if(cn>20 && cn<=30){
                namerica_countries.add(cn);
            }
            else if(cn>30 &&cn<=40){
                samerica_countries.add(cn);
            }
            else if(cn>40 && cn<=60){
                europe_countries.add(cn);
            }
            else if(cn>60 && cn<=80){
                africa_countries.add(cn);
            }
            else if(cn>80 && cn<=90){
                australia_countries.add(cn);
            }

        }
    }

    /**
     * This is a getter method.
     * @return asia_countries an array list which includes countries in Asia.
     */

    public ArrayList<Integer> getAsiaCountries() {
        return asia_countries;
    }

    /**
     * This is a getter method.
     * @return namerica_countries an array list which includes countries in North America.
     */

    public ArrayList<Integer> getNamericaCountries() {
        return namerica_countries;
    }

    /**
     * This is a getter method.
     * @return samerica_countries an array list which includes countries in South America.
     */

    public ArrayList<Integer> getSamericaCountries() {
        return samerica_countries;
    }

    /**
     * This is a getter method.
     * @return europe_countries an array list which includes countries in Europe.
     */

    public ArrayList<Integer> getEuropeCountries() {
        return europe_countries;
    }

    /**
     * This is a getter method.
     * @return africa_countries an array list which includes countries in Africa.
     */

    public ArrayList<Integer> getAfricaCountries() {
        return africa_countries;
    }

    /**
     * This is a getter method.
     * @return australia_countries an array list which includes countries in Australia.
     */

    public ArrayList<Integer> getAustraliaCountries() {
        return australia_countries;
    }
}
