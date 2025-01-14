import java.util.*;
class Solution {
    public static void main(String[] args) {
        new Solution().maxSpending(new int[][]{{8, 5, 2}, {6, 4, 1}, {9, 7, 3}});
    }
    public long maxSpending(int[][] values) {
        int m = values.length, n = values[0].length;
        int[] col = new int[m];// 坐标(i, col[i])
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> values[a][col[a]] - values[b][col[b]]);
        for (int i = 0; i < m; i++) {
            col[i] = n - 1;
            pq.offer(i);
        }
        long ans = 0;
        int d = 0;
        while (!pq.isEmpty()) {
            d++;
            int poll = pq.poll();
            ans += (long) d * values[poll][col[poll]];
            if (col[poll] > 0) {
                col[poll]--;
                pq.offer(poll);
            }
        }
        return ans;
    }
}