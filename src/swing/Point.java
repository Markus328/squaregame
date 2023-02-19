/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGTimer;
import SimpleGameEngine.SGWorld;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import swing.Blob;
import swing.Fruit;
import swing.GameModel;

public class Point
extends Blob {
    private static final distanceComparator c = new distanceComparator();
    private SGEntity target = this;
    private ArrayList<Blob> scapingBlobs = new ArrayList<Blob>();
    private int randomTargetX;
    private int randomTargetY;
    private static Random r = new Random();
    private SGTimer reactionTimer;
    public static final int LOSED = 16;
    private ArrayList<Blob> reactionedBlobs = new ArrayList<Blob>();
    private ArrayList<Fruit> reactionedFruits = new ArrayList<Fruit>();
    private Rectangle reactionArea = new Rectangle();
    private Rectangle reactionBlob = new Rectangle();
    private int prox = 1;

    public Point(SGWorld world, int id, Point2D position, Dimension2D dimensions) {
        super(world, id, position, dimensions);
        this.reactionTimer = new SGTimer(0.7);
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        super.step(elapsedTimeInSeconds);
        this.velocity.setLocation(0, 0);
        this.reactionTimer.play();
        this.reactionArea.setBounds((int)this.getBoudingBox().getX() - 600, (int)this.getBoudingBox().getY() - 600, 1200 + (int)this.getDimensions().getWidth(), 1200 + (int)this.getDimensions().getHeight());
        this.reactionBlob.setBounds((int)this.getBoudingBox().getX() - 300, (int)this.getBoudingBox().getY() - 300, 600 + (int)this.getDimensions().getWidth(), 600 + (int)this.getDimensions().getHeight());
        this.doAction(this.prox);
        if (this.reactionTimer.step(elapsedTimeInSeconds)) {
            this.reactionTimer.reset();
            this.target = this.doAction(this.prox);
            this.prox = 1;
        }
        double dx = 0.0;
        double dy = 0.0;
        double angle = 0.0;
        dx = this.target.getBoudingBox().getCenterX() - this.getBoudingBox().getCenterX();
        if (this.canMove(dx, dy = this.target.getBoudingBox().getCenterY() - this.getBoudingBox().getCenterY())) {
            angle = Math.atan2(-dy, dx);
        } else {
            ++this.prox;
        }
        if (this.target instanceof Fruit && this.target.colisionTest(this.getBoudingBox())) {
            try {
                this.target = this.reactionedFruits.get(this.reactionedFruits.indexOf(this.target) + 1);
            }
            catch (Exception e) {
                this.target = this;
            }
        } else if (this.target == this) {
            if (!this.hasFlag(LOSED)) {
                this.addFlags(LOSED);
                this.randomTargetX = r.nextInt((int)(this.getWorld().getDimensions().getWidth() - this.getDimensions().getWidth()));
                this.randomTargetY = r.nextInt((int)(this.getWorld().getDimensions().getHeight() - this.getDimensions().getHeight()));
            }
            if (this.canMove(dx = (double)this.randomTargetX - this.getBoudingBox().getCenterX(), dy = (double)this.randomTargetY - this.getBoudingBox().getCenterY())) {
                angle = Math.atan2(-dy, dx);
                this.removeFlags(dx < 10.0 && dy < 10.0 ? LOSED : 0);
            } else {
                this.removeFlags(LOSED);
            }
        }
        this.velocity.setLocation(Math.cos(angle) * this.getSpeed(), -Math.sin(angle) * this.getSpeed());
        this.move(this.velocity.getX() * elapsedTimeInSeconds, this.velocity.getY() * elapsedTimeInSeconds);
    }

    public SGEntity doAction(int prox) {
        c.self = this;
        for (Blob blob : ((GameModel)this.getWorld()).getBlobs()) {
            if (blob == this || !this.reactionBlob.intersects(blob.getBoudingBox())) {
                this.reactionedBlobs.remove(blob);
                this.scapingBlobs.remove(blob);
                continue;
            }
            if (this.reactionedBlobs.contains(blob)) continue;
            this.reactionedBlobs.add(blob);
            if (blob.canEat(this)) {
                if (this.scapingBlobs.contains(blob)) continue;
                this.scapingBlobs.add(blob);
                continue;
            }
            this.scapingBlobs.remove(blob);
        }
        Collections.sort(this.reactionedBlobs, c);
        if (this.reactionedBlobs.size() > prox - 1 && this.canEat(this.reactionedBlobs.get(prox - 1))) {
            return this.reactionedBlobs.get(prox - 1);
        }
        for (Fruit fruit : ((GameModel)this.getWorld()).getFruits()) {
            if (this.reactionArea.intersects(fruit.getBoudingBox()) && !fruit.hasFlag(1)) {
                if (this.reactionedFruits.contains(fruit)) continue;
                this.reactionedFruits.add(fruit);
                continue;
            }
            this.reactionedFruits.remove(fruit);
        }
        if (this.reactionedFruits.size() > prox - 1) {
            Collections.sort(this.reactionedFruits, c);
            return this.reactionedFruits.get(prox - 1);
        }
        return this;
    }

    public boolean canMove(double dx, double dy) {
        if (!this.scapingBlobs.isEmpty()) {
            double angle = 0.0;
            double dbx = 0.0;
            double dby = 0.0;
            double angle2 = 0.0;
            double angleMin = 0.0;
            double angleMax = 0.0;
            angle2 = (Math.atan2(-dy, dx) * 180.0 / Math.PI + 360.0) % 360.0;
            for (Blob b : this.scapingBlobs) {
                dbx = b.getBoudingBox().getCenterX() - this.getBoudingBox().getCenterX();
                dby = b.getBoudingBox().getCenterY() - this.getBoudingBox().getCenterY();
                angle = Math.atan2(dby, -dbx) * 180.0 / Math.PI;
                angleMin = (angle - 90.0 + 360.0) % 360.0;
                angleMax = angleMin + 180.0;
                if (angle2 + (double)(360 * (int)(angleMax / 360.0)) >= angleMin && angle2 + (double)(360 * (int)(angleMax / 360.0)) <= angleMax) continue;
                return false;
            }
        }
        return true;
    }

    private static class distanceComparator
    implements Comparator<SGEntity> {
        private SGEntity self;

        private distanceComparator() {
        }

        @Override
        public int compare(SGEntity o1, SGEntity o2) {
            if (Math.sqrt((o1.getBoudingBox().getCenterX() - this.self.getBoudingBox().getCenterX()) * (o1.getBoudingBox().getCenterX() - this.self.getBoudingBox().getCenterX()) + (o1.getBoudingBox().getCenterY() - this.self.getBoudingBox().getCenterY()) * (o1.getBoudingBox().getCenterY() - this.self.getBoudingBox().getCenterY())) < Math.sqrt((o2.getBoudingBox().getCenterX() - this.self.getBoudingBox().getCenterX()) * (o2.getBoudingBox().getCenterX() - this.self.getBoudingBox().getCenterX()) + (o2.getBoudingBox().getCenterY() - this.self.getBoudingBox().getCenterY()) * (o2.getBoudingBox().getCenterY() - this.self.getBoudingBox().getCenterY()))) {
                return -1;
            }
            return 1;
        }
    }
}

