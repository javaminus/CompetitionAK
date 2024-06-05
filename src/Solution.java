import java.util.*;

class Solution {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        int[] indegree = new int[n];
        for (int[] rela : relations) {
            int x = rela[0] - 1, y = rela[1] - 1;
            indegree[y]++;
            g[x].add(y);
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        int ans = 0;
        int[] dp = new int[n]; // 表示完成任务i的最少用时
        while (!queue.isEmpty()) {
            int x = queue.poll();
            dp[x] += time[x];
            ans = Math.max(ans, dp[x]);
            for (int y : g[x]) {
                dp[y] = Math.max(dp[y], dp[x]);
                if (--indegree[y] == 0) {
                    queue.offer(y);
                }
            }
        }
        return ans;
    }
}