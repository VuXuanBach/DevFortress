/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */

package view.component;

import javax.swing.ImageIcon;

/**
 *
 * @author Luan Nguyen Thanh <tklarryonline@gmail.com>
 */
public class ListEntry {

    private String title;
    private ImageIcon imageIcon;

    public ListEntry(ImageIcon image, String title) {
        this.title = title;
        this.imageIcon = image;
    }

    public String getTitle() {
        return title;
    }

    public ImageIcon getImage() {
        return imageIcon;
    }

    // Override standard toString method to give a useful result
    @Override
    public String toString() {
        return title;
    }
}