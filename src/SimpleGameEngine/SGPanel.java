/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import SimpleGameEngine.SGGameViewManager;
import SimpleGameEngine.SGImageFactory;
import SimpleGameEngine.SGRenderer;
import SimpleGameEngine.SGStepwatch;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class SGPanel
extends JPanel {
    protected SGRenderer renderer;
    protected SGGameViewManager scroller;
    protected SGImageFactory imageFactory;
    SGStepwatch stepwatch = new SGStepwatch();

    public SGPanel() {
        this.imageFactory = new SGImageFactory();
        this.scroller = new SGGameViewManager();
        this.renderer = new SGRenderer(this.scroller);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.renderer.setOriginalGraphics(g);
        this.scroller.setGraphics((Graphics2D)g);
        this.step(this.stepwatch.tick());
        try {
          
        Thread.sleep(1000/60);
        } catch (InterruptedException e) {
        }
        this.repaint();
    }

    protected void step(double elapsedTimeInSeconds) {
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.setup();
    }

    protected void setup() {
    }

    public SGRenderer getRenderer() {
        return this.renderer;
    }

    public SGImageFactory getImageFactory() {
        return this.imageFactory;
    }
}

