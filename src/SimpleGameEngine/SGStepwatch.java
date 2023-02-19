/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

public class SGStepwatch {
    protected double mCurrentTime;
    protected double mLastTime;
    protected double mElapsedTime;

    public double tick() {
        if (this.mCurrentTime == 0.0) {
            this.mLastTime = this.mCurrentTime = (double)System.currentTimeMillis();
        } else {
            this.mCurrentTime = System.currentTimeMillis();
        }
        this.mElapsedTime = (this.mCurrentTime - this.mLastTime) / 1000.0;
        this.mLastTime = this.mCurrentTime;
        return this.mElapsedTime;
    }
}

