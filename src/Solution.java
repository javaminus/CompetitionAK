import java.util.Arrays;

class Solution {
    public long maximumValueSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][][] prefixSum = new long[m][3][2]; // 前i行的第j大元素[值， 列]
        int[][] p = new int[3][2];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(p[i], Integer.MIN_VALUE / 2);
        }
        for (int i = 0; i < m; i++) {
            update(grid[i], p);
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 2; k++) {
                    prefixSum[i][j][k] = p[j][k];
                }
            }
        }
        long[][][] suffixSum = new long[m][3][2];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(p[i], Integer.MIN_VALUE / 2);
        }
        for (int i = m - 1; i >= 0; i--) {
            update(grid[i], p);
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 2; k++) {
                    suffixSum[i][j][k] = p[j][k];
                }
            }
        }
        long ans = Long.MIN_VALUE;
        for (int i = 1; i < m - 1; i++) {
            for (int b = 0; b < n; b++) {
                for (long[] a : prefixSum[i - 1]) {
                    if (a[1] == b) {
                        continue;
                    }
                    for (long[] c : suffixSum[i + 1]) {
                        if (c[1] == b || c[1] == a[1]) {
                            continue;
                        }
                        ans = Math.max(ans, grid[i][b] + a[0] + c[0]);
                    }
                }
            }
        }
        return ans;
    }

    private void update(int[] row, int[][] p) {
        for (int j = 0; j < row.length; j++) {
            int x = row[j];
            if (x > p[0][0]) {
                if (p[0][1] != j) {
                    if (p[1][1] != j) {
                        p[2] = new int[]{p[1][0], p[1][1]};
                    }
                    p[1] = new int[]{p[0][0], p[0][1]};
                }
                p[0] = new int[]{x, j};
            } else if (x > p[1][0] && j != p[0][1]) {
                if (p[1][1] != j) {
                    p[2] = new int[]{p[1][0], p[1][1]};
                }
                p[1] = new int[]{x, j};
            } else if (x > p[2][0] && j != p[0][1] && j != p[1][1]) {
                p[2] = new int[]{x, j};
            }
        }
    }
}