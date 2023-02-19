/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGWorld;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SGTrigger
extends SGEntity {
    private final ArrayList<SGEntity> observedEntities = new ArrayList<SGEntity>();

    public SGTrigger(SGWorld world, int id, Point2D position, Dimension2D dimensions) {
        super(world, id, "trigger", position, dimensions);
    }

    public void addObservedEntities(SGEntity ... entities) {
        SGEntity[] sGEntityArray = entities;
        int n = entities.length;
        int n2 = 0;
        while (n2 < n) {
            SGEntity entity = sGEntityArray[n2];
            this.observedEntities.add(entity);
            ++n2;
        }
    }

    public void removeObservedEntities(SGEntity ... entities) {
        SGEntity[] sGEntityArray = entities;
        int n = entities.length;
        int n2 = 0;
        while (n2 < n) {
            SGEntity entity = sGEntityArray[n2];
            this.observedEntities.remove(entity);
            ++n2;
        }
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        super.step(elapsedTimeInSeconds);
        for (SGEntity entity : this.observedEntities) {
            if (!this.colisionTest(entity.getBoudingBox())) continue;
            this.onHit(entity, elapsedTimeInSeconds);
        }
    }

    public void onHit(SGEntity entity, double elapsedTimeInSeconds) {
    }
}

