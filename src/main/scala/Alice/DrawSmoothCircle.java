package Alice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawSmoothCircle {
    public static void main(String[] argv) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setPaint(Color.green);
        g2d.fillOval(10, 10, 50, 50);
        g2d.dispose();

        ImageIO.write(bufferedImage, "png", new File("newimage3.png"));
    }
}