package com.Java_Template.backtrack;

/**
 * 17. 电话号码的字母组合(https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/)
 * 78. 子集(https://leetcode.cn/problems/subsets/description/) 经典回溯
 * 131. 分割回文串(https://leetcode.cn/problems/palindrome-partitioning/description/)
 * 784. 字母大小写全排列(https://leetcode.cn/problems/letter-case-permutation/description/)
 * 79. 单词搜索(https://leetcode.cn/problems/word-search/description/)
 * 494. 目标和(https://leetcode.cn/problems/target-sum/description/)
 */
public interface problem {
    public boolean exist(char[][] board, String word);

    // 494. 目标和
    public int findTargetSumWays(int[] nums, int target);
}
