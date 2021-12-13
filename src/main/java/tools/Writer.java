package tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Writer {
    public static void write(String output) {
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("output.txt",true));
            myWriter.append(output);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
