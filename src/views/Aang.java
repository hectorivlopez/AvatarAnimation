package views;

import animations.AangThread;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import static views.Drawing.*;

public class Aang {
    public BufferedImage buffer;
    public int x0;
    public int y0;

    public int[] leftShoulder;
    public int[] rightShoulder;

    public int[] leftElbow;
    public int[] rightElbow;

    public int[] leftHand;
    public int[] rightHand;

    public int[] leftHip;
    public int[] rightHip;

    public int[] leftKnee;
    public int[] rightKnee;

    public int[] leftFeet;
    public int[] rightFeet;

    public double angle;

    public Aang(int x0, int y0, double scale, BufferedImage buffer) {
        this.x0 = x0;
        this.y0 = y0;
        this.buffer = buffer;
        this.angle = 0;

        this.leftShoulder = new int[] {x0 - 15, y0 - 60};
        this.rightShoulder = new int[] {x0 + 15, y0 - 60};
        this.leftHip = new int[] {x0 - 15, y0};
        this.rightHip = new int[] {x0 + 15, y0};

        AangThread thread = new AangThread(this);
        thread.start();

    }

    public synchronized void draw() {

        drawCircle(leftShoulder[0] + (Math.abs(rightShoulder[0] - leftShoulder[0]) / 2), leftShoulder[1] - 15, 15, Color.orange, buffer);

        drawRect(leftHip[0], leftHip[1], rightShoulder[0], rightShoulder[1], Color.green, buffer);
        this.leftKnee = drawVector(leftHip[0], leftHip[1], 50, 135, "D", Color.red, buffer);
        this.rightKnee = drawVector(rightHip[0], rightHip[1], 50, 45, "D", Color.red, buffer);

        drawVector(leftKnee[0], leftKnee[1], 50, angle, "R", Color.red, buffer);
        drawVector(rightKnee[0], rightKnee[1], 50, -angle + Math.PI, "R", Color.red, buffer);

        this.leftElbow = drawVector(leftShoulder[0], leftShoulder[1], 40, 160, "D", Color.red, buffer);
        this.rightElbow = drawVector(rightShoulder[0], rightShoulder[1], 40, 20, "D", Color.red, buffer);

        drawVector(leftElbow[0], leftElbow[1], 35, angle - Math.PI, "R", Color.red, buffer);
        drawVector(rightElbow[0], rightElbow[1], 35, -angle + (2 *Math.PI), "R", Color.red, buffer);
    }

    public void leftArm() {

    }

    public void rightArm() {

    }

    public void leftLeg() {

    }

    public void rightLeg() {

    }

}
