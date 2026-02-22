package collection;

import java.util.HashMap;
import java.util.Map;

public class ex02 {
    public static void main(String[] args) {
        Map<String, String> dict = new HashMap<>();
        dict.put("Network", "network");
        dict.put("Thread", "thread");

        for (String key : dict.keySet()) {
            System.out.println(key + " : " + dict.get(key));
        }
    }
}
