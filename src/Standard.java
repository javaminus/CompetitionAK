import java.util.Scanner;

public class Standard {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int n = sc.nextInt();
            if (n >= 111 * (n % 11)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}