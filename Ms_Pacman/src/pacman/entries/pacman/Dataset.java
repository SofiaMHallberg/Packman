package pacman.entries.pacman;

import java.io.*;
import java.util.LinkedList;

public class Dataset {
    private BufferedReader reader;
    LinkedList<Tuple> trainingDataSet;
    LinkedList<Tuple> testDataSet;

    public void readData() throws IOException {
        reader=new BufferedReader(new InputStreamReader(new FileInputStream("myData/trainingData.txt")));
        LinkedList<Tuple> dataSet=new LinkedList<>();

        while (reader.ready()) {
            Tuple tuple=new Tuple(reader.readLine().split(";"));
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

    public LinkedList<Tuple> getTrainingDataSet() {
        return trainingDataSet;
    }

    public LinkedList<Tuple> getTestDataSet() {
        return testDataSet;
    }
}
