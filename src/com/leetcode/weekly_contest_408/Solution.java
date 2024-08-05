package com.leetcode.weekly_contest_408;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2024/7/28 22:40
 *
 * 力扣408场周赛 2/4 rank 661
 */
public class Solution {
    // https://leetcode.cn/problems/count-the-number-of-substrings-with-dominant-ones/
    public int numberOfSubstrings(String s) {
        // 想通了就不难了，trick就是减枝
        char[] cs = s.toCharArray();
        int n = s.length();
        int m = 0;
        int[] a = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (cs[i] == '0') {
                a[m++] = i;
            }
        }
        int tot1 = n - m; // 所有1的数量
        a[m] = n;
        int ans = 0, i = 0; // i就是a数组的下标
        for (int left = 0; left < n; left++) { // 枚举左端点
            if (cs[left] == '1') {
                ans += a[i] - left;
            }
            // 枚举0
            for (int k = i; k < m; k++) {
                int cnt0 = k - i + 1;
                if (cnt0 * cnt0 > tot1) {
                    break;
                }
                int cnt1 = a[k] - left + 1 - cnt0;
                ans += Math.max(0, a[k + 1] - a[k] - Math.max(0, cnt0 * cnt0 - cnt1));
            }
            if (cs[left] == '0') {
                i++;
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/check-if-the-rectangle-corner-is-reachable/description/
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
