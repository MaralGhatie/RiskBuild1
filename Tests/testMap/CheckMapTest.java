package testMap;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

import Map.CheckMap;

/**
 * Map validation – including :
 *  reading an invalid map file and
 *  checking map and continents being connected,
 * @author raoying
 * @version 3.0
 *
 */
public class CheckMapTest {
    CheckMap test_cmap = new CheckMap();

    public TreeMap<Integer, ArrayList<String>> read_map(int file_no) throws IOException {
        TreeMap<Integer, ArrayList<String>> test_map = new TreeMap<>();
        String mapfile = "";
        boolean check2 = true;
        //read map from file
        switch (file_no) {
            case 1:
                mapfile = ".//maps/incorrect3.txt";
                break;
            case 2:
                mapfile = ".//maps/incorrect4.txt";
                break;
            case 3:
                mapfile = ".//maps/incorrect1.txt";
                break;
            case 4:
                mapfile = "Countries.txt";
                break;
        }
        BufferedReader read = new BufferedReader(new FileReader(mapfile));
        ArrayList<String> filearray = new ArrayList<String>();
        String s;
        int position = 0;
        while ((s = read.readLine()) != null) {
            filearray.add(position, s);
            //System.out.println(s);
            position++;
        }

        int num_country = position;

        for (int i = 0; i < filearray.size(); i++) {

            StringTokenizer st = new StringTokenizer(filearray.get(i), ",");

            if (st.countTokens() < 2) {
                check2 = false;
                break;
            }
            int countrynumber = Integer.parseInt(st.nextToken());
            ArrayList<String> temp = new ArrayList<String>();


            while (st.hasMoreTokens()) {
                temp.add(st.nextToken());
            }

            test_map.put(countrynumber, temp);


        }
        return test_map;
    }

    @Test
    void checkCorrectivityTest() throws IOException {
        TreeMap<Integer, ArrayList<String>> test_4 = new TreeMap<>();
        test_4 = read_map(4);
        boolean flag_4 = test_cmap.checkCorrectivity(test_4);
        assertTrue(flag_4);
    }

    @Test
    void isBidirectionalTest() throws IOException {
        TreeMap<Integer, ArrayList<String>> test_2 = new TreeMap<>();
        test_2 = read_map(2);
        boolean flag_2 = test_cmap.checkCorrectivity(test_2);
        assertFalse(flag_2);
    }

    @Test
    void isSingleContinentTest() throws IOException {
        TreeMap<Integer, ArrayList<String>> test_1 = new TreeMap<>();
        test_1 = read_map(1);
        boolean flag_1 = test_cmap.checkCorrectivity(test_1);
        assertFalse(flag_1);
    }

    @Test
    void isConnectedTest() throws IOException {
        TreeMap<Integer, ArrayList<String>> test_3 = new TreeMap<>();
        test_3 = read_map(3);
        boolean flag_3 = test_cmap.checkCorrectivity(test_3);
        assertFalse(flag_3);
    }
}