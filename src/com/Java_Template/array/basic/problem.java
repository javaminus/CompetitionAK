package com.Java_Template.array.basic;

import java.util.List;

/**
 * 2808. 使循环数组所有元素相等的最少秒数(https://leetcode.cn/problems/minimum-seconds-to-equalize-a-circular-array/description/?envType=daily-question&envId=2024-01-30)
 *
 * 填充法
 * 1798. 你能构造出连续值的最大数目（https://leetcode.cn/problems/maximum-number-of-consecutive-values-you-can-make/description/）
 * 330. 按要求补齐数组（https://leetcode.cn/problems/patching-array/description/）
 * 2952. 需要添加的硬币的最小数量(https://leetcode.cn/problems/minimum-number-of-coins-to-be-added/description/?envType=daily-question&envId=2024-03-30)
 *
 */
public interface problem {

    // 2808. 使循环数组所有元素相等的最少秒数
    public int minimumSeconds(List<Integer> nums);

    // 1798. 你能构造出连续值的最大数目
    public int getMaximumConsecutive(int[] coins);

    // 330. 按要求补齐数组
    public int minPatches(int[] nums, int n);

    // 2952. 需要添加的硬币的最小数量
    public int minimumAddedCoins(int[] coins, int target);
}
