import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("F:\\leetcode\\numlist.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String s = sc.next();
            System.out.println(s);
        }

    }
}
