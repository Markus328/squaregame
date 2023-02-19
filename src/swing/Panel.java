/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGEntity;
import SimpleGameEngine.SGFrame;
import SimpleGameEngine.SGGameViewManager;
import SimpleGameEngine.SGPanel;
import SimpleGameEngine.SGPublisher;
import SimpleGameEngine.SGRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Set;
import swing.Blob;
import swing.Fruit;
import swing.GameController;
import swing.GameModel;
import swing.Point;
import swing.Square;
import swing.WorldLimitTrigger;

public class Panel
extends SGPanel {
    private Image testImg;
    private GameModel model;
    SGRenderer renderer;
    GameController gc;
    Rectangle mTempSrc = new Rectangle();
    Rectangle mTempDest = new Rectangle();
    Point point;
    Blob square;
    public int spectatingId = 1;
    SGGameViewManager scroll;
    private SGFrame frame;

    public Panel(GameModel model, SGFrame frame) {
        this.model = model;
        this.frame = frame;
    }

    @Override
    protected void setup() {
        super.setup();
        this.model.setup();
        this.square = (Square)this.model.getEntity(4);
        this.renderer = this.getRenderer();
        SGPublisher publisher = new SGPublisher();
        this.frame.registerKeyPublisher(publisher);
        this.frame.registerMousePublisher(publisher);
        this.gc = new GameController(this.model, this);
        publisher.registerSubscriber(this.gc, "controller1");
    }

    @Override
    public void step(double elapsedTimeInSeconds) {
        this.gc.step(elapsedTimeInSeconds);
        this.model.step(elapsedTimeInSeconds);
        this.renderer.create("gameGraph");
        this.renderer.setGraphics("gameGraph");
        this.scroller.setGraphics(this.renderer.getGraphics("gameGraph"));
        if (this.square != null) {
            if (this.square.getDimensions().getWidth() * this.scroller.getScalingFactorX() > 500.0) {
                this.scroller.zoom(-1.0 * this.scroller.getScalingFactorX() * elapsedTimeInSeconds);
            } else if (this.square.getDimensions().getWidth() * this.scroller.getScalingFactorX() < 40.0) {
                this.scroller.zoom(1.0 * this.scroller.getScalingFactorX() * elapsedTimeInSeconds);
            }
        } else if (this.model.getDimensions().getWidth() * this.scroller.getScalingFactorX() > 768.0) {
            this.scroller.zoom(-this.scroller.getScalingFactorX() * elapsedTimeInSeconds);
        }
        int squareCenterX = this.square != null ? (int)(this.square.getPosition().getX() * this.scroller.getScalingFactorX()) + (int)(this.square.getDimensions().getWidth() * this.scroller.getScalingFactorX() / 2.0) : (int)(this.model.getDimensions().getWidth() / 2.0 * this.scroller.getScalingFactorX());
        int squareCenterY = this.square != null ? (int)(this.square.getPosition().getY() * this.scroller.getScalingFactorY()) + (int)(this.square.getDimensions().getHeight() * this.scroller.getScalingFactorY() / 2.0) : (int)(this.model.getDimensions().getHeight() / 2.0 * this.scroller.getScalingFactorY());
        this.scroller.fix(new java.awt.Point(squareCenterX, squareCenterY), new java.awt.Point(this.getWidth() / 2, this.getHeight() / 2));
        this.renderer.beginDrawing();
        Set<Map.Entry<Integer, SGEntity>> entryset = this.model.getEntities().entrySet();
        for (Map.Entry<Integer, SGEntity> entry : entryset) {
            if (entry.getValue().getCategory().equals("Fruit")) {
                this.renderer.fillRect(((Fruit)entry.getValue()).getBoudingBox(), ((Fruit)entry.getValue()).getColor());
                continue;
            }
            if (entry.getValue().getCategory().equals("Blob")) {
                if (entry.getValue().hasFlag(Blob.DEAD)) continue;
                this.renderer.fillRect(entry.getValue().getBoudingBox(), Color.BLACK);
                continue;
            }
            if (!(entry.getValue() instanceof WorldLimitTrigger)) continue;
            this.renderer.fillRect(entry.getValue().getBoudingBox(), Color.MAGENTA);
        }
        this.renderer.dispose("gameGraph");
        if (this.model.getCurrentState() == -2) {
            this.renderer.setGraphics("OriginalGraphics");
            this.renderer.drawString("press space!", this.getWidth() / 2, 60.0f, Color.BLACK, new Font("GUI", 2, 50));
        }
    }
}

