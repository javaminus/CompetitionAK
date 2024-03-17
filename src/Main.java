import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        if (n % 495 == 0) {
            System.out.println(-1);
            return;
        }
        String s = String.valueOf(n);
        for (int i = 0; i < 1000; i++) {
            long v = Long.valueOf((s + i));
            if (v % 495 == 0) {
                System.out.println(i);
                return;
            }
        }
        scanner.close();
        }
    }