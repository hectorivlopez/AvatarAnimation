package animations;

import views.AnimatedScreen;

public class WaterAnimation extends Thread {
    public AnimatedScreen screen;

    public WaterAnimation(AnimatedScreen screen) {
        this.screen = screen;
    }

    public void run () {
        while(screen.element.equals("water")) {
            System.out.println("Agua");

            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
