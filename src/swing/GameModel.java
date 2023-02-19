/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGWorld;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import swing.Blob;
import swing.Fruit;
import swing.Point;
import swing.Square;
import swing.WorldLimitTrigger;

public class GameModel
extends SGWorld {
    public static final int SQUARE_ID = 4;
    private int numOfPoints = 120;
    private ArrayList<Fruit> fruits = new ArrayList<Fruit>();
    public static final int RUNNING_STATE = -1;
    public static final int PAUSED_STATE = -2;
    public static final int UPPER_LIMIT_ID = 0;
    public static final int LOWER_LIMIT_ID = 1;
    public static final int LEFT_LIMIT_ID = 2;
    public static final int RIGHT_LIMIT_ID = 3;

    private int originState = RUNNING_STATE;
    private int currentState = PAUSED_STATE;
    private int numOfFruits = 2000;
    private Random random = new Random();
    private HashMap<Integer, SGEntity> entities = new HashMap<Integer, SGEntity>();
    private ArrayList<Blob> blobs = new ArrayList<Blob>();

    public GameModel(Dimension2D dimension2d) {
        super(dimension2d);
    }

    @Override
    public void setup() {
        super.setup();
        Dimension mTempDimension = new Dimension();
        java.awt.Point mTempPosition = new java.awt.Point();
        ((Dimension2D)mTempDimension).setSize(40, 40);
        ((Point2D)mTempPosition).setLocation(this.random.nextInt((int)this.getDimensions().getWidth()), this.random.nextInt((int)this.getDimensions().getHeight()));
        this.entities.put(SQUARE_ID, new Square(this, SQUARE_ID, mTempPosition, mTempDimension));
        this.blobs.add((Blob)this.entities.get(SQUARE_ID));
        int i = 0;
        while (i < this.numOfPoints) {
            ((Point2D)mTempPosition).setLocation(this.random.nextInt((int)this.getDimensions().getWidth()), this.random.nextInt((int)this.getDimensions().getHeight()));
            this.newPoint(mTempPosition, mTempDimension);
            ++i;
        }
        ((Dimension2D)mTempDimension).setSize(10.0, 10.0);
        i = 0;
        while (i < this.numOfFruits) {
            ((Point2D)mTempPosition).setLocation(this.random.nextInt((int)this.getDimensions().getWidth()), this.random.nextInt((int)this.getDimensions().getHeight()));
            this.entities.put(this.blobs.size() + SQUARE_ID + i, new Fruit(this, this.blobs.size() + SQUARE_ID + i, mTempPosition, mTempDimension));
            this.fruits.add((Fruit)this.entities.get(this.blobs.size() + SQUARE_ID + i));
            ++i;
        }
        ((Dimension2D)mTempDimension).setSize(this.getDimensions().getWidth(), 60.0);
        ((Point2D)mTempPosition).setLocation(0.0, -60.0);
        this.entities.put(0, new WorldLimitTrigger(this, 0, mTempPosition, mTempDimension));
        ((Point2D)mTempPosition).setLocation(0.0, this.getDimensions().getHeight());
        this.entities.put(1, new WorldLimitTrigger(this, 1, mTempPosition, mTempDimension));
        ((Dimension2D)mTempDimension).setSize(60.0, this.getDimensions().getHeight());
        ((Point2D)mTempPosition).setLocation(-60.0, 0.0);
        this.entities.put(2, new WorldLimitTrigger(this, 2, mTempPosition, mTempDimension));
        ((Point2D)mTempPosition).setLocation(this.getDimensions().getWidth(), 0.0);
        this.entities.put(3, new WorldLimitTrigger(this, 3, mTempPosition, mTempDimension));
        SGEntity[] blob = new SGEntity[this.blobs.size()];
        ((WorldLimitTrigger)this.entities.get(0)).addObservedEntities(this.blobs.toArray(blob));
        ((WorldLimitTrigger)this.entities.get(1)).addObservedEntities(this.blobs.toArray(blob));
        ((WorldLimitTrigger)this.entities.get(2)).addObservedEntities(this.blobs.toArray(blob));
        ((WorldLimitTrigger)this.entities.get(3)).addObservedEntities(this.blobs.toArray(blob));
    }

    public SGEntity getEntity(int id) {
        return this.entities.get(id);
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        super.step(elapsedTimeInSeconds);
        if (this.currentState == RUNNING_STATE) {
            Set<Map.Entry<Integer, SGEntity>> entryset = this.entities.entrySet();
            for (Map.Entry<Integer, SGEntity> entry : entryset) {
                if (!entry.getValue().isActive() || entry.getValue().hasFlag(Blob.DEAD)) continue;
                entry.getValue().step(elapsedTimeInSeconds);
            }
        }
    }

    public void resetWorld() {
        this.entities.get(SQUARE_ID).setPosition(0.0, 0.0);
    }

    public HashMap<Integer, SGEntity> getEntities() {
        return this.entities;
    }

    public int getCurrentState() {
        return this.currentState;
    }

    public void pause() {
        if (this.getEntity(SQUARE_ID).hasFlag(Blob.DEAD)) {
            this.getEntity(SQUARE_ID).removeFlags(Blob.DEAD);
            this.getEntity(SQUARE_ID).addFlags(Blob.RESPAWING);
        } else if (this.currentState != PAUSED_STATE) {
            this.originState = this.currentState;
            this.currentState = PAUSED_STATE;
        } else {
            this.currentState = this.originState;
        }
    }

    public void newPoint(Point2D position, Dimension2D dimensions) {
        Point newb = new Point(this, this.blobs.size() + SQUARE_ID, position, dimensions);
        this.entities.put(this.blobs.size() + SQUARE_ID, newb);
        this.blobs.add(newb);
    }

    public ArrayList<Blob> getBlobs() {
        return this.blobs;
    }

    public ArrayList<Fruit> getFruits() {
        return this.fruits;
    }
}

