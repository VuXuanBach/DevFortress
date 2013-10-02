/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.component;

/**
 *
 * @author BACH
 */
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import view.lnf.DevStyle;

public class BarChart extends JPanel {

    private double percent, time;
    private String title;

    /**
     * Creates a new bar chart.
     */
    public BarChart(final String title, double percent, double time) {
        this.title = title;
        this.percent = percent;
        this.time = time;

        // create the chart
        final JFreeChart chart = createChart(createSampleDataset());
        // add the chart to a panel
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 220));
        add(chartPanel);
    }

    /**
     * Creates a dataset for the demo.
     *
     * @return A dataset.
     */
    private DefaultCategoryDataset createSampleDataset() {
        // create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String series1 = "Progress";
        String series2 = "Time";
        dataset.addValue(percent, series1, series1);
        dataset.addValue(time, series2, series2);
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        // Creates the chart
        JFreeChart chart = ChartFactory.createBarChart(title, // chart title
                "", // domain axis label
                "Percentage (%)", // range axis label
                dataset, // data
                PlotOrientation.HORIZONTAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
                );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.gray);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.gray);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 100);

        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, DevStyle.LIGHTER_MARINE);
        renderer.setSeriesPaint(1, DevStyle.TEAL_ORANGE);

        return chart;
    }

    public void setFocus() {
    }
}
