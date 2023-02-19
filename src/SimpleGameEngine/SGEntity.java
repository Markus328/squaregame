/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import SimpleGameEngine.SGWorld;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class SGEntity {
    private int mFlags;
    private int id;
    private double moveAccumulatorx;
    private double moveAccumulatory;
    private double growAccumulatorW;
    private double growAccumulatorH;
    private Point2D position = new Point();
    private Dimension2D dimensions = new Dimension();
    private String category;
    private boolean isActive = true;
    private SGWorld world;
    private Rectangle2D bBoxPadding = new Rectangle();
    private Rectangle2D boundingBox = new Rectangle();
    private DebugDrawingStyle debugDrawingStyle;
    private int debugColor;

    public SGEntity(SGWorld world, int id, String category, Point2D position, Dimension2D dimensions) {
        this.world = world;
        this.id = id;
        this.category = category;
        this.position.setLocation(position);
        this.dimensions.setSize(dimensions);
        this._updateBoundingBox();
    }

    public void addFlags(int flags) {
        this.mFlags |= flags;
    }

    public boolean hasFlag(int flag) {
        return (this.mFlags & flag) != 0;
    }

    public void removeFlags(int flags) {
        this.mFlags &= (flags ^= 0xFFFFFFFF);
    }

    public void move(double offsetX, double offsetY) {
        boolean resetX = false;
        boolean resetY = false;
        double restX = 0.0;
        double restY = 0.0;
        if ((int)(this.moveAccumulatorx + offsetX) != (int)this.moveAccumulatorx) {
            restX = this.moveAccumulatorx + offsetX - (double)((int)(this.moveAccumulatorx + offsetX));
            resetX = true;
        }
        if ((int)(this.moveAccumulatory + offsetY) != (int)this.moveAccumulatory) {
            restY = this.moveAccumulatory + offsetY - (double)((int)(this.moveAccumulatory + offsetY));
            resetY = true;
        }
        this.moveAccumulatorx += offsetX;
        this.moveAccumulatory += offsetY;
        this.setPosition(this.getPosition().getX() + (double)((int)this.moveAccumulatorx), this.getPosition().getY() + (double)((int)this.moveAccumulatory));
        if (resetX) {
            this.moveAccumulatorx = restX;
        }
        if (resetY) {
            this.moveAccumulatory = restY;
        }
    }

    public String getCategory() {
        return this.category;
    }

    public void grow(double offsetW, double offsetH) {
        boolean resetX = false;
        boolean resetY = false;
        double restX = 0.0;
        double restY = 0.0;
        if ((int)(this.growAccumulatorW + offsetW) != (int)this.growAccumulatorW) {
            restX = this.growAccumulatorW + offsetW - (double)((int)(this.growAccumulatorW + offsetW));
            resetX = true;
        }
        if ((int)(this.growAccumulatorH + offsetH) != (int)this.growAccumulatorH) {
            restY = this.growAccumulatorH + offsetH - (double)((int)(this.growAccumulatorH + offsetH));
            resetY = true;
        }
        this.growAccumulatorW += offsetW;
        this.growAccumulatorH += offsetH;
        this.setDimensions(this.getDimensions().getWidth() + (double)((int)this.growAccumulatorW), this.getDimensions().getHeight() + (double)((int)this.growAccumulatorH));
        if (resetX) {
            this.growAccumulatorW = restX;
        }
        if (resetY) {
            this.growAccumulatorH = restY;
        }
    }

    public int getDebugColor() {
        return this.debugColor;
    }

    public DebugDrawingStyle getDebugDrawningStyle() {
        return this.debugDrawingStyle;
    }

    public Dimension2D getDimensions() {
        return this.dimensions;
    }

    public int getId() {
        return this.id;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public SGWorld getWorld() {
        return this.world;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDebugColor(int color) {
        this.debugColor = color;
    }

    public void setDebugDrawingStyle(DebugDrawingStyle drawingStyle) {
        this.debugDrawingStyle = drawingStyle;
    }

    public void setDimensions(double x, double y) {
        this.dimensions.setSize(x, y);
        this._updateBoundingBox();
    }

    public void setDimensions(Dimension2D dimensions) {
        this.dimensions.setSize(dimensions);
        this._updateBoundingBox();
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setPosition(double x, double y) {
        this.position.setLocation(x, y);
        this._updateBoundingBox();
    }

    public void setPosition(Point2D position) {
        this.position.setLocation(position);
        this._updateBoundingBox();
    }

    public void _updateBoundingBox() {
        this.boundingBox.setRect(this.getPosition().getX() + this.bBoxPadding.getX(), this.getPosition().getY() + this.bBoxPadding.getY(), this.getDimensions().getWidth() - this.bBoxPadding.getWidth(), this.getDimensions().getHeight() - this.bBoxPadding.getHeight());
    }

    public void step(double elapsedTimeInSeconds) {
    }

    public void setup() {
    }

    public boolean colisionTest(Rectangle2D r1) {
        return this.boundingBox.intersects(r1);
    }

    public Rectangle2D getBoudingBox() {
        return this.boundingBox;
    }

    public Rectangle2D getbBoxPadding() {
        return this.bBoxPadding;
    }

    public void setbBoxPadding(Rectangle2D bBoxPadding) {
        this.bBoxPadding.setRect(bBoxPadding);
        this._updateBoundingBox();
    }

    public void setbBoxPadding(double x, double y, double w, double h) {
        this.bBoxPadding.setRect(x, y, w, h);
        this._updateBoundingBox();
    }

    public static enum DebugDrawingStyle {
        FILLED,
        OUTLINE;

    }
}

