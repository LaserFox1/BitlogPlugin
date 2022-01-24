package tools;

import java.io.*;
import java.util.*;

public class Reader {
    public static ArrayList<String> read(String input) {
        ArrayList<String> arr = new ArrayList<String>();
        try {
            Scanner s1 = new Scanner(new File(input));
            while (s1.hasNext()) {
                arr.add((s1.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arr;
    }


}