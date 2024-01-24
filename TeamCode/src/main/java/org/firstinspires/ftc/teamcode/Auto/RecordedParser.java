package org.firstinspires.ftc.teamcode.Auto;

public class RecordedParser {

    /*
    Format: in = "{{1,2,3,4,5,...},{1,2,3,4,5,...},...}"
     */
    public double[][] parse(String in) {
        double[][] parsed;
        int rows;
        int braces=0;

        for (int i = 0; i < in.length(); i++){
            if (in.charAt(i)=='{' || in.charAt(i)=='}')
                braces += 1;
        }
        rows = (braces-2)/2;

        int columns;
        int commas = 0;

        for (int i = 0; i < in.indexOf('}'); i++){
            if (in.charAt(i)==';') {
                commas += 1;
            }
        }
        columns = commas+1;

        parsed = new double[rows][columns];
    }

}
