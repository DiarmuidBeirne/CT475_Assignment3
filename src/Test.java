import java.util.ArrayList;
import java.util.Collections;

public class Test {

    // Simple Test Class to test program is working
    public static void main (String [] args) {
        /*
        Github Use Only
        DB file path: /Users/I342041/Documents/Github2.0/MachineLearningAssignment3/owls.csv
        Alan Devane (14408498): C:/Users/alan.devane/Documents/GitHub/CT475_Assignment3/owls.csv
         */
        fileReader csv = new fileReader("/Users/I342041/Documents/Github2.0/MachineLearningAssignment3/owls.csv");

        ArrayList<Entry> testList = fileReader.readCSVFile();
        LogisticRegressionAlgorithm testRun = new LogisticRegressionAlgorithm(.666,testList);
        ArrayList<Entry> trainingDataList = testRun.getTrainEntries();
        ArrayList<Entry> testingDataList = testRun.getTestEntries();


        System.out.println("Training Data:");
        for(Entry i : trainingDataList)
        {
            System.out.println(i.toString());
        }
        System.out.println("Testing Data:");
        for(Entry i : testingDataList)
        {
            System.out.println(i.toString());
        }

    }

}