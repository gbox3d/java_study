public class exam03 {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        //같은 폴더에 있는 클래스를 사용할 때는 public을 사용하지 않아도 된다.
        TestClass01 testClass01 = new TestClass01(10, 20);
        testClass01.print();

        //method overloading test
        System.out.printf("%d \n",testClass01.getSum());
        System.out.printf("%d \n",testClass01.getSum(30,40));
        System.out.printf("%d \n",testClass01.getSum(50,60,70));

    }
}
