/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package model;

public class PackageItem {

    private String itemName;
    private double packagePrice;
    private int packageQuantity;

    public PackageItem(String itemName, int packageQuantity, double packagePrice) {
        this.itemName = itemName;
        this.packagePrice = packagePrice;
        this.packageQuantity = packageQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return packagePrice;
    }

    public void setPrice(double price) {
        this.packagePrice = price;
    }

    public int getQuantity() {
        return packageQuantity;
    }

    public void setQuantity(int quantity) {
        this.packageQuantity = quantity;
    }
}
