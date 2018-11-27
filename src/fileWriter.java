/*
CT475_Assignment3 
Author: 15331436 | Diarmuid Beirne

26 Nov 2018
*/

import java.io.*;
import java.util.ArrayList;

public class fileWriter {

    private static String filePath;

    public fileWriter(String filePath) {
        this.filePath = filePath;

    }

    public void writeToFile(String fileOutString) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter(filePath, "UTF-8");
        writer.println(fileOutString);
        writer.close();
    }


}
