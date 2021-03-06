/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.view.util.SpriteSheetManager;

/**
 *
 * @author zozo
 */
public class AnimalDialog extends javax.swing.JDialog {

    private Animal _currentAnimal;

    /**
     * Creates new form AnimalDialog
     */
    public AnimalDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        animalDescription.setEditable(false);
        animalDetailPanel.setVisible(false);
        buyButton.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        animalListPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        animalDetailPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        animalNameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        animalDescription = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        animalCostLabel = new javax.swing.JLabel();
        thumbnail = new javax.swing.JButton();
        commandPanel = new javax.swing.JPanel();
        buyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        animalListPanel.setAutoscrolls(true);
        animalListPanel.setPreferredSize(new java.awt.Dimension(480, 480));
        animalListPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(480, 480));
        jScrollPane2.setViewportView(jPanel1);

        animalListPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(animalListPanel, java.awt.BorderLayout.CENTER);

        animalDetailPanel.setPreferredSize(new java.awt.Dimension(240, 480));

        jLabel1.setText("Name: ");

        animalNameLabel.setText("jLabel2");

        jLabel2.setText("Description: ");

        jScrollPane1.setViewportView(animalDescription);

        jLabel3.setText("Cost: ");

        animalCostLabel.setText("jLabel4");

        thumbnail.setContentAreaFilled(false);
        thumbnail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thumbnailActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout animalDetailPanelLayout = new org.jdesktop.layout.GroupLayout(animalDetailPanel);
        animalDetailPanel.setLayout(animalDetailPanelLayout);
        animalDetailPanelLayout.setHorizontalGroup(
            animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(animalDetailPanelLayout.createSequentialGroup()
                .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(animalDetailPanelLayout.createSequentialGroup()
                        .add(16, 16, 16)
                        .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(animalNameLabel)
                            .add(jLabel1)
                            .add(animalDetailPanelLayout.createSequentialGroup()
                                .add(jLabel3)
                                .add(18, 18, 18)
                                .add(animalCostLabel)))
                        .add(0, 118, Short.MAX_VALUE))
                    .add(animalDetailPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(animalDetailPanelLayout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(thumbnail)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(jScrollPane1))))
                .addContainerGap())
        );
        animalDetailPanelLayout.setVerticalGroup(
            animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(animalDetailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(animalNameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(animalDetailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(animalCostLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 151, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(thumbnail)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        getContentPane().add(animalDetailPanel, java.awt.BorderLayout.EAST);

        commandPanel.setPreferredSize(new java.awt.Dimension(722, 60));

        buyButton.setText("Buy");
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout commandPanelLayout = new org.jdesktop.layout.GroupLayout(commandPanel);
        commandPanel.setLayout(commandPanelLayout);
        commandPanelLayout.setHorizontalGroup(
            commandPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(commandPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(buyButton)
                .addContainerGap(705, Short.MAX_VALUE))
        );
        commandPanelLayout.setVerticalGroup(
            commandPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(commandPanelLayout.createSequentialGroup()
                .add(16, 16, 16)
                .add(buyButton)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(commandPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
        // TODO add your handling code here:
        if (_currentAnimal != null) {
            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_buyButtonActionPerformed

    private void thumbnailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thumbnailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thumbnailActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(AnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimalDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                AnimalDialog dialog = new AnimalDialog(new javax.swing.JFrame(), true);
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

    private void setCurrentAnimal(Animal animal) {
        _currentAnimal = animal;
        if (animal == null) {
            animalDetailPanel.setVisible(false);
            buyButton.setEnabled(false);
        } else {
            renderAnimalDetail(animal);
        }
    }

    public Animal getCurrentAnimal() {
        return _currentAnimal;
    }

    private void renderAnimalDetail(Animal animal) {
        animalNameLabel.setText(animal.getName());
        animalDescription.setText(animal.getDescription());
        animalCostLabel.setText(String.valueOf(animal.getAnimalCost()));
        animalDetailPanel.setVisible(true);
        buyButton.setEnabled(true);
    }

    public JPanel getAnimalListPanel() {
        return animalListPanel;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel animalCostLabel;
    private javax.swing.JTextPane animalDescription;
    private javax.swing.JPanel animalDetailPanel;
    private javax.swing.JPanel animalListPanel;
    private javax.swing.JLabel animalNameLabel;
    private javax.swing.JButton buyButton;
    private javax.swing.JPanel commandPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton thumbnail;
    // End of variables declaration//GEN-END:variables

    public void setAnimals(List<Animal> animals) throws IOException {
        for (final Animal a : animals) {
            final BufferedImage animalImage = SpriteSheetManager.getInstance().getDownSprites("animal", a.getThumbnail()).get(0);
            final JButton btn = new JButton();
            btn.setIcon(new ImageIcon(animalImage));
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    setCurrentAnimal(a);
                    thumbnail.setIcon(new ImageIcon(animalImage));
                    thumbnail.repaint();
                }
            });
            //animalListPanel.add(btn);
            this.jPanel1.add(btn);
        }
    }
}
