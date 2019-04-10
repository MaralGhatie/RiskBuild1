
package Map;
//import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * This class is for reading existing maps through text file
 * @author paras
 * @version 1.0
 */
public class Loadexmap {

    private static int num_country;
    private TreeMap<Integer, ArrayList<String>> new_map = new TreeMap<>();
    private TreeMap<Integer,ArrayList<String>> adjacent_map = new TreeMap<>();

    private boolean check2 = true; //for empty country names and null country names

    /**
     * This function is used to read existing maps in computer directory
     * @throws java.io.IOException if file is not read properly
     * @see java.io.IOException
     * @author paras
     * @version 1.0
     */
    public boolean makeMap() throws Exception{

        File folder = new File(".//maps/");
        File[] list = folder.listFiles();

        System.out.println("The files in the directory are:");

        for (int i = 0; i < list.length; i++) {
            System.out.println("File " + Integer.toString(i+1) + list[i].getName());
        }

        System.out.println("Enter the File # that you want to read");

        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();

        String mapfile = list[choice-1].getName();

        System.out.println("Mapfile = " + mapfile);

        BufferedReader read  = new BufferedReader(new FileReader(".//maps/" + mapfile));

        ArrayList<String> filearray = new ArrayList<String>();
        String s;
        int position = 0;

        while((s = read.readLine())!= null){
            filearray.add(position,s);
            //System.out.println(s);
            position++;
        }

        num_country = position;
        System.out.println("Num country is : " + Integer.toString(num_country)) ;
        System.out.println("before = " + Boolean.toString(check2));

        for (int i = 0;i < filearray.size();i++){

            StringTokenizer st = new StringTokenizer(filearray.get(i),",");

            //System.out.println(Integer.toString(st.countTokens()));

            if (st.countTokens() < 2) {
                check2 = false;
                break;
            }
            int countrynumber = Integer.parseInt(st.nextToken());
            // String countryname = st.nextToken();

            //System.out.println("Pass " + Integer.toString(i) + Boolean.toString(check2));

            ArrayList<String> temp = new ArrayList<String>();


            while(st.hasMoreTokens()){
                temp.add(st.nextToken());
            }

            new_map.put(countrynumber,temp);


        }

        System.out.println("after = " + Boolean.toString(check2));

        //Print_Maps();

        if (!check2){
            System.out.println("Some names of continents have no countries or are provided with either null or empty names");
            return false;
        }
        CheckMap check = new CheckMap();
        boolean result = check.checkCorrectivity(new_map);
        if(result && check2){
            System.out.println("**************************");
            System.out.println("Congrats!! your map is totally correct! Shall we play now? :D");
            System.out.println("**************************");
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * This function is getter method for adjacency mao
     * @return adjacent_map whcih is equal to adjacent map
     * @author paras
     * @version 1.0
     */
    public TreeMap<Integer,ArrayList<String>> getAdjmap(){
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

