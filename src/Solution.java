import java.util.*;

class Solution {
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] indegree = new int[n];
        for (int x : favorite) {
            indegree[x]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        List<Integer>[] rg = new List[n];
        Arrays.setAll(rg, e -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int x = queue.poll();
            int y = favorite[x];
            rg[y].add(x);
            if (--indegree[y] == 0) {
                queue.offer(y);
            }
        }
        int maxRingSize = 0, sumChainSize = 0;
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                continue;
            }
            indegree[i] = 0;
            int ringSize = 1;
            for (int x = favorite[i]; x != i; x = favorite[x]) {
                indegree[x] = 0;
                ringSize++;
            }
            if (ringSize == 2) {
                sumChainSize += dfs(i, rg) + dfs(favorite[i], rg);
            }else{
                maxRingSize = Math.max(maxRingSize, ringSize);
            }
        }
        return Math.max(maxRingSize, sumChainSize);
    }

    private int dfs(int x, List<Integer>[] g) { // 求从x点出发最长的链
        int res = 1;
        for (int y : g[x]) {
            res = Math.max(res, dfs(y, g) + 1);
        }
        return res;
    }
}