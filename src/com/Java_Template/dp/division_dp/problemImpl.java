package com.Java_Template.dp.division_dp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minus
 * @date 2024/4/15 14:11
 *
 *
 * NOTE:划分型dp
 * 一般：定义 dfs(i,j,and)表示当前考虑到 nums[i]，已经划分了 j 段，且当前待划分的这一段已经参与 AND 运算的结果为 and，在这种情况下，继续向后划分，可以得到的最小和。
 */
public class problemImpl {

    /*  3117. 划分数组得到最小的值之和
        给你两个数组 nums 和 andValues，长度分别为 n 和 m。
        数组的 值 等于该数组的 最后一个 元素。
        你需要将 nums 划分为 m 个 不相交的连续 子数组，对于第 ith 个子数组 [li, ri]，子数组元素的按位AND运算结果等于 andValues[i]，换句话说，对所有的 1 <= i <= m，nums[li] & nums[li + 1] & ... & nums[ri] == andValues[i] ，其中 & 表示按位AND运算符。
        返回将 nums 划分为 m 个子数组所能得到的可能的 最小 子数组 值 之和。如果无法完成这样的划分，则返回 -1 。*/
    public int minimumValueSum(int[] nums, int[] andValues) {
        HashMap<Long, Integer> memo = new HashMap<>();
        int ans = dfs(0, 0, -1, nums, andValues, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    /*定义 dfs(i,j,and)表示当前考虑到 nums[i]，已经划分了 j 段，且当前待划分的这一段已经参与 AND 运算的结果为 and，在这种情况下，继续向后划分，可以得到的最小和。*/
    private int dfs(int i, int j, int and, int[] nums, int[] andValues, Map<Long,Integer> memo) {
        int n = nums.length;
        int m = andValues.length;
        if (m - j > n - i) { // 剩余元素不足
            return Integer.MAX_VALUE / 2;
        }
        if (j == m) { // 分了m段
            return i == n ? 0 : Integer.MAX_VALUE / 2;
        }
        and &= nums[i];
        if (and < andValues[j]) { // 剪枝：无法等于 andValues[j]
            return Integer.MAX_VALUE / 2;
        }
        long mask = (long) i << 36 | (long) j << 32 | and; // 三个状态压缩成一个 long
        if (memo.containsKey(mask)) {
            return memo.get(mask);
        }
        int res = dfs(i + 1, j, and, nums, andValues, memo); // 不划分
        if (and == andValues[j]) { // 划分，nums[i]是这一段的最后一个数
            res = Math.min(res, dfs(i + 1, j + 1, -1, nums, andValues, memo) + nums[i]);
        }
        memo.put(mask, res);
        return res;
    }
}
