package animations;

import views.AnimatedScreen;

public class RepaintThread extends Thread {
    public AnimatedScreen screen;

    public boolean rigth;

    public RepaintThread(AnimatedScreen screen) {
        this.screen = screen;
        this.rigth = true;

    }

    public void run () {




        while (true) {
            synchronized (screen.mutex) {
                screen.factor++;







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
