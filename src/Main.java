import com.clone.Animal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        Animal animal1 = new Animal("老虎");
        Animal animal2 = new Animal("老虎");
        System.out.println(animal1.equals(animal2));
        System.out.println(animal1==animal2);
        String s1 = "老虎";
        String s2 = "老虎";
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);

        String s3 = new String("老虎");
        String s4 = new String("老虎");
        System.out.println(s3.equals(s4));
        System.out.println(s3 == s4);
    }
}
