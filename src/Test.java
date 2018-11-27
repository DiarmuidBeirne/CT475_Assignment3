import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Test {

    // Test Class to test algorithm
    public static void main (String [] args) throws IOException {
        //set initial values to -1 to check if completed for validation - Alan Devane
        String fileInPath = "-1";
        String fileOutPath = "-1";
        double trainingSplit = -1;
        int numIterations = -1;
        double learningRate = -1;
        int numTests = -1;
        int printMode = -1;
        BufferedReader br;
        double avgAccuracy;
        ArrayList<Entry> testList = new ArrayList<>();

        fileReader csv;
        br = new BufferedReader(new InputStreamReader(System.in));
        while (fileInPath.equalsIgnoreCase("-1")){

            System.out.print("Enter File Path\t");
            fileInPath = br.readLine(); //Prompt User for file path - Alan Devane
            csv = new fileReader(fileInPath); //Create a file reader object with file path - Alan Devane
            if (!csv.checkFilePath()) //Check to see if file exists
            {
                System.out.println("File Path Incorrect!");
                fileInPath = "-1";

            }
            else{
                testList = csv.readCSVFile();
            }

        }


        while (trainingSplit == -1) { //checks if the input is completed - Alan Devane
        System.out.print("Enter Training Split: \t"); //Prompt User for Training Split - Alan Devane
        try {
            trainingSplit = Double.parseDouble(br.readLine());

        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format: Enter a value between 0 and 1!");
        }
        if(trainingSplit > 1 || trainingSplit < 0) //Only accept values between 0 and .99
        {
            System.out.println("Error!! Enter Value between 0 and 1");
            trainingSplit = -1;
        }
    }
        while (numIterations == -1) {
        System.out.print("Enter Number of Iterations:\t"); //Prompt User for Num of Iterations - Alan Devane
        try{
            numIterations = Integer.parseInt(br.readLine());

        }catch(NumberFormatException nfe){
            System.err.println("Invalid Format: Enter a integer value between 0 and 1,000,000!");
        }
            if(numIterations > 1000000 || numIterations < 0) //Only accept values between 0 and .99
            {
                System.out.println("Error!! Enter Value between 0 and 1,000,000");
                numIterations = -1;
            }
        }

        while (learningRate == -1) {
            System.out.print("Enter Learning Rate:\t"); //Prompt User for Learning Rate  - Alan Devane
            try {
                learningRate = Double.parseDouble(br.readLine());

            } catch (NumberFormatException nfe) {
                System.err.println("Invalid Format: Enter a value between 0 and 1!");
            }
            if(learningRate > 1 || learningRate < 0) //Only accept values between 0 and .99
            {
                System.out.println("Error!! Enter Value between 0 and 1");
                learningRate = -1;
            }
        }

        while (numTests == -1) {
            System.out.print("Enter Number of Tests to Run:\t"); //Prompt User for Num of Iterations - Alan Devane
            try{
                numTests = Integer.parseInt(br.readLine());

            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format: Enter a integer value between 0 and 100!");
            }
            if(numTests > 100|| numTests < 0) //Only accept values between 0 and .99
            {
                System.out.println("Error!! Enter Value between 0 and 100");
                numTests = -1;
            }
        }



        while (printMode == -1) {
            System.out.println("--Print Mode--\nEnter 0 to display full results of each test run\nEnter 1 to display overview of test run"); //2 different print modes - Alan Devane
            System.out.print("Enter Print Mode:\t"); //Prompt User for Print Mode - Alan Devane
            try{
                printMode = Integer.parseInt(br.readLine());

            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format: Enter a integer value, either 0 or 1");
            }
            if(printMode > 1|| printMode < 0) //Only accept 1 or 0
            {
                System.out.println("Error!! Enter a integer value, either 0 or 1");
                printMode = -1;
            }
        }




        avgAccuracy = 0;
        String allResults = "";

        for (int i = 0; i < numTests; i++) {
            allResults += "Test " + (i + 1) + "\n";
            LogisticRegressionAlgorithm testRun = new LogisticRegressionAlgorithm(trainingSplit,numIterations,learningRate, testList, printMode);
            testRun.getTestAccuracy();
            avgAccuracy += testRun.getTestAccuracy();
            allResults += testRun.getResultsToPrint();
        }

            String overallAcc = "\n\n----------------------------\nOverall Accuracy = " + avgAccuracy / numTests;


            fileWriter Writer= new fileWriter("/Users/I342041/Documents/Github2.0/CT475_Assignment3/results.txt");
            Writer.writeToFile(allResults + overallAcc);

            System.out.println("Output file located in project folder (results.txt)");





    }

}