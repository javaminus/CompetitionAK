import java.util.Arrays;

class Solution {
    public boolean checkPartitioning(String s) {
        int n = s.length();
        boolean[][] f = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = true;
            for (int j = i + 1; j < n; j++) {
                if (j == i + 1) {
                    f[i][j] = s.charAt(i) == s.charAt(j);
                }else {
                    f[i][j] = (s.charAt(i) == s.charAt(j)) && f[i + 1][j - 1];
                }
            }
        }
        System.out.println(Arrays.deepToString(f));
        for (int i = n - 2; i > 0; i--) {
            for (int j = i; j < n - 1; j++) {
                if (f[0][i - 1] && f[i][j] && f[j + 1][n - 1]) {
                    return true;
                }
            }
        }
        return false;
    }
    
}