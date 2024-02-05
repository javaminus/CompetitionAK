package com.Java_Template.hash;

/**
 * 算法：哈希、贡献法、矩阵哈希、字符串哈希、前缀哈希、后缀哈希、哈希碰撞
 * 功能：前后缀计数、索引、加和
 * 题目：
 * 100183. 最大好子数组和(https://leetcode.cn/problems/maximum-good-subarray-sum/description/)前缀哈希
 * 930. 和相同的二元子数组(https://leetcode.cn/problems/binary-subarrays-with-sum/description/)前缀哈希
 * 1248. 统计「优美子数组」(https://leetcode.cn/problems/count-number-of-nice-subarrays/description/)
 * 974. 和可被 K 整除的子数组(https://leetcode.cn/problems/subarray-sums-divisible-by-k/description/) 同余定理
 * Problem: 523. 连续的子数组和(https://leetcode.cn/problems/continuous-subarray-sum/description/) 同余定理
 * 1524. 和为奇数的子数组数目（https://leetcode.cn/problems/number-of-sub-arrays-with-odd-sum/description/）
 * 525. 连续数组(https://leetcode.cn/problems/contiguous-array/description/) 难想
 */
public interface problem {
    // 00183. 最大好子数组和
    public long maximumSubarraySum(int[] nums, int k);

    // 930. 和相同的二元子数组
    public int numSubarraysWithSum(int[] nums, int goal);

    // 1248. 统计「优美子数组」
    public int numberOfSubarrays(int[] nums, int k);

    // 974. 和可被 K 整除的子数组
    public int subarraysDivByK(int[] nums, int k);

    // Problem: 523. 连续的子数组和
    public boolean checkSubarraySum(int[] nums, int k);

    // 1524. 和为奇数的子数组数目
    public int numOfSubarrays(int[] arr);

    // 525. 连续数组
    public int findMaxLength(int[] nums);
}
