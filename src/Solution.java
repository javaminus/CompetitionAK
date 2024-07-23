import java.util.Arrays;

class Solution {
    private static final long Mod = (long) 1e9 + 7;

    public int dieSimulator(int n, int[] rollMax) {
        int m = rollMax.length; // 6
        int[][][] f = new int[n][m][15];
        for (int j = 0; j < m; ++j) {
            Arrays.fill(f[0][j], 1);
        }
        for (int i = 1; i < n; ++i) {
            for (int last = 0; last < m; ++last) {
                for (int left = 0; left < rollMax[last]; ++left) {
                    long res = 0;
                    for (int j = 0; j < m; ++j) {
                        if (j != last) {
                            res += f[i - 1][j][rollMax[j] - 1];
                        } else if (left > 0) {
                            res += f[i - 1][j][left - 1];
                        }
                    }
                    f[i][last][left] = (int) (res % Mod);
                }
            }
        }
        long ans = 0;
        for (int j = 0; j < m; ++j) {
            ans += f[n - 1][j][rollMax[j] - 1];
        }
        return (int) (ans % Mod);
    }
}