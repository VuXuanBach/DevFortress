/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.lnf;

/**
 * The behavior that every panel needs to initiate itself.
 *
 * @author Luan Nguyen Thanh
 */
public interface PanelCreator {
    /**
     * Initialize the components in Panel class
     */
    void init();
    /**
     * Create and manage layout
     */
    void initLayout();
    /**
     * Initialize the listeners
     */
    void initListener();
}
