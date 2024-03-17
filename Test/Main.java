import java.util.Scanner;

public class Main {
    static final int M = (int) 1e9 + 7;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n + 1];
        int[] b = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = scanner.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            b[i] = scanner.nextInt();
        }
        long r1 = 1, r2 = 1;
        for (int i = 1; i <= n; i++) {
            if (a[i] < b[i]) {
                int temp = a[i];
                a[i] = b[i];
                b[i] = temp;
            }
            r1 = (r1 * a[i]) % M;
            r2 = (r2 * b[i]) % M;
        }
        System.out.println((r1 + r2) % M);
    }
}
