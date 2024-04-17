package com.Java_Template.dp.linear_dp;

import java.util.Arrays;

/**
 * @author Minus
 * @date 2024/4/17 16:45
 * <p>
 * 线性dp
 * 一般定义 f[i][j] 表示对 (s[:i],t[:j]) 的求解结果。
 */
public class probemImpl {
    /*  1143. 最长公共子序列
        给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
        一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
        例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
        两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。*/
    // dfs版本
    char[] s, t;
    int m, n;
    int[][] memo;

    public int longestCommonSubsequence(String text1, String text2) {
        s = text1.toCharArray();
        t = text2.toCharArray();
        m = s.length;
        n = t.length;
        memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(m - 1, n - 1);
    }

    private int dfs(int i, int j) { // i表示text1的第i个位置选与不选，j表示text2的第j个位置选与不选
        if (i < 0 || j < 0) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        if (s[i] == t[j]) { // 相同
            return dfs(i - 1, j - 1) + 1;
        }
        return memo[i][j] = Math.max(dfs(i - 1, j), dfs(i, j - 1));
    }

    // 二维dp版本 可以使用滚动数组优化成一维
    public int longestCommonSubsequence_dp_tow(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];  // i表示text1的第i个位置选与不选，j表示text2的第j个位置选与不选
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return dp[m][n];
    }

    /*  72. 编辑距离
        给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
        你可以对一个单词进行如下三种操作：
        插入一个字符
        删除一个字符
        替换一个字符*/
    public int minDistance(String word1, String word2) {
        // dfs(i,j) 插入：dfs(i,j+1); 删除：dfs(i+1,j); 替换：dfs(i+1,j+1)
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i + 1][0] = i + 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i + 1] = i + 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j];
                }else{
                    dp[i + 1][j + 1] = Math.min(Math.min(dp[i][j + 1], dp[i + 1][j]), dp[i][j]) + 1;
                }
            }
        }
        return dp[m][n];
    }

}
