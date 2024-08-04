import java.util.Arrays;

class Solution {
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        int[] parent = new int[n - 1];
        Arrays.setAll(parent, i -> i);
        int m = queries.length;
        int[] ans = new int[m];
        int cnt = n - 1;
        for (int i = 0; i < m; i++) {
            int u = queries[i][0], v = queries[i][1] - 1;
            int fv = find(parent, v);
            for (int j = find(parent, u); j < v; j = find(parent, j + 1)) {
                parent[j] = fv;
                cnt--;
            }
            ans[i] = cnt;
        }
        return ans;
    }

    private int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }


}