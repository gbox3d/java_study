public class exam01 {
    String name = "exam01";
    String description = "This is exam01";

    public exam01() {
        System.out.println("exam01 생성자 호출");
    }

    public exam01(String name, String description) {
        this.name = name;
        this.description = description;
        System.out.println("exam01 생성자 호출");
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        exam01 exam01 = new exam01();
        System.out.println(exam01.name);
        System.out.println(exam01.description);

        exam01 exam02 = new exam01("exam02", "This is exam02");
        System.out.println(exam02.name);
        System.out.println(exam02.description);
    }
}
