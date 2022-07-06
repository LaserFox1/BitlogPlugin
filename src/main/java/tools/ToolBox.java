package tools;

import org.apache.maven.shared.model.fileset.FileSet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static FileSet defaultSet(){
        FileSet fs = new FileSet();
        fs.setDirectory("src/test/resources/features");
        fs.setIncludes(Arrays.stream(new String[]{"*.feature"}).toList());
        return  fs;
    }

}
