
 // public 은 사용할 수 없다.
class TestClass01 {
    public int a;
    public int b;
    public TestClass01(int a, int b) {
        this.a = a;
        this.b = b;
    }
    public void print() {
        System.out.println("a: " + a + ", b: " + b);
    }

    //method overloading
    int getSum() {
        return a + b;
    }
    int getSum(int a,int b) {
        this.a = a;
        this.b = b;
        return a + b;
    }

    int getSum(int a,int b, int c) {
        this.a = a;
        this.b = b;
        return a + b + c;
    }

}

public class exam02 {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        TestClass01 testClass01 = new TestClass01(10, 20);
        testClass01.print();
    }
    
}
