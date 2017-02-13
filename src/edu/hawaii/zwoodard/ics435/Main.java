package edu.hawaii.zwoodard.ics435;

import edu.hawaii.zwoodard.ics435.data.DataGenerator;
import edu.hawaii.zwoodard.ics435.data.Point;
import edu.hawaii.zwoodard.ics435.data.XYLinearDataSet;
import libsvm.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int FEATURES_LENGTH = 2;
    private static final int NUM_POINTS = 10000;

    public static void main(String[] args) throws IOException {

        XYLinearDataSet data = DataGenerator.getNoisyLinearData(NUM_POINTS, 3.0);
        GUIHandler guiHandler = new GUIHandler("Testing", data, true);
        guiHandler.pack();
        guiHandler.setVisible(true);
//
/*
        List<Point> points = readPointsFromFile("ex7Data/twofeature.txt");
        XYLinearDataSet trainingSet = new XYLinearDataSet(points, null);
        GUIHandler guiHandler = new GUIHandler("Testing", trainingSet, true);
        guiHandler.pack();
        guiHandler.setVisible(true);
        svm_problem problem = new svm_problem();
        problem.l = trainingSet.points.size();
        double classifiers[] = new double[trainingSet.points.size()];
        svm_node data[][] = pointsToSvmNodes(trainingSet.points);

        for(int i = 0; i < trainingSet.points.size(); i++) {
            classifiers[i] = trainingSet.points.get(i).label;
        }

        problem.y = classifiers;
        problem.x = data;

        svm_parameter parameters = buildSVMParameters();

        svm_model model = svm.svm_train(problem, parameters);

        double b = -model.rho[0];
        double[][] svs = new double[model.SV.length][2];
        for(int i = 0; i < svs.length; i++) {
            svm_node[] svmNode = model.SV[i];
            svs[i][0] = svmNode[0].value;
            svs[i][1] = svmNode[1].value;
        }

        double[] w = Matrix.multiply(Matrix.transpose(svs), model.sv_coef[0]);
        guiHandler.addSVMLine(w, b);*/
        System.out.println("Done");
//        double[] labels = new double[problem.x.length];
//        svm.svm_cross_validation(problem, parameters, 10, labels);
//        System.out.println(Arrays.toString(labels));


//        svm_node[][] testNodes = pointsToSvmNodes(testSet.points);
//        int correct = 0;
//        for(int i = 0; i < testSet.points.size(); i++) {
//            double result = svm.svm_predict(model, testNodes[i]);
//            if(result==testSet.points.get(i).label) correct++;
//            System.out.println(testSet.points.get(i).label + "\t" + result);
//        }
//        System.out.println(String.format("Accuracy: %d/%d (%f%%)", correct, testSet.points.size(), correct / (double) testSet.points.size() * 100));
//        System.out.println(Arrays.deepToString(model.sv_coef));
//        System.out.println("Done");

    }

    private static svm_node[][] pointsToSvmNodes(List<Point> points) {
        svm_node[][] nodes = new svm_node[points.size()][2];
        for(int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            nodes[i][0] = new svm_node();
            nodes[i][0].value = p.x;
            nodes[i][0].index = 1;
            nodes[i][1] = new svm_node();
            nodes[i][1].value = p.y;
            nodes[i][1].index = 2;
        }

        return nodes;
    }

    private static List<Point> readPointsFromFile(String filename) {
        List<Point> points = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while(line!=null) {
                String[] parts = line.split(" ");
                Point p = new Point(
                        Double.parseDouble(parts[1].split(":")[1]),
                        Double.parseDouble(parts[2].split(":")[1]),
                        Byte.parseByte(parts[0])
                );
                points.add(p);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return points;
    }

    private static svm_parameter buildSVMParameters() {
        svm_parameter parameters = new svm_parameter();
        parameters.svm_type = svm_parameter.C_SVC;
        parameters.kernel_type = svm_parameter.LINEAR;
        parameters.degree = 3;
        parameters.gamma = 0;
        parameters.coef0 = 0;
        parameters.nu = 0.5;
        parameters.cache_size = 40;
        parameters.C = 1;
        parameters.eps = 1e-3;
        parameters.p = 0.1;
        parameters.shrinking = 1;
        parameters.probability = 0;
        parameters.nr_weight = 0;
        parameters.weight_label = new int[0];
        parameters.weight = new double[0];

        return parameters;
    }
}
