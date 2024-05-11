package animations;

import views.AnimatedScreen;

public class AvatarAnimation extends Thread {
    public AnimatedScreen screen;

    public AvatarAnimation(AnimatedScreen screen) {
        this.screen = screen;
    }

    public void run () {
        while(screen.element.equals("all")) {


            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
