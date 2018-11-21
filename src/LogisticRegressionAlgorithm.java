import java.util.ArrayList;
import java.util.Collections;

public class LogisticRegressionAlgorithm {


    private ArrayList<Entry> trainEntries;
    private ArrayList<Entry> testEntries;
    private ArrayList<String> labels;
    private int numAttributes;
    private String fileOut;
    double binaryLabel;





    public LogisticRegressionAlgorithm(double trainingSplit, ArrayList<Entry> entries) {



        trainEntries = generateTrainingEntries(entries, trainingSplit); //generate Training Data, entries ArrayList and trainingsplit passed in -Alan Devane (14408498)
        testEntries = generateTestEntries(entries, trainingSplit); //generate Testing, entries ArrayList and trainingsplit passed in - Alan Devane (14408498)

    }



    private ArrayList<Entry> generateTrainingEntries(ArrayList<Entry> entries, double trainingSplit) {
        Collections.shuffle(entries); //randomize order -Alan Devane (14408498)
        int lastTrainingEntry = (int) Math.round(entries.size() * trainingSplit); //Get the index of the last training Set entry-Alan Devane (14408498)
        ArrayList<Entry> trainingSet = new ArrayList<>();
        trainingSet.addAll(entries.subList(0, lastTrainingEntry));
        return trainingSet;

    }

    public ArrayList<Entry> getTrainEntries() { //simply gets the training Entry List -Alan Devane (14408498)
        return trainEntries;
    }

    private ArrayList<Entry> generateTestEntries(ArrayList<Entry> entries, double trainingSplit) {
        Collections.shuffle(entries); //randomize order - Alan Devane (14408498)
        int firstTestEntry = (int) Math.round(entries.size() * trainingSplit); //gets the index of the first test set entry -Alan Devane (14408498)
        ArrayList<Entry> testSet = new ArrayList<>();
        testSet.addAll(entries.subList(firstTestEntry, entries.size()));
        return testSet;
    }

    public ArrayList<Entry> getTestEntries() { //simply gets Testing Entry List - Alan Devane (14408498)
        return testEntries;
    }




}