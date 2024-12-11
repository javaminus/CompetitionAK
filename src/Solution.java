import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Solution {
    private static final int Mod = (int) 1e9 + 7;
    HashMap<Integer, List<Integer>> map = new HashMap<>();

    public int knightDialer(int n) {
        map.put(1, Arrays.asList(6, 8));
        map.put(2, Arrays.asList(7, 9));
        map.put(3, Arrays.asList(4, 8));
        map.put(4, Arrays.asList(3, 9, 0));
        map.put(6, Arrays.asList(1, 7, 0));
        map.put(7, Arrays.asList(2, 6));
        map.put(8, Arrays.asList(1, 3));
        map.put(9, Arrays.asList(4, 2));
        map.put(0, Arrays.asList(4, 6));
        int[][] memo = new int[10][n + 1];
        for (int i = 0; i < 10; i++) {
            Arrays.fill(memo[i], -1);
        }

        int ans = 0;
        for (int i = 0; i < 10; i++) {
            ans += new Object() {
                int dfs(int i, int k) {
                    if (k == 0) {
                        return 1;
                    }
                    if (memo[i][k] != -1) {
                        return memo[i][k];
                    }
                    int res = 0;
                    for (int j : map.getOrDefault(i, new ArrayList<>())) {
                        res += dfs(j, k - 1);
                        res %= Mod;
                    }
                    return memo[i][k] = res;
                }
            }.dfs(i, n - 1);
            ans %= Mod;
        }
        return ans;
    }
}