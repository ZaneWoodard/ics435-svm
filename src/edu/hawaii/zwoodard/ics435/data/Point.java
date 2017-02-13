package edu.hawaii.zwoodard.ics435.data;

public class Point {
    public final double x, y;
    public Byte label;

    public Point() {
        this(0d, 0d, (byte) 0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, Byte label) {
        this(x, y);
        this.label = label;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Byte getLabel() {
        return label;
    }

    public void setLabel(Byte label) {
        this.label = label;
    }

    public String toString() {
        return String.format("%d\t%f\t%f", label, x, y);
    }
}
