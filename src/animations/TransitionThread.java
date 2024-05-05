package animations;

import views.AnimatedScreen;

public class TransitionThread extends Thread {
    public AnimatedScreen screen;
    public boolean toWhite;
    public int delay;
    public int whiteDelay;
    public boolean onDelay;

    public TransitionThread(AnimatedScreen screen, boolean complete, int delay, int whiteDelay) {
        this.screen = screen;
        this.delay = delay;
        this.whiteDelay = whiteDelay;
        this.toWhite = complete;
        this.onDelay = complete ? false : true;
    }

    public void run() {
        while (screen.onTransition) {
            if (!onDelay) {
                if (toWhite) {
                    screen.transitionOpacity++;
                    if (screen.transitionOpacity == 255) {
                        onDelay = true;
                        toWhite = false;
                    }
                } else {
                    screen.transitionOpacity--;
                    if (screen.transitionOpacity == 0) {
                        screen.onTransition = false;
                    }
                }
            } else {
                synchronized (screen.mutex) {
                    screen.onChangeBackground = true;
                }
                synchronized (screen.mutex) {
                    screen.update();

                }

                try {
                    sleep(whiteDelay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (screen.mutex) {
                    screen.onChangeBackground = false;
                }
                onDelay = false;
            }



            try {
                sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
