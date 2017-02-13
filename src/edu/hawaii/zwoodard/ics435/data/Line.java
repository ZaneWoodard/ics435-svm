package edu.hawaii.zwoodard.ics435.data;

public class Line implements SeparatingHyperplane {
    public final Double m;
    public final Double b;
    public final Point p1, p2;

    public Line(Double m, Double b) {
        this.m = m;
        this.b = b;

        this.p1 = new Point(DataGenerator.MIN_X, b);
        this.p2 = new Point(DataGenerator.MAX_X, m*DataGenerator.MAX_X + b);
    }

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;

        this.m = (p2.y - p1.y) / (p2.x - p1.x);
        this.b = p1.y - m*p1.x;
    }

    public Double yAtX(Double x) {
        return x*m + b;
    }

    public Double distanceFrom(Point p) {
        //https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
        //Perpendicular distance
        return Math.abs(m*p.x - p.y + b) / Math.sqrt(Math.pow(m, 2) + 1);
    }

    public String toString() {
        return String.format("y=%fx+%f", m, b);
    }
}
