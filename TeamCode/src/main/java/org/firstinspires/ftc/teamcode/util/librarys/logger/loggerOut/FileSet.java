package org.firstinspires.ftc.teamcode.util.librarys.logger.loggerOut;

import org.firstinspires.ftc.teamcode.util.librarys.logger.util.DefaultFile;

import java.io.*;

public class FileSet {
    public FileSet (String name) throws IOException {
        this.name = name;

        dir = new File(DefaultFile.DEFAULT_DIR + File.separator + name);

        if (dir.mkdirs()) { //if it doesn't exist, numFiles = 0
            System.out.println("dir.getPath() = " + dir.getPath());
            File setData = new File(dir.getPath() + File.separator + "SetData" + ".txt");
            numFiles = 0;
        } else { //otherwise numFiles = the number of files
            File setData = new File(dir.getPath() + File.separator + "SetData.txt");
            System.out.println("setData.getPath() = " + setData.getPath());
            BufferedReader reader = new BufferedReader(new FileReader(setData));
            numFiles = Integer.parseInt(reader.readLine());
        }
    }

    public File getEntry (int i) throws IOException {
        if( i >= numFiles ) {
            return getNewEntry();
        }
        return new File(dir.getPath() + "entry_" + i + ".txt");
    }

    public File getNewEntry () throws IOException {
        ++numFiles;
        updateSetData();
        return new File(dir.getPath() + File.separator + "entry_" + (numFiles-1) + ".txt");
    }

    public void updateSetData () throws IOException {
        System.out.println("updated setdata: " + String.valueOf(numFiles));
        BufferedWriter writer = new BufferedWriter(new FileWriter(dir.getPath() + File.separator + "SetData.txt"));
        writer.write(String.valueOf(numFiles) + "\n");
        writer.flush();
    }

    public int getNumFiles () {
        return numFiles;
    }

    public String getName () {
        return name;
    }

    String name;
    File dir;
    int numFiles;
}