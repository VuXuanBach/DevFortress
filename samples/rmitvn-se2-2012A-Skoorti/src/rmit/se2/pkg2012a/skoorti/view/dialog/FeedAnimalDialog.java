/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.dialog;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;

/**
 *
 * @author zozo
 */
public class FeedAnimalDialog extends javax.swing.JDialog {
    private Animal animal;
    private Food _currentFood;
    /**
     * Creates new form FeedAnimalDialog
     */
    public FeedAnimalDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.noticePanel.setPreferredSize(new Dimension(240, this.getHeight()));
        if (_currentFood == null) {
            foodDetailPanel.setVisible(false);
        }
        
        final FeedAnimalDialog that = this;
        
        Mediator.subscribe("animal:feed", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                if (o1 instanceof String) {
                    // cant be fed
                    return;
                }
                Food f = (Food)o1;
                // update the animal detail
                renderAnimalDetail(animal);
            }
        
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        foodPanel = new javax.swing.JPanel();
        noticePanel = new javax.swing.JPanel();
        animalDetailPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        animalNameLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        animalDescriptionLabel = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        angerBar = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();
        hungerBar = new javax.swing.JProgressBar();
        foodDetailPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        valueBar = new javax.swing.JProgressBar();
        foodNameLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        foodDescriptionLabel = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        foodCostLabel = new javax.swing.JLabel();
        commandPanel = new javax.swing.JPanel();
        feedButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        foodPanel.setPreferredSize(new java.awt.Dimension(480, 360));
        foodPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        getContentPane().add(foodPanel, java.awt.BorderLayout.CENTER);

        noticePanel.setPreferredSize(new java.awt.Dimension(240, 480));

        jLabel4.setText("Name: ");

        animalNameLabel.setText("jLabel4");

        jLabel5.setText("Description: ");

        animalDescriptionLabel.setPreferredSize(new java.awt.Dimension(220, 100));
        jScrollPane2.setViewportView(animalDescriptionLabel);

        jLabel6.setText("Anger: ");

        jLabel7.setText("Hunger: ");

        jLabel1.setText("Name: ");

        jLabel2.setText("Description: ");

        jLabel3.setText("Value:");

        foodNameLabel.setText("jLabel1");

        foodDescriptionLabel.setPreferredSize(new java.awt.Dimension(220, 100));
        jScrollPane3.setViewportView(foodDescriptionLabel);

        jLabel8.setText("Cost: ");

        foodCostLabel.setText("jLabel9");

        org.jdesktop.layout.GroupLayout foodDetailPanelLayout = new org.jdesktop.layout.GroupLayout(foodDetailPanel);
        foodDetailPanel.setLayout(foodDetailPanelLayout);
        foodDetailPanelLayout.setHorizontalGroup(
            foodDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(foodDetailPanelLayout.createSequentialGroup()
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 228, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 8, Short.MAX_VALUE))
            .add(foodDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(foodDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(foodDetailPanelLayout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(foodNameLabel))
                    .add(valueBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(foodDetailPanelLayout.createSequentialGroup()
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(foodCostLabel)))
                .addContainerGap())
        );
        foodDetailPanelLayout.setVerticalGroup(
            foodDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(foodDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(foodDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(foodNameLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(foodDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8)
                    .add(foodCostLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(valueBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout animalDetailPanelLayout = new org.jdesktop.layout.GroupLayout(animalDetailPanel);
        animalDetailPanel.setLayout(animalDetailPanelLayout);
        animalDetailPanelLayout.setHorizontalGroup(
            animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, animalDetailPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(animalDetailPanelLayout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(animalNameLabel))
                    .add(jLabel5)
                    .add(animalDetailPanelLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(angerBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6)
                            .add(jLabel7)
                            .add(hungerBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(44, 44, 44))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, animalDetailPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(foodDetailPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        animalDetailPanelLayout.setVerticalGroup(
            animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(animalDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(animalNameLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(angerBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(hungerBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(foodDetailPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout noticePanelLayout = new org.jdesktop.layout.GroupLayout(noticePanel);
        noticePanel.setLayout(noticePanelLayout);
        noticePanelLayout.setHorizontalGroup(
            noticePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(noticePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(animalDetailPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        noticePanelLayout.setVerticalGroup(
            noticePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(noticePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(animalDetailPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(noticePanel, java.awt.BorderLayout.LINE_END);

        feedButton.setText("Feed");
        feedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedButtonActionPerformed(evt);
            }
        });
        commandPanel.add(feedButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        commandPanel.add(cancelButton);

        getContentPane().add(commandPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void feedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedButtonActionPerformed
        // TODO add your handling code here:
        if (this._currentFood == null) {
            JOptionPane.showMessageDialog(this, "Please choose a food");
            return;
        }
        this.getAnimal().feed(this._currentFood);
        Mediator.publish("feedAnimalDialog:destroy", this.getAnimal().getName());
    }//GEN-LAST:event_feedButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        if (this.animal.isHungry()) {
            int confirm = JOptionPane.showConfirmDialog(this, "If you do not feed the animal, "
                + "it will be angry and eat people instead", "Are you sure ?", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                Mediator.publish("feedAnimalDialog:destroy", this.getAnimal().getName());
            }
        } else {
            this.setVisible(false);
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FeedAnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FeedAnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FeedAnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FeedAnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FeedAnimalDialog dialog = new FeedAnimalDialog(new javax.swing.JFrame(), false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar angerBar;
    private javax.swing.JTextPane animalDescriptionLabel;
    private javax.swing.JPanel animalDetailPanel;
    private javax.swing.JLabel animalNameLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel commandPanel;
    private javax.swing.JButton feedButton;
    private javax.swing.JLabel foodCostLabel;
    private javax.swing.JTextPane foodDescriptionLabel;
    private javax.swing.JPanel foodDetailPanel;
    private javax.swing.JLabel foodNameLabel;
    private javax.swing.JPanel foodPanel;
    private javax.swing.JProgressBar hungerBar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel noticePanel;
    private javax.swing.JProgressBar valueBar;
    // End of variables declaration//GEN-END:variables

    public void setAnimal(Animal animal) {
        this.animal=animal;
        renderAnimalDetail(animal);
    }
    
    public void renderAnimalDetail(Animal animal) {
        this.animalNameLabel.setText(animal.getName());
        this.animalDescriptionLabel.setEditable(false);
        this.animalDescriptionLabel.setText(animal.getDescription());
        this.angerBar.setValue(animal.getAnger());
        this.hungerBar.setValue(animal.getHunger());
        
        this.noticePanel.revalidate();
        this.noticePanel.repaint();
    }
    
    public Animal getAnimal() {
        return animal;
    }

    public JPanel getFoodPanel() {
        return foodPanel;
    }

    public JPanel getNoticePanel() {
        return noticePanel;
    }
    
    public void setCurrentFood(Food f) {
        this._currentFood = f;
        foodDetailPanel.setVisible(true);
        
        // renders the current food
        this.foodNameLabel.setText(f.getName());
        this.foodDescriptionLabel.setText(f.getDescription());
        this.valueBar.setValue(f.getValue());
        this.foodCostLabel.setText(String.valueOf(f.getCost()));
        
        this.noticePanel.revalidate();
        this.noticePanel.repaint();       
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FeedAnimalDialog other = (FeedAnimalDialog) obj;
        if (this.animal != other.animal && (this.animal == null || !this.animal.equals(other.animal))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.animal != null ? this.animal.hashCode() : 0);
        return hash;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
    
}
