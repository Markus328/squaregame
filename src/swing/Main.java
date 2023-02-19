/*
 * Decompiled with CFR 0.152.
 */
package swing;

import SimpleGameEngine.SGFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import swing.GameModel;
import swing.Panel;

public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final SGFrame frame = new SGFrame("Square Game");
        GameModel model = new GameModel(new Dimension(9000, 9000));
        final Panel panel = new Panel(model, frame);
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                frame.add(panel);
                frame.prepare();
            }
        });
    }
}

