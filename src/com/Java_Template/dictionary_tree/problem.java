package com.Java_Template.dictionary_tree;

import java.util.List;

/**
 * 字典树
 *
 * 3045. 统计前后缀下标对 II（https://leetcode.cn/problems/count-prefix-and-suffix-pairs-ii/description/）
 * 212. 单词搜索 II(https://leetcode.cn/problems/word-search-ii/description/?envType=study-plan-v2&envId=top-interview-150)
 * 100268. 最长公共后缀查询(https://leetcode.cn/problems/longest-common-suffix-queries/description/)
 *
 */
public interface problem {
    // 3045. 统计前后缀下标对 II
    public long countPrefixSuffixPairs(String[] words);

    // 212. 单词搜索 II
    public List<String> findWords(char[][] board, String[] words);

    // 100268. 最长公共后缀查询
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery);
}
