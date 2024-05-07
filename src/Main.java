import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        int[][] prefixSum = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i + 1][j + 1] += prefixSum[i + 1][j] + prefixSum[i][j + 1] - prefixSum[i][j] + grid[i][j];
            }
        }
        int ans = 0;
        for (int i0 = 0; i0 <= n; i0++) {
            for (int j0 = 0; j0 <= n; j0++) {
                for (int i1 = i0 + 1; i1 <= n; i1++) {
                    for (int j1 = j0 + 1; j1 <= n; j1++) {
                        ans = Math.max(ans, prefixSum[i1][j1] - prefixSum[i0][j0]);
                    }
                }
            }
        }
        System.out.println(ans);
        sc.close();
    }
}
