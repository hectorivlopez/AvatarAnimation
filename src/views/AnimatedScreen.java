package views;

import animations.TransitionThread;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import views.Drawing.*;

import static views.Drawing.*;

public class AnimatedScreen extends JPanel {
    public int width;
    public int height;
    public BufferedImage buffer;
    public BufferedImage bgBuffer;
    public BufferedImage transitionBuffer;
    public Graphics gBuffer;
    public boolean onTransition;
    public boolean onChangeBackground;
    public int transitionOpacity;
    public String element;
    public String elementString;

    public int factor;
    public Object mutex;

    public double xScale;
    public double yScale;
    public double angRotate;

    public AnimatedScreen(int width, int height) {
        this.width = width;
        this.height = height;

        this.factor = 0;

        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.bgBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.transitionBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        this.gBuffer = buffer.getGraphics();

        this.onTransition = false;
        this.onChangeBackground = false;

        this.transitionOpacity = 255;
        this.element = "air";
        this.elementString = "air";

        this.mutex = new Object();

        this.xScale = 1;
        this.yScale = 1;
        this.angRotate = 0;
        //schedule();
    }

    public void schedule() {
        TimerTask airOutTransition = new TimerTask() {
            public void run() {
                transition(true, 1, 200);
                element = "water";
            }
        };
        TimerTask waterOutTransition = new TimerTask() {
            public void run() {
                transition(true, 1, 200);
                element = "earth";
            }
        };
        TimerTask earthOutTransition = new TimerTask() {
            public void run() {
                transition(true, 1, 200);
                element = "fire";
            }
        };
        TimerTask fireOutTransition = new TimerTask() {
            public void run() {
                transition(true, 2, 500);
                element = "all";
            }
        };

        Timer timer = new Timer();

        timer.schedule(airOutTransition, 13500);
        timer.schedule(waterOutTransition, 20500);
        timer.schedule(earthOutTransition, 33000);
        timer.schedule(fireOutTransition, 45000);
    }

    public void grid(BufferedImage buffer) {
        Color gray2 = new Color(68, 75, 114);
        Color gray = new Color(49, 55, 91);

        // Vertical Lines
        for (int i = 0; i < getWidth(); i++) {
            if (i % 50 == 0) {
                for (int j = 0; j < getHeight(); j++) {
                    if (i % 100 == 0) {
                        buffer.setRGB(i, j, gray2.getRGB());
                    } else {
                        buffer.setRGB(i, j, gray.getRGB());
                    }
                }
            }
        }

        // Horizontal Lines
        for (int j = 0; j < getHeight(); j++) {
            if (j % 50 == 0) {
                for (int i = 0; i < getWidth(); i++) {
                    if (j % 100 == 0) {
                        buffer.setRGB(i, j, gray2.getRGB());
                    } else {
                        buffer.setRGB(i, j, gray.getRGB());
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        // ------------------------------ Background ------------------------------
        Graphics gBgBuffer = bgBuffer.getGraphics();

        gBgBuffer.setColor(new Color(39, 43, 76));
        gBgBuffer.fillRect(0, 0, bgBuffer.getWidth(), bgBuffer.getHeight());

        grid(bgBuffer);

        gBuffer.drawImage(bgBuffer, 0, 0, this);


        // ------------------------------ Drawing ------------------------------

        aang(500, 300, xScale, angRotate);


        g.drawImage(buffer, 0, 0, this);


        // ------------------------------ Transitions ------------------------------
        if (onTransition) {
            fillBackground(new Color(255, 255, 255, transitionOpacity), transitionBuffer);
            g.drawImage(transitionBuffer, 0, 0, this);
        }

    }

    public void aang(int x0, int y0, double scale, double angle) {


        fillEllipseScaledRotated(x0 - 3, y0 + 2, 9, 4, 70, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.red, buffer); // Zapato
        fillEllipseScaledRotated(x0 + 2, y0 - 7, 9, 5, -30, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.red, buffer); // Tobillo


        fillEllipseScaledRotated(x0 + 17, y0 - 17, 13, 9, -30, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.orange, buffer); // Rodilla
        fillEllipseScaledRotated(x0 + 5, y0 - 25, 22, 9, 10, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.orange, buffer); // Pierna

        fillEllipseScaledRotated(x0 + 2, y0 - 50, 30, 11, 110, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.orange, buffer); // Torso


        fillEllipseScaledRotated(x0 - 3, y0 - 34, 13, 3, 3, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.red, buffer); // Cinturon
        //fillRectScaled(x0 - 16, y0 - 37, x0 + 10, y0 - 31, scale, x0, y0 - 25, x0 + 16, y0 - 100, Color.red, buffer);
        fillCircleScaledRotated(x0, y0 + 35, 22, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, Color.blue, buffer); // Bola

        fillCircleScaledRotated(x0 + 16, y0 - 86, 14, scale, angle, x0, y0 - 25, x0 + 16, y0 - 100, new Color(227, 198, 168), buffer); // Cabeza
        //fillEllipseScaled(100, 100, 13, 3, 3, xScale, 100, 100, Color.red, buffer); // Cinturon


    }

    public void update() {
        switch (element) {
            case "air":
                elementString = "Aire";
                break;
            case "water":
                elementString = "Agua";
                break;
            case "earth":
                elementString = "Tierra";
                break;
            case "fire":
                elementString = "Fuego";
                break;
            case "all":
                elementString = "Todos";
                break;
        }
    }

    public void transition(boolean complete, int delay, int whiteDelay) {
        this.onTransition = true;
        this.transitionOpacity = complete ? 0 : 255;
        TransitionThread thread = new TransitionThread(this, complete, delay, whiteDelay);
        thread.start();
    }

    public void airBackground() {

    }
}
