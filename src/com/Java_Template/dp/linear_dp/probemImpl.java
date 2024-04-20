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



}
