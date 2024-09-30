package com.Java_Template.string;

/**
 * 100198. 匹配模式数组的子数组数目 II(https://leetcode.cn/problems/number-of-subarrays-that-match-a-pattern-ii/description/)
 * 3076. 数组中的最短非公共子字符串(https://leetcode.cn/problems/shortest-uncommon-substring-in-an-array/description/)
 * 796. 旋转字符串(https://leetcode.cn/problems/rotate-string/description/)
 * 2565. 最少得分子序列(https://leetcode.cn/problems/subsequence-with-the-minimum-score/description/)  前后缀分解
 */
public interface problem {

    // 100198. 匹配模式数组的子数组数目 II
    public int countMatchingSubarrays(int[] nums, int[] pattern);

    // 3076. 数组中的最短非公共子字符串
    public String[] shortestSubstrings(String[] arr);

    // 796. 旋转字符串
    public boolean rotateString(String s, String goal);

    // 2565. 最少得分子序列
    public int minimumScore(String s, String t);
}
