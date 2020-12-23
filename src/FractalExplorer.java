import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Класс пользовательского интерфейса для отображения фракталов
 */
public class FractalExplorer {

    // Приватные поля класса
    private final Rectangle2D.Double aDouble;
    private FractalGenerator fractalGenerator;
    private JImageDisplay imageDisplay;
    private final int size;

    /**
     * Конструктор класса
     * @param size размер окна
     */
    public FractalExplorer (int size){
        if (size <= 0) throw new IllegalArgumentException("Size could be greater than 0, now " + size);
        else this.size = size;
        aDouble = new Rectangle2D.Double();
        new Mandelbrot().getInitialRange(aDouble);
        fractalGenerator = new Mandelbrot();
    }

    /**
     * метод для создания пользовательского интерфейса
     */
    private void createAndShowGUI() {
        //  установки для окна по умолчанию
        JFrame frame = new JFrame("Fractal Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        fractalGenerator.getInitialRange(aDouble);

        // установки настроек изображения
        contentPane.setLayout(new BorderLayout());
        imageDisplay = new JImageDisplay(size, size);
        imageDisplay.addMouseListener(new MyMouseListener().mouseListener);
        contentPane.add(imageDisplay, BorderLayout.CENTER);

        // кнопка сброса изображения
        JButton resetButton = new JButton("Reset Display");
        resetButton.addActionListener(e -> {
                    imageDisplay.clearImage();
                    fractalGenerator.getInitialRange(aDouble);
                    drawFractal();
                }
        );

        // кнопка сохранения изображения
        JButton saveButton = new JButton("Save Image");
        saveButton.addActionListener(e -> {
                    JFileChooser jFileChooser = new JFileChooser();
                    FileFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
                    jFileChooser.setFileFilter(fileFilter);
                    jFileChooser.setAcceptAllFileFilterUsed(false);
                    if (jFileChooser.showDialog(frame, "Save") == JFileChooser.APPROVE_OPTION) {
                        try {
                            ImageIO.write(imageDisplay.getBufferedImage(), ".png", jFileChooser.getSelectedFile());
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(frame, ioException.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        );

        // Надстройки для вывода на окне кнопок сброса и сохранения
        JPanel jPanelForButtons = new JPanel();
        jPanelForButtons.add(resetButton);
        jPanelForButtons.add(saveButton);
        contentPane.add(jPanelForButtons, BorderLayout.SOUTH);

        // выбор фрактала
        JComboBox<FractalGenerator> jComboBox = new JComboBox<>();
        jComboBox.addItem(new Mandelbrot());
        jComboBox.addItem(new Tricorn());
        jComboBox.addItem(new BurningShip());
        jComboBox.addActionListener(e -> {
            fractalGenerator = (FractalGenerator) jComboBox.getSelectedItem();
            if (fractalGenerator == null) throw new NullPointerException("Select type of fractal.");
            else {
                fractalGenerator.getInitialRange(aDouble);
                drawFractal();
                imageDisplay.repaint();
            }
        });

        // Надстройки для вывода на окне выбора фракталов
        JPanel jPanelForComboBox = new JPanel();
        jPanelForComboBox.add(new JLabel("Fractal"));
        jPanelForComboBox.add(jComboBox);
        contentPane.add(jPanelForComboBox, BorderLayout.NORTH);

        // Свойства окна
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }


    private void drawFractal() {
        double xCoord;
        double yCoord;
        int numIters;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                xCoord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, size, i);
                yCoord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, size, j);
                numIters = fractalGenerator.numIterations(xCoord, yCoord);
                if (numIters == -1) imageDisplay.drawPixel(i, j, 0);
                else {
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    imageDisplay.drawPixel(i, j, rgbColor);
                }
            }
        }
        imageDisplay.repaint();
    }

    private class MyMouseListener extends MouseAdapter {

        public MouseListener mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (size >= 0) {
                    double xCord;
                    double yCord;
                    xCord = FractalGenerator.getCoord(aDouble.x, aDouble.x + aDouble.width, size, e.getX());
                    yCord = FractalGenerator.getCoord(aDouble.y, aDouble.y + aDouble.height, size, e.getY());
                    FractalGenerator.recenterAndZoomRange(aDouble, xCord, yCord, 0.5);
                    drawFractal();
                }
            }


            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

        };

    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(600);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}