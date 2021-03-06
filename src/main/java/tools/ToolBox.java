package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ToolBox {

    public static List<String> tokenize(String s) {
        StringTokenizer stringTokenizer = new StringTokenizer(s);
        List<String> arr = new ArrayList<>();
        while(stringTokenizer.hasMoreTokens()){
            arr.add(stringTokenizer.nextToken());
        }
        return arr;
    }

    public static void resetFile(String filename) {
        File myObj = new File(filename);
        try {
            if (!myObj.createNewFile()) {
                FileWriter fwOb = new FileWriter(filename, false);
                PrintWriter pwOb = new PrintWriter(fwOb, false);
                pwOb.flush();
                pwOb.close();
                fwOb.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String concat(String input, String excluded) {
        StringBuilder s = new StringBuilder();

        List<String> arr = ToolBox.tokenize(input);
        for (String s1 : arr) {
            if(!s1.equals(excluded))
                s.append(s1).append(" ");
        }
        return s.toString();
    }
    public static String concat(String input) {
        StringBuilder s = new StringBuilder();

        List<String> arr = ToolBox.tokenize(input);
        for (String s1 : arr) {
            s.append(s1).append(" ");
        }
        return s.toString();
    }

}
