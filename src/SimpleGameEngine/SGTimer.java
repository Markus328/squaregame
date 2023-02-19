/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

public class SGTimer {
    double time = 0.0;
    double actualTime = 0.0;
    boolean isRunning = false;

    public SGTimer(double time) {
        this.time = time;
        this.actualTime = time;
    }

    public boolean step(double elapsedTimeInSeconds) {
        if (this.isRunning) {
            this.actualTime -= elapsedTimeInSeconds;
            if (this.actualTime <= 0.0) {
                return true;
            }
        }
        return false;
    }

    public void play() {
        this.isRunning = true;
    }

    public void pause() {
        this.isRunning = false;
    }

    public void reset() {
        this.isRunning = false;
        this.actualTime = this.time;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public double getActualTime() {
        return this.actualTime;
    }
}

