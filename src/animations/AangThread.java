package animations;

import views.Aang;

public class AangThread extends Thread {
    public Aang aang;

    public AangThread(Aang aang) {
        this.aang = aang;
    }

    public void run() {
        while (true) {
            double speed = 0.1;
            synchronized (aang) {
                aang.angle += speed;

                if(aang.angle >= 2 * Math.PI) aang.angle = 0;

                aang.leftHip[0]++;
                aang.rightHip[0]++;

                aang.leftShoulder[0]++;
                aang.rightShoulder[0]++;
            }


            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
