import java.util.ArrayList;
import java.util.Collections;

public class LogisticRegressionAlgorithm {

    private final double learningRate;
    private ArrayList<String> types;
    private int numProperties;
    private ArrayList<Entry> trainEntries;
    private ArrayList<Entry> testEntries;
    private double[][] coefficients;
    private int numIterations;
    double binary;
    private double testAccuracy;
    private int printMode;
    private String ResultsToPrint;




    public LogisticRegressionAlgorithm(double trainingSplit, int iterations, double learningRate, ArrayList<Entry> entries, int printMode) {

        this.numIterations = iterations;
        this.learningRate = learningRate;
        this.printMode = printMode;
        numProperties = getNumberOfProperties(entries);
        types = getTypes(entries);
        Collections.shuffle(entries); //randomize order - Alan Devane
        trainEntries = generateTrainingEntries(entries, trainingSplit); //generate Training Data, entries ArrayList and trainingsplit passed in -Alan Devane
        testEntries = generateTestEntries(entries, trainingSplit); //generate Testing, entries ArrayList and trainingsplit passed in - Alan Devane

        ArrayList<String> results = new ArrayList<String>();


            trainAlgorithm();
            ResultsToPrint = testAlgorithm();





    }

    public ArrayList<String> getTypes(ArrayList<Entry> entries) { //returns  a list of all types from the list of Entrys - Alan Devane
        ArrayList<String> types = new ArrayList<>();
        types.add(entries.get(0).getType());
        for (Entry e : entries) {
            if (!(types.contains(e.getType()))) {
                types.add(e.getType());
            }
        }
        return types;
    }

    private int getNumberOfProperties(ArrayList<Entry> entries) {
            return entries.get(0).getProperties().length;
    }


    private ArrayList<Entry> generateTrainingEntries(ArrayList<Entry> entries, double trainingSplit) {
        int lastTrainingEntry = (int) Math.round(entries.size() * trainingSplit); //Get the index of the last training Set entry- Alan Devane
        ArrayList<Entry> trainingSet = new ArrayList<>();
        trainingSet.addAll(entries.subList(0, lastTrainingEntry));
        return trainingSet;

    }

    public ArrayList<Entry> getTrainEntries() { //simply gets the training Entry List -Alan Devane (14408498)
        return trainEntries;
    }

    private ArrayList<Entry> generateTestEntries(ArrayList<Entry> entries, double trainingSplit) {

        int firstTestEntry = (int) Math.round(entries.size() * trainingSplit); //gets the index of the first test set entry -Alan Devane
        ArrayList<Entry> testSet = new ArrayList<>();
        testSet.addAll(entries.subList(firstTestEntry, entries.size()));
        return testSet;
    }

    public ArrayList<Entry> getTestEntries() { //simply gets Testing Entry List - Alan Devane (14408498)
        return testEntries;
    }



    //Method to predict type
    private double classify(double[] coefficients, Entry entries) {
        double log = 0.0;
        log += coefficients[0];
        double[] properties = entries.getProperties();
        for (int i = 1; i <= numProperties; i++) {
            log += coefficients[i] * properties[i - 1];
        }
        return sigmoidFunc(log);
    }

    private double sigmoidFunc(double log) {  //sigmoid method - DB
        log = 1.0 / (1.0 + Math.exp(-log));
        return log;
    }

    //train algorithm -DB
    public void trainAlgorithm() {

        //create 2d array for coefficients -DB
        coefficients = new double[types.size()][numProperties + 1];

        double converge = 1.0; //tracks convergence of coefficients -DB

        //iterate for each type and each entry  - DB
        for (int t = 0; t < types.size(); t++) {


            //coefficients for user Specified num of Iterations - DB
            for (int i = 0; i < numIterations; i++) {


                    double[] delta = new double[numProperties + 1];

                    for (Entry e : trainEntries) {
                        //assign binary value to type, either is type under test or is not type under test

                        if (types.get(t).equals(e.getType())) {
                            binary = 1.0; //if type of Entry is type under test, type is 1 -DB
                        } else {
                            binary = 0.0; //if type of Entry is not type under test, type is 0 - DB
                        }
                        //classification method for current type - DB
                        double y = classify(coefficients[t], e);


                        //find difference in Coefficient to classify each Entry of training data - DB
                        delta[0] += y - binary; //difference of intercept - DB

                        for (int j = 1; j <= numProperties; j++) {
                            double diff = (e.getProperties()[j - 1]) * (y - binary); //Difference of property coefficients -DB
                            delta[j] += diff;
                        }

                    }


                    converge = Math.abs(delta[0] / trainEntries.size()); //Update convergence

                    for (int k = 0; k < numProperties + 1; k++) { //Update coefficients with average diff
                        delta[k] = delta[k] / trainEntries.size(); //Calculate average
                        if (Math.abs(delta[k]) > converge) {
                            converge = Math.abs(delta[k]); //check if coefficients have converged
                        }
                        //update coefficients
                        coefficients[t][k] -= learningRate * (delta[k]);
                    }

            }
        }
    }


    private String testAlgorithm() { //Algorithm to test Data

        double[][] confusionMatrix = new double[types.size()][types.size()];
        String resultString = "";
        for (Entry entry : testEntries) {

            //Variable to track highest probability type Initialised at -1 to ensure y will be greater
            double maxY = -1;
            String predicType = "";
            double y;

            for (int typ = 0; typ < types.size(); typ++) {

                //run classification method for type
                y = classify(coefficients[typ], entry);

                //set predicted type to most likely type
                if (y > maxY) {
                    maxY = y;
                    predicType = types.get(typ);
                }
            }
            if (printMode == 0) { //Check print mode - Alan Devane
                //Adds the actual type, properties and predicted type to the result String -DB
                resultString += ("Actual " + entry.toString() + "\t\tPredicted Type: " + predicType + "\t\t");
                if (entry.getType().toString().equalsIgnoreCase(predicType)) { //adds correct/incorrect to the String
                    resultString += "Prediction Correct\n";
                } else {
                    resultString += "Prediction Incorrect\n";
                }

            }
            confusionMatrix[types.indexOf(entry.getType())][types.indexOf(predicType)]++;
        }

        //calculates the accuracy of the test -Alan Devane
        double accuracy = 0.0;
        for (int i = 0; i < types.size(); i++) {
            accuracy += confusionMatrix[i][i];
        }
        accuracy = (accuracy / testEntries.size()) * 100;
        testAccuracy = accuracy;


        //Add confusion matrix to result String -Alan Devane
        resultString += ("Confusion Matrix\n");
        for(int j = 0; j < types.size(); j++){
            resultString += ("\t\t\t\t" + types.get(j));
        }
        resultString += ("\n");
        for(int k = 0; k < types.size(); k++){
            resultString += (types.get(k));
            for(int j = 0; j<types.size(); j++){
                resultString += ("\t\t\t\t" + confusionMatrix[k][j]);
            }
            resultString += ("\n");
        }

        resultString += ("Accuracy: " + accuracy + "%" + "\r\n\n");
        resultString += "\r \n";



        return resultString;

    }

    public double getTestAccuracy() { //Returns Accuracy of Test - Alan Devane
        return testAccuracy;
    }

    public String getResultsToPrint() { //Returns the Result String - Alan Devane
        return ResultsToPrint;
    }


}