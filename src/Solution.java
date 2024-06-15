import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    private static int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] cost = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
        }
        cost[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int row = poll[0], col = poll[1], c = poll[2];
            if (cost[row][col] < c) {
                continue;
            }
            for (int i = 0; i < 4; i++) {
                int[] dir = dirs[i];
                int newRow = row + dir[0], newCol = col + dir[1];
                int newC = c + (grid[row][col] == i + 1 ? 0 : 1);
                if (newRow >= 0 && newCol >= 0 && newRow < m && newCol < n && newC < cost[newRow][newCol]) {
                    cost[newRow][newCol] = newC;
                    pq.offer(new int[]{newRow, newCol, newC});
                }
            }
        }
        return cost[m - 1][n - 1];
    }
}