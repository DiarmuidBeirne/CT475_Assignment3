import javax.swing.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;


// fileReader Class to read in a CSV file and create an ArrayList of entries.
//It also splits the data into training/testing based on a percentage given. - Diarmuid Beirne (15331436)
public class fileReader {

    private static ArrayList<Entry> entries = new ArrayList<>();

    private static String filePath;

    //Diarmuid Beirne (15331436)
    //
    public fileReader(String filePath) {
        this.filePath = filePath;

        entries.clear();

    }

    public static ArrayList<Entry> readCSVFile() {
        String tempLine;
        String delimiter = ",";
        ArrayList<String> strings = new ArrayList<>();


        //Read in file line by line - Diarmuid Beirne 15331436
        //Assumes that all properties are doubles except the last one being a string - Diarmuid Beirne 15331436
        //Properties are added to the entries arraylist as an array of doubles with type being added as a string - Diarmuid Beirne 15331436

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((tempLine = br.readLine()) != null) {
                Entry newEntry;

                String[] row = tempLine.split(delimiter);
                int numberOfProperties = row.length - 1;

                double[] attributes = new double[numberOfProperties];
                for (int i = 0; i < numberOfProperties; i++) {
                        attributes[i] = Double.parseDouble(row[i]);

                }
                String type = row[row.length - 1];

                newEntry = new Entry(attributes, type);
                entries.add(newEntry);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "File Path Not Found!");
        }

        return entries;
    }


}
