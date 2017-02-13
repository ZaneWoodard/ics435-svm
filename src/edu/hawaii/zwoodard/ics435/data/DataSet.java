package edu.hawaii.zwoodard.ics435.data;

import java.util.List;

public abstract class DataSet {
    public List<Point> points;
    public SeparatingHyperplane separatingHyperplane;

    public void addPoint(Point p) {
        points.add(p);
    }

    public abstract DataSet[] split(double... frac);
}
