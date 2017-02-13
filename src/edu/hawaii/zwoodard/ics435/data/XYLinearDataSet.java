package edu.hawaii.zwoodard.ics435.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;


public class XYLinearDataSet extends DataSet {
    public Line separatingLine;
    public XYLinearDataSet(List<Point> points, Line separatingLine) {
        super.points = points;
        super.separatingHyperplane = separatingLine;
        this.separatingLine = separatingLine;
    }

    public XYLinearDataSet(List<Point> points) {
        this(points, null);
    }

    public XYLinearDataSet(Point[] points, Line separatingLine) {
        this(Arrays.asList(points), separatingLine);
    }

    public Byte classifyPoint(Point p) {
        return XYLinearDataSet.classifyPoint(p, this.separatingLine);
    }

    public static Byte classifyPoint(Point p, Line l) {
        Double lineY = p.x* l.m + l.b;
        return (byte) ((p.y > lineY) ? 1 : -1);
    }

    @Override
    public XYLinearDataSet[] split(double... fractions) {
        final double DELTA = 0.005;
        final double sum = DoubleStream.of(fractions).sum();
        if(Math.abs(1 - sum) > DELTA) {
            throw new IllegalArgumentException("Fractions must sum to 1.0");
        }

        XYLinearDataSet[] sets = new XYLinearDataSet[fractions.length];
        int[] counts = new int[fractions.length];
        int parentLength = super.points.size();

        int baseIndex = 0;
        for(int i = 0; i < fractions.length; i++) {
            if(i==fractions.length-1) {
                counts[i] = parentLength - baseIndex;
            } else {
                counts[i] = (int) (fractions[i] * parentLength);
            }
            XYLinearDataSet dataSet = new XYLinearDataSet(super.points.subList(baseIndex, baseIndex+counts[i]), this.separatingLine);
            baseIndex += counts[i];
            sets[i] = dataSet;
        }

        return sets;
    }

    public void saveToFile(String filename) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.separatingLine.toString()).append('\n');
            for(Point p : this.points) {
                sb.append(p.toString()).append('\n');
            }
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
