import java.util.HashMap;

class Solution {
    private final static int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
    public int mostFrequentPrime(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] direction : directions) {
                    int num = mat[i][j];
                    int x = i + direction[0];
                    int y = j + direction[1];
                    while (x >= 0 && y >= 0 && x < m && y < n) {
                        num = num * 10 + mat[x][y];
                        if (judge(num)) {
                            cnt.put(num,cnt.getOrDefault(num, 0) + 1);
                        }
                        x += direction[0];
                        y += direction[1];
                    }
                }
            }
        }
        int ans = -1, c = 0;
        for (int x : cnt.keySet()) {
            if (cnt.get(x) > c) {
                ans = x;
                c = cnt.get(x);
            }
            if (cnt.get(x) == c) {
                ans = Math.max(ans, x);
            }
        }
        return ans;
    }

    private boolean judge(int x) {
        if (x <= 10) {
            return false;
        }
        for (int i = 2; i*i < x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}