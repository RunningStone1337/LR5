import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {

    public static final int MAX = 2000;


    @Override
    public void getInitialRange(Rectangle2D.Double range) {

        range.x = -2.0;
        range.y = -2.0;
        range.height = 4.0;
        range.width = 4.0;
    }

    /**
     * метод нахождения числа итераций для фрактала
     */
    @Override
    public int numIterations(double x, double y) {
        ComplexNumber compN1 = new ComplexNumber(x, y);
        ComplexNumber compN2 = new ComplexNumber();
        int i = 0;
        while (compN2.getX() * compN2.getX() + compN2.getY() * compN2.getY() <= 4 && i < MAX) {
            double tempX = compN2.getX();
            compN2.setX(compN2.getX() * compN2.getX() + compN1.getX() - compN2.getY() * compN2.getY());
            compN2.setY(Math.abs(2 * compN2.getY() * tempX) + compN1.getY());
            i++;
        }
        if (i >= MAX) return -1;
        else return i;
    }

    @Override
    public String toString() {
        return "Burning Ship";
    }
}
