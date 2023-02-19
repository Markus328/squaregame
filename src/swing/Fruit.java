/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGTimer;
import SimpleGameEngine.SGWorld;
import java.awt.Color;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Random;
import swing.Blob;
import swing.GameModel;

public class Fruit
extends SGEntity {
    public static final int EATEN = 1;
    private SGTimer replaceTime;
    private SGTimer lifeTime;
    private Color color;
    private double nutritivity = 1.0;
    private Random random = new Random();

    public Fruit(SGWorld world, int id, Point2D position, Dimension2D dimensions) {
        super(world, id, "Fruit", position, dimensions);
        this.replaceTime = new SGTimer(3.0);
        this.lifeTime = new SGTimer(this.random.nextInt(40) + 20);
        this.color = Color.RED;
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        super.step(elapsedTimeInSeconds);
        if (!this.hasFlag(1)) {
            GameModel model = (GameModel)this.getWorld();
            this.lifeTime.play();
            if (this.lifeTime.step(elapsedTimeInSeconds)) {
                this.lifeTime.reset();
                this.addFlags(1);
            }
        } else {
            this.replaceTime.play();
            if (this.color == Color.RED) {
                this.color = new Color(138, 76, 44);
            }
            if (this.replaceTime.step(elapsedTimeInSeconds)) {
                this.replaceTime.reset();
                this.removeFlags(1);
                this.setPosition(this.random.nextDouble() * (this.getWorld().getDimensions().getWidth() - this.getDimensions().getWidth()), this.random.nextDouble() * (this.getWorld().getDimensions().getHeight() - this.getDimensions().getHeight()));
                this.color = Color.RED;
            }
        }
    }

    public Color getColor() {
        return this.color;
    }

    public double getNutritivity() {
        return this.nutritivity;
    }

    public void grownBlob(Blob blob) {
        if (!this.hasFlag(1)) {
            blob.setPendentDimensions(blob.getPendentDimensions().getWidth() + this.nutritivity, blob.getPendentDimensions().getHeight() + this.nutritivity);
            this.addFlags(1);
        }
    }
}

