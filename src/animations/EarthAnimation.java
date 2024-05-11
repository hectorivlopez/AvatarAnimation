package animations;

import views.AnimatedScreen;

public class EarthAnimation extends Thread {
    public AnimatedScreen screen;

    public EarthAnimation(AnimatedScreen screen) {
        this.screen = screen;
    }

    public void run () {
        while(screen.element.equals("earth")) {


            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
