package me.largetimmo.comp4004.a1.a1junitTest;

import java.io.*;
import java.util.List;

public class TestUtil {

    public static File createTempFileWithContent(List<String> lines){
        try {
            File fTemp = File.createTempFile("testtemp","txt");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fTemp)));
            for (String line: lines){
                bw.write(line);
                if(!line.contains("\n")){
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            return fTemp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addLinesTOFile(File f, List<String> lines) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(f,true));
        PrintWriter pw = new PrintWriter(bw);
        for (String line: lines){
            pw.write(line);
            if(!line.contains("\n")){
                pw.println();
            }
        }
        pw.flush();
        pw.close();
    }
}
