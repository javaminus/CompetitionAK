package test;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int a = 0, b = 0, c = 0, d = 0;
        int m = matrix.length, n = matrix[0].length;
        List<Integer> ans = new ArrayList<>();
        while (true) {
            for (int i = a; i < n; i++) {
                ans.add(matrix[a][i]);
            }
            a++;
            for (int i = 0; i < m; i++) {
                ans.add(matrix[n - 1][i]);
            }
            for (int i = n - 1; i >= 0; i--) {
                ans.add(matrix[m - 1][i]);
            }
            for (int i = m - 1; i >= 0; i--) {
                ans.add(matrix[i][0]);
            }
        }

    }
}