package edu.hawaii.zwoodard.ics435.data;

import java.util.ArrayList;
import java.util.Random;

public class DataGenerator {
    public static double MAX_X = 100D;
    public static double MAX_Y = 100D;
    public static double MIN_X = 0D;
    public static double MIN_Y = 0D;
    private static double NOISE_TO_SIGNAL_RATIO = 1.0/10.0;

    public static Random rand = new Random();

    public static XYLinearDataSet getNoisyLinearData(int numPoints, double margin) {

        //Generate the linearly separable points
        int noisyPoints = (int) (numPoints*NOISE_TO_SIGNAL_RATIO);
        int goodPoints = numPoints-noisyPoints;
        XYLinearDataSet dataSet = getLinearData(goodPoints, margin);

        int i = 0;
        while( i < noisyPoints) {
            //Make the point shifted off the line by a gaussian distribution
            Double x = MIN_X + (rand.nextDouble() * (MAX_X - MIN_X));
            Double lineY = dataSet.separatingLine.yAtX(x);
            Double y_shift = rand.nextGaussian() * margin*2;

            y_shift *= (rand.nextBoolean()) ? -1 : 1;
            Double y = lineY+y_shift;

            if(MAX_X > x && x > MIN_X && MAX_Y > y && y > MIN_Y) {
                Point p = new Point(x, y);

                //Completely random label
                p.label = (byte) (rand.nextBoolean() ? -1 : 1);
                dataSet.addPoint(p);
                i++;
            }
        }

        dataSet.saveToFile("addednoisepoints.dat");
        return dataSet;
    }

    public static XYLinearDataSet getLinearData(int numPoints, double margin) {
        Line separatingLine = getSeparatingLine();
        XYLinearDataSet dataSet = new XYLinearDataSet(new ArrayList<>(), separatingLine);
        int i = 0;
        while (i < numPoints) {
            Point p = new Point(MAX_X*rand.nextDouble(), MAX_Y*rand.nextDouble());
            System.out.println(separatingLine.distanceFrom(p));
            if(separatingLine.distanceFrom(p) > margin) {
                i++;
                Byte label = dataSet.classifyPoint(p);
                p.label = label;
                dataSet.addPoint(p);
            }
        }

        return dataSet;
    }

    private static Line getSeparatingLine() {
        Point lineP1 = new Point(MAX_X*rand.nextDouble(), MAX_Y*rand.nextDouble());
        Point lineP2 = new Point(MAX_X*rand.nextDouble(), MAX_Y*rand.nextDouble());
        Point minX = (lineP1.x > lineP2.x) ? lineP2 : lineP1;
        Point maxX = (lineP1.x > lineP2.x) ? lineP1 : lineP2;

        Double m = (maxX.y - minX.y) / (maxX.x - minX.x);
        Double b = lineP1.y - m*lineP2.x;
        Line separatingLine = new Line(m, b);

        return separatingLine;
    }

}
