import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    private static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static int m, n;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();
        int jj = sc.nextInt();
        int kk = sc.nextInt();
        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int l = 0; l < n; l++) {
                grid[i][l] = sc.nextInt();
            }
        }
        // int[] dist = new int[m*n];
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2);
        }
        dist[jj][kk] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{jj,kk, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], y = poll[1], d = poll[2];
            if (dist[x][y] < d) {
                continue;
            }
            for (int[] dir : directions) {
                int newX = x + dir[0], newY = y + dir[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n || grid[newX][newY] != 0) {
                    if (grid[x][y] + d < dist[newX][newY]) {
                        dist[newX][newY] = grid[x][y] + d;
                        pq.offer(new int[]{newX, newY, dist[newX][newY]});
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = dist[i][j];
                if (x >= Integer.MAX_VALUE / 2) {
                    System.out.println(-1);
                    return;
                }
                ans = Math.max(ans, x);
            }
        }
        System.out.println(ans);
    }

    private static int getId(int x, int y) {
        return x * n + y;
    }
}