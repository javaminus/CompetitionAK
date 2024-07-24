import java.util.Arrays;

class Solution {
    private static long Mod = (long) 1e9 + 7;
    long[][] memo;
    public int countRoutes(int[] locations, int start, int finish, int fuel) {
        int n = locations.length;
        memo = new long[n][fuel + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return (int) dfs(start, fuel, finish, locations) + (start == finish ? 1 : 0);
    }

    private long dfs(int i, int fuel, int finish, int[] locations) {
        if (memo[i][fuel] != -1) {
            return memo[i][fuel];
        }
        long res = 0;
        for (int j = 0; j < locations.length; j++) {
            if (j == i) {
                continue;
            }
            int cost = Math.abs(locations[i] - locations[j]);
            if (cost <= fuel) {
                res = (dfs(j, fuel - cost, finish, locations) + (j == finish ? 1 : 0) + res) % Mod;
            }
        }
        return memo[i][fuel] = res;
    }
}