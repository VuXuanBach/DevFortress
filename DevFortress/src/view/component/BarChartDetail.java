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
import java.util.ArrayList;
import javax.swing.JPanel;
import model.ProjectRequirement;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import view.lnf.DevStyle;

public class BarChartDetail extends JPanel {

    private String title;
    private ArrayList<ProjectRequirement> reqs;

    /**
     * Creates a new bar chart.
     */
    public BarChartDetail(final String title, ArrayList<ProjectRequirement> reqs) {
        this.title = title;
        this.reqs = reqs;

        // create the chart
        final JFreeChart chart = createChart(createSampleDataset());
        // add the chart to a panel
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));
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
        
        for(ProjectRequirement pr : reqs) {
            String serie = pr.getRequiredSkill().getName();
            dataset.addValue(pr.getPercentFunction(), serie, serie);
        }
        
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
