package pacman.entries.pacman;

import dataRecording.DataTuple;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Dataset {

    private BufferedReader reader;
    LinkedList<DataTuple> trainingDataSet;
    LinkedList<DataTuple> testDataSet;

    public Dataset() {
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() throws IOException {
        reader=new BufferedReader(new InputStreamReader(new FileInputStream("myData/trainingData.txt")));
        LinkedList<DataTuple> dataSet=new LinkedList<>();

        while (reader.ready()) {
            DataTuple dataTuple=new DataTuple(reader.readLine());
            dataSet.add(dataTuple);
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

    public LinkedList<DataTuple> getTrainingDataSet() {
        return trainingDataSet;
    }
    public LinkedList<DataTuple> getTestDataSet() {
        return testDataSet;
    }
}
