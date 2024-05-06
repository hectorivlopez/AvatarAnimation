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
        schedule();
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

        aangSit(500, 300, 1.1, 500, 300 -25, angRotate, 500 + 16, 300 - 100 /*500, 350*/);
        //drawArc(100, 100, 100, 200, 10, Color.green, buffer);

        g.drawImage(buffer, 0, 0, this);


        // ------------------------------ Transitions ------------------------------
        if (onTransition) {
            fillBackground(new Color(255, 255, 255, transitionOpacity), transitionBuffer);
            g.drawImage(transitionBuffer, 0, 0, this);
        }

    }

    public void aangSit(int x0, int y0, double scale, int x0Scale, int y0Scale, double angle, int x0Rotate, int y0Rotate) {
        Color yellow = new Color(244, 216, 160);
        Color orange = new Color(236, 133, 89);
        Color brown = new Color(120, 78, 70);
        Color blue = new Color(61, 140, 193);
        Color blueAir = new Color(132, 217, 241);

        int[] xPoints = new int[] {
                x0 + 24, x0 + 32,
                x0 + 24, x0 + 32,
                x0 + 16, x0 + 29, x0 + 22,
                x0 + 27,
                400, 600,
                x0 + 30, x0 + 22,
        };
        int[] yPoints = new int[] {
                y0 - 50, y0 - 51,
                y0 - 49, y0 - 50,
                y0 - 97, y0 - 82, y0 - 84,
                y0 - 87,
                150, 250,
                y0 - 83, y0 - 85,
        };

        int[][] scaledPoints = scale(xPoints, yPoints, x0Scale, y0Scale, false, scale, scale);
        int[][] rotatedPoints = rotate(scaledPoints[0], scaledPoints[1], x0Rotate, y0Rotate, false, angle);

        int[] finalXPoints = rotatedPoints[0];
        int[] finalYPoints = rotatedPoints[1];

        fillEllipseScaledRotated(x0 - 3, y0 + 2, 9, 4, 70, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, brown, buffer); // Zapato
        fillEllipseScaledRotated(x0 + 2, y0 - 7, 9, 5, -30, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, brown, buffer); // Tobillo

        fillEllipseScaledRotated(x0 + 17, y0 - 17, 13, 9, -30, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, yellow, buffer); // Rodilla
        fillEllipseScaledRotated(x0 + 5, y0 - 25, 22, 9, 10, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, yellow, buffer); // Pierna

        fillEllipseScaledRotated(x0 + 2, y0 - 50, 30, 11, 110, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, yellow, buffer); // Torso

        fillEllipseScaledRotated(x0 + 18, y0 - 50, 10, 5, -5, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, yellow, buffer); // Brazo
        fillEllipseScaledRotated(x0 + 28, y0 - 51, 4, 5, -5, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, new Color(227, 198, 168), buffer); // Mano

        drawLine(finalXPoints[0], finalYPoints[0], finalXPoints[1], finalYPoints[1], blue, buffer);
        drawLine(finalXPoints[2], finalYPoints[2], finalXPoints[3], finalYPoints[3], blue, buffer);

        fillEllipseScaledRotated(x0 + 6, y0 - 60, 12, 18, 100, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, orange, buffer); // Torso naranja

        fillEllipseScaledRotated(x0 - 2, y0 - 34, 13, 4, 3, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, orange, buffer); // Cinturon

        fillCircleScaledRotated(x0 + 16, y0 - 84, 13, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, new Color(227, 198, 168), buffer); // Cabeza

        drawArc(finalXPoints[4], finalYPoints[4], finalXPoints[5], finalYPoints[5], (int) (-6 * scale), blue, buffer);
        drawArc(finalXPoints[4], finalYPoints[4], finalXPoints[6], finalYPoints[6], (int) (-6 * scale), blue, buffer);
        drawLine(finalXPoints[10], finalYPoints[10], finalXPoints[11], finalYPoints[11], blue, buffer);

        floodFill(finalXPoints[7], finalYPoints[7],  blue, buffer);

        fillCircleScaledRotated(x0 + 22, y0 - 80, 2, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, Color.black, buffer);

        fillCircleScaledRotated(x0, y0 + 35, 22, scale, angle, x0Scale, y0Scale, x0Rotate, y0Rotate, blueAir, buffer); // Bola
        //drawArc(finalXPoints[8], finalYPoints[8], finalXPoints[9], finalYPoints[9], -50, Color.green, buffer);
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
