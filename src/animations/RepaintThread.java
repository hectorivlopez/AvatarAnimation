package animations;

import views.AnimatedScreen;

public class RepaintThread extends Thread {
    public AnimatedScreen screen;
    public boolean growing;

    public RepaintThread(AnimatedScreen screen) {
        this.screen = screen;
    }

    public void run () {
        double speed = 0.01;
        double rotationSpeed = 0.1;
        int count = 0;
        while (true) {
            synchronized (screen.mutex) {
                screen.factor++;

                if(count == 0) {
                    screen.angRotate += rotationSpeed;

                }
                if (screen.angRotate >= 2 * Math.PI) {
                    count++;
                    if(count == 100) {
                        screen.angRotate = 0;
                        count = 0;
                    }
                }

                if (growing) {
                    screen.xScale += speed;
                    screen.yScale += speed;
                } else {
                    screen.xScale -= speed;
                    screen.yScale -= speed;
                }
                if (screen.xScale >= 1.5) {
                    growing = false;
                }
                if (screen.xScale <= 0.5) {
                    growing = true;
                }

                screen.repaint();
            }

            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
