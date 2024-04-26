package com.Java_Template.dp.linear_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    /*  1639. 通过给定词典构造目标字符串的方案数
        给你一个字符串列表 words 和一个目标字符串 target 。words 中所有字符串都 长度相同  。
        你的目标是使用给定的 words 字符串列表按照下述规则构造 target ：
        从左到右依次构造 target 的每一个字符。
        为了得到 target 第 i 个字符（下标从 0 开始），当 target[i] = words[j][k] 时，你可以使用 words 列表中第 j 个字符串的第 k 个字符。
        一旦你使用了 words 中第 j 个字符串的第 k 个字符，你不能再使用 words 字符串列表中任意单词的第 x 个字符（x <= k）。也就是说，所有单词下标小于等于 k 的字符都不能再被使用。
        请你重复此过程直到得到目标字符串 target 。
        请注意， 在构造目标字符串的过程中，你可以按照上述规定使用 words 列表中 同一个字符串 的 多个字符 。
        请你返回使用 words 构造 target 的方案数。由于答案可能会很大，请对 109 + 7 取余 后返回。
        （译者注：此题目求的是有多少个不同的 k 序列，详情请见示例。）*/
    private static final int Mod = (int) (1e9 + 7);
    public int numWays(String[] words, String target) {
        int m = words[0].length(), n = target.length();
        int[][] dp = new int[n + 1][m + 1]; // dp[i][j]表示从前j个字母选择构成target[:i]的方案数
        Arrays.fill(dp[0], 1); // 构成空字串的方案数，都是1
        int[][] cnt = new int[m + 1][26]; // cnt[i][j]表示前i个字符构成字符（j+'a'）的方案数
        for (String s : words) {
            for (int i = 0; i < m; i++) {
                cnt[i + 1][s.charAt(i) - 'a']++;
            }
        }
        for (int i = 1; i <= n; i++) {
            int c = target.charAt(i - 1) - 'a';
            int t = m - (n - i);
            for (int j = i; j <= t; j++) {
                long ans = (long) cnt[j][c] * dp[i - 1][j - 1] + dp[i][j - 1];
                dp[i][j] = (int) (ans % Mod);
            }
        }
        return dp[n][m];
    }

    /*  1883. 准时抵达会议现场的最小跳过休息次数
        给你一个整数 hoursBefore ，表示你要前往会议所剩下的可用小时数。要想成功抵达会议现场，你必须途经 n 条道路。道路的长度用一个长度为 n 的整数数组 dist 表示，其中 dist[i] 表示第 i 条道路的长度（单位：千米）。另给你一个整数 speed ，表示你在道路上前进的速度（单位：千米每小时）。
        当你通过第 i 条路之后，就必须休息并等待，直到 下一个整数小时 才能开始继续通过下一条道路。注意：你不需要在通过最后一条道路后休息，因为那时你已经抵达会议现场。
        例如，如果你通过一条道路用去 1.4 小时，那你必须停下来等待，到 2 小时才可以继续通过下一条道路。如果通过一条道路恰好用去 2 小时，就无需等待，可以直接继续。
        然而，为了能准时到达，你可以选择 跳过 一些路的休息时间，这意味着你不必等待下一个整数小时。注意，这意味着与不跳过任何休息时间相比，你可能在不同时刻到达接下来的道路。
        例如，假设通过第 1 条道路用去 1.4 小时，且通过第 2 条道路用去 0.6 小时。跳过第 1 条道路的休息时间意味着你将会在恰好 2 小时完成通过第 2 条道路，且你能够立即开始通过第 3 条道路。
        返回准时抵达会议现场所需要的 最小跳过次数 ，如果 无法准时参会 ，返回 -1 。*/
/*    public int minSkips(int[] dist, int speed, int hoursBefore) {
        int sumDist = Arrays.stream(dist).sum();
        if (sumDist > (long) hoursBefore * speed) {
            return -1;
        }

        int n = dist.length;
        int[][] memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        for (int i = 0; ; i++) {
            if (dfs(i, n - 2, memo, dist, speed) + dist[n - 1] <= (long) speed * hoursBefore) {
                return i;
            }
        }
    }
    // 定义dfs(i,j) 为最多跳过i次到达dist[j]需要的时间*speed，本质还是比较时间
    private int dfs(int i, int j, int[][] memo, int[] dist, int speed) {
        // 递归边界
        if (j < 0) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        // 不跳过
        int res = (dfs(i, j - 1, memo, dist, speed) + dist[j] + speed - 1) / speed * speed;
        // 如果可以tiaog
        if (i > 0) {
            res = Math.min(res, dfs(i - 1, j - 1, memo, dist, speed) + dist[j]);
        }
        return memo[i][j] = res;
    }*/
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        int sumDist = Arrays.stream(dist).sum();
        if (sumDist > (long) hoursBefore * speed) {
            return -1;
        }

        int n = dist.length;
        int[][] dp = new int[n][n];
        for (int i = 0; ; i++) {
            for (int j = 0; j < n - 1; j++) {
                dp[i][j + 1] = (dp[i][j] + dist[j] + speed - 1) / speed * speed;
                if (i > 0) {
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i - 1][j] + dist[j]);
                }
            }
            if (dp[i][n - 1] + dist[n - 1] <= (long) speed * hoursBefore) {
                return i;
            }
        }
    }

    /*  44. 通配符匹配
        给你一个输入字符串 (s) 和一个字符模式 (p) ，请你实现一个支持 '?' 和 '*' 匹配规则的通配符匹配：
        '?' 可以匹配任何单个字符。
        '*' 可以匹配任意字符序列（包括空字符序列）。
        判定匹配成功的充要条件是：字符模式必须能够 完全匹配 输入字符串（而不是部分匹配）。*/
    public boolean isMatch(String s, String p) {
        /*  dp[0][0]=True，即当字符串 sss 和模式 ppp 均为空时，匹配成功；
            dp[i][0]=False，即空模式无法匹配非空字符串；
            dp[0][j] 需要分情况讨论：因为星号才能匹配空字符串，所以只有当模式 p 的前 j 个字符均为星号时，dp[0][j] 才为真。*/
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1]; // dp[i][j]表示s的前i个字符与p的前j个字符匹配
        dp[0][0] = true; // 两个字符串都是空串
        for (int i = 0; i < n; i++) {
            if (p.charAt(i) == '*') {
                dp[0][i + 1] = true;
            }else{
                break; // 保证连续
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    dp[i + 1][j + 1] = dp[i + 1][j] || dp[i][j + 1];
                }
            }
        }
        return dp[m][n];
    }

    /*  10. 正则表达式匹配
        给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
        '.' 匹配任意单个字符
        '*' 匹配零个或多个前面的那一个元素
        所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。*/
    public boolean isMatchs(String s, String p) { // 看不懂啊~
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }else{
                    if (matches(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[m][n];
    }
    private boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }


    /*  300. 最长递增子序列  LIS问题
        给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
        子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的
        子序列。*/
    // dfs(i) 表示以 nums[i] 结尾的最长递增子序列（LIS）的长度。
    public int lengthOfLIS(int[] nums) { // O(n^2)的时间复杂度 ，使用二分可以优化到O(n*logn)的时间复杂度
        int n = nums.length;
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            ans = Math.max(ans, ++dp[i]);
        }
        return ans;
    }

    // 二分贪心
    // g[i]表示长度为i+1的ls的末尾值最小元素 这不算动态规划了，应该算贪心
    public int lengthOfLIS_2(int[] nums) {
        ArrayList<Integer> g = new ArrayList<>(); // 维护该位置最小值
        for (int x : nums) {
            int j = lowerBound(g, x);
            if (j == g.size()) {
                g.add(x);
            }else{
                g.set(j, x);
            }
        }
        return g.size();
    }
    private int lowerBound(List<Integer> g, int target) {
        int left = 0, right = g.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right + 1;
    }

    /*public int lengthOfLIS(int[] nums) { // 解法三：树状数组，维护区间最大长度
        int n = nums.length;
        // 离散化
        int[] temp = nums.clone();
        Arrays.sort(temp);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(temp[i], i + 1);
        }
        BIT bit = new BIT(n);
        for (int i = 0; i < n; i++) {
            int x = map.get(nums[i]);
            int len = Math.max(bit.query(x - 1), 0) + 1;
            bit.update(x, len);
        }
        return bit.query(n);
    }

    class BIT{ // 维护区间的最大长度
        int n;
        int[] treeArr; // 存储当前位置的最大长度

        public BIT(int n) {
            this.n = n;
            treeArr = new int[n + 1];
        }

        private int lowBit(int x) {
            return x & -x;
        }

        private void update(int x, int len) {
            while (x <= n) {
                if (treeArr[x] < len) {
                    treeArr[x] = len;
                }
                x += lowBit(x);
            }
        }

        public int query(int x) { // 查找最长度len
            int res = Integer.MIN_VALUE;
            while (x > 0) {
                res = Math.max(res, treeArr[x]);
                x -= lowBit(x);
            }
            return res;
        }
    }*/


    /*  673. 最长递增子序列的个数
        给定一个未排序的整数数组 nums ， 返回最长递增子序列的个数 。
        注意 这个数列必须是 严格 递增的。*/
    public int findNumberOfLIS(int[] nums) { // 动态规划 O(n^2), 使用二分+贪心可以得到O(n*logn)的时空复杂度
        int n = nums.length, ans = 0, maxLength = 0;
        int[] dp = new int[n];
        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            cnt[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        cnt[i] = cnt[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        cnt[i] += cnt[j];
                    }
                }
            }
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                ans = cnt[i];
            } else if (dp[i] == maxLength) {
                ans += cnt[i];
            }
        }
        return ans;
    }

    /*public int findNumberOfLIS(int[] nums) { // 推荐做法！！！
        // 树状数组给我的感觉就是内嵌一个二分查找，之前我对树状数组的理解就仅限于求前缀和，显然现在的理解更加深刻
        int n = nums.length;
        int[] temp = nums.clone();
        Arrays.sort(temp);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(temp[i], i+1); // 下标从1开始
        }
        BIT bit = new BIT(n);
        for (int i = 0; i < n; i++) {
            int x = map.get(nums[i]); // 获取下标
            int[] info = bit.query(x - 1);
            int len = info[0], cnt = info[1];
            bit.update(x, new int[]{len + 1, Math.max(1, cnt)});
        }
        return bit.query(n - 1)[1];
    }

    class BIT{
        int n;
        int[][] treeArr; // {len,cnt}

        public BIT(int n) {
            this.n = n;
            treeArr = new int[n + 1][2];
        }

        public int[] query(int x) { // {len,cnt} 查询最大长度与次数
            int len = 0, cnt = 0;
            while (x > 0) {
                if (len == treeArr[x][0]) {
                    cnt += treeArr[x][1];
                } else if (len < treeArr[x][0]) {
                    len = treeArr[x][0];
                    cnt = treeArr[x][1];
                }
                x -= lowBit(x);
            }
            return new int[]{len, cnt};
        }

        private void update(int x, int[] info) {
            while (x <= n) {
                int len = treeArr[x][0], cnt = treeArr[x][1];
                if (len == info[0]) {
                    cnt += info[1];
                } else if (len < info[0]) {
                    len = info[0];
                    cnt = info[1];
                }
                treeArr[x][0] = len;
                treeArr[x][1] = cnt;
                x += lowBit(x);
            }
        }

        private int lowBit(int x) {
            return x & -x;
        }
    }*/
    /*class Solution { // 看不懂 （垃圾做法，抛弃，不如树状数组）
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        // 创建一个数组，用于存储长度为i的递增子序列的列表
        List<int[]>[] len = new ArrayList[n];
        Arrays.setAll(len, e -> new ArrayList<Integer>());
        int size = 0; // 当前递增子序列的长度
        for(int num : nums){
            // 在len数组中找到num应该插入的位置
            int index = binarySearchLength(len, size, num);
            int count = 1; // 默认num作为新的递增子序列的起点，所以count为1

            // 如果index大于0，说明num可以接在某个长度为index的递增子序列后面
            if(index > 0){
                List<int[]> list = len[index-1];
                int pos = binarySearchIndex(list, num); // 找到num在该递增子序列中的插入位置
                // 计算以num结尾的递增子序列的个数
                count = list.get(list.size()-1)[1] - (pos == 0 ? 0 : list.get(pos - 1)[1]);
            }
            // 如果len[index]为空，说明需要新增一个长度为index+1的递增子序列
            if(len[index].size()==0){
                len[index].add(new int[]{num, count});
                size++;
            } else {
                // 否则，在长度为index+1的递增子序列的末尾追加num，并更新递增子序列的个数
                List<int[]> list = len[index];
                int[] last = list.get(list.size()-1);

                if(last[0] == num)
                    last[1] += count;
                else
                    list.add(new int[]{num, last[1] + count});

            }
        }
        // 返回长度为size的最后一个递增子序列的个数
        return len[size-1].get(len[size-1].size()-1)[1];
    }

    // 二分查找应该插入的位置
    public int binarySearchLength(List<int[]>[] len, int right, int target){
        int left = 0;
        while(left < right){
            int mid = left + right >> 1;
            // 如果当前长度为mid的递增子序列的最大值小于目标值，则继续向右搜索
            if(len[mid].get(len[mid].size()-1)[0] < target)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }

    // 二分查找应该插入的位置
    public int binarySearchIndex(List<int[]> list,int target){

        int left = 0 , right = list.size() - 1;

        while(left < right){
            int mid = left + right >> 1;
            // 如果当前位置的值大于等于目标值，则继续向右搜索
            if(list.get(mid)[0] >= target)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }
}*/

    /*  2826. 将三个组排序
        给你一个整数数组 nums 。nums 的每个元素是 1，2 或 3。在每次操作中，你可以删除 nums 中的一个元素。返回使 nums 成为 非递减 顺序所需操作数的 最小值。*/
    public int minimumOperations(List<Integer> nums) { // 可以取等，只需要非递减  使用状态机可以优化到O(n)的时间复杂度
        ArrayList<Integer> g = new ArrayList<>();
        for (int x : nums) {
            int j = upperBound(g, x);
            if (j == g.size()) {
                g.add(x);
            }else{
                g.set(j, x);
            }
        }
        return nums.size() - g.size();
    }

    private int upperBound(List<Integer> g, int target) {
        int left = 0, right = g.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) <= target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }

    /*  1671. 得到山形数组的最少删除次数
        我们定义 arr 是 山形数组 当且仅当它满足：

        arr.length >= 3
        存在某个下标 i （从 0 开始） 满足 0 < i < arr.length - 1 且：
        arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
        arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
        给你整数数组 nums​ ，请你返回将 nums 变成 山形状数组 的​ 最少 删除次数。*/
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] suffix = new int[n];
        ArrayList<Integer> g = new ArrayList<>();
        for (int i = n - 1; i > 0; i--) {
            int j = lowerBound(g, nums[i]);
            if (j == g.size()) {
                g.add(nums[i]);
            }else{
                g.set(j, nums[i]);
            }
            suffix[i] = j + 1;
        }
        int mx = 0;
        g.clear();
        for (int i = 0; i < n - 1; i++) {
            int j = lowerBound(g, nums[i]);
            if (j == g.size()) {
                g.add(nums[i]);
            }else{
                g.set(j, nums[i]);
            }
            int pre = j + 1;
            if (pre >= 2 && suffix[i] >= 2) {
                mx = Math.max(mx, pre + suffix[i] - 1);
            }
        }
        return n - mx;
    }
/*    private int lowerBound(List<Integer> g, int target) {
        int left = 0, right = g.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right + 1;
    }*/

    /*  1964. 找出到每个位置为止最长的有效障碍赛跑路线
        你打算构建一些障碍赛跑路线。给你一个 下标从 0 开始 的整数数组 obstacles ，数组长度为 n ，其中 obstacles[i] 表示第 i 个障碍的高度。
        对于每个介于 0 和 n - 1 之间（包含 0 和 n - 1）的下标  i ，在满足下述条件的前提下，请你找出 obstacles 能构成的最长障碍路线的长度：
        你可以选择下标介于 0 到 i 之间（包含 0 和 i）的任意个障碍。
        在这条路线中，必须包含第 i 个障碍。
        你必须按障碍在 obstacles 中的 出现顺序 布置这些障碍。
        除第一个障碍外，路线中每个障碍的高度都必须和前一个障碍 相同 或者 更高 。
        返回长度为 n 的答案数组 ans ，其中 ans[i] 是上面所述的下标 i 对应的最长障碍赛跑路线的长度。*/
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int n = obstacles.length;
        int[] ans = new int[n];
        ArrayList<Integer> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = obstacles[i];
            int j = upperBound(g, x);
            if (j == g.size()) {
                g.add(x);
            }else{
                g.set(j, x);
            }
            ans[i] = j + 1;
        }
        return ans;
    }
/*    private int upperBound(List<Integer> g, int target) {
        int left = 0, right = g.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) <= target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right + 1;
    }*/

    /*  1626. 无矛盾的最佳球队
    假设你是球队的经理。对于即将到来的锦标赛，你想组合一支总体得分最高的球队。球队的得分是球队中所有球员的分数 总和 。
    然而，球队中的矛盾会限制球员的发挥，所以必须选出一支 没有矛盾 的球队。如果一名年龄较小球员的分数 严格大于 一名年龄较大的球员，则存在矛盾。同龄球员之间不会发生矛盾。
    给你两个列表 scores 和 ages，其中每组 scores[i] 和 ages[i] 表示第 i 名球员的分数和年龄。请你返回 所有可能的无矛盾球队中得分最高那支的分数 。*/
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]); // 按分数排序,如果分数相同，我们就按照年龄从小到大排序，按来年龄排序也行，不过需要对分数做离散化处理。
        int len = 1000; // 年龄最大为1000,如果再大就离散化
        BIT bit = new BIT(len); // 树状数组存储满足条件的最大分数前缀和 （区间查询，单点更新）
        for (int id : ids) {
            bit.update(ages[id], bit.query(ages[id]) + scores[id]);
        }
        return bit.query(len);
    }
    class BIT{
        int n;
        int[] treeArr;
        public BIT(int n){
            this.n = n;
            treeArr = new int[n + 1];
        }

        private int lowBit(int x) {
            return x & -x;
        }

        private int query(int x) {
            int res = 0;
            while (x > 0) {
                res = Math.max(res, treeArr[x]);
                x -= lowBit(x);
            }
            return res;
        }

        private void update(int x, int score) {
            while (x <= n) {
                treeArr[x] = Math.max(score, treeArr[x]); // 满足年龄要求的区间加分数
                x += lowBit(x);
            }
        }
    }
    public int bestTeamScore_DP(int[] scores, int[] ages) { // 动态规划做法
        int n = scores.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]); // 先按照分数排序，同分数按照年龄排序
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (ages[ids[j]] <= ages[ids[i]]) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            dp[i] += scores[ids[i]];
            ans = Math.max(dp[i], ans);
        }
        return ans;
    }

    /*  354. 俄罗斯套娃信封问题
        给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
        当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
        请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
        注意：不允许旋转信封。*/
    public int maxEnvelopes(int[][] envelopes) { // 这题和1626. 无矛盾的最佳球队一个套路，树状数组秒杀
        int n = envelopes.length;
        int maxN = 100005;
        Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        // 也可以离散化
        BIT bit = new BIT(maxN);
        for (int i = 0; i < n; i++) {
            bit.update(envelopes[i][1],bit.query(envelopes[i][1] - 1)+1); // 这里的envelopes[i][1] - 1，是查找比envelopes[i][1]小的信封，所以要减一
        }
        return bit.query(maxN);
    }
    public int maxEnvelopes_2(int[][] envelopes) {
        int n = envelopes.length;
        Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int[] height = new int[n];
        for (int i = 0; i < n; i++) {
            height[i] = envelopes[i][1];
        }
        // 找height的最长递增子序列
        ArrayList<Integer> g = new ArrayList<>();
        for (int x : height) {
            int id = binarySearch(g, x);
            if (id == g.size()) {
                g.add(x);
            }else{
                g.set(id, x);
            }
        }
        return g.size();
    }

    private int binarySearch(List<Integer> g, int target) {
        int left = 0, right = g.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right + 1;
    }


    /*  1691. 堆叠长方体的最大高度
        给你 n 个长方体 cuboids ，其中第 i 个长方体的长宽高表示为 cuboids[i] = [widthi, lengthi, heighti]（下标从 0 开始）。请你从 cuboids 选出一个 子集 ，并将它们堆叠起来。
        如果 widthi <= widthj 且 lengthi <= lengthj 且 heighti <= heightj ，你就可以将长方体 i 堆叠在长方体 j 上。你可以通过旋转把长方体的长宽高重新排列，以将它放在另一个长方体上。
        返回 堆叠长方体 cuboids 可以得到的 最大高度 。*/
    public int maxHeight(int[][] cuboids) {
        int n = cuboids.length;
        for (int[] c : cuboids) {
            Arrays.sort(c);
        }
        Arrays.sort(cuboids, (a, b) -> a[0] + a[1] + a[2] - (b[0] + b[1] + b[2]));
        int ans = 0;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = cuboids[i][2];
            for (int j = 0; j < i; j++) {
                if (cuboids[j][0] <= cuboids[i][0] && cuboids[j][1] <= cuboids[i][1] && cuboids[j][2] <= cuboids[i][2]) {
                    dp[i] = Math.max(dp[i], dp[j] + cuboids[i][2]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /*  960. 删列造序 III
        给定由 n 个小写字母字符串组成的数组 strs ，其中每个字符串长度相等。
        选取一个删除索引序列，对于 strs 中的每个字符串，删除对应每个索引处的字符。
        比如，有 strs = ["abcdef","uvwxyz"] ，删除索引序列 {0, 2, 3} ，删除后为 ["bef", "vyz"] 。
        假设，我们选择了一组删除索引 answer ，那么在执行删除操作之后，最终得到的数组的行中的 每个元素 都是按字典序排列的（即 (strs[0][0] <= strs[0][1] <= ... <= strs[0][strs[0].length - 1]) 和 (strs[1][0] <= strs[1][1] <= ... <= strs[1][strs[1].length - 1]) ，依此类推）。
        请返回 answer.length 的最小可能值 。*/
    public int minDeletionSize(String[] strs) {
        int m = strs.length, n = strs[0].length();
        int[] dp = new int[n]; // 其中 dp[i] 为以下标 i 结尾的最长公共递增子序列长度
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) { // 长度
            for (int j = 0; j < i; j++) {
                boolean flag = true;
                for (int k = 0; k < m && flag; k++) {
                    if (strs[k].charAt(j) > strs[k].charAt(i)) {
                        flag = false;
                    }
                }
                if (flag) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int maxRemain = 0;
        for (int x : dp) {
            maxRemain = Math.max(maxRemain, x);
        }
        return n - maxRemain;
    }
    /*  2407. 最长递增子序列 II
        给你一个整数数组 nums 和一个整数 k 。
        找到 nums 中满足以下要求的最长子序列：
        子序列 严格递增
        子序列中相邻元素的差值 不超过 k 。
        请你返回满足上述要求的 最长子序列 的长度。
        子序列 是从一个数组中删除部分元素后，剩余元素不改变顺序得到的数组。*/
    /* public int lengthOfLIS(int[] nums, int k) { // 普通动态规划做法超时，不愧是2280的难度分
        int n = nums.length;
        int[] dp = new int[n]; // 其中 dp[i] 为以下标 i 结尾的最长公共递增子序列长度
        Arrays.fill(dp, 1);
        int ans = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) { // 这一段可以使用线段树/树状数组优化
                if (nums[j] < nums[i] && nums[i] - nums[j] <= k) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }*/
    /*import java.util.Arrays;

class Solution { // TODO {模板}  区间查询+区间修改，使用线段树优化dp
    public int lengthOfLIS(int[] nums, int k) {
        int mx = Arrays.stream(nums).max().getAsInt();
        SegmentTree segmentTree = new SegmentTree(nums, mx);
        for (int x : nums) {
            if (x == 1) {
                segmentTree.update(1, 1);
            }else{
                int res = 1 + segmentTree.query(Math.max(0, x - k), x - 1); // 查询区间的最大值，这里的tree[]存储的是区间的长度
                segmentTree.update(x, res); // 单点更新
            }
        }
        return segmentTree.query(0, mx); // 区间查询最大值
    }

    class SegmentTree {
        int[] tree; // 存储线段树的数组
        int[] nums; // 存储原始数组的副本
        int n;

        public SegmentTree(int[] nums, int n) {
            this.nums = nums;
            this.n = n;
            this.tree = new int[4 * n];
        }

        // 区间查询
        public int query(int left, int right) {
            return queryTree(1, 1, n, left, right);
        }

        private int queryTree(int node, int start, int end, int left, int right) {
            if (right < start || left > end) {
                return 0; // 区间不重叠，返回合适的默认值
            }
            if (left <= start && right >= end) {
                return tree[node]; // 完全包含，返回节点的值
            }
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            int res = 0;
            if (left <= mid) {
                res = Math.max(res, queryTree(leftChild, start, mid, left, right));
            }
            if (right > mid) {
                res = Math.max(res, queryTree(rightChild, mid + 1, end, left, right));
            }
            // 根据实际需求合并左右子树信息
            return res;
        }

        // 单点更新
        public void update(int index, int value) {
            updateTree(1, 1, n, index, value);
        }

        private void updateTree(int node, int start, int end, int index, int value) {
            if (start == end) {
                tree[node] = value;
            } else {
                int mid = (start + end) / 2;
                int leftChild = 2 * node;
                int rightChild = 2 * node + 1;
                // if (index >= start && index <= mid) {
                if (index <= mid) {
                    updateTree(leftChild, start, mid, index, value);
                } else {
                    updateTree(rightChild, mid + 1, end, index, value);
                }
                // 根据实际需求合并左右子树信息
                tree[node] = Math.max(tree[leftChild], tree[rightChild]);
            }
        }

    }
}*/

    /*  1187. 使数组严格递增
        给你两个整数数组 arr1 和 arr2，返回使 arr1 严格递增所需要的最小「操作」数（可能为 0）。
        每一步「操作」中，你可以分别从 arr1 和 arr2 中各选出一个索引，分别为 i 和 j，0 <= i < arr1.length 和 0 <= j < arr2.length，然后进行赋值运算 arr1[i] = arr2[j]。
        如果无法让 arr1 严格递增，请返回 -1。*/
    /*int[] a, b;
    private HashMap<Integer, Integer>[] memo;
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        a = arr1;
        b = arr2;
        Arrays.sort(b);
        int n = arr1.length;
        memo = new HashMap[n];
        Arrays.setAll(memo, m -> new HashMap<>());
        int ans = dfs(n - 1, Integer.MAX_VALUE);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int suf) { // 表示后一个元素时suf的情况下，遍历到i,最少的更改次数
        if (i < 0) {
            return 0;
        }
        if (memo[i].containsKey(suf)) {
            return memo[i].get(suf);
        }
        // 不改
        int res = a[i] < suf ? dfs(i - 1, a[i]) : Integer.MAX_VALUE / 2;
        // 修改  在数组b中查找小于suf的最大值
        int j = binarySearch(b, suf);
        if (j >= 0) { // 找到了
            res = Math.min(res, dfs(i - 1, b[j]) + 1);
        }
        memo[i].put(suf, res);
        return res;
    }

    private int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right;
    }*/

    /*  368. 最大整除子集
        给你一个由 无重复 正整数组成的集合 nums ，请你找出并返回其中最大的整除子集 answer ，子集中每一元素对 (answer[i], answer[j]) 都应当满足：
        answer[i] % answer[j] == 0 ，或
        answer[j] % answer[i] == 0
        如果存在多个有效解子集，返回其中任何一个均可。*/
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxVal = 1, maxCnt = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > maxCnt) {
                maxCnt = dp[i];
                maxVal = nums[i];
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        if (maxCnt == 1) {
            ans.add(nums[0]);
            return ans;
        }
        for (int i = n - 1; i >= 0; i--) {
            if (dp[i] == maxCnt && maxVal % nums[i] == 0) {
                ans.add(nums[i]);
                maxVal = nums[i];
                maxCnt--;
            }
        }
        return ans;
    }

    /*  1713. 得到子序列的最少操作次数
        给你一个数组 target ，包含若干 互不相同 的整数，以及另一个整数数组 arr ，arr 可能 包含重复元素。
        每一次操作中，你可以在 arr 的任意位置插入任一整数。比方说，如果 arr = [1,4,1,2] ，那么你可以在中间添加 3 得到 [1,4,3,1,2] 。你可以在数组最开始或最后面添加整数。
        请你返回 最少 操作次数，使得 target 成为 arr 的一个子序列。
        一个数组的 子序列 指的是删除原数组的某些元素（可能一个元素都不删除），同时不改变其余元素的相对顺序得到的数组。比方说，[2,7,4] 是 [4,2,3,7,2,1,4] 的子序列（加粗元素），但 [2,4,2] 不是子序列。*/
    public int minOperations_TTL(int[] target, int[] arr) { // 暴力超时
        int m = target.length, n = arr.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (target[i] == arr[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }else{
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return m - dp[m][n];
    }
    // 其实就是按照target的排序规则，在arr中找最长的单调增的子序列
    public int minOperations(int[] target, int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length; i++) {
            map.put(target[i], i);
        }
        ArrayList<Integer> g = new ArrayList<>();
        for (int x : arr) {
            Integer idx = map.get(x); // 这里使用Integer包装类就不会报错了
            if (idx != null) {
                int j = binarySearch(g, idx);
                if (j == g.size()) {
                    g.add(idx);
                }else{
                    g.set(j, idx);
                }
            }
        }
        return target.length - g.size();
    }
    public int binarySearch(List<Integer> list,Integer target){
        int left = 0 , right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right + 1; // 二分找第一个大于等于x的位置, 将x替换到该位置
    }

}
