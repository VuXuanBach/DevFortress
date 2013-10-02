package sprite;

import java.awt.image.BufferedImage;

public class SpriteManager extends javax.swing.JPanel implements Runnable {
    // list of sprite

    java.util.ArrayList<Sprite> spriteList = new java.util.ArrayList<Sprite>();
    // image object for double buffering
    private BufferedImage drawingBoard;
    BufferedImage backgroundImage;
    private boolean running = false;  // animation thread

    public SpriteManager() throws Exception {

        // read background image 
        String jpeg_file = "littleroottown.png";
        backgroundImage = javax.imageio.ImageIO.read(this.getClass().getResource(jpeg_file));

        // set panel size to dimension of background image
        int w = backgroundImage.getWidth(this);
        int h = backgroundImage.getHeight(this);
        setPreferredSize(new java.awt.Dimension(w, h));

        // resize the drawing board to fit the background
        drawingBoard = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    }

    // add 50 bunnies sprites to the sprite list  
    public void initSpriteList() throws Exception {
        BufferedImage image = javax.imageio.ImageIO.read(this.getClass().getResource("s1.png"));

        java.util.Random random = new java.util.Random();
//        for (int i = 0; i < 50; i++) {
        Sprite sprite = new Sprite(image, 32, 32);
        sprite.x = 50 + 16 * random.nextInt(20);
        sprite.y = 250 + 4 * random.nextInt(20);
        sprite.relativeSpeed = 1;   // range from 1 to 4
        sprite.gestureWait = 8;           // change it to 1 to see what happen
        spriteList.add(sprite);
//        }

    }

    // override the paint() method
    public void paintComponent(java.awt.Graphics gr) {
        gr.drawImage(drawingBoard, 0, 0, this);
    }

    // animation thread
    public void run() {
        while (running) {
            try {
                animateFrame();
                repaint();
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void animateFrame() {
        java.awt.Graphics2D gr = (java.awt.Graphics2D) drawingBoard.getGraphics();
        gr.drawImage(backgroundImage, 0, 0, this);

        for (int i = 0; i < spriteList.size(); i++) {
            Sprite sprite = spriteList.get(i);
            sprite.move();
            sprite.draw(gr);
        }
    }

    public void start() throws Exception {
        initSpriteList();
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) throws Exception {
        javax.swing.JFrame window = new javax.swing.JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setTitle("Macteki Sprite Manager");

        SpriteManager manager = new SpriteManager();
        window.add(manager);
        window.pack();

        window.setVisible(true);

        manager.start();
    }
}