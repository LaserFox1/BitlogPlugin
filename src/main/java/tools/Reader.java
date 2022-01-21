package tools;

import java.io.*;
import java.util.*;

public class Reader {
    public static List<Object> read(String input) {
        List<Object> arr = new ArrayList<Object>();
        try {

            arr = tokenize(new FileReader(input));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return arr;
    }

    public static List<Object> tokenize(FileReader fileReader) {
        StreamTokenizer streamTokenizer = new StreamTokenizer(fileReader);
        streamTokenizer.eolIsSignificant(true);
        List<Object> tokens = new ArrayList<Object>();

        try {
            int currentToken = streamTokenizer.nextToken();

            while (currentToken != StreamTokenizer.TT_EOF) {

                Object o = (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) ?
                        streamTokenizer.nval
                        : streamTokenizer.ttype == StreamTokenizer.TT_WORD ?
                        streamTokenizer.sval :  (char) currentToken;

                tokens.add(o);
                currentToken = streamTokenizer.nextToken();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tokens;
    }
}