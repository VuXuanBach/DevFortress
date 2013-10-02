package org.map.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import org.app.map.ZooBuildItem;
import org.app.map.ZooMap;
import org.app.map.zooitem.*;
import org.app.map.zooitem.animal.Animal;
import org.app.map.zooitem.animal.AnimalFly1;
import org.app.map.zooitem.animal.AnimalSwim1;
import org.app.map.zooitem.animal.AnimalWalk1;
import org.map.exception.BlockedException;
import org.map.exception.OutOfBoundException;

/**
 *
 * @author dell
 */
public class MouseCanvasListener implements MouseListener {

    private ZooMap map;

    public MouseCanvasListener(ZooMap pMap) {
        map = pMap;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x, y;
        x = (e.getX() - (e.getX() % ZooTile.TILE_SIZE)) / ZooTile.TILE_SIZE;
        y = (e.getY() - (e.getY() % ZooTile.TILE_SIZE)) / ZooTile.TILE_SIZE;
        if (SwingUtilities.isRightMouseButton(e)) {
            map.removeItem(new ZooBuilding(x, y));
        } else {
            try {
                ZooBuildItem item = map.getItemAt(x, y);
                if (item == null) {
                    item = map.getTileAt(x, y);
                    if (item.getBuildState() == ZooBuildItem.BLOCK) {
                        return;
                    }

                    //Add Acay13
                    switch (map.getSelectedItem()) {
                        case 0:
                            map.addItem(new ZooBuilding(x, y));
                            break;
                        case 1:
                            map.addItem(new ZooBuilding(x, y));
                            break;
                        case 2:
                            //System.out.println("X = " + x + " Y = " + y);
                            ZooItem zooCageLand = new ZooCageLand(x, y);
                            map.addItem(zooCageLand);

                            break;
                        case 3:
                            ZooItem zooCageWater = new ZooCageWater(x, y);
                            map.addItem(zooCageWater);
                            break;
                        case 4:
                            ZooItem zooCageAir = new ZooCageAir(x, y);
                            map.addItem(zooCageAir);
                            break;
                        case 5:
                            ZooItem landCage = map.getItemLandCageAt(x, y);
                            if (landCage != null) {
                                ZooItem walkAnimal11 = new AnimalWalk1(x, y, 1);
                                ((ZooCageLand) landCage).addAnimal((Animal) walkAnimal11);
                                map.addItem(walkAnimal11);
//                                Thread t1 = new Thread((Animal) walkAnimal11);
//                                t1.run();
                            }
                            break;
                        case 6:
                            landCage = map.getItemLandCageAt(x, y);
                            if (landCage != null) {
                                ZooItem walkAnimal12 = new AnimalWalk1(x, y, 2);
                                ((ZooCageLand) landCage).addAnimal((Animal) walkAnimal12);
                                map.addItem(walkAnimal12);
                            }
                            break;
                        case 7:
                            landCage = map.getItemLandCageAt(x, y);
                            if (landCage != null) {
                                ZooItem walkAnimal13 = new AnimalWalk1(x, y, 3);
                                ((ZooCageLand) landCage).addAnimal((Animal) walkAnimal13);
                                map.addItem(walkAnimal13);
                            }
                            break;
                        case 8:
                            ZooItem waterCage = map.getItemWaterCageAt(x, y);
                            if (waterCage != null) {
                                ZooItem swimAnimal11 = new AnimalSwim1(x, y, 1);
                                ((ZooCageWater) waterCage).addAnimal((Animal) swimAnimal11);
                                map.addItem(swimAnimal11);
//                                Thread t4 = new Thread((Animal) swimAnimal11);
//                                t4.run();
                            }
                            break;
                        case 9:
                            waterCage = map.getItemWaterCageAt(x, y);
                            if (waterCage != null) {
                                ZooItem swimAnimal12 = new AnimalSwim1(x, y, 2);
                                ((ZooCageWater) waterCage).addAnimal((Animal) swimAnimal12);
                                map.addItem(swimAnimal12);
                            }
                            break;
                        case 10:
                            waterCage = map.getItemWaterCageAt(x, y);
                            if (waterCage != null) {
                                ZooItem swimAnimal13 = new AnimalSwim1(x, y, 3);
                                ((ZooCageWater) waterCage).addAnimal((Animal) swimAnimal13);
                                map.addItem(swimAnimal13);
                            }
                            break;
                        case 11:
                            ZooItem airCage = map.getItemAirCageAt(x, y);
                            if (airCage != null) {
                                ZooItem flyAnimal11 = new AnimalFly1(x, y, 1);
                                ((ZooCageAir) airCage).addAnimal((Animal) flyAnimal11);
                                map.addItem(flyAnimal11);
                                Thread t7 = new Thread((Animal) flyAnimal11);
                                t7.run();
                            }
                            break;
                        case 12:
                            airCage = map.getItemAirCageAt(x, y);
                            if (airCage != null) {
                                ZooItem flyAnimal12 = new AnimalFly1(x, y, 2);
                                ((ZooCageAir) airCage).addAnimal((Animal) flyAnimal12);
                                map.addItem(flyAnimal12);
                            }
                            break;
                        case 13:
                            airCage = map.getItemAirCageAt(x, y);
                            if (airCage != null) {
                                ZooItem flyAnimal13 = new AnimalFly1(x, y, 3);
                                ((ZooCageAir) airCage).addAnimal((Animal) flyAnimal13);
                                map.addItem(flyAnimal13);
                            }
                            break;
                        default:
                            break;

                    }

                } 
            } catch (OutOfBoundException ex) {
                System.out.println("Not enough space to create building");
                System.exit(0);
            } catch (BlockedException ex) {
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
