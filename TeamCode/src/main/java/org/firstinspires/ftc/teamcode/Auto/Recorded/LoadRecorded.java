package org.firstinspires.ftc.teamcode.Auto.Recorded;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadRecorded {
    static BufferedReader reader;
    static final String RecorderOutputFileName = "RecorderOutputFile.txt";

    public static ArrayList<ArrayList<Double>> load (String fileName) throws IOException {
        if (reader == null) reader = new BufferedReader(new FileReader(RecorderOutputFileName));

        int fileNum = 0;
        String name = "";
        while (true) {
             char c = (char) reader.read();
             if (c != ',') {
                 name += c;
             } else if (c == (char)(-1)) return null;
             else {
                 fileNum++;
                 if (name.equals(fileName)) break;
                 name = "";
             }
        }

        ArrayList<ArrayList<Double>> LoadedData = new ArrayList<>();

        //
    }
}
