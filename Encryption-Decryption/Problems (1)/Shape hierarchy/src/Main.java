abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {

    double a;
    double b;
    double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getPerimeter() {
        return a + b + c;
    }

    public double getArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt( (s * (s - a) * (s - b) * (s - c) ) );
    }
}

class Rectangle extends Shape {

    double a;
    double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getPerimeter() {
        return ( (a * 2) + (b * 2));
    }

    public double getArea() {
        return (a * b);
    }
}

class Circle extends Shape {

    double a;

    public Circle(double a) {
        this.a = a;
    }

    public double getPerimeter() {
        return 2 * Math.PI * a;
    }

    public double getArea() {
        return Math.PI * Math.pow(a, 2);
    }
}