package com.leetcode.biweekly_contest_137;

import java.util.Arrays;

/**
 * @author Minus
 * @date 2024/8/18 16:28
 *
 *
 */
public class Solution {
    // https://leetcode.cn/problems/find-the-power-of-k-size-subarrays-ii/description/ 双指针，特别好的一个题（考察细节）
    public int[] resultsArray(int[] nums, int k) {
        if (k == 1) {
            return nums;
        }
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Arrays.fill(ans, -1);
        for (int right = 0,left = 0; right < n; right++) {
            while (right + 1 < n && nums[right] + 1 == nums[right + 1]) {
                right++;
            }
            while (right - left + 1 >= k) {
                ans[left] = nums[left + k - 1];
                left++;
            }
            left = right;
        }
        return ans;
    }

    // https://leetcode.cn/problems/maximum-value-sum-by-placing-three-rooks-ii/description/  这题思路不难，但是真的难实现，超级考验coding能力
    public long maximumValueSum(int[][] board) {
        int m = board.length, n = board[0].length;
        int[][][] suffix = new int[m][3][2];
        int[][] p = new int[3][2];
        for (int[] pi : p) {
            pi[0] = Integer.MIN_VALUE;
        }
        for (int i = m - 1; i >= 0; i--) {
            update(board[i], p);
            for (int j = 0; j < 3; j++) {
                suffix[i][j][0] = p[j][0];
                suffix[i][j][1] = p[j][1];
            }
        }
        long ans = Long.MIN_VALUE;
        for (int[] pi : p) {
            pi[0] = Integer.MIN_VALUE;
        }
        for (int i = 1; i < m - 1; i++) { // 枚举中间元素
            update(board[i - 1], p);
            for (int j = 0; j < n; j++) { // 第二个车
                for (int[] a : p) { // 第一个车
                    for (int[] c : suffix[i + 1]) { // 第三个车
                        if (a[1] != j && c[1] != j && a[1] != c[1]) {
                            ans = Math.max(ans, (long) a[0] + board[i][j] + c[0]);
                            break;
                        }
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
                        p[2] = p[1]; // 二维数组都是引用赋值
                    }
                    p[1] = p[0]; // 二维数组都是引用赋值
                }
                p[0] = new int[]{x, j}; // 这里一定要new，不能直接赋值
            } else if (x > p[1][0] && j != p[0][1]) {
                if (p[1][1] != j) {
                    p[2] = p[1];
                }
                p[1] = new int[]{x, j};
            } else if (x > p[2][0] && j != p[0][1] && j != p[1][1]) {
                p[2] = new int[]{x, j};
            }
        }
    }
}
