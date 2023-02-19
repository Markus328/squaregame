/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGTimer;
import SimpleGameEngine.SGWorld;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import swing.Fruit;
import swing.GameModel;

public class Blob
extends SGEntity {
    private SGTimer spawnTimer;
    private double speed = 300.0;
    protected Point velocity = new Point();
    private Dimension pendentDimensions = new Dimension();
    private Rectangle blobBoundingBox = new Rectangle();
    public static int EATABLE = 1;
    public static int DEAD = 2;
    public static int POWERED = 4;
    public static int RESPAWING = 8;
    private int maxSize = 2000;
    private SGTimer weaknessInterval;
    private double size;

    public Blob(SGWorld world, int id, Point2D position, Dimension2D dimensions) {
        super(world, id, "Blob", position, dimensions);
        this.spawnTimer = new SGTimer(3.0);
        this.weaknessInterval = new SGTimer(0.5);
        this.updateSize();
        this.pendentDimensions.setSize(dimensions);
        this.blobBoundingBox.setBounds((int)(this.getBoudingBox().getX() + this.getDimensions().getWidth() / 3.0), (int)(this.getBoudingBox().getY() + this.getDimensions().getHeight() / 3.0), (int)(this.getDimensions().getWidth() / 3.0), (int)(this.getDimensions().getHeight() / 3.0));
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        super.step(elapsedTimeInSeconds);
        this.blobBoundingBox.setBounds((int)(this.getPosition().getX() + 2.0 * this.getDimensions().getWidth() / 5.0), (int)(this.getPosition().getY() + 2.0 * this.getDimensions().getHeight() / 5.0), (int)(this.getDimensions().getWidth() / 5.0), (int)(this.getDimensions().getHeight() / 5.0));
        if (this.hasFlag(RESPAWING)) {
            this.respawn();
            this.removeFlags(RESPAWING);
        }
        if (!this.hasFlag(EATABLE)) {
            this.spawnTimer.play();
            if (this.spawnTimer.step(elapsedTimeInSeconds)) {
                this.spawnTimer.reset();
                this.addFlags(EATABLE);
            }
        }
        if (this.hasFlag(EATABLE)) {
            ArrayList<Blob> blobs = ((GameModel)this.getWorld()).getBlobs();
            for (Blob b : blobs) {
                if (b == this || !b.colisionTest(this.blobBoundingBox) || this.canEat(b) || !b.canEat(this)) continue;
                if (!this.hasFlag(DEAD)) {
                    b.eat(this);
                }
                if (this.getId() == 4) {
                    this.addFlags(DEAD);
                    continue;
                }
                this.addFlags(RESPAWING);
            }
        }
        if (!this.hasFlag(DEAD) && !this.hasFlag(RESPAWING)) {
            ArrayList<Fruit> fruits = ((GameModel)this.getWorld()).getFruits();
            for (Fruit fruit : fruits) {
                if (!this.colisionTest(fruit.getBoudingBox())) continue;
                fruit.grownBlob(this);
            }
        }
        if (this.hasFlag(POWERED)) {
            this.weaknessInterval.play();
            if (this.weaknessInterval.step(elapsedTimeInSeconds)) {
                this.weaknessInterval.reset();
                this.setPendentDimensions(this.pendentDimensions.getWidth() - 10.0, this.pendentDimensions.getHeight() - 10.0);
            }
        }
        if (this.getDimensions().getWidth() > (double)this.maxSize) {
            this.addFlags(POWERED);
        } else if (this.pendentDimensions.getWidth() < (double)(this.maxSize / 4)) {
            this.removeFlags(POWERED);
        }
        this.doGrown(elapsedTimeInSeconds);
    }

    public boolean canEat(Blob blob) {
        return this.size >= blob.getSize() * 1.2 && !blob.hasFlag(DEAD);
    }

    public double getSize() {
        return this.size;
    }

    private void respawn() {
        Random r = new Random();
        this.setPosition(r.nextInt((int)(this.getWorld().getDimensions().getWidth() - this.getDimensions().getWidth())), r.nextInt((int)(this.getWorld().getDimensions().getHeight() - this.getDimensions().getHeight())));
        this.setDimensions(40.0, 40.0);
        this.setPendentDimensions(40.0, 40.0);
        this.removeFlags(EATABLE);
    }

    public void updateSize() {
        this.size = this.getDimensions().getWidth() * this.getDimensions().getHeight();
    }

    public void setPendentDimensions(Dimension2D dimensions) {
        this.pendentDimensions.setSize(dimensions);
    }

    public void setPendentDimensions(double x, double y) {
        this.pendentDimensions.setSize(x, y);
    }

    public void eat(Blob blob) {
        this.setPendentDimensions(blob.getDimensions().getWidth() / 2.0 + this.pendentDimensions.getWidth(), blob.getDimensions().getHeight() / 2.0 + this.pendentDimensions.getHeight());
    }

    public void doGrown(double elapsedtimeInSeonds) {
        this.grow(3.0 * (this.pendentDimensions.getWidth() - this.getDimensions().getWidth()) * elapsedtimeInSeonds, 3.0 * (this.pendentDimensions.getHeight() - this.getDimensions().getHeight()) * elapsedtimeInSeonds);
        this.updateSize();
        this.setSpeed(300.0 - 5.0 * Math.sqrt(this.size) / 40.0);
    }

    public Dimension getPendentDimensions() {
        return this.pendentDimensions;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

