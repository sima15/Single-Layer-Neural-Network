package network;

import dataRetrieval.InputData;

import java.util.ArrayList;


/**
 * Created by Sima on 10/4/2016.
 */
public class Perceptron {

    /**
     * Defines if the data belongs to a logic gate
     */
    boolean isLogicGateData;
    String dataName;
    double[] weights;
    Neuron neuron;
    double[] errorArray = new double[1000];

    public Perceptron(String dataName, boolean isLogicGateData){
        this.dataName = dataName;
        this.isLogicGateData = isLogicGateData;
    }

    /**
     * Starts the training phase of the perceptron
     * @return Optimal weights found by the program
     */
    public double[] startTraining(){
        InputData.readData(dataName);
        double[][] trainingData;
        InputData.createTestNTraining();

        if (!isLogicGateData)
            trainingData = InputData.getTrainData();
        else
            trainingData = InputData.getData();

        double[] optimalWeights = new double[3];
        int realValue ;
        double[] x = new double[2];
        weights = new double[]{Math.random()/2.5-0.2, Math.random()/2.5-0.2, Math.random()/2.5-0.2};
        neuron = new Neuron(weights) ;

        for(int j=0; j<1000; j++) {
            for (int i = 0; i < trainingData.length; i++) {
                x[0] = trainingData[i][0];
                x[1] = trainingData[i][1];
                realValue = (int) trainingData[i][2];
                weights = neuron.train(x, weights, realValue);
                double error = neuron.calTotError();
                if (i == trainingData.length-1) errorArray[j] = error;
            }
            optimalWeights = weights;
        }
        return optimalWeights;
    }


    /**
     * Tests the created perceptron
     * @return A list of outputs for every line of the input testing data
     */
    public ArrayList<Integer> testNetwork(){
        double[][] testData;
        if (!isLogicGateData)
            testData = InputData.getTestData();
        else testData = InputData.getData();

        ArrayList<Integer> outputList = new ArrayList<>(testData.length);
        double[] x = new double[2];
        for (int i=0; i<testData.length; i++){
            x[0] = testData[i][0];
            x[1] = testData[i][1];
            int output = neuron.calOutput(x, weights);
            outputList.add(output);
        }
        return outputList;
    }


    /**
     *
     * @return An array of average errors
     */
    public double[] getErrorArray(){
        return errorArray;
    }
}
