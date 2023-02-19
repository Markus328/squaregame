/*
 * Decompiled with CFR 0.152.
 */
package SimpleGameEngine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class SGImageFactory {
    public Image createImage(String path, Class caller) {
        BufferedImage img = null;
        try {
            InputStream input = caller.getResourceAsStream(path);
            img = ImageIO.read(input);
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return img;
    }
}

