/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGPublisher;
import SimpleGameEngine.SGSubscriber;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import swing.GameModel;
import swing.Panel;

public class GameController
implements SGSubscriber {
    GameModel model;
    Panel panel;

    public GameController(GameModel model, Panel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (SGPublisher.hasSequencialCombination(17) && e.getKeyCode() == 82) {
            this.panel.square = null;
        }
        if (e.getKeyCode() == 32) {
            this.model.pause();
        }
        if (e.getKeyCode() == 27 && System.currentTimeMillis() - SGSubscriber.keys.get(e.getKeyCode()).getWhen() < 200L) {
            this.panel.spectatingId = this.panel.spectatingId + 1 < this.model.getBlobs().size() ? this.panel.spectatingId + 1 : 0;
            this.panel.square = this.model.getBlobs().get(this.panel.spectatingId);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void step(double elapsedTimeInSeconds) {
        if (this.model.getCurrentState() == -1) {
            try {
                double dx = this.panel.getMousePosition().x - this.panel.getWidth() / 2;
                double dy = this.panel.getMousePosition().y - this.panel.getHeight() / 2;
                double angle = Math.atan2(-dy, dx);
                this.panel.square.velocity.setLocation(0, 0);
                if (Math.sqrt(dx * dx + dy * dy) > 60.0) {
                    this.panel.square.velocity.setLocation(Math.cos(angle) * this.panel.square.getSpeed(), -Math.sin(angle) * this.panel.square.getSpeed());
                }
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
    }
}

