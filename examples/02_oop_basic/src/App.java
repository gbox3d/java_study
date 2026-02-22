public class App {

    // Inner classes
    public class Circle {

        public void print() {
         System.out.println("This is a circle");
     }
    }

    public class Square {

        public void print() {
         System.out.println("This is a square");
     }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        App app = new App();

        Circle circle = app.new Circle();
        circle.print();

        Square square = app.new Square();
        square.print();


    }
}
