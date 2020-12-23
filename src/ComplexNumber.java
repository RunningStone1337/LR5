/**
 * Класс для хранения комплексных чисел
 */
public class ComplexNumber {

    private double x; // действительная часть
    private double y; // комплексная часть

    /**
     * конструктор класса по умолчанию
     */
    ComplexNumber() {
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * конструктор класса
     */
    ComplexNumber(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}