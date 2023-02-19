/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGWorld;
import java.awt.Point;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import swing.Blob;

public class Square
extends Blob {
    private Point mousePosition = new Point();

    public Square(SGWorld world, int id, Point2D position, Dimension2D dimensions) {
        super(world, id, position, dimensions);
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        super.step(elapsedTimeInSeconds);
        this.move(this.velocity.getX() * elapsedTimeInSeconds, this.velocity.getY() * elapsedTimeInSeconds);
    }
}

