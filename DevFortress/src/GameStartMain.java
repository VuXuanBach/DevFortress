
import java.awt.Dimension;
import javax.swing.JFrame;
import model.facade.DevModel;
import view.lnf.DevStyle;
import view.mediator.ApplicationMediator;
import view.menu.StartMenuFrame;
import view.menu.StartMenuPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BACH
 */
public class GameStartMain {
    // Constants

    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;

    public static void main(String[] args) {
        DevStyle.setLookAndFeel(true);
        
        DevModel model = DevModel.getInstance();
        ApplicationMediator mediator = new ApplicationMediator();
        
        StartMenuFrame frame = new StartMenuFrame("", model, mediator);
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
        
        frame.add(new StartMenuPanel(model, mediator));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        mediator.addColleague(frame);
    }
}
