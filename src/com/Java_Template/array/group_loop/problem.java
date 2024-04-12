package com.Java_Template.array.group_loop;

/**
 * 分组循环
 * 3105. 最长的严格递增或递减子数组（https://leetcode.cn/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/description/）
 * 978. 最长湍流子数组(https://leetcode.cn/problems/longest-turbulent-subarray/description/)
 * 845. 数组中的最长山脉(https://leetcode.cn/problems/longest-mountain-in-array/description/)
 *
 */
public interface problem {

    // 3105. 最长的严格递增或递减子数组
    public int longestMonotonicSubarray(int[] nums);

    // 978. 最长湍流子数组
    public int maxTurbulenceSize(int[] nums);

    // 845. 数组中的最长山脉
    public int longestMountain(int[] nums);

    // 2948. 交换得到字典序最小的数组
    public int[] lexicographicallySmallestArray(int[] nums, int limit);
}
