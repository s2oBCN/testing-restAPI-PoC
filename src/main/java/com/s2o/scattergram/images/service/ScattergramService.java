package com.s2o.scattergram.images.service;


import com.s2o.scattergram.images.domain.ScattergramParams;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.lang.invoke.MethodHandles;

/**
 *
 */
@Service
public class ScattergramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Color defaultBackgroundColor= Color.BLACK;
    private static final  double SIZE = 0.1D;
    private static  final  double DELTA = SIZE / 1.0D;


    /**
     * generate the scattergram file
     * @return
     */
    @Async
    public void generateFile(ScattergramParams scatter){

        try {
            DefaultXYDataset dataset = generateDataSet(scatter);
            JFreeChart chart = ChartFactory.createScatterPlot("", "X", "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
            XYPlot plot = (XYPlot) chart.getPlot();

            plot.setBackgroundPaint(defaultBackgroundColor);
            plot.setDomainGridlinesVisible(false);
            plot.setRangeGridlinesVisible(false);

            //Override the dft renderer in order to get the specific color of each dot from the array passed
            MyRenderer renderer = new MyRenderer(false, true, scatter);
            plot.setRenderer(renderer);

            renderer.setSeriesShape(0,  new java.awt.geom.Ellipse2D.Double(-DELTA, -DELTA, SIZE, SIZE));
            plot.setRenderer(renderer);

            ChartUtilities.saveChartAsPNG(new File(scatter.getPath()), chart, scatter.getImgWidth(), scatter.getImgHeight());

        }catch (Exception e){
            LOGGER.error("Scattegram.generateFile() [ERROR: "+e.getMessage()+"]");

        }
    }

    /**
     * generate the xydataset
     * @return
     */
    private DefaultXYDataset generateDataSet(ScattergramParams scatter){
        DefaultXYDataset dataSet = new DefaultXYDataset();
        dataSet.addSeries("Series", scatter.getAxis());
        return dataSet;
    }

    /***
     * renderer class needed to override the parent renderer in order to be able to set different colors in each of the graph points
     */
    private class MyRenderer extends XYLineAndShapeRenderer {

        ScattergramParams scatter;

        public MyRenderer(boolean lines, boolean shapes, ScattergramParams scatter) {
            super(lines, shapes);
            this.scatter = scatter;
        }

        @Override
        public Paint getItemPaint(int row, int col) {
            return new Color(scatter.getDotcolor()[col]);

        }
    }
}
