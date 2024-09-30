package com.Java_Template.sliding_window;

/**
 * 1248. 统计「优美子数组」(https://leetcode.cn/problems/count-number-of-nice-subarrays/description/)
 * 1438. 绝对差不超过限制的最长连续子数组(https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/) 两种方法：滑动窗口+单调队列，滑动窗口+有序集合（红黑树）
 * 2398. 预算内的最多机器人数目(https://leetcode.cn/problems/maximum-number-of-robots-within-budget/description/)单调队列+滑动窗口
 * 2799. 统计完全子数组的数目(https://leetcode.cn/problems/count-complete-subarrays-in-an-array/description/) 滑动窗口+子数组
 * 3306. 元音辅音字符串计数 II(https://leetcode.cn/problems/count-of-substrings-containing-every-vowel-and-k-consonants-ii/description/) 双滑动窗口
 */
public interface problem {
    // 1248. 统计「优美子数组」
    public int numberOfSubarrays(int[] nums, int k);

    // 1438. 绝对差不超过限制的最长连续子数组
    public int longestSubarray(int[] nums, int limit);

    // 2398. 预算内的最多机器人数目
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget);

    // 2799. 统计完全子数组的数目
    public int countCompleteSubarrays(int[] nums);

    // 3306. 元音辅音字符串计数 II
    public long countOfSubstrings(String word, int k);
}
