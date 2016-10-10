import dataRetrieval.InputData;
import network.Perceptron;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sima on 10/4/2016.
 */
public class Main {

    public static void main(String[] args){
        doCrossValid();
    }

    public static void doCrossValid(){
        double[] accuracyArr = new double[10];

        /**
         * Defines if the data set belongs to a logic gate
        */
        boolean gateData = false;
        /**
         * Defines the exact name of the data file to be used
         */
        String dataName = "origData.txt";
//        String dataName = "ORdata.txt";
//        String dataName = "NANDdata.txt";
//        String dataName = "NORdata.txt";
//        String dataName = "XORdata.txt";


        for(int i=0; i<10; i++){
            /**
             * train the neural network
             */
            Perceptron network = new Perceptron(dataName, gateData);
            double[] weights = network.startTraining();
            System.out.print("Optimal weights in validation #"+ i+ " : ");
            System.out.println(Arrays.toString(weights));

            /**
             * test the neural network
             */
            ArrayList<Integer> output = network.testNetwork();
            double accuracy = getAccuracy(InputData.getRealOutput(), output);
            accuracyArr[i] = accuracy;
            System.out.println("Errors in this perceptron: "+ Arrays.toString(network.getErrorArray()));
            System.out.println();
        }
        System.out.println();
        System.out.println("accuracy list: "+ Arrays.toString(accuracyArr));
    }

    /**
     * Measures the accuracy of a trained perceptron
     * @param real The real values in the data set provided
     * @param output The output of the perceptron
     * @return Percecptron accuracy in percentage
     */
    public static double getAccuracy(ArrayList<Integer> real, ArrayList<Integer> output){
        int count = real.size();
        int correct =0;

        for(int i=0; i<real.size(); i++){
            if (real.get(i).equals(output.get(i))) correct++;
        }
        return correct/(1.0 * count);
    }
}
