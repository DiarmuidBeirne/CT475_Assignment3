
public class Entry {  //class to store data for one Entry - Diarmuid Beirne 15331436

    public double[] properties;
    public String type;

    public Entry(double[] properties, String type) { //Each Entry stores an array of properties and a type - DB
        this.properties = properties;
        this.type = type;
    }

    //Returns the array of Properties - DB
    public double[] getProperties() {
        return properties;
    }

    //Returns the type of the Entry - DB
    public String getType() {
        return type;
    }


    //To String Method - DB
    public String toString() {
        String toReturn = "Type: " + type + "\t";
        for (int i = 0; i < properties.length; i++) {
            toReturn += properties[i];
            toReturn += " ";
        }
        return toReturn;
    }
}