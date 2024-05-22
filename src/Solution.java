import java.awt.*;
import java.util.HashSet;

class Solution {
    public double minAreaFreeRect(int[][] points) {
        int n = points.length;
        Point[] A = new Point[n];
        HashSet<Point> pointSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            A[i] = new Point(points[i][0], points[i][1]);
            pointSet.add(A[i]);
        }
        double ans = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            Point p1 = A[i];
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                Point p2 = A[j];
                for (int k = j + 1; k < n; k++) {
                    if (i == k) {
                        continue;
                    }
                    Point p3 = A[k];
                    Point p4 = new Point(p2.x + p3.x - p1.x, p2.y + p3.y - p1.y);

                    if (pointSet.contains(p4)) { // 到这里其实就已经是平行四边形了，下面判断矩形
                        int dot = ((p2.x - p1.x) * (p3.x - p1.x) + (p2.y - p1.y) * (p3.y - p1.y)); // 内积
                        if (dot == 0) {
                            // 说明是矩形
                            double area = p1.distance(p2) * p1.distance(p3);
                            if (area < ans) {
                                ans = area;
                            }
                        }
                    }
                }
            }
        }
        return ans == Double.MAX_VALUE ? 0 : ans;
    }
}