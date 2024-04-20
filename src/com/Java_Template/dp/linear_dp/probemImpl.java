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

    /*  583. 两个字符串的删除操作
        给定两个单词 word1 和 word2 ，返回使得 word1 和  word2 相同所需的最小步数。
        每步 可以删除任意一个字符串中的一个字符。*/
    public int minDistance1(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
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
                } else {
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j], dp[i][j + 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }


    /*  712. 两个字符串的最小ASCII删除和
        给定两个字符串s1 和 s2，返回 使两个字符串相等所需删除字符的 ASCII 值的最小和 。*/
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i + 1][0] = s1.charAt(i) + dp[i][0];
        }
        for (int i = 0; i < n; i++) {
            dp[0][i + 1] = s2.charAt(i) + dp[0][i];
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j];
                }else{
                    // 别写错了：dp[i + 1][j + 1] = Math.min(dp[i + 1][j] + s1.charAt(i), dp[i][j + 1] + s2.charAt(j));举例捋一下
                    dp[i + 1][j + 1] = Math.min(dp[i + 1][j] + s2.charAt(j), dp[i][j + 1] + s1.charAt(i));
                }
            }
        }
        return dp[m][n];
    }

    /*  97. 交错字符串
        给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
        两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空
        子字符串
        ：
        s = s1 + s2 + ... + sn
        t = t1 + t2 + ... + tm
        |n - m| <= 1
        交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
        注意：a + b 意味着字符串 a 和 b 连接。*/
    public boolean isInterleave(String s1, String s2, String s3) {
        // 我们定义 f(i,j) 表示 s1的前i个元素和s2的前j个元素能否交错组成s3的前(i+j) 个元素
        int m = s1.length(), n = s2.length(), t = s3.length();
        if (m + n != t) {
            return false;
        }
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                int p = i + j - 1;
                if (i > 0) { // 等于0其实是初始化的过程
                    dp[i][j] = dp[i][j] || (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p));
                }
                if (j > 0) {
                    dp[i][j] = dp[i][j] || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }
        return dp[m][n];
    }

    /*  115. 不同的子序列
        给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数，结果需要对 109 + 7 取模。*/
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length(), Mod = (int) 1e9 + 7;
        int[][] dp = new int[m + 1][n + 1]; // dp[i][j]表示s[:i]中有多少个与t[:j]相匹配
        // 初始化
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i + 1][j + 1] = dp[i][j + 1]; // 不选s[i]
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i + 1][j + 1] = (dp[i + 1][j + 1] + dp[i][j]) % Mod;
                }
            }
        }
        return dp[m][n];
    }

    /*  1035. 不相交的线
        在两条独立的水平线上按给定的顺序写下 nums1 和 nums2 中的整数。
        现在，可以绘制一些连接两个数字 nums1[i] 和 nums2[j] 的直线，这些直线需要同时满足：
         nums1[i] == nums2[j]
        且绘制的直线不与任何其他连线（非水平线）相交。
        请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。
        以这种方法绘制线条，并返回可以绘制的最大连线数。*/
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 不选
                dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                if (nums1[i] == nums2[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }
            }
        }
        return dp[m][n];
    }

    /*  1458. 两个子序列的最大点积
        给你两个数组 nums1 和 nums2 。
        请你返回 nums1 和 nums2 中两个长度相同的 非空 子序列的最大点积。
        数组的非空子序列是通过删除原数组中某些元素（可能一个也不删除）后剩余数字组成的序列，但不能改变数字间相对顺序。比方说，[2,3,5] 是 [1,2,3,4,5] 的一个子序列而 [1,5,3] 不是。*/
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i + 1][j + 1] = Math.max(Math.max(Math.max(dp[i + 1][j], dp[i][j + 1]), dp[i][j] + nums1[i]*nums2[j]),nums1[i]*nums2[j]);
            }
        }
        return dp[m][n];
    }

    /*  1092. 最短公共超序列
        给你两个字符串 str1 和 str2，返回同时以 str1 和 str2 作为 子序列 的最短字符串。如果答案不止一个，则可以返回满足条件的 任意一个 答案。
        如果从字符串 t 中删除一些字符（也可能不删除），可以得到字符串 s ，那么 s 就是 t 的一个子序列。*/

/*    public String shortestCommonSupersequence(String s, String t) { // 递归超时
        if (s.isEmpty()) {
            return t;
        }
        if (t.isEmpty()) {
            return s;
        }
        String s1 = s.substring(0, s.length() - 1);
        String t1 = t.substring(0, t.length() - 1);
        char x = s.charAt(s.length() - 1);
        char y = t.charAt(t.length() - 1);
        if (x == y) {
            return shortestCommonSupersequence(s1, t1) + x;
        }else{
            String ans1 = shortestCommonSupersequence(s1, t);
            String ans2 = shortestCommonSupersequence(s, t1);
            if (ans1.length() < ans2.length()) {
                return ans1 + x;
            }else{
                return ans2 + y;
            }
        }
    }*/
    /*  1:1翻译成递推：爆内存
    private String s, t;
    private String[][] memo;
    public String shortestCommonSupersequence(String s, String t) {
        this.s = s;
        this.t = t;
        memo = new String[s.length()][t.length()];
        return dfs(s.length() - 1, t.length() - 1);
    }

    private String dfs(int i, int j) {
        if (i < 0) {
            return t.substring(0, j + 1);
        }
        if (j < 0) {
            return s.substring(0, i + 1);
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        if (s.charAt(i) == t.charAt(j)) {
            return dfs(i - 1, j - 1) + s.charAt(i);
        }
        // 不相同
        String ans1 = dfs(i - 1, j);
        String ans2 = dfs(i, j - 1);
        if (ans1.length() < ans2.length()) {
            return memo[i][j] = ans1 + s.charAt(i);
        }else{
            return memo[i][j] = ans2 + t.charAt(j);
        }
    }*/
    // 如果只求最短公共超序列的长度，那么递归返回的是一个整数而不是字符串，这样就只需要 O(nm) 的时间和空间，这是可以接受的。
    // 问题在于，我们需要构造一个具体的最短公共超序列。
    /*
    private String s, t;
    private int[][] memo; // 存储长度
    public String shortestCommonSupersequence(String s, String t) {
        this.s = s;
        this.t = t;
        memo = new int[s.length()][t.length()];
        return makeAns(s.length() - 1, t.length() - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0) {
            return j + 1;
        }
        if (j < 0) {
            return i + 1;
        }
        if (memo[i][j] > 0) {
            return memo[i][j];
        }
        if (s.charAt(i) == t.charAt(j)) {
            return memo[i][j] = dfs(i - 1, j - 1) + 1;
        }else{
            return memo[i][j] = Math.min(dfs(i - 1, j), dfs(i, j - 1)) + 1;
        }
    }

    private String makeAns(int i, int j) {
        if (i < 0) {
            return t.substring(0, j + 1);
        }
        if (j < 0) {
            return s.substring(0, i + 1);
        }
        if (s.charAt(i) == t.charAt(j)) {
            return makeAns(i - 1, j - 1) + s.charAt(i);
        }
        if (dfs(i, j) == dfs(i - 1, j) + 1) {
            return makeAns(i - 1, j) + s.charAt(i);
        }else{
            return makeAns(i, j - 1) + t.charAt(j);
        }
    }*/
    public String shortestCommonSupersequence(String str1, String str2) { // 终解
        char[] s = str1.toCharArray(), t = str2.toCharArray();
        int m = s.length, n = t.length;
        int[][] dp = new int[m + 1][n + 1]; // 长度dp数组
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s[i] == t[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }else{
                    dp[i + 1][j + 1] = Math.min(dp[i][j + 1], dp[i + 1][j]) + 1;
                }
            }
        }
        int len = dp[m][n];
        char[] ans = new char[len];
        for (int i = m - 1, j = n - 1, k = len - 1; ; ) { // 学会
            if (i < 0) {
                System.arraycopy(t, 0, ans, 0, j + 1); // 学会
                break;
            }
            if (j < 0) {
                System.arraycopy(s, 0, ans, 0, i + 1);
                break;
            }
            if (s[i] == t[j]) {
                ans[k--] = s[i--];
                j--; // 相当于继续递归 makeAns(i - 1, j - 1)
            } else if (dp[i + 1][j + 1] == dp[i + 1][j] + 1) {
                ans[k--] = t[j--];
            }else{
                ans[k--] = s[i--];
            }
        }
        return new String(ans);
    }


}
