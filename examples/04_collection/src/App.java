import java.util.Vector;

public class App {
    public static void main(String[] args) throws Exception {

        Vector<Integer> v = new Vector<Integer>();
        v.add(1);
        v.add(2);

        for (int i = 0; i < v.size(); i++) {
            System.out.println(v.get(i));
        }

        //set the value of the first element
        v.set(0, 3);
        
        System.out.println(v.get(0));

        Object[] _arr = v.toArray();

        System.out.println("--------------------");

        for (int i = 0; i < _arr.length; i++) {
            System.out.println(_arr[i]);
        }

        System.out.println("Hello, World!");
    }
}
