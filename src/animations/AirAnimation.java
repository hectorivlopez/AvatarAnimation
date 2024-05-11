package animations;

import views.AnimatedScreen;

public class AirAnimation extends Thread {
    public AnimatedScreen screen;
    public boolean rigth;
    public boolean growing;
    public double t;
    public RotateAangThread rotationThread;

    public AirAnimation(AnimatedScreen screen) {
        this.screen = screen;
        this.rigth = true;
        this.growing = true;
        this.t = Math.PI;

    }

    public void run() {
        double speed = 0.07;
        double rotationSpeed = 0.23;
        double scaleSpeed = 0.007;
        double midScale = 0.83;

        screen.xScale = midScale;

        int leftLimit = 200;
        int rightLimit = 800;

        boolean willRotate = false;
        boolean inAreaToRotate = false;

        while (screen.element.equals("air")) {
            // ------------------------------ Movement ------------------------------
            // Trajectory
            int x = 500 + (int) ((((double) rightLimit - (double) leftLimit) / 2) * Math.cos(t));
            int y = 300 + (int) (70 * Math.sin(t));
            screen.xAang = x;
            screen.yAang = y;

            // Direction
            if (t >= 0 && t <= Math.PI) {
                if (this.rigth) {
                    screen.aangMirror = -1;
                    this.rigth = false;
                    screen.xScale = midScale;

                }
            } else {
                if (!this.rigth) {
                    screen.aangMirror = 1;
                    this.rigth = true;
                    screen.xScale = midScale;

                }
            }

            // Scale
            if (!this.rigth) {
                if (screen.xAang > 500) {
                    screen.xScale += scaleSpeed;

                } else {
                    screen.xScale -= scaleSpeed;
                }

            } else {
                if (screen.xAang < 500) {
                    screen.xScale -= scaleSpeed;

                } else {
                    screen.xScale += scaleSpeed;
                }
            }

            t += speed;
            if (t >= 2 * Math.PI) t = 0;

            // ------------------------------ Rotation ------------------------------
            if (this.rigth && screen.xAang > 340 && screen.xAang < 360) {
                if(!inAreaToRotate) {
                    if (willRotate) {
                        aangRotate("left", rotationSpeed);
                        willRotate = false;
                        speed -= 0.04;
                    }
                    else {
                        willRotate = true;
                    }
                    inAreaToRotate = true;
                }
            }
            if(screen.xAang > 360) {
                inAreaToRotate = false;
            }
            if(rotationThread != null && !rotationThread.isAlive()) {
                speed += 0.04;
                rotationThread = null;
            }

            try {
                sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void aangRotate(String direction, double rotationSpeed) {
        rotationThread = new RotateAangThread(screen, direction, rotationSpeed);
        rotationThread.start();
    }

    class RotateAangThread extends Thread {
        public AnimatedScreen screen;
        public String direction;
        public double rotationSpeed;

        public RotateAangThread(AnimatedScreen screen, String direction, double rotationSpeed {
            this.screen = screen;
            this.direction = direction;
            this.rotationSpeed = rotationSpeed;
        }

        public void run() {
            if (direction.equals("left")) {
                screen.angRotate = 2 * Math.PI;
                while (screen.angRotate > 0) {
                    screen.angRotate -= rotationSpeed;

                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                screen.angRotate = 0;
            } else {
                while (screen.angRotate < 2 * Math.PI) {
                    screen.angRotate += rotationSpeed;

                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                screen.angRotate = 0;
            }

        }
    }
}
