/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import SimpleGameEngine.SGPublisher;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.JFrame;

public class SGFrame
extends JFrame {
    HashMap<String, SGPublisher> publishers = new HashMap<String, SGPublisher>();

    public SGFrame() {
        super("Window");
        this.setFocusable(true);
        this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    }

    public SGFrame(String name) {
        super(name);
        this.setFocusable(true);
        this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    }

    public void prepare() {
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
    }

    public void registerKeyPublisher(KeyListener listener) {
        this.addKeyListener(listener);
    }

    public void registerMousePublisher(MouseListener listener) {
        this.addMouseListener(listener);
    }
}

