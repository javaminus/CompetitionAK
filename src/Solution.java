import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public int[][] sortMatrix(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 令 k = i - j + n
        for (int k = 1; k < m + n; k++) {
            int minJ = Math.max(0, n - k);
            int maxJ = Math.min(n - 1, m + n - 1 - k);
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = minJ; j <= maxJ; j++) {
                list.add(grid[k + j - n][j]);
            }
            Collections.sort(list, minJ == 0 ? null : Collections.reverseOrder());
            for (int j = minJ; j <= maxJ; j++) {
                grid[k + j - n][j] = list.get(j - minJ);
            }
        }
        return grid;
    }
}