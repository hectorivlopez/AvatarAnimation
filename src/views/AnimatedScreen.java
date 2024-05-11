package views;

import animations.*;

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
    public int xAang;
    public int yAang;
    public int aangMirror;

    public Aang aang;

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

        this.xAang = 100;
        this.yAang = 300;
        this.aangMirror = 1;



        schedule();
    }

    public void schedule() {


        transition(false, 1, 200);

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
        // ---------- Air ----------
        if (element.equals("air")) {
            aangSit(xAang, yAang, xScale, xAang, yAang - 25, angRotate, xAang + 16, yAang - 100, aangMirror);
        }
        if(aang != null) {
            aang.draw();
        }

        g.drawImage(buffer, 0, 0, this);


        // ------------------------------ Transitions ------------------------------
        if (onTransition) {
            fillBackground(new Color(255, 255, 255, transitionOpacity), transitionBuffer);
            g.drawImage(transitionBuffer, 0, 0, this);
        }

    }

    public void aangSit(int x0, int y0, double scale, int x0Scale, int y0Scale, double angle, int x0Rotate, int y0Rotate, int mirror) {
        Color yellow = new Color(244, 216, 160);
        Color orange = new Color(236, 133, 89);
        Color brown = new Color(120, 78, 70);
        Color blue = new Color(61, 140, 193);
        Color blueAir = new Color(132, 217, 241);

        int[] xPoints = new int[]{
                x0 + (24 * mirror), x0 + (32 * mirror),
                x0 + (24 * mirror), x0 + (32 * mirror),
                x0 + (16 * mirror), x0 + (29 * mirror), x0 + (22 * mirror), // Flecha
                x0 + (26 * mirror), // Relleno flecha
                x0 + (16 * mirror) // Rotation
                /*400, 600,
                x0 + (30 * mirror), x0 + (22 * mirror),*/
        };
        int[] yPoints = new int[]{
                y0 - 50, y0 - 51,
                y0 - 49, y0 - 50,
                y0 - 97, y0 - 82, y0 - 84,
                y0 - 86,
                y0 - 84
                /*150, 250,*/
                /*y0 - 83, y0 - 85,*/
        };

        int[][] scaledPoints = scale(xPoints, yPoints, x0Scale, y0Scale, false, scale, scale);
        int[][] rotatedPoints = rotate(scaledPoints[0], scaledPoints[1], scaledPoints[0][8], scaledPoints[1][8], false, angle);

        int[] finalXPoints = rotatedPoints[0];
        int[] finalYPoints = rotatedPoints[1];

        // Air ball
        fillCircleScaledRotated(x0, y0 + 35, 22, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], blueAir, buffer); // Bola

        // Shoe
        fillEllipseScaledRotated(x0 + (-3 * mirror), y0 + 2, 9, 4, 70 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], brown, buffer); // Zapato
        // Ankle
        fillEllipseScaledRotated(x0 + (2 * mirror), y0 - 7, 9, 5, -30 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], brown, buffer); // Tobillo

        // Knee
        fillEllipseScaledRotated(x0 + (17 * mirror), y0 - 17, 13, 9, -30 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], yellow, buffer); // Rodilla
        // Leg
        fillEllipseScaledRotated(x0 + (5 * mirror), y0 - 25, 22, 9, 10 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], yellow, buffer); // Pierna

        // Torso
        fillEllipseScaledRotated(x0 + (2 * mirror), y0 - 50, 30, 11, 110 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], yellow, buffer); // Torso

        // Arm
        fillEllipseScaledRotated(x0 + (18 * mirror), y0 - 50, 10, 5, -5 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], yellow, buffer); // Brazo
        // Hand
        fillEllipseScaledRotated(x0 + (28 * mirror), y0 - 51, 4, 5, -5 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], new Color(227, 198, 168), buffer); // Mano

        // Hand arrow
        fillPolygon(
                new int[] {finalXPoints[0], finalXPoints[1], finalXPoints[3], finalXPoints[2]},
                new int[] {finalYPoints[0], finalYPoints[1], finalYPoints[3], finalYPoints[2]},
                blue,
                buffer
        );

        // Torso orange
        fillEllipseScaledRotated(x0 + (6 * mirror), y0 - 60, 12, 18, 100 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], orange, buffer); // Torso naranja

        // Belt
        fillEllipseScaledRotated(x0 - (2 * mirror), y0 - 34, 13, 4, 3 * mirror, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], orange, buffer); // Cinturon

        // Head
        fillCircleScaledRotated(x0 + (16 * mirror), y0 - 84, 13, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], new Color(227, 198, 168), buffer); // Cabeza
        // Head arrow
        drawArc(finalXPoints[4], finalYPoints[4], finalXPoints[5], finalYPoints[5], (int) (-6 * scale * mirror), 30, blue, buffer);
        drawArc(finalXPoints[4], finalYPoints[4], finalXPoints[6], finalYPoints[6], (int) (-6 * scale * mirror), 30, blue, buffer);
        drawLine(finalXPoints[5], finalYPoints[5], finalXPoints[6], finalYPoints[6], blue, buffer);
        /*if(Math.abs(finalXPoints[6] - finalXPoints[5]) > 4)*/ floodFill(finalXPoints[7], finalYPoints[7], blue, buffer);

        // Eye
        fillCircleScaledRotated(x0 + (22 * mirror), y0 - 80, 3, scale, angle, x0Scale, y0Scale, finalXPoints[8], finalYPoints[8], Color.black, buffer); // Ojo
    }

    public void update() {
        switch (element) {
            case "air":
                elementString = "Aire";
                AirAnimation airAnimation = new AirAnimation(this);
                airAnimation.start();

                break;
            case "water":
                elementString = "Agua";
                //this.aang = new Aang(200, 250, 1, this.buffer);
                WaterAnimation waterAnimation = new WaterAnimation(this);
                waterAnimation.start();
                break;
            case "earth":
                elementString = "Tierra";
                EarthAnimation earthAnimation = new EarthAnimation(this);
                earthAnimation.start();
                break;
            case "fire":
                elementString = "Fuego";
                FireAnimation fireAnimation = new FireAnimation(this);
                fireAnimation.start();
                break;
            case "all":
                elementString = "Todos";
                AvatarAnimation avatarAnimation = new AvatarAnimation(this);
                avatarAnimation.start();
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
