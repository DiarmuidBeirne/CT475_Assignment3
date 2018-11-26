import javax.swing.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;


// fileReader Class to read in a CSV file and create an ArrayList of entries.
//It also splits the data into training/testing based on a percentage given. - DB
public class fileReader {

    private static ArrayList<Entry> entries = new ArrayList<>();

    private static String filePath;


    public fileReader(String filePath) {
        this.filePath = filePath;

        entries.clear();

    }

    public static ArrayList<Entry> readCSVFile() {
        String tempLine;
        String delimiter = ",";
        ArrayList<String> strings = new ArrayList<>();


        //Read in file line by line - Diarmuid Beirne 15331436
        //Assumes that all properties are doubles except the last one being a string - DB
        //Properties are added to the entries arraylist as an array of doubles with type being added as a string - DB

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((tempLine = br.readLine()) != null) {  //reads in line of the file
                Entry newEntry;                            //creates a new Entry Object

                String[] row = tempLine.split(delimiter); //splits the row at each comma into separate strings and stores in an array - DB
                int numberOfProperties = row.length - 1; // last string is the type - DB

                double[] attributes = new double[numberOfProperties];
                for (int i = 0; i < numberOfProperties; i++) {
                        attributes[i] = Double.parseDouble(row[i]); //create doubles array of the attributes -DB

                }
                String type = row[row.length - 1]; //Assign type to the last string in row array - DB

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


    public boolean checkFilePath() { //method to check file path is correct -Alan Devane

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
