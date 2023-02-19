/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import SimpleGameEngine.SGGameViewManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class SGRenderer {
    private Graphics2D graphics;
    private Rectangle2D rect2d = new Rectangle().getBounds2D();
    private SGGameViewManager scroller;
    private HashMap<String, Graphics2D> allGraphics = new HashMap<String, Graphics2D>();

    public SGRenderer(SGGameViewManager scroller) {
        this.scroller = scroller;
    }

    public void fillRect(Rectangle2D r, Color color) {
        this.graphics.setColor(color);
        this.rect2d.setRect((int)(r.getX() * this.scroller.getScalingFactorX()), (int)(r.getY() * this.scroller.getScalingFactorY()), (int)(this.scroller.getScalingFactorX() * r.getWidth()), (int)(r.getHeight() * this.scroller.getScalingFactorY()));
        this.graphics.fill(this.rect2d);
    }

    public void fillRect(double x, double y, double width, double height, Color color) {
        this.graphics.setColor(color);
        this.rect2d.setRect(x * this.scroller.getScalingFactorX(), y * this.scroller.getScalingFactorY(), width * this.scroller.getScalingFactorX(), height * this.scroller.getScalingFactorY());
        this.graphics.fill(this.rect2d);
    }

    public void drawString(String text, float x, float y, Color color, Font font) {
        this.graphics.setColor(color);
        if (font != null) {
            this.graphics.setFont(font);
        }
        this.graphics.drawString(text, x, y);
    }

    public void drawString(String text, Point2D dimensions, Color color, Font font) {
        this.graphics.setColor(color);
        if (font != null) {
            this.graphics.setFont(font);
        }
        this.graphics.drawString(text, (float)dimensions.getX(), (float)dimensions.getY());
    }

    public void drawImage(Image img, Rectangle source, Rectangle destination) {
        if (this.graphics.getClipBounds().intersects(destination)) {
            this.graphics.drawImage(img, destination.x, destination.y, destination.x + destination.width, destination.y + destination.height, source.x, source.y, source.x + source.width, source.y + source.height, null);
        }
    }

    public void setGraphics(String name) {
        if (this.allGraphics.get(name) != null) {
            this.graphics = this.allGraphics.get(name);
        }
    }

    public void rotate(double angle) {
        this.graphics.rotate(angle / 180.0 * Math.PI);
    }

    public void rotate(double angle, double x, double y) {
        this.graphics.rotate(angle / 180.0 * Math.PI, x, y);
    }

    public void rotate(double angle, Point2D translateTo) {
        this.graphics.rotate(angle / 180.0 * Math.PI, translateTo.getX(), translateTo.getY());
    }

    public void setClip(int x, int y, int width, int height) {
        this.graphics.clipRect(x, y, width, height);
    }

    public Rectangle getClipBounds() {
        return this.graphics.getClipBounds();
    }

    public void beginDrawing() {
        for (Map.Entry<String, Graphics2D> entry : this.allGraphics.entrySet()) {
            entry.getValue().clearRect(-this.scroller.getOriginX(), -this.scroller.getOriginY(), Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        }
    }

    public void dispose(String name) {
        this.allGraphics.get(name).dispose();
    }

    public void create(String name) {
        this.allGraphics.put(name, (Graphics2D)this.graphics.create());
    }

    public Graphics2D getGraphics(String name) {
        return this.allGraphics.get(name);
    }

    public void setOriginalGraphics(Graphics g) {
        this.graphics = (Graphics2D)g;
        this.allGraphics.put("OriginalGraphics", this.graphics);
    }
}

