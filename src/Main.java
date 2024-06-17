import java.util.HashSet;
public class Main {
    public static void main(String[] args) {
        for (int i = 12345; i <= 98765; i++) {
            for (int j = 1234; j <= 9876; j++) {
                if (judge(i, j) && i - j == 33333) {
                    System.out.println(i + " " + j);
                }
            }
        }
        System.out.println(-1);
    }

    private static boolean judge(int x, int y) {
        HashSet<Integer> set = new HashSet<>();
        while (x > 0) {
            if (set.contains(x % 10)) {
                return false;
            }
            set.add(x % 10);
            x /= 10;
        }
        while (y > 0) {
            if (set.contains(y % 10)) {
                return false;
            }
            set.add(y % 10);
            y /= 10;
        }
        return !set.contains(0);
    }

}