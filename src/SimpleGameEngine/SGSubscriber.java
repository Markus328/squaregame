/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public interface SGSubscriber {
    public static final HashMap<Integer, KeyEvent> keys = new HashMap<Integer, KeyEvent>();
    public static final HashMap<Integer, MouseEvent> clicks = new HashMap<Integer, MouseEvent>();

    public void keyPressed(KeyEvent var1);

    public void keyReleased(KeyEvent var1);

    public void keyTyped(KeyEvent var1);

    public void mouseClicked(MouseEvent var1);

    public void mousePressed(MouseEvent var1);

    public void mouseReleased(MouseEvent var1);

    public void mouseEntered(MouseEvent var1);

    public void mouseExited(MouseEvent var1);
}

