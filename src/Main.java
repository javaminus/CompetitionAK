import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int x = scanner.nextInt();
        int k = scanner.nextInt();
        int cnt = (n * x) / k;
        System.out.println(cnt);
        int temp = 0, total = n * x, s = 0;
        while (total > 0) {
            if (temp + total < k) {
                break;
            }
            if (k > temp) {
                temp += x;
                s += 1;
                total -= x;
            }else{
                System.out.print(s + " ");
                temp -= k;
                s = 0;
            }
        }
        if (s > 0) {
            System.out.print(s + " ");
        }
    }
}