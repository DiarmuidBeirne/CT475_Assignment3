    import java.util.ArrayList;
    import java.util.Collections;

    public class LogisticRegressionAlgorithm {

        private final double learningRate;
        private ArrayList<String> types;
        private int numProperties;
        private ArrayList<Entry> trainEntries;
        private ArrayList<Entry> testEntries;
        private double[][] coeffs;
        private int numIterations;
        int binary;
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






        //training algorithm -DB
        public void trainAlgorithm() {

            //create 2d array for coeffs -DB
            coeffs = new double[types.size()][numProperties + 1];


            for (int t = 0; t < types.size(); t++) { //iterate for each type - DB
                for (int i = 0; i < numIterations; i++) {  //iterates for user Specified num of times to run algorithm- DB

                        double[] delta = new double[numProperties + 1]; //Coefficient for each property

                        for (Entry et : trainEntries) {



                            //classify current type - DB
                            double y = classifier(coeffs[t], et);

                            //assign binary value to type (test/not tested)-DB
                            if (types.get(t).equalsIgnoreCase(et.getType().toString())) {
                                binary = 1; //if type is being Tested, binary value is 1  -DB
                            }
                            else {
                                binary = 0; //if type is not being Tested, binary value is 0 - DB
                            }


                            delta[0] += y - binary; //difference of intercept - DB



                            for (int j = 0; j < numProperties; j++) { //find difference in Coeff and update Delta - DB
                                double diff = (et.getProperties()[j]) * (y - binary); //Difference of property coeff -DB
                                delta[j + 1] += diff;
                            }

                        }

                        for (int k = 0; k <= numProperties; k++) { //Iterate for each property -DB
                            delta[k] /= (trainEntries.size() + 1); //gets average difference

                            coeffs[t][k] -= learningRate * delta[k];
                        }

                }
            }
        }

        //Classifier Method - DB
        private double classifier(double[] coeffs, Entry entries) {
            double log = 0.0;
            log = coeffs[0];
            double[] properties = entries.getProperties();
            for (int i = 0; i < numProperties; i++) {
                log += coeffs[i + 1] * properties[i];
            }
            log = log * -1;
            double logIt = 1 / (1 + Math.exp(log));
            return logIt;
        }


        private String testAlgorithm() { //Algorithm to test Data

            double[][] confusionMatrix = new double[types.size()][types.size()]; //create empty confusion matrix for test -DB
            String resultString = "";
            for (Entry entry : testEntries) {




                double y;
                double maxY = 0; //initial value for y, y will be the highest type probability
                String predictedType = " ";


                for (int typ = 0; typ < types.size(); typ++) {

                    //run classification method for type
                    y = classifier(coeffs[typ], entry);

                    //set predicted type to most likely type
                    if (y > maxY) {
                        maxY = y;
                        predictedType = types.get(typ);
                    }
                }
            if (printMode == 0) {   //Check print mode, print mode outputs every entry with the predicted type and if it is correct - Alan Devane


                    resultString += ("Actual " + entry.toString() + "\t\tPredicted Type: " + predictedType + "\t\t");
                    if (entry.getType().toString().equalsIgnoreCase(predictedType)) { //adds correct/incorrect to the String
                        resultString += "Prediction Correct\n";
                    } else {
                        resultString += "Prediction Incorrect\n";
                    }

                }
                confusionMatrix[types.indexOf(entry.getType())][types.indexOf(predictedType)]++;
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
                resultString += ("\t\t\t\t" + types.get(j) + "\n");
            }


            for(int k = 0; k < types.size(); k++){
                resultString += (types.get(k));
                for(int j = 0; j<types.size(); j++){
                    resultString += ("\t\t\t\t" + confusionMatrix[k][j] + "\n");
                }

            }

            resultString += ("Accuracy: " + accuracy + "%" + "\r\n");
            resultString += "\r";



            return resultString;

        }

        public double getTestAccuracy() { //Returns Accuracy of Test - Alan Devane
            return testAccuracy;
        }

        public String getResultsToPrint() { //Returns the Result String - Alan Devane
            return ResultsToPrint;
        }


    }