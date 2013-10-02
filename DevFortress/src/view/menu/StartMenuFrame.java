/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menu;

import javax.swing.JFrame;
import model.facade.DevModel;
import view.mediator.ApplicationMediator;
import view.mediator.Colleague;
import view.util.ViewUtilities;

/**
 *
 * @author BACH
 */
public class StartMenuFrame extends JFrame implements Colleague {

    private DevModel model;
    private ApplicationMediator mediators;

    public StartMenuFrame(String title, DevModel model, ApplicationMediator mediators) {
        super(title);
        this.model = model;
        this.mediators = mediators;
    }

    @Override
    public void send(String message) {
    }

    @Override
    public void receive(String message) {
        if(message.equals(ViewUtilities.MEDIATOR_START_GAME)) {
            dispose();
        }
    }
}
