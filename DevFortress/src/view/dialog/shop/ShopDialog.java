/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.dialog.shop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.PackageItem;
import model.exception.MoneyException;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import view.component.JTKButton;
import view.component.ListEntry;
import view.lnf.DevStyle;
import view.lnf.PanelCreator;
import view.mediator.ApplicationMediator;
import view.mediator.Colleague;
import view.util.ViewUtilities;

/**
 *
 * @author Luan Nguyen Thanh <tklarryonline@gmail.com>
 */
public class ShopDialog extends JDialog implements PanelCreator, Colleague {

    private static final int NUMBER_OF_TYPES = 4;
    // Types' icons
    private static final ImageIcon BEER_ICON = ViewUtilities.makeIcon(
            "items/Beer_48.png");
    private static final ImageIcon FOOD_ICON = ViewUtilities.makeIcon(
            "items/Food_48.png");
    private static final ImageIcon HARDWARE_ICON = ViewUtilities.makeIcon(
            "items/Computer_48.png");
    // Types' names
    private static final String[] TYPES_NAME = {"Beer", "Food & Bev.", "Hardware", "View all"};
    // Model
    private DevModel model;
    private ApplicationMediator mediator;
    // Swing components
    private CardLayout pnDetailsLayout;
    private JPanel pnTypes, pnDetails;
    private JList shopTypesList;

    /**
     *
     * @param model
     */
    public ShopDialog(DevModel model, ApplicationMediator mediator) {
        this.model = model;
        this.mediator = mediator;

        init();
        initListener();
    }

    @Override
    public void init() {
        // Initializes the frame
        setTitle("Shopping");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Initializes components
        pnDetailsLayout = new CardLayout();

        shopTypesList = new JList(makeShopTypesList());
        shopTypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shopTypesList.setLayoutOrientation(JList.VERTICAL);
        shopTypesList.setVisibleRowCount(4);
        shopTypesList.setCellRenderer(new ShopCellRenderer());
        shopTypesList.getSelectionModel().
                addListSelectionListener(new SharedListSelectionHandler());

        initLayout();
        setResizable(false);
        setPreferredSize(new Dimension(930, 600));
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void initLayout() {
        // Initializes the frame
        setLayout(new BorderLayout());

        pnTypes = makeShopTypePn();
        pnDetails = makeDetailsPn();
        pnDetailsLayout.show(pnDetails, TYPES_NAME[0]);

        add(pnTypes, BorderLayout.WEST);
        add(pnDetails);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
    }

    private JPanel makeShopTypePn() {
        MigLayout layout = new MigLayout("fill, insets 0 10 0 10");
        JPanel panel = new JPanel(layout);

        JLabel label = new JLabel("Shop");
        label.setFont(DevStyle.FORM_TITLE);
        panel.add(label, "north, gapx 10 10, gapy 0 5");

        shopTypesList.setOpaque(false);
        panel.add(shopTypesList, "grow");

        panel.setPreferredSize(new Dimension(230, 600));
//        panel.setOpaque(true);
//        panel.setBackground(Color.BLACK);

        return panel;
    }

    private ListEntry[] makeShopTypesList() {
        ListEntry[] entries = new ListEntry[NUMBER_OF_TYPES];

        entries[0] = new ListEntry(BEER_ICON, TYPES_NAME[0]);
        entries[1] = new ListEntry(FOOD_ICON, TYPES_NAME[1]);
        entries[2] = new ListEntry(HARDWARE_ICON, TYPES_NAME[2]);
        entries[3] = new ListEntry(null, TYPES_NAME[3]);

        return entries;
    }

    private JPanel makeDetailsPn() {
        JPanel panel = new JPanel(pnDetailsLayout);

        panel.add(makeBeerShopPn(), TYPES_NAME[0]);
        panel.add(makeFoodShopPn(), TYPES_NAME[1]);
        panel.add(makeHardwareShopPn(), TYPES_NAME[2]);
        panel.add(makeViewAllShopPn(), TYPES_NAME[3]);

        return panel;
    }

    private JPanel makeBeerShopPn() {
        JPanel panel = new JPanel(new BorderLayout());

        Image img = BEER_ICON.getImage().getScaledInstance(32, 32,
                Image.SCALE_SMOOTH);
        JLabel title = new JLabel("Beer", new ImageIcon(img),
                SwingConstants.LEADING);
        title.setFont(DevStyle.FORM_TITLE);
        panel.add(title, BorderLayout.NORTH);

        MigLayout layout = new MigLayout("wrap 5, gap 22 15");

        JPanel inside = new JPanel(layout);

        for (PackageItem pi : model.getListBeer()) {
            inside.add(makeItemCell(pi));
        }

        JScrollPane scrollPane = new JScrollPane(inside);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPane);

        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        return panel;
    }

    private JPanel makeFoodShopPn() {
        JPanel panel = new JPanel(new BorderLayout());

        Image img = FOOD_ICON.getImage().getScaledInstance(32, 32,
                Image.SCALE_SMOOTH);
        JLabel title = new JLabel("Food & Beverage", new ImageIcon(img),
                SwingConstants.LEADING);
        title.setFont(DevStyle.FORM_TITLE);
        panel.add(title, BorderLayout.NORTH);

        MigLayout layout = new MigLayout("wrap 5, gap 22 15");

        JPanel inside = new JPanel(layout);

        for (PackageItem pi : model.getListFood()) {
            inside.add(makeItemCell(pi));
        }

        JScrollPane scrollPane = new JScrollPane(inside);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPane);

        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        return panel;
    }

    private JPanel makeHardwareShopPn() {
        JPanel panel = new JPanel(new BorderLayout());

        Image img = HARDWARE_ICON.getImage().getScaledInstance(32, 32,
                Image.SCALE_SMOOTH);
        JLabel title = new JLabel("Hardware", new ImageIcon(img),
                SwingConstants.LEADING);
        title.setFont(DevStyle.FORM_TITLE);
        panel.add(title, BorderLayout.NORTH);

        MigLayout layout = new MigLayout("wrap 5, gap 22 15");

        JPanel inside = new JPanel(layout);

        for (PackageItem pi : model.getListComputer()) {
            inside.add(makeItemCell(pi));
        }

        JScrollPane scrollPane = new JScrollPane(inside);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPane);

        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        return panel;
    }

    private JPanel makeViewAllShopPn() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("View all", SwingConstants.LEADING);
        title.setFont(DevStyle.FORM_TITLE);
        panel.add(title, BorderLayout.NORTH);

        MigLayout layout = new MigLayout("debug, wrap 5, gap 20 15");

        JPanel inside = new JPanel(layout);

        // Beer part
        Image beerImg = BEER_ICON.getImage().getScaledInstance(24, 24,
                Image.SCALE_SMOOTH);
        JLabel beerTitle = new JLabel("Beer", new ImageIcon(beerImg),
                SwingConstants.LEADING);
        beerTitle.setFont(DevStyle.FORM_HEADER);
        inside.add(beerTitle, "span, growx");

        for (PackageItem pi : model.getListBeer()) {
            inside.add(makeItemCell(pi));
        }

        // Food part
        Image foodImg = FOOD_ICON.getImage().getScaledInstance(24, 24,
                Image.SCALE_SMOOTH);
        JLabel foodTitle = new JLabel("Food", new ImageIcon(foodImg),
                SwingConstants.LEADING);
        foodTitle.setFont(DevStyle.FORM_HEADER);
        inside.add(foodTitle, "span, growx");

        for (PackageItem pi : model.getListFood()) {
            inside.add(makeItemCell(pi));
        }
        // Hardware part
        Image hardwareImg = HARDWARE_ICON.getImage().getScaledInstance(24, 24,
                Image.SCALE_SMOOTH);
        JLabel hardwareTitle = new JLabel("Hardware", new ImageIcon(hardwareImg),
                SwingConstants.LEADING);
        hardwareTitle.setFont(DevStyle.FORM_HEADER);
        inside.add(hardwareTitle, "span, growx");

        for (PackageItem pi : model.getListComputer()) {
            inside.add(makeItemCell(pi));
        }

        JScrollPane scrollPane = new JScrollPane(inside);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPane);

        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        return panel;
    }

    private JPanel makeItemCell(final PackageItem pi) {
        // Gets values from PackageItem
        String itemTypeName = pi.getItemName();
        int packageQuantity = pi.getQuantity();
        double packagePrice = pi.getPrice();
        
        // Creates panel
        JPanel panel = new JPanel();
        MigLayout layout = new MigLayout("wrap 1, ins 10");
        panel.setLayout(layout);

        // Creates components
        JLabel lblItemIcon = ViewUtilities.makeLabel(packageQuantity + "",
                "items/" + itemTypeName + "_48.png", JLabel.CENTER,
                JLabel.LEADING);
        lblItemIcon.setVerticalAlignment(JLabel.CENTER);
        lblItemIcon.setHorizontalAlignment(JLabel.CENTER);
        lblItemIcon.setMinimumSize(new Dimension(96, 96));
        lblItemIcon.setBorder(BorderFactory.createLineBorder(Color.black));
        lblItemIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblItemIcon.setFont(DevStyle.FORM_HEADER);
        panel.add(lblItemIcon);

        JLabel lblItemName = new JLabel(itemTypeName + " x" + packageQuantity);
        lblItemName.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblItemName, "growx");

        JLabel lblItemPrice = new JLabel("$" + packagePrice);
        lblItemPrice.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblItemPrice, "growx");

        JTKButton btnBuy = new JTKButton("Buy");
        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                buyItem(pi);
            }
        });
        panel.add(btnBuy, "growx");

        return panel;
    }

    private void buyItem(PackageItem pi) {
        try {
            // Maybe we could use this method
            model.buyItem(pi);
        } catch (MoneyException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        }
        send(ViewUtilities.MEDIATOR_BUY_ITEM);
    }

    class SharedListSelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                // Since this is a single-selected List, 
                // minIndex will be the selected index.
                if (lsm.isSelectedIndex(minIndex)) {
                    pnDetailsLayout.show(pnDetails, TYPES_NAME[minIndex]);
                }
            }
        }
    }
}

class ShopCellRenderer extends JLabel implements ListCellRenderer {

    public ShopCellRenderer() {
        setOpaque(true);
        setIconTextGap(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        ListEntry entry = (ListEntry) value;
        setText(entry.getTitle());
        setFont(DevStyle.SMALL_BUTTON_FONT);

        // Resize image
        Image image;
        if (entry.getImage() != null) {
            image = entry.getImage().getImage();
            image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } else {
            image = (Image) new BufferedImage(32, 32,
                    BufferedImage.TYPE_INT_ARGB);
        }
        setIcon(new ImageIcon(image));
        if (isSelected) {
            setBackground(DevStyle.HIGHLIGHT_COLOR);
            setForeground(DevStyle.FORE_COLOR);
        } else {
            setBackground(DevStyle.BACK_COLOR);
            setForeground(DevStyle.FORE_COLOR);
        }
        return this;
    }
}
