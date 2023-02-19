/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import SimpleGameEngine.SGSubscriber;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class SGPublisher
implements KeyListener,
MouseListener {
    protected HashMap<String, SGSubscriber> subs = new HashMap<String, SGSubscriber>();

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().mousePressed(e);
        }
        if (!SGSubscriber.clicks.containsKey(e.getButton())) {
            SGSubscriber.clicks.put(e.getButton(), e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().mouseReleased(e);
        }
        SGSubscriber.clicks.remove(e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().mouseExited(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().keyPressed(e);
        }
        if (!SGSubscriber.keys.containsKey(e.getKeyCode())) {
            SGSubscriber.keys.put(e.getKeyCode(), e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (Map.Entry<String, SGSubscriber> entry : this.subs.entrySet()) {
            entry.getValue().keyReleased(e);
        }
        SGSubscriber.keys.remove(e.getKeyCode());
    }

    public void registerSubscriber(SGSubscriber sub, String controlName) {
        this.subs.put(controlName, sub);
    }

    public static boolean hasCombination(int ... keyCodes) {
        int[] nArray = keyCodes;
        int n = keyCodes.length;
        int n2 = 0;
        while (n2 < n) {
            int code = nArray[n2];
            if (!SGSubscriber.keys.containsKey(code)) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean hasSequencialCombination(int ... keyCodes) {
        int i = 0;
        while (i < keyCodes.length) {
            if (!SGSubscriber.keys.containsKey(keyCodes[i])) return false;
            try {
                if (SGSubscriber.keys.get(keyCodes[i - 1]).getWhen() >= SGSubscriber.keys.get(keyCodes[i]).getWhen()) {
                    return false;
                }
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
            ++i;
        }
        return true;
    }
}

