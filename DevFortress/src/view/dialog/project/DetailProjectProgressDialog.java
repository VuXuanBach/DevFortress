/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dialog.project;

import javax.swing.JDialog;
import model.Project;
import model.facade.DevModel;
import org.jfree.chart.ChartPanel;
import view.component.BarChartDetail;
import view.mediator.Mediator;

/**
 *
 * @author BACH
 */
public class DetailProjectProgressDialog extends JDialog {

    private DevModel model;
    private Mediator mediator;
    private Project currProj;

    public DetailProjectProgressDialog(DevModel model, Mediator mediator, Project project) {
        this.model = model;
        this.mediator = mediator;
        currProj = project;
        
        BarChartDetail barChart = new BarChartDetail("", currProj.getRequirements());
        
        add(barChart);
        
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
