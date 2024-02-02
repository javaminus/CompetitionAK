package com.template.动态规划;

/**
 * @author Minus
 * @date 2023/12/5 22:42
 *
 * 打家劫舍
 */
public class 删除并获得点数 {
    public int deleteAndEarn(int[] nums) {
        // 数组最大值，用来作为数组长度
        int maxVal = 0;
        for (int val : nums) {
            maxVal = Math.max(val, maxVal);
        }
        int[] sum = new int[maxVal + 1];
        for (int val : nums) {
            // 求和
            sum[val] += val;
        }
        return rob(sum);
    }

    // 打家劫舍
    private int rob(int[] sum) {
        int n = sum.length;
        int a = sum[0], b = Math.max(sum[0], sum[1]);
        for (int i = 2; i < n; i++) {
            int temp = b;
            b = Math.max(a + sum[i], b);
            a = temp;
        }
        return b;
    }
}
