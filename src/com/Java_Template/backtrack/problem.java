package com.Java_Template.backtrack;

import java.util.List;

/**
 * 17. 电话号码的字母组合(https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/)
 * 78. 子集(https://leetcode.cn/problems/subsets/description/) 经典回溯
 * 131. 分割回文串(https://leetcode.cn/problems/palindrome-partitioning/description/)
 * 784. 字母大小写全排列(https://leetcode.cn/problems/letter-case-permutation/description/)
 * 79. 单词搜索(https://leetcode.cn/problems/word-search/description/)
 * 494. 目标和(https://leetcode.cn/problems/target-sum/description/)
 * 894. 所有可能的真二叉树(https://leetcode.cn/problems/all-possible-full-binary-trees/description/?envType=daily-question&envId=2024-04-02) 1784这题应该有2000的难度
 */
public interface problem {
    public boolean exist(char[][] board, String word);

    // 494. 目标和
    public int findTargetSumWays(int[] nums, int target);

    // 78. 子集
    public List<List<Integer>> subsets(int[] nums);


    // 894. 所有可能的真二叉树
    public List<TreeNode> allPossibleFBT(int n);
}
