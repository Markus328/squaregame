/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

public class SGWorld {
    private Dimension2D dimensions = new Dimension();

    public SGWorld(Dimension2D dimensions) {
        this.dimensions.setSize(dimensions);
    }

    public void step(double elapsedTimeInSeconds) {
    }

    public void setup() {
    }

    public Dimension2D getDimensions() {
        return this.dimensions;
    }
}

