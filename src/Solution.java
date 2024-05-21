import java.util.Arrays;

class Solution {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        // 利用正方形四条边长度相等 + 正方形两对角线长度相等
        int[] edge = {
                getDistance(p1, p2),
                getDistance(p1, p3),
                getDistance(p1, p4),
                getDistance(p2, p3),
                getDistance(p2, p4),
                getDistance(p3, p4)
        };
        Arrays.sort(edge);
        // 可能会有x=0，y=0的情况
        return edge[0] > 0 && edge[0] == edge[1] && edge[0] == edge[2] && edge[0] == edge[3] && edge[4] == edge[5];
    }
    int getDistance(int[] a, int[] b) {
        int x = a[0] - b[0];
        int y = a[1] - b[1];
        return x * x + y * y;
    }
}