package rmit.se2.pkg2012a.skoorti.view.util;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener, Serializable {

    protected class Key implements Serializable {

        public String name;
        public int keyCode, pressCount;
        public boolean pressed;

        public Key(String name, int keyCode) {
            this.name = name;
            this.keyCode = keyCode;
            this.pressed = false;
        }

        public void toggle(boolean toggle) {
            if (pressed != toggle) {
                pressed = toggle;
            }
            if (pressed) {
                pressCount++;
            }
        }
    }

    protected class Click implements Serializable {

        public String name;
        public int mouseCode, clickCount, pressCount;
        public boolean pressed, clicked;

        public Click(String name, int mouseCode) {
            this.mouseCode = mouseCode;
            this.name = name;
            pressed = false;
            clicked = false;
        }

        public void togglePressed(boolean toggle) {
            if (pressed != toggle) {
                pressed = toggle;
            }
            if (pressed) {
                pressCount++;
            }
        }

        public void toggleClicked(boolean toggle) {
            if (clicked != toggle) {
                clicked = toggle;
            }
            if (clicked) {
                clickCount++;
            }
        }
    }

    public class Mouse implements Serializable {

        public int x, y;
        public boolean dragged, inScreen;

        private Mouse() {
        }
    }
    public ArrayList<Key> keys = new ArrayList<Key>();
    public ArrayList<Click> clicks = new ArrayList<Click>();
    public Mouse MOUSE = new Mouse();

    public InputManager(Canvas c) {
        c.addKeyListener(this);
        c.addMouseListener(this);
        c.addMouseMotionListener(this);
    }

    public void addKeyMapping(String s, int keyCode) {
        keys.add(new Key(s, keyCode));
    }

    public void addMouseMapping(String s, int mouseCode) {
        clicks.add(new Click(s, mouseCode));
    }

    public boolean isKeyPressed(String s) {
        for (int i = 0; i < keys.size(); i++) {
            if (s.equals(keys.get(i).name)) {
                return keys.get(i).pressed;
            }
        }
        return false;
    }

    public boolean isMouseClicked(String s) {
        for (int i = 0; i < clicks.size(); i++) {
            if (s.equals(clicks.get(i).name)) {
                if (clicks.get(i).clicked) {
                    clicks.get(i).clicked = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isMousePressed(String s) {
        for (int i = 0; i < clicks.size(); i++) {
            if (s.equals(clicks.get(i).name)) {
                return clicks.get(i).pressed;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (int i = 0; i < keys.size(); i++) {
            if (e.getKeyCode() == keys.get(i).keyCode) {
                keys.get(i).toggle(true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (int i = 0; i < keys.size(); i++) {
            if (e.getKeyCode() == keys.get(i).keyCode) {
                keys.get(i).toggle(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < clicks.size(); i++) {
            if (e.getButton() == clicks.get(i).mouseCode) {
                clicks.get(i).toggleClicked(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < clicks.size(); i++) {
            if (e.getButton() == clicks.get(i).mouseCode) {
                clicks.get(i).togglePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < clicks.size(); i++) {
            if (e.getButton() == clicks.get(i).mouseCode) {
                clicks.get(i).togglePressed(false);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        MOUSE.inScreen = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        MOUSE.inScreen = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        MOUSE.dragged = true;
        MOUSE.x = e.getX();
        MOUSE.y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        MOUSE.dragged = false;
        MOUSE.x = e.getX();
        MOUSE.y = e.getY();
    }
}