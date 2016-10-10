package network;

/**
 * Created by Sima on 10/4/2016.
 */
public class Neuron {
    /**
     * Array of weights used in the perceptron
     */
    double[] weights = null;
    /**
     * Current output of the perceptron
     */
    int output = 0;
    /**
     * Real output in the data set
     */
    int realValue = 0;
    /**
     * Output choosing threshold
     */
    double threshold = 0.5;
    /**
     * Error threshold
     */
    double errThresh = 0.001;
    double learningRate = 0.001;
    double totalError =0;
    double error = 0;
    /**
     * Number of data lines fed into the perceptron so far
     */
    int dataCount =0;

    public Neuron( double[] w){
        weights = w;
    }

    /**
     * Trains the perceptron with the given input
     * @param x Input values
     * @param w Current weights
     * @param value Correct output value in the data set
     * @return  New weights
     */
    public double[] train(double[] x, double[] w, int value){
        realValue = value;
        int output = calOutput(x, w);
        this.output = output;
        error = calError(realValue,output);
        dataCount++;
        if ( error < errThresh) return w;
        else {
            double[] weight = updateWeights(x, w);
            return weight;
        }
    }

    /**
     * Calculates the h (sigma) function of the inputs
     * @param x Input values
     * @param w Current weights
     * @return result of the h function
     */
    public double h(double[] x, double[] w){
        double sum =0;
        for (int i=0; i<x.length; i++) {
            sum += x[i]*w[i+1];
        }
        return sum+ w[0];
    }

    /**
     * Calculates the sigmoid function
     * @param in the result of the sigma function
     * @return
     */
    public int g(double in){
        double result = 1/(1+ Math.exp(-1*in));
        if (result> threshold) return 1;
        else return 0;
    }

    /**
     *
     * @param x inputs
     * @param w weights
     * @return output of the perceptron
     */
    public int calOutput(double[]x, double[] w){
        return g(h(x, w));
    }

    /**
     *
     * @param real Correct value y the data set
     * @param out  Output value by perceptron
     * @return error
     */
    public double calError(int real, int out){
        int error = (real - out)*(real - out);
        return error;
    }

    /**
     *
     * @param in result of the sigma function
     * @return  Derivative of the g function
     */
    public double gPrime(double in){
        return 1/(1+ Math.exp(-1*in))* (1-(1/(1+ Math.exp(-1*in))));
    }


    /**
     *
     * @param x inputs
     * @param weight weights
     * @return New values for the weights
     */
    public double[] updateWeights(double[] x, double[] weight){
        /**
         * new updated weights
         */
        double[] w = new double[weight.length];
        double delta =0;
        double multConst = learningRate * (realValue - output)* gPrime(h(x, weight));

        for (int i=1; i<weights.length; i++){
            delta = multConst * x[i-1];
            w[i] = weight[i] + delta;
        }
        w[0] = weight[0] + learningRate * (realValue - output);
        return w;
    }

    /**
     *
     * @return Average error so far
     */
    public double calTotError(){
        totalError += (realValue - output) * (realValue - output);
        return totalError/dataCount;
    }

}
