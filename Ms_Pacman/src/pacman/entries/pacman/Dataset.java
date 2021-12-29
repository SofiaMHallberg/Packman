package pacman.entries.pacman;

import java.io.*;
import java.util.LinkedList;

public class Dataset {
    private BufferedReader reader;
    LinkedList<String[]> trainingDataSet;
    LinkedList<String[]> testDataSet;

    public void readData() throws IOException {
        reader=new BufferedReader(new InputStreamReader(new FileInputStream("myData/trainingData.txt")));
        LinkedList<String[]> dataSet=new LinkedList<>();

        String[] tuple;

        while (reader.ready()) {
            tuple=reader.readLine().split(";");
            dataSet.add(tuple);
        }

        int nbrOfTuples=dataSet.size();

        boolean hasTuplesLeft=true;
        trainingDataSet=new LinkedList<>();
        testDataSet=new LinkedList<>();
        while(hasTuplesLeft) {
            for (int i=0; i<4; i++) {
                trainingDataSet.add(dataSet.remove());
            }

            testDataSet.add(dataSet.remove());

            if(dataSet.size()<5)
                hasTuplesLeft=false;
        }
    }

    public LinkedList<String[]> getTrainingDataSet() {
        return trainingDataSet;
    }

    public LinkedList<String[]> getTestDataSet() {
        return testDataSet;
    }
}
