package views;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayDeque;
import java.util.Deque;

public class Drawing {
    public static void draw(int x, int y, Color color, BufferedImage buffer) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, color.getRGB());
        }
    }

    // ------------------------------ Lines ------------------------------
    public static void drawLine(int x1, int y1, int x2, int y2, Color color, BufferedImage buffer) {
        double m = (double) (y2 - y1) / (x2 - x1);
        if ((x2 - x1) == 0) {
            m = 0;
        }

        int r = 20;
        int g = 255;
        int b = 50;

        // Evaluate x
        if (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) {
            // views.Line color
            double colorRate = (double) (Math.abs(x2 - x1)) / 205;
            int colorIncrement = 1;
            if (colorRate < 1) {
                colorIncrement = (int) Math.floor(1 / colorRate);
                colorRate = 1;
            } else {
                colorRate = Math.ceil(colorRate);
            }

            double y = y1;
            if (x2 > x1) {
                for (int x = x1; x <= x2; x++) {
                    Color gradColor = new Color(r, g, b);
                    draw(x, (int) y, color != null ? color : gradColor, buffer);

                    y += m;

                    if (x % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            } else {
                for (int x = x1; x >= x2; x--) {
                    Color gradColor = new Color(r, g, b);
                    draw(x, (int) y, color != null ? color : gradColor, buffer);

                    y -= m;

                    if (x % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            }
        }

        // Evaluate y
        else {
            // views.Line color
            double colorRate = (double) (Math.abs(y2 - y1)) / 205;
            int colorIncrement = 1;
            if (colorRate < 1) {
                colorIncrement = (int) Math.floor(1 / colorRate);
                colorRate = 1;
            } else {
                colorRate = Math.ceil(colorRate);
            }

            double x = x1;
            if (y2 > y1) {
                for (int y = y1; y <= y2; y++) {
                    Color gradColor = new Color(r, g, b);
                    draw((int) x, y, color != null ? color : gradColor, buffer);

                    if (m != 0) {
                        x += 1 / m;
                    }

                    if (y % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            } else {
                for (int y = y1; y >= y2; y--) {
                    Color gradColor = new Color(r, g, b);
                    draw((int) x, y, color != null ? color : gradColor, buffer);

                    if (m != 0) {
                        x -= 1 / m;
                    }

                    if (y % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            }
        }

    }

    public static void drawPolyline(int x1, int y1, int x2, int y2, Color color, int numFragments, int fragment, BufferedImage buffer) {
        double m = (double) (y2 - y1) / (x2 - x1);
        if ((x2 - x1) == 0) {
            m = 0;
        }

        int r = 20 + (int) ((((double) fragment - 1) * (205 / (double) numFragments)));
        int g = 255 - (int) ((((double) fragment - 1) * (205 / (double) numFragments)));
        int b = 50 + (int) ((((double) fragment - 1) * (205 / (double) numFragments)));

        // Evaluate x
        if (Math.abs(x2 - x1) >= Math.abs(y2 - y1)) {
            // views.Line color
            double colorRate = (double) (Math.abs(x2 - x1) * (numFragments)) / 205;
            int colorIncrement = 1;
            if (colorRate < 1) {
                colorIncrement = (int) Math.floor(1 / colorRate);
                colorRate = 1;
            } else {
                colorRate = Math.ceil(colorRate);
            }

            double y = y1;
            if (x2 > x1) {
                for (int x = x1; x <= x2; x++) {
                    Color gradColor = new Color(r, g, b);
                    draw(x, (int) y, color != null ? color : gradColor, buffer);

                    y += m;

                    if (x % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            } else {
                for (int x = x1; x >= x2; x--) {
                    Color gradColor = new Color(r, g, b);
                    draw(x, (int) y, color != null ? color : gradColor, buffer);

                    y -= m;

                    if (x % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            }
        }

        // Evaluate y
        else {
            // views.Line color
            double colorRate = (double) (Math.abs(y2 - y1) * (numFragments)) / 205;
            int colorIncrement = 1;
            if (colorRate < 1) {
                colorIncrement = (int) Math.floor(1 / colorRate);
                colorRate = 1;
            } else {
                colorRate = Math.ceil(colorRate);
            }

            double x = x1;
            if (y2 > y1) {
                for (int y = y1; y <= y2; y++) {
                    Color gradColor = new Color(r, g, b);
                    draw((int) x, y, color != null ? color : gradColor, buffer);

                    if (m != 0) {
                        x += 1 / m;
                    }

                    if (y % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            } else {
                for (int y = y1; y >= y2; y--) {
                    Color gradColor = new Color(r, g, b);
                    draw((int) x, y, color != null ? color : gradColor, buffer);

                    if (m != 0) {
                        x -= 1 / m;
                    }

                    if (y % (int) colorRate == 0) {
                        b += colorIncrement;
                        r += colorIncrement;
                        g -= colorIncrement;
                    }
                }
            }
        }


    }


    // ------------------------------ Shapes ------------------------------
    public static void drawRect(int x1, int y1, int x2, int y2, Color color, BufferedImage buffer) {
        if (color != null) {
            drawLine(x1, y1, x2, y1, color, buffer);
            drawLine(x1, y1, x1, y2, color, buffer);
            drawLine(x2, y1, x2, y2, color, buffer);
            drawLine(x1, y2, x2, y2, color, buffer);
        } else {
            drawPolyline(x1, y1, x2, y1, color, 2, 1, buffer);
            drawPolyline(x1, y1, x1, y2, color, 2, 1, buffer);
            drawPolyline(x2, y1, x2, y2, color, 2, 2, buffer);
            drawPolyline(x1, y2, x2, y2, color, 2, 2, buffer);
        }

    }

    public static void drawArc(int x1, int y1, int x2, int y2, int ry, Color color, BufferedImage buffer) {
        int xc = (int) (Math.min(x1, x2) + Math.abs(x2 - x1) / (double) 2);
        int yc = (int) (Math.min(y1, y2) + Math.abs(y2 - y1) / (double) 2);

        //int rx = (int) (Math.abs(x2 - x1) / (double) 2);
        int rx = (int) (Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2))) / 2;

        int numPoints = (int) (Math.PI * Math.max(rx, ry)); // Calculate number of points based on circumference
        double dx = x2 - x1;
        double dy = y2 - y1;
        double alpha = dx != 0 ? Math.atan(dy / dx) : Math.PI / 2;
        if(dx < 0) {
            alpha += Math.PI;
        }
        if(x1 > x2) {
            alpha += Math.PI;
        }
        /*System.out.println("Angle: " + Math.toDegrees(alpha));
        System.out.println(Math.sin(alpha));*/


        if(x1 > x2 || (x1 == x2 && y1 > y2)) ry *= -1;

        for (int i = 0; i < numPoints; i++) {
            double t = (i * Math.PI) / numPoints;
            double tf = ((i + 1) * Math.PI) / numPoints;

           /* int x = xc + (int) (rx * Math.cos(t));
            int y = yc + (int) (ry * Math.sin(t));*/

            int x = xc + (int) ((double) rx * Math.cos(t) * Math.cos(alpha) - (double) ry * Math.sin(t) * Math.sin(alpha));
            int y = yc + (int) ((double) rx * Math.cos(t) * Math.sin(alpha) + (double) ry * Math.sin(t) * Math.cos(alpha));

            int xf = xc + (int) ((double) rx * Math.cos(tf) * Math.cos(alpha) - (double) ry * Math.sin(tf) * Math.sin(alpha));
            int yf = yc + (int) ((double) rx * Math.cos(tf) * Math.sin(alpha) + (double) ry * Math.sin(tf) * Math.cos(alpha));


            drawPolyline(x, y, xf, yf, color, numPoints + 1, i + 1, buffer);


        }
       /* fillCircle(x1, y1, 2, Color.white, buffer);*/
       /* fillCircle(x2, y2, 2, Color.black, buffer);*/
    }

    public static void drawCircle(int xc, int yc, int r, Color color, BufferedImage buffer) {
        for (int x = xc - r; x < xc + r; x++) {
            int ySup = yc + (int) Math.sqrt((Math.pow(r, 2) - Math.pow(x - xc, 2)));
            int yInf = yc - (int) Math.sqrt((Math.pow(r, 2) - Math.pow(x - xc, 2)));

            draw(x, ySup, color, buffer);
            draw(x, yInf, color, buffer);
        }
        for (int y = yc - r; y < yc + r; y++) {
            int xSup = xc + (int) Math.sqrt((Math.pow(r, 2) - Math.pow(y - yc, 2)));
            int xInf = xc - (int) Math.sqrt((Math.pow(r, 2) - Math.pow(y - yc, 2)));

            draw(xSup, y, color, buffer);
            draw(xInf, y, color, buffer);
        }
    }

    public static void drawCirclePolar(int xc, int yc, int r, Color color, BufferedImage buffer) {
        int numPoints = (int) (2 * Math.PI * r); // Calculate number of points based on circumference

        int R = 255;
        int g = 255;
        int b = 50;

        double colorRate = (double) numPoints / 205;
        int colorIncrement = 1;
        if (colorRate < 1) {
            colorIncrement = (int) Math.floor(1 / colorRate);
            colorRate = 1;
        } else {
            colorRate = Math.ceil(colorRate);
        }

        for (int i = 0; i < numPoints; i++) {
            double t = (i * 2 * Math.PI) / numPoints;

            int x = xc + (int) (r * Math.cos(t));
            int y = yc + (int) (r * Math.sin(t));

            Color grad = new Color(R, g, b);

            draw(x, y, color != null ? color : grad, buffer);

            if (i % (int) colorRate == 0) {
                b += colorIncrement;
                g -= colorIncrement;
            }
        }
    }

    public static void drawEllipse(int xc, int yc, int rx, int ry, double angle, Color color, BufferedImage buffer) {
        int numPoints = (int) (2 * Math.PI * Math.max(rx, ry)); // Calculate number of points based on circumference
        while (angle >= 360) angle -= 360;
        double alpha = Math.toRadians(angle);

        int R = 255;
        int g = 255;
        int b = 50;

        double colorRate = (double) numPoints / 205;
        int colorIncrement = 1;
        if (colorRate < 1) {
            colorIncrement = (int) Math.floor(1 / colorRate);
            colorRate = 1;
        } else {
            colorRate = Math.ceil(colorRate);
        }

        for (int i = 0; i < numPoints; i++) {
            double t = (i * 2 * Math.PI) / numPoints;

           /* int x = xc + (int) (rx * Math.cos(t));
            int y = yc + (int) (ry * Math.sin(t));*/
            int x = xc + (int) ((double) rx * Math.cos(t) * Math.cos(alpha) - (double) ry * Math.sin(t) * Math.sin(alpha));
            int y = yc + (int) ((double) rx * Math.cos(t) * Math.sin(alpha) + (double) ry * Math.sin(t) * Math.cos(alpha));


            Color grad = new Color(R, g, b);

            draw(x, y, color != null ? color : grad, buffer);

            if (i % (int) colorRate == 0) {
                b += colorIncrement;
                g -= colorIncrement;
            }
        }
    }


    // Fill
    public static void floodFill(int x, int y, Color targetColor, BufferedImage buffer) {
        int originalColor = buffer.getRGB(x, y);

        if (originalColor == targetColor.getRGB()) {
            return;
        }

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] currentPixel = stack.pop();
            int px = currentPixel[0];
            int py = currentPixel[1];

            if (buffer.getRGB(px, py) != targetColor.getRGB()) {
                //buffer.setRGB(px, py, targetColor.getRGB());
                draw(px, py, targetColor, buffer);

                if (px + 1 >= 0 && px + 1 < buffer.getWidth() && py >= 0 && py < buffer.getHeight()) {
                    stack.push(new int[]{px + 1, py});
                }
                if (px - 1 >= 0 && px - 1 < buffer.getWidth() && py >= 0 && py < buffer.getHeight()) {
                    stack.push(new int[]{px - 1, py});
                }
                if (px >= 0 && px < buffer.getWidth() && py + 1 >= 0 && py + 1 < buffer.getHeight()) {
                    stack.push(new int[]{px, py + 1});
                }
                if (px >= 0 && px < buffer.getWidth() && py - 1 >= 0 && py - 1 < buffer.getHeight()) {
                    stack.push(new int[]{px, py - 1});
                }

            }
        }
    }

    public static void fillScanLine(int xc, int yc, Color targetColor, BufferedImage buffer) {
        int y = yc;
        while (y >= 0 && y < buffer.getHeight() && buffer.getRGB(xc, y) != targetColor.getRGB()) {
            int x = xc;
            while (x >= 0 && x < buffer.getWidth() && buffer.getRGB(x, y) != targetColor.getRGB()) {
                draw(x++, y, targetColor, buffer);
            }
            x = xc - 1;
            while (x >= 0 && x < buffer.getWidth() && buffer.getRGB(x, y) != targetColor.getRGB()) {
                draw(x--, y, targetColor, buffer);
            }
            y++;
        }
        y = yc - 1;
        while (y >= 0 && y < buffer.getHeight() && buffer.getRGB(xc, y) != targetColor.getRGB()) {
            int x = xc;
            while (x >= 0 && x < buffer.getWidth() && buffer.getRGB(x, y) != targetColor.getRGB()) {
                draw(x++, y, targetColor, buffer);
            }
            x = xc - 1;
            while (x >= 0 && x < buffer.getWidth() && buffer.getRGB(x, y) != targetColor.getRGB()) {
                draw(x--, y, targetColor, buffer);
            }
            y--;
        }

    }

    public static void fillRect(int x1, int y1, int x2, int y2, Color color, BufferedImage buffer) {
        drawRect(x1, y1, x2, y2, color, buffer);

        int xc = Math.min(x1, x2) + (Math.abs(x1 - x2) / 2);
        int yc = Math.min(y1, y2) + (Math.abs(y1 - y2) / 2);

        floodFill(xc, yc, color, buffer);
    }

    public static void fillBackground(Color color, BufferedImage buffer) {
        for (int i = 0; i < buffer.getHeight(); i++) {
            for (int j = 0; j < buffer.getWidth(); j++) {
                draw(j, i, color, buffer);
            }
        }
    }

    public static void fillCircle(int xc, int yc, int r, Color color, BufferedImage buffer) {
        drawCircle(xc, yc, r, color, buffer);

        floodFill(xc, yc, color, buffer);
    }

    public static void fillEllipse(int xc, int yc, int rx, int ry, double angle, Color color, BufferedImage buffer) {
        drawEllipse(xc, yc, rx, ry, angle, color, buffer);
        floodFill(xc, yc, color, buffer);
    }

    public static void fillPolygon(int[] xPoints, int[] yPoints, Color color, BufferedImage buffer) {
        int nPoints = xPoints.length;
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.drawPolygon(xPoints, yPoints, nPoints);

        // Calculate centroid of the polygon
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < nPoints; i++) {
            sumX += xPoints[i];
            sumY += yPoints[i];
        }
        int centroidX = sumX / nPoints;
        int centroidY = sumY / nPoints;

        // Now fill the polygon using flood fill from the centroid
        floodFill(centroidX, centroidY, color, buffer);
    }


    // ------------------------------ Transforms ------------------------------
    public static int[][] multiplyMatrices(double[][] scaleMatrix, int[][] initialMatrix) {
        int n = scaleMatrix.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double newValue = 0;
                for (int k = 0; k < n; k++) {
                    newValue += scaleMatrix[i][k] * (double) initialMatrix[k][j];
                }
                result[i][j] = (int) newValue;
            }
        }

        return result;
    }

    public static int[][] scale(int[] xPoints, int[] yPoints, int xc, int yc, boolean center, double xScale, double yScale) {
        int xCenter = 0 + xc;
        int yCenter = 0 + yc;

        // Calculate the center
        if (center) {
            int sumX = 0;
            int sumY = 0;

            for (int i = 0; i < xPoints.length; i++) {
                sumX += xPoints[i];
                sumY += yPoints[i];
            }

            xCenter = sumX / xPoints.length;
            yCenter = sumY / yPoints.length;
        }

        // Create initial matrix
        int[][] initialMatrix = new int[xPoints.length][xPoints.length];
        for(int i = 0; i < xPoints.length; i++) {
            initialMatrix[0][i] = xPoints[i];
            initialMatrix[1][i] = yPoints[i];
        }


        // Setting center to [0, 0]
        for (int i = 0; i < xPoints.length; i++) {
            initialMatrix[0][i] -= xCenter;
            initialMatrix[1][i] -= yCenter;
        }

        for (int i = 2; i < xPoints.length; i++) {
            for (int j = 0; j < xPoints.length; j++) {
                initialMatrix[i][j] = 1;
            }
        }

        // Create resizing matrix
        double[][] scalelMatrix = new double[xPoints.length][xPoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            for (int j = 0; j < xPoints.length; j++) {
                if (i == j) {
                    scalelMatrix[i][j] = 1;
                } else {
                    scalelMatrix[i][j] = 0;
                }
            }
        }

        scalelMatrix[0][0] = xScale;
        scalelMatrix[1][1] = yScale;

        int[][] resultMatrix = multiplyMatrices(scalelMatrix, initialMatrix);

        // Translation to original center
        for (int i = 0; i < xPoints.length; i++) {
            resultMatrix[0][i] += xCenter;
            resultMatrix[1][i] += yCenter;
        }

        return resultMatrix;
    }

    public static int[][] rotate(int[] xPoints, int[] yPoints, int xc, int yc, boolean center, double angle) {
        int xCenter = 0 + xc;
        int yCenter = 0 + yc;

        // Calculate the center
        if (center) {
            int sumX = 0;
            int sumY = 0;

            for (int i = 0; i < xPoints.length; i++) {
                sumX += xPoints[i];
                sumY += yPoints[i];
            }

            // Posible problema
            xCenter = sumX / xPoints.length;
            yCenter = sumY / yPoints.length;
        }

        // Create initial matrix
        int[][] initialMatrix = new int[xPoints.length][xPoints.length];
        for(int i = 0; i < xPoints.length; i++) {
            initialMatrix[0][i] = xPoints[i];
            initialMatrix[1][i] = yPoints[i];
        }

        // Setting center to [0, 0]
        for (int i = 0; i < xPoints.length; i++) {
            initialMatrix[0][i] -= xCenter;
            initialMatrix[1][i] -= yCenter;
        }

        for (int i = 2; i < xPoints.length; i++) {
            for (int j = 0; j < xPoints.length; j++) {
                initialMatrix[i][j] = 1;
            }
        }

        // Create rotator matrix
        double[][] rotatelMatrix = new double[xPoints.length][xPoints.length];
        for (int i = 0; i < xPoints.length; i++) {
            for (int j = 0; j < xPoints.length; j++) {
                if (i == j) {
                    rotatelMatrix[i][j] = 1;
                } else {
                    rotatelMatrix[i][j] = 0;
                }
            }
        }

        rotatelMatrix[0][0] = Math.cos(angle);
        rotatelMatrix[0][1] = -1 * Math.sin(angle);
        rotatelMatrix[1][0] = Math.sin(angle);
        rotatelMatrix[1][1] = Math.cos(angle);

        int[][] resultMatrix = multiplyMatrices(rotatelMatrix, initialMatrix);

        // Translation to original center
        for (int i = 0; i < xPoints.length; i++) {
            resultMatrix[0][i] += xCenter;
            resultMatrix[1][i] += yCenter;
        }

        return resultMatrix;
    }


    public static void fillRectScaled(int x1, int y1, int x2, int y2, double scale, int x0, int y0, Color color, BufferedImage buffer) {
        int[] rectXPoints = new int[]{x1, x2, x2, x1};
        int[] rectYPoints = new int[]{y1, y1, y2, y2};

        int[][] newRectCoords = scale(rectXPoints, rectYPoints, x0, y0, false, scale, scale);

        int[] xPoints = newRectCoords[0];
        int[] yPoints = newRectCoords[1];

        fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[2], color, buffer);
    }

    public static void fillCircleScaled(int xc, int yc, int r, double scale, int x0, int y0,  Color color, BufferedImage buffer) {
        int[] circleXPoints = new int[]{xc, xc + r, r};
        int[] circleYPoints = new int[]{yc, yc + r, r};

        int[][] newCircleCoords = scale(circleXPoints, circleYPoints, x0, y0, false, scale, scale);
        fillCircle(newCircleCoords[0][0], newCircleCoords[1][0], newCircleCoords[0][1] - newCircleCoords[0][0], color, buffer);

    }

    public static void fillCircleScaledRotated(int xc, int yc, int r, double scale, double angle, int x0Scale, int y0Scale,  int x0Rotate, int y0Rotate,  Color color, BufferedImage buffer) {
        int[] circleXPoints = new int[]{xc, xc + r, r};
        int[] circleYPoints = new int[]{yc, yc + r, r};

        int[][] scaledPoints = scale(circleXPoints, circleYPoints, x0Scale, y0Scale, false, scale, scale);
        int[] scaledXPoints = scaledPoints[0];
        int[] scaledYPoints = scaledPoints[1];

        int scaledR = scaledXPoints[1] - scaledXPoints[0];

        int[][] rotatedPoints = rotate(scaledXPoints, scaledYPoints, x0Rotate, y0Rotate, false, angle);

        fillCircle(rotatedPoints[0][0], rotatedPoints[1][0], scaledR, color, buffer);
    }

    public static void fillEllipseScaled(int xc, int yc, int rx, int ry, double angle, double scale, int x0, int y0, Color color, BufferedImage buffer) {
        int[] ellipseXPoints = new int[]{xc, xc + rx, rx};
        int[] ellipseYPoints = new int[]{yc, yc + ry, ry};

        int[][] newEllipseCoords = scale(ellipseXPoints, ellipseYPoints, x0, y0, false, scale, scale);

        drawEllipse(
                newEllipseCoords[0][0],
                newEllipseCoords[1][0],
                newEllipseCoords[0][1] - newEllipseCoords[0][0],
                newEllipseCoords[1][1] - newEllipseCoords[1][0],
                angle,
                color,
                buffer
        );
        floodFill(newEllipseCoords[0][0], newEllipseCoords[1][0], color, buffer);
    }

    public static void fillEllipseScaledRotated(int xc, int yc, int rx, int ry, double angle, double scale, double angRotate, int x0Scale, int y0Scale, int x0Rotate, int y0Rotate, Color color, BufferedImage buffer) {
        int[] ellipseXPoints = new int[]{xc, xc + rx, rx};
        int[] ellipseYPoints = new int[]{yc, yc + ry, ry};

        //int[][] newEllipseCoords = scale(ellipseXPoints, ellipseYPoints, x0Scale, y0Scale, false, scale, scale);

        int[][] scaledPoints = scale(ellipseXPoints, ellipseYPoints, x0Scale, y0Scale, false, scale, scale);
        int[] scaledXPoints = scaledPoints[0];
        int[] scaledYPoints = scaledPoints[1];

        int scaledRx = scaledXPoints[1] - scaledXPoints[0];
        int scaledRy = scaledYPoints[1] - scaledYPoints[0];

        int[][] rotatedPoints = rotate(scaledXPoints, scaledYPoints, x0Rotate, y0Rotate, false, angRotate);


        drawEllipse(
                rotatedPoints[0][0],
                rotatedPoints[1][0],
                scaledRx,
                scaledRy,
                angle + Math.toDegrees(angRotate),
                color,
                buffer
        );

        //drawCircle(rotatedPoints[0][0], rotatedPoints[1][0], 3, Color.white, buffer);
        floodFill(rotatedPoints[0][0], rotatedPoints[1][0], color, buffer);
    }


}
