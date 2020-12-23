import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * основной класс для работы с изображениями
 */
public class JImageDisplay extends JComponent {


    private final BufferedImage image;

    /**
     * конструктор класса изображения
     */
    public JImageDisplay(int width, int height) {
        int TYPE_INT_RGB = 1;
        image = new BufferedImage(width, height, TYPE_INT_RGB);
        Dimension SIZE = new Dimension(width, height);
        setPreferredSize(SIZE);
    }


    @Override
    protected void paintComponent (Graphics g) {
        g.drawImage (image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /**
     * установка всех пикселей в чёрный цвет
     */
    public void clearImage() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, 0);
            }
        }
    }

    /**
     * установка определённого пикселя в определённый цвет
     */
    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }

    public BufferedImage getBufferedImage () {
        return image;
    }
}
