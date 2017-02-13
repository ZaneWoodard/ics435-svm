package edu.hawaii.zwoodard.ics435;

import edu.hawaii.zwoodard.ics435.data.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.List;

public class GUIHandler extends JFrame {
    private final JFreeChart chart;

    private double MIN_X, MAX_X, MIN_Y, MAX_Y;

    public GUIHandler(String plotTitle, XYLinearDataSet dataSet) {
        this(plotTitle, dataSet, false);
    }

    public GUIHandler(String plotTitle, XYLinearDataSet dataSet, boolean autoScale) {
        XYDataset xy = addInitialPoints(dataSet.points);
        this.chart = ChartFactory.createScatterPlot(plotTitle, "X", "Y", xy);
        if(autoScale) {
            double[] domainRange = calculateRangeAndDomain(dataSet);
            MIN_X = domainRange[0];
            MAX_X = domainRange[1];
            MIN_Y = domainRange[2];
            MAX_Y = domainRange[3];
        } else {
            MIN_X = DataGenerator.MIN_X;
            MAX_X = DataGenerator.MAX_X;
            MIN_Y = DataGenerator.MIN_Y;
            MAX_Y = DataGenerator.MAX_Y;
        }
        this.chart.getXYPlot().getDomainAxis().setRange(MIN_X, MAX_X);
        this.chart.getXYPlot().getRangeAxis().setRange(MIN_Y, MAX_Y);

        if(dataSet.separatingLine!=null) {
            addLine(dataSet.separatingLine, "Real Line");
        }
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500,500));
        setContentPane(chartPanel);
    }

    private XYDataset addInitialPoints(List<Point> points) {

        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries red = new XYSeries("-1");
        XYSeries blue = new XYSeries("1");
        XYSeries learnedRed = new XYSeries("Learned -1");
        XYSeries learnedBlue = new XYSeries("Learned 1");

        for(Point p : points) {
            if (p.label == -1) {
                red.add(p.x, p.y);
            } else if (p.label == 1) {
                blue.add(p.x, p.y);
            } else {
                throw new InputMismatchException("Unrecognized label: " + p.label);
            }
        }

        result.addSeries(red);
        result.addSeries(blue);
        result.addSeries(learnedRed);
        result.addSeries(learnedBlue);

        return result;
    }

    protected void addLine(Line line, String name) {

        XYItemRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);

        XYSeriesCollection lineDataSet = (XYSeriesCollection) this.chart.getXYPlot().getDataset(1);
        if(lineDataSet==null) {
            lineDataSet = new XYSeriesCollection();
        }

        XYSeries series;
        try {
            series = lineDataSet.getSeries(name);
            series.clear();
            series.add(line.p1.x, line.p1.y);
            series.add(line.p2.x, line.p2.y);
        } catch(UnknownKeyException e) {
            series = new XYSeries(name);
            series.add(line.p1.x, line.p1.y);
            series.add(line.p2.x, line.p2.y);
            lineDataSet.addSeries(series);
        }


        this.chart.getXYPlot().setRenderer(1, lineRenderer);
        this.chart.getXYPlot().setDataset(1, lineDataSet);
    }

    public void addSVMLine(double[] w, double b) {
            double x1 = MIN_X, x2 = MAX_X, y1, y2;

            y1 = (-b - (w[0]*x1)) / w[1];
            y2 = (-b - (w[0]*x2)) / w[1];

            Point p1 = new Point(x1, y1);
            Point p2 = new Point(x2, y2);
            Line l = new Line(p1, p2);
            addLine(l, "Decision Boundary");

    }

    private double[] calculateRangeAndDomain(XYLinearDataSet dataSet) {

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for(Point p : dataSet.points) {
            if(p.x > maxX) {
                maxX = p.x;
            } else if(p.x < minX) {
                minX = p.x;
            }
            if(p.y > maxY) {
                maxY = p.y;
            } else if(p.y < minY) {
                minY = p.y;
            }
        }
        minX = Math.floor(minX);
        minY = Math.floor(minY);
        maxX = Math.ceil(maxX);
        maxY = Math.ceil(maxY);

        return new double[] { minX, maxX, minY, maxY};

    }

}
