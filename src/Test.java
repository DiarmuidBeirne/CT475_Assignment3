import java.util.ArrayList;

public class Test {

    // Simple Test Class to test program is working
    public static void main (String [] args) {
        fileReader csv = new fileReader("/Users/I342041/Documents/Github2.0/MachineLearningAssignment3/owls.csv");

        ArrayList<Entry> testList = fileReader.readCSVFile();

        for(Entry i : testList)
        {
            System.out.println(i.toString());

        }
    }

}
