/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import java.awt.Graphics2D;
import java.awt.Point;

public class SGGameViewManager {
    private Graphics2D graphics;
    private double moveAccumulatorx;
    private double moveAccumulatory;
    private int originX = 0;
    private int originY = 0;
    private double scalingFactorX = 1.0;
    private double scalingFactorY = 1.0;

    public void scroll(double pixelsRightX, double pixelsDownY) {
        boolean resetX = false;
        boolean resetY = false;
        double restX = 0.0;
        double restY = 0.0;
        if ((int)(this.moveAccumulatorx + pixelsRightX) != (int)this.moveAccumulatorx) {
            restX = this.moveAccumulatorx + pixelsRightX - (double)((int)(this.moveAccumulatorx + pixelsRightX));
            resetX = true;
        }
        if ((int)(this.moveAccumulatory + pixelsDownY) != (int)this.moveAccumulatory) {
            restY = this.moveAccumulatory + pixelsDownY - (double)((int)(this.moveAccumulatory + pixelsDownY));
            resetY = true;
        }
        this.moveAccumulatorx += pixelsRightX;
        this.moveAccumulatory += pixelsDownY;
        this.originX -= (int)this.moveAccumulatorx;
        this.originY -= (int)this.moveAccumulatory;
        this.graphics.translate(this.originX, this.originY);
        if (resetX) {
            this.moveAccumulatorx = restX;
        }
        if (resetY) {
            this.moveAccumulatory = restY;
        }
    }

    public void setGraphics(Graphics2D graphics2d) {
        this.graphics = graphics2d;
    }

    public int getOriginX() {
        return this.originX;
    }

    public int getOriginY() {
        return this.originY;
    }

    public void fix(Point worldPosition, Point panelPosition) {
        this.originX = panelPosition.x - worldPosition.x;
        this.originY = panelPosition.y - worldPosition.y;
        this.graphics.translate(this.originX, this.originY);
    }

    public double getScalingFactorX() {
        return this.scalingFactorX;
    }

    public double getScalingFactorY() {
        return this.scalingFactorY;
    }

    public void zoom(double scalingFactor) {
        this.scalingFactorX += scalingFactor;
        this.scalingFactorY += scalingFactor;
    }
}

