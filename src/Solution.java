import java.util.Arrays;

class Solution {
    public boolean canReachCorner(int x, int y, int[][] circles) {
        int n = circles.length;
        int[] parent = new int[n + 2];
        Arrays.setAll(parent, i -> i);
        for (int i = 0; i < n; i++) {
            int[] c = circles[i];
            int ox = c[0], oy = c[1], r = c[2];
            if (ox <= r || oy + r >= y) { // 圆 i 和左边界或上边界有交集
                union(parent, i, n);
            }
            if (oy <= r || ox + r >= x) { // 圆 i 和下边界或右边界有交集
                union(parent, i, n + 1);
            }
            for (int j = 0; j < i; j++) { // 圆i与圆j有交集
                int[] q = circles[j];
                if ((long) (ox - q[0]) * (ox - q[0]) + (long) (oy - q[1]) * (oy - q[1]) <= (long) (r + q[2]) * (r + q[2])) {
                    union(parent, i, j);
                }
            }
            if (find(parent, n) == find(parent, n + 1)) {
                return false;
            }
        }
        return true;
    }

    private int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }

    private void union(int[] parent, int index1, int index2) {
        parent[find(parent, index1)] = find(parent, index2);
    }
}