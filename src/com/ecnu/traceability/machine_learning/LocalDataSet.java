package com.ecnu.traceability.machine_learning;

public class LocalDataSet {
    static double [] localdata = {-2.37,8.85,-0.76,-1.12,9.28,-0.93,2.14,9.47,-1.61,7.25,
            10.15,-0.76,3.98,4.86,0.19,-1.54,2.22,0.61,-3.6,1.61,
            0.34,-4.71,6.89,0.27,-11.88,19.5,2.91,-5.13,15.7,-1.73,
            5.79,4.14,-0.57,8.12,8.28,-1.08,3.06,19.04,-4.48,1.23,
            5.22,-0.34,4.06,6.36,3.06,-1.73,3.11,-0.19,-2.76,1.65,
            -2.11,-7.86,8.31,-4.6,-8.89,14.14,-1.38,-5.67,12.83,1.27,
            4.25,6.28,15.36,-10.88,19.7,-4.48,0.3,13.21,1.27,6.51,
            3.41,-1.08,-2.49,-1.65,-2.49,-2.49,-1.65,-2.49,-3.79,3.83,
            -4.37,-5.86,18.89,-3.34,-5.94,16.21,1.65,-2.49,5.28,-2.64,
            -4.18,16.28,-8.39,-0.5,18.31,-2.96,1.92,8.01,-1.88,2.6,
            2.07,3.11,-2.15,1.04,-1.08,-3.41,1.57,-1.99,-5.6,7.12,
            -3.15,-6.78,17.27,-1.12,-3.68,3.91,15.62,-0.08,2.45,0.61,
            0.08,6.24,-5.37,-0.69,19.27,-7.44,5.13,4.4,0.08,8.05,
            3.99,-0.23,-0.5,2.11,-0.08,-2.56,1.88,-1.54,-7.12,11.8,
            -5.09,-9.66,18.89,-3.38,-5.9,14.33,0.76,-0.46,3.95,-2.56,
            -1.08,10.34,-5.52,-1.69,19.72,-9.11,1.61,13.38,0.65,13.78,
            3.91,2.49,-1.12,2.26,3.3,-0.95,2.49,-1.08,-1.61,2.6,
            -1.57,-7.01,19.5,-4.02,-5.98,9.28,1.12,-10.38,-2.91,17.27,
            -4.18,6.09,-2.83,-6.44,18.58,-8.77,-1.57,7.27,-0.5,-2.56,
            11.14,-1.12,-1.84,11.35,-2.07,0.23,6.05,-0.8,1.31,6.47,
            -1.38,3.26,5.24,-1.69,5.6,5.56,-1.57,0.38,19.27,-3.06,
            -7.21,17.08,5.33,6.28,7.63,6.97,2.49,5.67,-0.61,-0.42,
            4.48,-1.54,0.04,4.25,-0.72,0.76,9.81,-0.57,3.53,12.53,
            -1.69,1.57,16.36,-1.42,-1.69,16.4,-0.08,-2.72,6.05,0.5,
            -2.26,7.82,-1.46,-1.57,5.71,-0.27,-1.54,6.44,-0.84,-0.04,
            9.62,-1.88,1.95,10.5,-2.15,2.22,9,-0.99,4.14,8.77,
            -0.5,7.35,6.21,0.53,-0.27,4.63,7.78,0.34,4.37,-2.64
    };

    static final String [] statues = {
            "Jogging", "Walking", "Upstairs", "Downstairs", "Sitting", "Standing"
    };

    static int label2index(String label) {
        int index;
        switch (label) {
            case "Jogging":
                index = 0;
                break;
            case "Walking":
                index = 1;
                break;
            case "Upstairs":
                index = 2;
                break;
            case "Downstairs":
                index = 3;
                break;
            case "Sitting":
                index = 4;
                break;
            case "Standing":
                index = 5;
                break;
            default:
                index = -1;
        }
        return index;
    }
}
