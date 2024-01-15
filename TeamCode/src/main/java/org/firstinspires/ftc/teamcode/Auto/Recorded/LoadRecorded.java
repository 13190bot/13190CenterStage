package org.firstinspires.ftc.teamcode.Auto.Recorded;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.Recorder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadRecorded {
    static BufferedReader reader;
    static final String RecorderOutputFileName = "/sdcard/FIRST/Recorder/Recorded.txt";
    static final String[] names = {"blueCenter","blueLeft","blueRight","redCenter","redLeft","redRight"};
    public static ArrayList<ArrayList<ArrayList<Double>>> data;
    public static void load () throws IOException {
        if (reader == null) reader = new BufferedReader(new FileReader(RecorderOutputFileName));

        data = new ArrayList<ArrayList<ArrayList<Double>>>();
        ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
        temp.add(new ArrayList<>());
        data.add(temp);

        int fileNum = 0;
        String cstring = "";
        int level = -1;
        int index = 0;
        while (true) {
             char c = (char) reader.read();
             if (c == (char) -1) {
                 break;
             }
             switch(c) {
                 case '{':
                     cstring = "";
                     level++;
                     break;
                 case '}':
                     level--;
                     if (level == 0) {
                         index++;
                         data.add(new ArrayList<ArrayList<Double>>());
                     }
                     break;
                 case ',':
                     data.get(fileNum).get(index).add(Double.parseDouble(cstring));
                     cstring = "";
//                     Double.parseDouble()
                     break;
                 case '\n':
                     fileNum++;
                 default:
                     cstring += c;
             }
        }

//        ArrayList<ArrayList<Double>> LoadedData = new ArrayList<>();

        //
    }

    public static double[][][] getArrayData() {
        double[][][] out = new double[data.size()][][];
        for (int i = 0; i < data.size(); i++) {
            out[i] = new double[data.get(i).size()][];
            for (int i2 = 0; i2 < data.get(i).size(); i2++) {
                Object[] temp = data.get(i).get(i2).toArray();
                double[] o = new double[temp.length];
                for (int i3 = 0; i3 < temp.length; i3++) {
                    o[i3] = (double) temp[i3];
                }
                out[i][i2] = o;
            }
        }
        return out;
    }

    public static void main(String[] args) throws IOException {
        load();
//        ArrayList<ArrayList<double[]>> = new ArrayList<ArrayList<double[]>>;

        double[][][] out = new double[data.size()][][];
        for (int i = 0; i < data.size(); i++) {
            out[i] = new double[data.get(i).size()][];
            for (int i2 = 0; i2 < data.get(i).size(); i2++) {
                Object[] temp = data.get(i).get(i2).toArray();
                double[] o = new double[temp.length];
                for (int i3 = 0; i3 < temp.length; i3++) {
                    o[i3] = (double) temp[i3];
                }
                out[i][i2] = o;
            }
        }

        Recorder.data = new ArrayList[]{data.get(0)};
        Recorder.dataToCode();

//        return o;
//        ArrayList<ArrayList<Double>>[];
//        toArray();
    }


}
