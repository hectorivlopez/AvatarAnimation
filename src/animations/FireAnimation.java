package animations;

import views.AnimatedScreen;

public class FireAnimation extends Thread {
    public AnimatedScreen screen;

    public FireAnimation(AnimatedScreen screen) {
        this.screen = screen;
    }

    public void run () {
        while(screen.element.equals("fire")) {


            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
