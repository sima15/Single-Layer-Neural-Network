package dataRetrieval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Sima on 10/4/2016.
 */
public class InputData {

    static BufferedReader br;
    static StringTokenizer st;
    /**
     * Defines if the data belongs to a logic gate
     */
    static  boolean isLogicGate;
    /**
     * The whole data in the data set provided
     */
    static double[][] data;
    /**
     * 80% of the data used for training the perceptron
     */
    static double[][] trainData;
    /**
     * 20% of the data used for testing the perceptron
     */
    static double[][] testData;
    /**
     * The real output data of the data set
     */
    static ArrayList<Integer> realOutput;
    static int numRows;


    /**
     * Reads the provided data into a 2D array of Strings
     * @param dataFileName The name of the file to get data from
     */
    public static void readData(String dataFileName){
        if (dataFileName.equals("origData.txt"))
            data = new double[100][3];
        else {
            data = new double[4][3];
            isLogicGate = true;
        }

        numRows = data.length;
        String line;
        int i=0;
        int j =0;
        try{
            br =new BufferedReader(new FileReader("data/" + dataFileName));
            while (( line = br.readLine()) != null){
                st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    data[i][j] = Double.parseDouble(st.nextToken());
                    j++;
                }
                j =0;
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Creates a random array of integers to create training and testing data
     * @return a random array of integers
     */
    public static ArrayList<Integer> getRandomNums(){
        int eighty = (int) (0.8*numRows);
        int twenty = numRows-eighty;

        ArrayList<Integer> randoms = new ArrayList<>(twenty);

        for(int i=0; i<twenty; i++){
            int val = (int) (Math.random()*(numRows-1));
            while (randoms.contains(val)) val = (int) (Math.random()*(numRows-1));
            randoms.add(val);
        }

        Set<Integer> set = new HashSet<>(twenty);
        set.addAll(randoms);
        ArrayList<Integer> randomVals = new ArrayList<>();
        randomVals.addAll(set);
        return  randomVals;
    }

    /**
     * Creates two different non-overlapping sets of training and testing data
     */
    public static void createTestNTraining(){
        if (isLogicGate){
            testData = new double[4][3];
            for (int i=0; i<numRows; i++)
                testData[i] = getData()[i];
        }
        else {
            int eighty = (int) (0.8 * numRows);
            int twenty = numRows - eighty;
            trainData = new double[eighty][];
            testData = new double[twenty][];
            realOutput = new ArrayList<>(twenty);
            List<double[]>[] subTables = new ArrayList[2];
            /**
             * initialize subTables list
             */
            for (int i = 0; i < 2; i++) {
                subTables[i] = new ArrayList();
            }
            ArrayList<Integer> randomVals = getRandomNums();
            for (int i = 0; i < numRows; i++) {
                if (randomVals.contains(i)) subTables[0].add(data[i]);
                else subTables[1].add(data[i]);
            }
            for (int k = 0; k < twenty; k++) {
                testData[k] = new double[3];
                trainData[k] = new double[3];
            }
            for (int k = twenty; k < eighty; k++) {
                trainData[k] = new double[3];
            }
            testData = subTables[0].toArray(new double[subTables[0].size()][]);
            trainData = subTables[1].toArray(new double[subTables[1].size()][]);
            for (int j = 0; j < subTables[0].size(); j++) {
                testData[j] = subTables[0].get(j);
            }
        }
    }

    /**
     *
     * @return The real ouput values of the provided data
     */
    public static ArrayList<Integer> getRealOutput(){
        int size;
        if (isLogicGate) {
            size = numRows;
            realOutput = new ArrayList<>(size);
        }
        else {
            size = getTestData().length;
            realOutput = new ArrayList<>(size);
        }
        for (int i=0; i<size; i++){
            realOutput.add(Character.getNumericValue(String.valueOf(testData[i][2]).charAt(0)));
        }
        return realOutput;
    }

    /**
     * returns the whole data
     * @return the whole data
     */
    public static double[][] getData(){
        return data;
    }

    /**
     *
     * @return training data only
     */
    public static double[][] getTrainData(){
        return trainData;
    }

    /**
     *
     * @return testing data only
     */
    public static double[][] getTestData(){
        return testData;
    }
}
