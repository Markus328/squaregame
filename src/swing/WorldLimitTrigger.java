/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGTrigger;
import SimpleGameEngine.SGWorld;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class WorldLimitTrigger
extends SGTrigger {
    public WorldLimitTrigger(SGWorld world, int id, Point2D position, Dimension2D dimensions) {
        super(world, id, position, dimensions);
    }

    @Override
    public void onHit(SGEntity entity, double elapsedTimeInSeconds) {
        super.onHit(entity, elapsedTimeInSeconds);
        if (this.getPosition().getY() < 0.0) {
            entity.setPosition(entity.getPosition().getX(), this.getPosition().getY() + this.getDimensions().getHeight());
        } else if (this.getPosition().getY() + this.getDimensions().getHeight() > this.getWorld().getDimensions().getHeight()) {
            entity.setPosition(entity.getPosition().getX(), this.getPosition().getY() - entity.getDimensions().getHeight());
        } else if (this.getPosition().getX() < 0.0) {
            entity.setPosition(this.getPosition().getX() + this.getDimensions().getWidth(), entity.getPosition().getY());
        } else if (this.getPosition().getX() + this.getDimensions().getWidth() > this.getWorld().getDimensions().getWidth()) {
            entity.setPosition(this.getPosition().getX() - entity.getDimensions().getWidth(), entity.getPosition().getY());
        }
    }
}

