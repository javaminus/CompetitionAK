package com.Java_Template.bit_operation.template;

import java.util.ArrayList;

/**
 * 按位或模板
 * 该模板可以做到
 *
 * 1. 求出所有子数组的按位或的结果，以及值等于该结果的子数组的个数。
 * 2. 求按位或结果等于任意给定数字的子数组的最短长度/最长长度。
 */
public class OR {

    // 2411. 按位或最大的最小子数组长度(https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/description/)
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        ArrayList<int[]> ors = new ArrayList<int[]>(); // (按位或的值,对应子数组的右端点的最小值)
        for (int i = n - 1; i >= 0; --i) {
            ors.add(new int[]{0, i});
            int k = 0;
            for (int[] or : ors) {
                or[0] |= nums[i];
                if (ors.get(k)[0] == or[0])
                    ors.get(k)[1] = or[1]; // 合并相同值，下标取最小的
                else ors.set(++k, or);
            }
            ors.subList(k + 1, ors.size()).clear(); // 清空 ors 列表中从索引 k + 1 到末尾的所有元素。也就是去重
            // 本题只用到了 ors[0]，如果题目改成任意给定数值，可以在 ors 中查找
            ans[i] = ors.get(0)[1] - i + 1;
        }
        return ans;
    }
}
