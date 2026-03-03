import mypack.AtomicInteger;
import mypack.test1;
// import lib;

public class App {

    static void testFunc(AtomicInteger a) {
        a.valueOf(10);
        System.out.println("testFunc: " + a);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        test1.print();
        // lib.libtest1.print();
        // lib.test2.libtest1.print();

        AtomicInteger a = new AtomicInteger(9);  //call by reference

        testFunc(a);

        System.out.println("main: " + a);

        
    }
}
