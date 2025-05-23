## [【牛客周赛E dp好题】](https://ac.nowcoder.com/acm/contest/102896/E)

![1740916150495](assets/1740916150495.png)

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 读取藏宝库格子的数量 n 和移动次数 k
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] a = new int[n];
        // 读取每个格子的权值
        for (int i = 0; i < n; i++){
            a[i] = sc.nextInt();
        }
        
        // 定义一个非常小的数，用作负无穷（避免直接使用真正的 -∞）
        long INF = Long.MIN_VALUE / 2;
        // dp[i] 表示正好在某次移动后到达第 i 格时获得的最大金币数
        // 注意：藏宝库格子编号为 1 到 n（初始位置 0 表示在藏宝阁外）
        long[] dp = new long[n + 1];
        Arrays.fill(dp, INF);
        
        // 第一次移动：从位置 0（藏宝阁外）移动到 1~6 格（若存在）
        for (int i = 1; i <= n && i <= 6; i++){
            dp[i] = a[i - 1];
        }
        
        // 处理第 2 至 k 次移动
        // 每次移动可以前进一步至最多 6 格
        // 对于当前移动到达的格子 i，其前一个位置 j 必须满足：
        //      j ∈ [max(上一次移动后最小可能位置, i - 6), i - 1]
        for (int move = 2; move <= k; move++){
            long[] newdp = new long[n + 1];
            Arrays.fill(newdp, INF);
            // 第 move 次移动后能到达的最小格子编号为 move （每次至少前进一格）
            // 能到达的最大格子编号为 move * 6（如果每次都走 6 格）
            int start = move;
            int end = Math.min(n, move * 6);
            for (int i = start; i <= end; i++){
                long best = INF;
                // 考虑能够到达格子 i 的所有可能前一个格子
                int lower = Math.max(move - 1, i - 6);
                for (int j = lower; j < i; j++){
                    best = Math.max(best, dp[j]);
                }
                if (best != INF) {
                    newdp[i] = best + a[i - 1];
                }
            }
            // 更新dp数组为当前移动状态
            dp = newdp;
        }
        
        // 在正好 k 次移动后，找到所能获得的最大金币数
        long ans = INF;
        int endIdx = Math.min(n, k * 6);
        for (int i = k; i <= endIdx; i++){
            ans = Math.max(ans, dp[i]);
        }
        System.out.println(ans);
    }
}
```



## [3448. 统计可以被最后一个数位整除的子字符串数目](https://leetcode.cn/problems/count-substrings-divisible-by-last-digit/)

> 给你一个只包含数字的字符串 `s` 。
>
> 请你返回 `s` 的最后一位 **不是** 0 的子字符串中，可以被子字符串最后一位整除的数目。
>
> **子字符串** 是一个字符串里面一段连续 **非空** 的字符序列。
>
> **注意：**子字符串可以有前导 0 。
>
> **示例 1：**
>
> **输入：**s = "12936"
>
> **输出：**11
>
> **解释：**
>
> 子字符串 `"29"` ，`"129"` ，`"293"` 和 `"2936"` 不能被它们的最后一位整除，总共有 15 个子字符串，所以答案是 `15 - 4 = 11` 。
>
> **示例 2：**
>
> **输入：**s = "5701283"
>
> **输出：**18
>
> **解释：**
>
> 子字符串 `"01"` ，`"12"` ，`"701"` ，`"012"` ，`"128"` ，`"5701"` ，`"7012"` ，`"0128"` ，`"57012"` ，`"70128"` ，`"570128"` 和 `"701283"` 都可以被它们最后一位数字整除。除此以外，所有长度为 1 且不为 0 的子字符串也可以被它们的最后一位整除。有 6 个这样的子字符串，所以答案为 `12 + 6 = 18` 。
>
> **示例 3：**
>
> **输入：**s = "1010101010"
>
> **输出：**25
>
> **解释：**
>
> 只有最后一位数字为 `'1'` 的子字符串可以被它们的最后一位整除，总共有 25 个这样的字符串。
>
>  
>
> **提示：**
>
> - `1 <= s.length <= 105`
> - `s` 只包含数字。

```java
class Solution {
    public long countSubstrings(String s) { // 不做空间优化
        long total = 0;
        int n = s.length();
        int[][][] dp = new int[n + 1][10][]; // dp[i][j][k]表示到达下标i，以nums[i]结尾模j余数为k的字串个数
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 9; j++) {
                dp[i][j] = new int[j];
            }
        }
        for (int i = 1; i <= n; i++) {
            int digit = s.charAt(i - 1) - '0';
            for (int j = 1; j <= 9; j++) {
                dp[i][j][digit % j] = 1;
                for (int k = 0; k < j; k++) {
                    dp[i][j][(k * 10 + digit) % j] += dp[i - 1][j][k];
                }
            }
            if (digit > 0) {
                total += dp[i][digit][0];
            }
        }
        return total;
    }
}
```

```java
class Solution { // 空间优化版本 其中dp[i][j]表示模i余j的子串个数
    public long countSubstrings(String s) {
        long ans = 0;
        long[][] dp = new long[10][9];
        for (char c : s.toCharArray()) {
            int d = c - '0';
            for (int i = 1; i < 10; i++) {
                long[] f = new long[i]; // 表示模i余数分别为[0...i]的个数
                f[d % i] = 1;
                for (int j = 0; j < i; j++) { // 枚举余数
                    f[(j * 10 % i + d) % i] += dp[i][j];
                }
                dp[i] = f;
            }
            ans += dp[d][0];
        }
        return ans;
    }
}
```



##  [【模板】3177. 求出最长好子序列 II](https://leetcode.cn/problems/find-the-maximum-length-of-a-good-subsequence-ii/)

> 这题是进阶线性dp最好的模板
>
> 题意：找到一个最长的子序列，其中相邻的不同元素最多为k个。
>
> - `1 <= nums.length <= 5 * 103`
> - `1 <= nums[i] <= 109`
> - `0 <= k <= min(50, nums.length)`
>
> 如果是暴力dp，我们需要保存多个状态。dfs（当前遍历到的元素下标，剩余可以使用的不同相邻元素，上一个元素 ），返回值就是选择的序列长度。这样时间复杂度是O(5000 * k * (不同元素个数<=5000) ) = 1.25e9，这肯定超时了。
>
> 优化：我们发现k特别的小，我们可以遍历以元素x结尾还可以使用k次不同。
>
> x==nums[i]: dp(nums[i],  k) = max( dp(nums[i],  k) + 1,  dp(y, k - 1) + 1)  ,其中y != nums[i]，这里可以用哈希表优化，见代码。

```java
import java.util.HashMap;

class Solution {
    public int maximumLength(int[] nums, int k) {
        // dp[i][j]表示以i结尾的剩余j个不同下标可用的最长子序列
        HashMap<Integer, int[]> fs = new HashMap<>();
        int[] mx = new int[k + 1];
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, e -> new int[k + 1]); // 取出以x结尾的序列
            for (int i = k; i >= 0; i--) {
                f[i]++;
                if (i > 0) {
                    f[i] = Math.max(f[i], mx[i - 1] + 1);
                }
                mx[i] = Math.max(mx[i], f[i]);
            }
        }
        return mx[k];
    }
}
```



1553\. 吃掉 N 个橘子的最少天数
--------------------

厨房里总共有 `n` 个橘子，你决定每一天选择如下方式之一吃这些橘子：

*   吃掉一个橘子。
*   如果剩余橘子数 `n` 能被 2 整除，那么你可以吃掉 `n/2` 个橘子。
*   如果剩余橘子数 `n` 能被 3 整除，那么你可以吃掉 `2*(n/3)` 个橘子。

每天你只能从以上 3 种方案中选择一种方案。

请你返回吃掉所有 `n` 个橘子的最少天数。

**示例 1：**

**输入：**n = 10
**输出：**4
**解释：**你总共有 10 个橘子。
第 1 天：吃 1 个橘子，剩余橘子数 10 - 1 = 9。
第 2 天：吃 6 个橘子，剩余橘子数 9 - 2\*(9/3) = 9 - 6 = 3。（9 可以被 3 整除）
第 3 天：吃 2 个橘子，剩余橘子数 3 - 2\*(3/3) = 3 - 2 = 1。
第 4 天：吃掉最后 1 个橘子，剩余橘子数 1 - 1 = 0。
你需要至少 4 天吃掉 10 个橘子。

**示例 2：**

**输入：**n = 6
**输出：**3
**解释：**你总共有 6 个橘子。
第 1 天：吃 3 个橘子，剩余橘子数 6 - 6/2 = 6 - 3 = 3。（6 可以被 2 整除）
第 2 天：吃 2 个橘子，剩余橘子数 3 - 2\*(3/3) = 3 - 2 = 1。（3 可以被 3 整除）
第 3 天：吃掉剩余 1 个橘子，剩余橘子数 1 - 1 = 0。
你至少需要 3 天吃掉 6 个橘子。

**示例 3：**

**输入：**n = 1
**输出：**1

**示例 4：**

**输入：**n = 56
**输出：**6

**提示：**

*   `1 <= n <= 2*10^9`

[https://leetcode.cn/problems/minimum-number-of-days-to-eat-n-oranges/description/?envType=daily-question&envId=2024-05-12](https://leetcode.cn/problems/minimum-number-of-days-to-eat-n-oranges/description/?envType=daily-question&envId=2024-05-12)

```java
// 最暴力的做法，直接超时
import java.util.Arrays;

class Solution {
    int[] memo;
    public int minDays(int n) {
        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return dfs(n);
    }

    private int dfs(int i) { // 表示吃到剩余i个苹果的最少天数
        if (i <= 0) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int ans = i;
        ans = Math.min(ans, dfs(i - 1) + 1);
        if (i % 2 == 0) {
            ans = Math.min(ans, dfs(i / 2) + 1);
        }
        if (i % 3 == 0) {
            ans = Math.min(ans, dfs(i / 3) + 1);
        }
        return memo[i] = ans;
    }
}
```

```java
// 使用贪心思想优化，但是爆内存
import java.util.Arrays;

class Solution {
    int[] memo;
    public int minDays(int n) {
        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return dfs(n);
    }

    private int dfs(int i) { // 表示吃到剩余i个苹果的最少天数
        if (i <= 1) {
            return i; // 注意递归边界
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int ans;
        ans = Math.min(dfs(i / 2) + i % 2, dfs(i / 3) + i % 3) + 1;
        return memo[i] = ans;
    }
}
```

```java
// 使用map来记忆化存储
import java.util.HashMap;

class Solution {
    HashMap<Integer, Integer> memo = new HashMap<>();
    public int minDays(int n) {
        return dfs(n);
    }

    private int dfs(int i) { // 表示吃到剩余i个苹果的最少天数
        if (i <= 1) {
            return i;
        }
        if (memo.containsKey(i)) {
            return memo.get(i);
        }
        int ans = Math.min(dfs(i / 2) + i % 2, dfs(i / 3) + i % 3) + 1;
        memo.put(i, ans);
        return ans;
    }
}
```

3041\. 修改数组后最大化数组中的连续元素数目
-------------------------

给你一个下标从 **0** 开始只包含 **正** 整数的数组 `nums` 。

一开始，你可以将数组中 **任意数量** 元素增加 **至多** `1` 。

修改后，你可以从最终数组中选择 **一个或者更多** 元素，并确保这些元素升序排序后是 **连续** 的。比方说，`[3, 4, 5]` 是连续的，但是 `[3, 4, 6]` 和 `[1, 1, 2, 3]` 不是连续的。

请你返回 **最多** 可以选出的元素数目。

**示例 1：**

**输入：**nums = \[2,1,5,1,1\]
**输出：**3
**解释：**我们将下标 0 和 3 处的元素增加 1 ，得到结果数组 nums = \[3,1,5,2,1\] 。
我们选择元素 \[_**3**_,_**1**_,5,_**2**_,1\] 并将它们排序得到 \[1,2,3\] ，是连续元素。
最多可以得到 3 个连续元素。

**示例 2：**

**输入：**nums = \[1,4,7,10\]
**输出：**1
**解释：**我们可以选择的最多元素数目是 1 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 106`

[https://leetcode.cn/problems/maximize-consecutive-elements-in-an-array-after-modification/description/](https://leetcode.cn/problems/maximize-consecutive-elements-in-an-array-after-modification/description/)

```java
import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> dp = new HashMap<>(); // 存储以key结尾的最大长度
        for (int num : nums) {
            // 当前数+1
            dp.put(num + 1, dp.getOrDefault(num, 0) + 1); // 不能交换顺序！！！
            // 不+1
            dp.put(num, dp.getOrDefault(num - 1, 0) + 1);
        }
        int ans = 1;
        for (Integer key : dp.keySet()) {
            ans = Math.max(ans, dp.get(key));
        }
        return ans;
    }
}
```

2767\. 将字符串分割为最少的美丽子字符串
-----------------------

给你一个二进制字符串 `s` ，你需要将字符串分割成一个或者多个 **子字符串**  ，使每个子字符串都是 **美丽** 的。

如果一个字符串满足以下条件，我们称它是 **美丽** 的：

*   它不包含前导 0 。
*   它是 `5` 的幂的 **二进制** 表示。

请你返回分割后的子字符串的 **最少** 数目。如果无法将字符串 `s` 分割成美丽子字符串，请你返回 `-1` 。

子字符串是一个字符串中一段连续的字符序列。

**示例 1：**

**输入：**s = "1011"
**输出：**2
**解释：**我们可以将输入字符串分成 \["101", "1"\] 。
- 字符串 "101" 不包含前导 0 ，且它是整数 51 = 5 的二进制表示。
- 字符串 "1" 不包含前导 0 ，且它是整数 50 = 1 的二进制表示。
  最少可以将 s 分成 2 个美丽子字符串。

**示例 2：**

**输入：**s = "111"
**输出：**3
**解释：**我们可以将输入字符串分成 \["1", "1", "1"\] 。
- 字符串 "1" 不包含前导 0 ，且它是整数 50 = 1 的二进制表示。
  最少可以将 s 分成 3 个美丽子字符串。

**示例 3：**

**输入：**s = "0"
**输出：**\-1
**解释：**无法将给定字符串分成任何美丽子字符串。

**提示：**

*   `1 <= s.length <= 15`
*   `s[i]` 要么是 `'0'` 要么是 `'1'` 。

[https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/description/](https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/description/)

```java
import java.util.HashMap;
import java.util.HashSet;

class Solution {
    HashMap<Integer, Integer> memo = new HashMap<>();
    int n;
    String s;
    static HashSet<String> set = new HashSet<String>();
    static {
        int n = 1 << 15;
        for (int i = 1; i <= n; i *= 5) {
            set.add(Integer.toString(i, 2));
        }
    }
    public int minimumBeautifulSubstrings(String s) { // 3ms
        n = s.length();
        this.s = s;
        int ans = dfs(0);
        return ans >= Integer.MAX_VALUE / 2 ? -1 : ans;
    }

    private int dfs(int i) { // 表示从0到i的最少分割数
        if (i == n) {
            return 0;
        }
        if (memo.containsKey(i)) {
            return memo.get(i);
        }
        int res = Integer.MAX_VALUE / 2;
        for (String t : set) {
            int len = t.length();
            if (i + len > n) {
                continue;
            }
            if (s.substring(i, i + len).equals(t)) {
                res = Math.min(res, dfs(i + len) + 1);
            }
        }
        memo.put(i, res);
        return res;
    }
}
```

```java
import java.util.Arrays;
import java.util.HashSet;

class Solution {
    static HashSet<String> set = new HashSet<String>();
    static {
        int n = 1 << 15;
        for (int i = 1; i <= n; i *= 5) {
            set.add(Integer.toString(i, 2));
        }
    }
    public int minimumBeautifulSubstrings(String s) { // 开n的大小的数组 3ms
        int n = s.length();
        int[] dp = new int[n]; // dp[i]表示从0到达i的最小划分次数
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                if (s.charAt(i) == '0') {
                    return -1;
                }
                dp[0] = 1; // 这里是把首位1单独划出来
                continue;
            }
            for (String t : set) {
                int len = t.length();
                if (i - len + 1 < 0) { // 不能有前导0
                    continue;
                }
                if (t.equals(s.substring(i - len + 1, i + 1))) {
                    if (i - len < 0) { // 表示前面的一片都符合要求
                        dp[i] = 1;
                    }else{
                        dp[i] = Math.min(dp[i], dp[i - len] + 1);
                    }

                }
            }
        }
        return dp[n - 1] >= Integer.MAX_VALUE / 2 ? -1 : dp[n - 1];
    }

}
```

```java
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    static ArrayList<String> cnt = new ArrayList<>();
    static {
        int n = 1 << 15;
        for (int i = 1; i <= n; i *= 5) {
            cnt.add(Integer.toString(i, 2));
        }
    }

    public int minimumBeautifulSubstrings(String s) { // 开 n+1 的大小的数组 3ms
        int n = s.length();
        int[] dp = new int[n + 1]; // dp[i]表示0到第i- 1个数最少的划分
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (String t : cnt) {
                int len = t.length();
                if (i - len < 0) {
                    break;
                }
                if (t.equals(s.substring(i - len, i))) {
                    dp[i] = Math.min(dp[i], dp[i - len] + 1);
                }
            }
        }
        return dp[n] == Integer.MAX_VALUE/2 ? -1 : dp[n];
    }
}
```

```java
import java.util.Arrays;

class Solution {
    public int minimumBeautifulSubstrings(String s) { // 10ms
        int n = s.length();
        int[] dp = new int[n + 1]; // dp[i]表示0到第i- 1个数最少的划分
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) { // j是初始起点，但是从0开始
                if (judge(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n] == Integer.MAX_VALUE / 2 ? -1 : dp[n];
    }

    private boolean judge(String s) { // 判断这个数是不是5的幂
        int n = 1 << 15;
        for (int i = 1; i <= n; i *= 5) {
            if (s.equals(Integer.toString(i, 2))) {
                return true;
            }
        }
        return false;
    }
}
```

100289\. 分割字符频率相等的最少子字符串（都是模板）
------------------------

给你一个字符串 `s` ，你需要将它分割成一个或者更多的 **平衡** 子字符串。比方说，`s == "ababcc"` 那么 `("abab", "c", "c")` ，`("ab", "abc", "c")` 和 `("ababcc")` 都是合法分割，但是 `("a", **"bab"**, "cc")` ，`(**"aba"**, "bc", "c")` 和 `("ab", **"abcc"**)` 不是，不平衡的子字符串用粗体表示。

请你返回 `s` **最少** 能分割成多少个平衡子字符串。

**注意：**一个 **平衡** 字符串指的是字符串中所有字符出现的次数都相同。

**示例 1：**

**输入：**s = "fabccddg"

**输出：**3

**解释：**

我们可以将 `s` 分割成 3 个子字符串：`("fab, "ccdd", "g")` 或者 `("fabc", "cd", "dg")` 。

**示例 2：**

**输入：**s = "abababaccddb"

**输出：**2

**解释：**

我们可以将 `s` 分割成 2 个子字符串：`("abab", "abaccddb")` 。

**提示：**

*   `1 <= s.length <= 1000`
*   `s` 只包含小写英文字母。

[https://leetcode.cn/problems/minimum-substring-partition-of-equal-character-frequency/](https://leetcode.cn/problems/minimum-substring-partition-of-equal-character-frequency/)

```java
import java.util.Arrays;

class Solution {
    int n;
    int[][] memo;
    public int minimumSubstringsInPartition(String s) { // 超时了，复杂度达到了O(n^3)
        n = s.length();
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, n - 1, s);
    }

    private int dfs(int i, int j, String s) {
        if (i == j) {
            return 1;
        }
        if (judge(s.substring(i, j + 1))) {
            return memo[i][j] = 1;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int ans = j - i + 1;
        for (int k = i; k < j; k++) {
            int t = dfs(i, k, s) + dfs(k + 1, j, s);
            ans = Math.min(ans, t);
        }
        return memo[i][j] = ans;

    }

    private boolean judge(String s) {
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        int c = -1;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] == 0) {
                continue;
            }
            if (c == -1) {
                c = cnt[i];
                continue;
            }
            if (cnt[i] != c) {
                return false;
            }
        }
        return true;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    public int minimumSubstringsInPartition(String s) { // 压缩一维翻译成递推同样超时，时间主要因为judge需要O(n)
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.setAll(dp, i -> i);
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (judge(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n];
    }

    private boolean judge(String s) {
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        int c = -1;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] == 0) {
                continue;
            }
            if (c == -1) {
                c = cnt[i];
                continue;
            }
            if (cnt[i] != c) {
                return false;
            }
        }
        return true;
    }
   
}
```

```java
import java.util.Arrays;

class Solution {
    public int minimumSubstringsInPartition(String s) { // 正解O(n^2)
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.setAll(dp, i -> i);
        for (int i = 1; i <= n; i++) {
            int[] cnt = new int[26];
            int mxFreq = 0;
            int cntN = 0;
            for (int j = i - 1; j >= 0; j--) { // i - j就是长度，这里的倒序就很厉害
                int k = s.charAt(j) - 'a';
                cnt[k]++;
                if (cnt[k] == 1) {
                    cntN++;
                }
                mxFreq = Math.max(mxFreq, cnt[k]);
                if (mxFreq * cntN == i - j) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n];
    }
}
```

## [我不是大富翁](https://ac.nowcoder.com/acm/contest/75771/D)

---------------------------------------

![1715502017392](F:\leetcode\src\com\Java_Template\dp\base\base_dp.assets\1715502017392.png)

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] nums = new int[m];
        for (int i = 0; i < m; i++) {
            nums[i] = sc.nextInt() % n;
        }
        solve(nums, n, m);
        sc.close();
    }

    private static void solve(int[] nums, int n, int m) {
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int num : nums) {
            boolean[] t = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (dp[i]) {
                    t[(i - num + n) % n] = true;
                    t[(i + num) % n] = true;
                }
            }
            dp = t;
        }
        System.out.println(dp[0] ? "YES" : "NO");
    }

}
```

2312\. 卖木头块
-----------

给你两个整数 `m` 和 `n` ，分别表示一块矩形木块的高和宽。同时给你一个二维整数数组 `prices` ，其中 `prices[i] = [hi, wi, pricei]` 表示你可以以 `pricei` 元的价格卖一块高为 `hi` 宽为 `wi` 的矩形木块。

每一次操作中，你必须按下述方式之一执行切割操作，以得到两块更小的矩形木块：

*   沿垂直方向按高度 **完全** 切割木块，或
*   沿水平方向按宽度 **完全** 切割木块

在将一块木块切成若干小木块后，你可以根据 `prices` 卖木块。你可以卖多块同样尺寸的木块。你不需要将所有小木块都卖出去。你 **不能** 旋转切好后木块来交换它的高度值和宽度值。

请你返回切割一块大小为 `m x n` 的木块后，能得到的 **最多** 钱数。

注意你可以切割木块任意次。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/04/27/ex1.png)

**输入：**m = 3, n = 5, prices = \[\[1,4,2\],\[2,2,7\],\[2,1,3\]\]
**输出：**19
**解释：**上图展示了一个可行的方案。包括：
- 2 块 2 x 2 的小木块，售出 2 \* 7 = 14 元。
- 1 块 2 x 1 的小木块，售出 1 \* 3 = 3 元。
- 1 块 1 x 4 的小木块，售出 1 \* 2 = 2 元。
  总共售出 14 + 3 + 2 = 19 元。
  19 元是最多能得到的钱数。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/04/27/ex2new.png)

**输入：**m = 4, n = 6, prices = \[\[3,2,10\],\[1,4,2\],\[4,1,3\]\]
**输出：**32
**解释：**上图展示了一个可行的方案。包括：
- 3 块 3 x 2 的小木块，售出 3 \* 10 = 30 元。
- 1 块 1 x 4 的小木块，售出 1 \* 2 = 2 元。
  总共售出 30 + 2 = 32 元。
  32 元是最多能得到的钱数。
  注意我们不能旋转 1 x 4 的木块来得到 4 x 1 的木块。

**提示：**

*   `1 <= m, n <= 200`
*   `1 <= prices.length <= 2 * 104`
*   `prices[i].length == 3`
*   `1 <= hi <= m`
*   `1 <= wi <= n`
*   `1 <= pricei <= 106`
*   所有 `(hi, wi)` **互不相同** 。

[https://leetcode.cn/problems/selling-pieces-of-wood/submissions/531260293/?envType=daily-question&envId=2024-03-15](https://leetcode.cn/problems/selling-pieces-of-wood/submissions/531260293/?envType=daily-question&envId=2024-03-15)

```java
class Solution {
    public long sellingWood(int m, int n, int[][] prices) {
        // 定义dp[i][j]表示切割一块高i,宽j的木块的最大收益
        long[][] dp = new long[m + 1][n + 1];
        for (int[] price : prices) {
            dp[price[0]][price[1]] = price[2];
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 0; k <= j / 2; k++) { // 竖着切
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - k] + dp[i][k]);
                }
                for (int k = 0; k <= i / 2; k++) { // 横着切
                    dp[i][j] = Math.max(dp[i][j], dp[i - k][j] + dp[k][j]);
                }
            }
        }
        return dp[m][n];
    }
}
```

2266\. 统计打字方案数
--------------

Alice 在给 Bob 用手机打字。数字到字母的 **对应** 如下图所示。

![](https://assets.leetcode.com/uploads/2022/03/15/1200px-telephone-keypad2svg.png)

为了 **打出** 一个字母，Alice 需要 **按** 对应字母 `i` 次，`i` 是该字母在这个按键上所处的位置。

*   比方说，为了按出字母 `'s'` ，Alice 需要按 `'7'` 四次。类似的， Alice 需要按 `'5'` 两次得到字母  `'k'` 。
*   注意，数字 `'0'` 和 `'1'` 不映射到任何字母，所以 Alice **不** 使用它们。

但是，由于传输的错误，Bob 没有收到 Alice 打字的字母信息，反而收到了 **按键的字符串信息** 。

*   比方说，Alice 发出的信息为 `"bob"` ，Bob 将收到字符串 `"2266622"` 。

给你一个字符串 `pressedKeys` ，表示 Bob 收到的字符串，请你返回 Alice **总共可能发出多少种文字信息** 。

由于答案可能很大，将它对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**pressedKeys = "22233"
**输出：**8
**解释：**
Alice 可能发出的文字信息包括：
"aaadd", "abdd", "badd", "cdd", "aaae", "abe", "bae" 和 "ce" 。
由于总共有 8 种可能的信息，所以我们返回 8 。

**示例 2：**

**输入：**pressedKeys = "222222222222222222222222222222222222"
**输出：**82876089
**解释：**
总共有 2082876103 种 Alice 可能发出的文字信息。
由于我们需要将答案对 109 + 7 取余，所以我们返回 2082876103 % (109 + 7) = 82876089 。

**提示：**

*   `1 <= pressedKeys.length <= 105`
*   `pressedKeys` 只包含数字 `'2'` 到 `'9'` 。

[https://leetcode.cn/problems/count-number-of-texts/description/](https://leetcode.cn/problems/count-number-of-texts/description/)

```java
class Solution {
    private static int Mod = (int) 1e9 + 7, MX = (int) 1e5 + 1;
    static int[] f = new int[MX];
    static int[] g = new int[MX];
    static { // 我们可以将末尾的 1 个、2 个或 3 个字符单独视作一个字母。
        f[0] = g[0] = 1;
        f[1] = g[1] = 1;
        f[2] = g[2] = 2;
        f[3] = g[3] = 4;
        for (int i = 4; i < MX; i++) {
            f[i] = (int) (((long) f[i - 1] + f[i - 2] + f[i - 3]) % Mod);
            g[i] = (int) (((long) g[i - 1] + g[i - 2] + g[i - 3] + g[i - 4]) % Mod);
        }
    }
    public int countTexts(String pressedKeys) {
        int n = pressedKeys.length();
        int ans = 1, cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt++;
            char c = pressedKeys.charAt(i);
            if (i == n - 1 || c != pressedKeys.charAt(i + 1)) {
                ans = (int) ((long) ans * (c == '7' || c == '9' ? g[cnt] : f[cnt]) % Mod);
                cnt = 0;
            }
        }
        return ans;
    }
}
```

213\. 打家劫舍 II
-------------

你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 **围成一圈** ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警** 。

给定一个代表每个房屋存放金额的非负整数数组，计算你 **在不触动警报装置的情况下** ，今晚能够偷窃到的最高金额。

**示例 1：**

**输入：**nums = \[2,3,2\]
**输出：**3
**解释：**你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。

**示例 2：**

**输入：**nums = \[1,2,3,1\]
**输出：**4
**解释：**你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     偷窃到的最高金额 = 1 + 3 = 4 。

**示例 3：**

**输入：**nums = \[1,2,3\]
**输出：**3

**提示：**

*   `1 <= nums.length <= 100`
*   `0 <= nums[i] <= 1000`

[https://leetcode.cn/problems/house-robber-ii/](https://leetcode.cn/problems/house-robber-ii/)

```java
import java.util.Arrays;

class Solution {
    int n;
    int[] nums;
    int[] memo;
    public int rob(int[] nums) { // dfs记忆化搜索
        this.nums = nums;
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        memo = new int[n];
        Arrays.fill(memo, -1);
        int res = dfs(n - 1, 1);
        Arrays.fill(memo, -1);
        return Math.max(res, dfs(n - 2, 0));
    }

    private int dfs(int i, int start) {
        if (i < start) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        return memo[i] = Math.max(dfs(i - 1, start), dfs(i - 2, start) + nums[i]);
    }
}

```

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        // 偷nums[0]，那么只能偷nums[2]到nums[n - 2]
        int[] dp1 = new int[n];
        dp1[2] = nums[2];
        for (int i = 3; i < n - 1; i++) {
            dp1[i] = Math.max(dp1[i - 1], dp1[i - 2] + nums[i]);
        }
        dp1[n - 2] += nums[0];
        // 不偷nums[0]，那么就是偷nums[1]到nums[n - 1]
        int[] dp2 = new int[n];
        dp2[1] = nums[1];
        for (int i = 2; i < n; i++) {
            dp2[i] = Math.max(dp2[i - 1], dp2[i - 2] + nums[i]);
        }
        return Math.max(dp1[n - 2], dp2[n - 1]);
    }
}

```

2320\. 统计放置房子的方式数
-----------------

一条街道上共有 `n * 2` 个 **地块** ，街道的两侧各有 `n` 个地块。每一边的地块都按从 `1` 到 `n` 编号。每个地块上都可以放置一所房子。

现要求街道同一侧不能存在两所房子相邻的情况，请你计算并返回放置房屋的方式数目。由于答案可能很大，需要对 `109 + 7` 取余后再返回。

注意，如果一所房子放置在这条街某一侧上的第 `i` 个地块，不影响在另一侧的第 `i` 个地块放置房子。

**示例 1：**

**输入：**n = 1
**输出：**4
**解释：**
可能的放置方式：
1. 所有地块都不放置房子。
2. 一所房子放在街道的某一侧。
3. 一所房子放在街道的另一侧。
4. 放置两所房子，街道两侧各放置一所。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/05/12/arrangements.png)

**输入：**n = 2
**输出：**9
**解释：**如上图所示，共有 9 种可能的放置方式。

**提示：**

*   `1 <= n <= 104`

[https://leetcode.cn/problems/count-number-of-ways-to-place-houses/description/](https://leetcode.cn/problems/count-number-of-ways-to-place-houses/description/)

```java
class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int countHousePlacements(int n) { // 只看单边
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % Mod;
        }
        return (int) ((long) dp[n] * dp[n] % Mod);
    }
}
```

53\. 最大子数组和
-----------

给你一个整数数组 `nums` ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

**子数组**

是数组中的一个连续部分。

**示例 1：**

**输入：**nums = \[-2,1,-3,4,-1,2,1,-5,4\]
**输出：**6
**解释：**连续子数组 \[4,-1,2,1\] 的和最大，为 6 。

**示例 2：**

**输入：**nums = \[1\]
**输出：**1

**示例 3：**

**输入：**nums = \[5,4,-1,7,8\]
**输出：**23

**提示：**

*   `1 <= nums.length <= 105`
*   `-104 <= nums[i] <= 104`

**进阶：**如果你已经实现复杂度为 `O(n)` 的解法，尝试使用更为精妙的 **分治法** 求解。

[https://leetcode.cn/problems/maximum-subarray/description/](https://leetcode.cn/problems/maximum-subarray/description/)

```java
class Solution {
    public int maxSubArray(int[] nums) {
        // dp[i] 表示：以 nums[i] 结尾的连续子数组的最大和
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = dp[i - 1] + nums[i];
            }else{
                dp[i] = nums[i];
            }
        }
        int ans = nums[0];
        for (int i = 1; i < n; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
```

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int preSum = 0;
        int minPre = 0;
        for (int num : nums) {
            preSum += num;
            ans = Math.max(ans, preSum - minPre);
            minPre = Math.min(minPre, preSum);
        }
        return ans;
    }
}
```

1749\. 任意子数组和的绝对值的最大值
---------------------

给你一个整数数组 `nums` 。一个子数组 `[numsl, numsl+1, ..., numsr-1, numsr]` 的 **和的绝对值** 为 `abs(numsl + numsl+1 + ... + numsr-1 + numsr)` 。

请你找出 `nums` 中 **和的绝对值** 最大的任意子数组（**可能为空**），并返回该 **最大值** 。

`abs(x)` 定义如下：

*   如果 `x` 是负整数，那么 `abs(x) = -x` 。
*   如果 `x` 是非负整数，那么 `abs(x) = x` 。

**示例 1：**

**输入：**nums = \[1,-3,2,3,-4\]
**输出：**5
**解释：**子数组 \[2,3\] 和的绝对值最大，为 abs(2+3) = abs(5) = 5 。

**示例 2：**

**输入：**nums = \[2,-5,1,-4,3,-2\]
**输出：**8
**解释：**子数组 \[-5,1,-4\] 和的绝对值最大，为 abs(-5+1-4) = abs(-8) = 8 。

**提示：**

*   `1 <= nums.length <= 105`
*   `-104 <= nums[i] <= 104`

[https://leetcode.cn/problems/maximum-absolute-sum-of-any-subarray/](https://leetcode.cn/problems/maximum-absolute-sum-of-any-subarray/)

```java
class Solution {
    public int maxAbsoluteSum(int[] nums) {
        int n = nums.length;
        int[] dpMax = new int[n + 1];
        int[] dpMin = new int[n + 1];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            dpMax[i + 1] = Math.max(dpMax[i] + nums[i], nums[i]);
            dpMin[i + 1] = Math.min(dpMin[i] + nums[i], nums[i]);
            ans = Math.max(ans, Math.max(dpMax[i + 1], -dpMin[i + 1]));
        }
        return ans;
    }
}
```

```java
class Solution {
    public int maxAbsoluteSum(int[] nums) { // 前缀和思想，不好理解，建议用dp思想
        int mn = 0, mx = 0, preSum = 0;
        for (int num : nums) {
            preSum += num;
            if (preSum > mx) {
                mx = preSum;
            }
            if (preSum < mn) {
                mn = preSum;
            }
        }
        return mx - mn;
    }
}
```

363\. 矩形区域不超过 K 的最大数值和
----------------------

给你一个 `m x n` 的矩阵 `matrix` 和一个整数 `k` ，找出并返回矩阵内部矩形区域的不超过 `k` 的最大数值和。

题目数据保证总会存在一个数值和不超过 `k` 的矩形区域。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/03/18/sum-grid.jpg)

**输入：**matrix = \[\[1,0,1\],\[0,-2,3\]\], k = 2
**输出：**2
**解释：**蓝色边框圈出来的矩形区域 `[[0, 1], [-2, 3]]` 的数值和是 2，且 2 是不超过 k 的最大数字（k = 2）。

**示例 2：**

**输入：**matrix = \[\[2,2,-1\]\], k = 3
**输出：**3

**提示：**

*   `m == matrix.length`
*   `n == matrix[i].length`
*   `1 <= m, n <= 100`
*   `-100 <= matrix[i][j] <= 100`
*   `-105 <= k <= 105`

**进阶：**如果行数远大于列数，该如何设计解决方案？

[https://leetcode.cn/problems/max-sum-of-rectangle-no-larger-than-k/description/](https://leetcode.cn/problems/max-sum-of-rectangle-no-larger-than-k/description/)

```java
class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) { // O(m*m*n*n)
        int m = matrix.length, n = matrix[0].length;
        int[][] preSum = new int[m + 1][n + 1];
        // 二维前缀和
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i + 1][j] + preSum[i][j + 1] - preSum[i][j] + matrix[i][j];
                if (preSum[i + 1][j + 1] == k) {
                    return k;
                }
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int x = i; x < m; x++) {
                    for (int y = j; y < n; y++) {
                        int area = preSum[x + 1][y + 1] + preSum[i][j] - preSum[i][y + 1] - preSum[x + 1][j];
                        // int area = sum[x][y] - (i - 1 < 0 ? 0 : sum[i - 1][y]) - (j - 1 < 0 ? 0 : sum[x][j - 1]) + (i - 1 < 0 || j - 1 < 0 ? 0 :sum[i - 1][j - 1]);
                        // System.out.println(area);
                        if (area == k) {
                            return k;
                        }
                        if (area < k) {
                            ans = Math.max(ans, area);
                        }
                    }
                }
            }
        }
        return ans;
    }
}
```

- **K lowerKey(K key)**：返回严格小于给定键的最大键；如果没有这样的键，则返回 `null`。
- **K floorKey(K key)**：返回小于或等于给定键的最大键；如果没有这样的键，则返回 `null`。
- **K ceilingKey(K key)**：返回大于或等于给定键的最小键；如果没有这样的键，则返回 `null`。
- **K higherKey(K key)**：返回严格大于给定键的最小键；如果没有这样的键，则返回 `null`。

```java
class Solution {
    public int maxSumSubmatrix(int[][] mat, int k) { // O(m^2 ∗ nlogn)
        int m = mat.length, n = mat[0].length;

        // 预处理前缀和
        int[][] sum = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }

        int ans = Integer.MIN_VALUE;
        // 遍历子矩阵的上边界
        for (int top = 1; top <= m; top++) {
            // 遍历子矩阵的下边界
            for (int bot = top; bot <= m; bot++) {
                // 使用「有序集合」维护所有遍历到的右边界
                TreeSet<Integer> ts = new TreeSet<>();
                ts.add(0);
                // 遍历子矩阵的右边界
                for (int r = 1; r <= n; r++) {
                    // 通过前缀和计算 right
                    int right = sum[bot][r] - sum[top - 1][r];
                    // 通过二分找 left
                    Integer left = ts.ceiling(right - k);
                    if (left != null) {
                        int cur = right - left;
                        ans = Math.max(ans, cur);
                    }
                    // 将遍历过的 right 加到有序集合
                    ts.add(right);
                }
            }
        }
        return ans;
    }
}
```

```java
class Solution {
    public int maxSumSubmatrix(int[][] mat, int k) { // 最大化二分收益，也就是调换压缩的行列
        int m = mat.length, n = mat[0].length;
        int[][] sum = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + mat[i - 1][j - 1];
            }
        }
        // 固定的是否为右边界
        boolean isRight = n > m;
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i <= (isRight ? m : n); i++) {
            for (int j = i; j <= (isRight ? m : n); j++) {
                TreeSet<Integer> ts = new TreeSet<>();
                ts.add(0);
                for (int fixed = 1; fixed <= (isRight ? n : m); fixed++) {
                    int a = isRight ? sum[j][fixed] - sum[i - 1][fixed] : sum[fixed][j] - sum[fixed][i - 1];
                    Integer b = ts.ceiling(a - k);
                    if (b != null) {
                        int cur = a - b;
                        ans = Math.max(ans, cur);
                    }
                    ts.add(a);
                }
            }
        }
        return ans;
    }
}
```



152\. 乘积最大子数组
-------------

给你一个整数数组 `nums` ，请你找出数组中乘积最大的非空连续

子数组

（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

测试用例的答案是一个 **32-位** 整数。

**示例 1:**

**输入:** nums = \[2,3,-2,4\]
**输出:** `6`
**解释:** 子数组 \[2,3\] 有最大乘积 6。

**示例 2:**

**输入:** nums = \[-2,0,-1\]
**输出:** 0
**解释:** 结果不能为 2, 因为 \[-2,-1\] 不是子数组。

**提示:**

*   `1 <= nums.length <= 2 * 104`
*   `-10 <= nums[i] <= 10`
*   `nums` 的任何前缀或后缀的乘积都 **保证** 是一个 **32-位** 整数

[https://leetcode.cn/problems/maximum-product-subarray/description/](https://leetcode.cn/problems/maximum-product-subarray/description/)

```java
class Solution {
    public int maxProduct(int[] nums) {
        /* 标签：动态规划
            遍历数组时计算当前最大值，不断更新
            令imax为当前最大值，则当前最大值为 imax = max(imax * nums[i], nums[i])
            由于存在负数，那么会导致最大的变最小的，最小的变最大的。因此还需要维护当前最小值imin，imin = min(imin * nums[i], nums[i])
            当负数出现时则imax与imin进行交换再进行下一步计算
            时间复杂度：O(n) */
        int ans = Integer.MIN_VALUE, iMax = 1, iMin = 1;
        for (int num : nums) {
            if (num < 0) {
                int temp = iMax;
                iMax = iMin;
                iMin = temp;
            }
            iMax = Math.max(iMax * num, num);
            iMin = Math.min(iMin * num, num);
            ans = Math.max(iMax, ans);
        }
        return ans;
    }
}
```

```java
class Solution {
    public int maxProduct(int[] nums) {
        int ans = Integer.MIN_VALUE, mx = 1, mn = 1;
        for (int x : nums) {
            if (x > 0) {
                mx = Math.max(mx * x, x);
                mn = Math.min(mn * x, x);
            }else{
                int t = mx; // 这里需要t在中间做临时存储
                mx = Math.max(mn * x, x);
                mn = Math.min(x * t, x);
            }
            ans = Math.max(mx, ans);
        }
        return ans;
    }
}
```



1289\. 下降路径最小和 II
-----------------

给你一个 `n x n` 整数矩阵 `grid` ，请你返回 **非零偏移下降路径** 数字和的最小值。

**非零偏移下降路径** 定义为：从 `grid` 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/08/10/falling-grid.jpg)

**输入：**grid = \[\[1,2,3\],\[4,5,6\],\[7,8,9\]\]
**输出：**13
**解释：**
所有非零偏移下降路径包括：
\[1,5,9\], \[1,5,7\], \[1,6,7\], \[1,6,8\],
\[2,4,8\], \[2,4,9\], \[2,6,7\], \[2,6,8\],
\[3,4,8\], \[3,4,9\], \[3,5,7\], \[3,5,9\]
下降路径中数字和最小的是 \[1,5,7\] ，所以答案是 13 。

**示例 2：**

**输入：**grid = \[\[7\]\]
**输出：**7

**提示：**

*   `n == grid.length == grid[i].length`
*   `1 <= n <= 200`
*   `-99 <= grid[i][j] <= 99`

[https://leetcode.cn/problems/minimum-falling-path-sum-ii/](https://leetcode.cn/problems/minimum-falling-path-sum-ii/)

```java
import java.util.Arrays;

class Solution {
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = grid[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (j == k) {
                        continue;
                    }
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + grid[i][j]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }
        return ans;
    }
}
```

1594\. 矩阵的最大非负积
---------------

给你一个大小为 `m x n` 的矩阵 `grid` 。最初，你位于左上角 `(0, 0)` ，每一步，你可以在矩阵中 **向右** 或 **向下** 移动。

在从左上角 `(0, 0)` 开始到右下角 `(m - 1, n - 1)` 结束的所有路径中，找出具有 **最大非负积** 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。

返回 **最大非负积** 对 **`109 + 7`** **取余** 的结果。如果最大积为 **负数** ，则返回 `-1` 。

**注意，**取余是在得到最大积之后执行的。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/12/23/product1.jpg)

**输入：**grid = \[\[-1,-2,-3\],\[-2,-3,-3\],\[-3,-3,-2\]\]
**输出：**\-1
**解释：**从 (0, 0) 到 (2, 2) 的路径中无法得到非负积，所以返回 -1 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/12/23/product2.jpg)

**输入：**grid = \[\[1,-2,1\],\[1,-2,1\],\[3,-4,1\]\]
**输出：**8
**解释：**最大非负积对应的路径如图所示 (1 \* 1 \* -2 \* -4 \* 1 = 8)

**示例 3：**

![](https://assets.leetcode.com/uploads/2021/12/23/product3.jpg)

**输入：**grid = \[\[1,3\],\[0,-4\]\]
**输出：**0
**解释：**最大非负积对应的路径如图所示 (1 \* 0 \* -4 = 0)

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 15`
*   `-4 <= grid[i][j] <= 4`

[https://leetcode.cn/problems/maximum-non-negative-product-in-a-matrix/](https://leetcode.cn/problems/maximum-non-negative-product-in-a-matrix/)

```java
class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][] dpMax = new long[m][n];
        long[][] dpMin = new long[m][n];
        dpMax[0][0] = grid[0][0];
        dpMin[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dpMax[i][0] = dpMax[i - 1][0] * grid[i][0];
            dpMin[i][0] = dpMin[i - 1][0] * grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dpMax[0][i] = dpMax[0][i - 1] * grid[0][i];
            dpMin[0][i] = dpMin[0][i - 1] * grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] > 0) {
                    dpMax[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] < 0) {
                    dpMin[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                    dpMax[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                }else{
                    dpMin[i][j] = 0;
                    dpMax[i][j] = 0;
                }
            }
        }
        return dpMax[m - 1][n - 1] >= 0 ? (int) (dpMax[m - 1][n - 1] % Mod) : -1;
    }
}
```

3177\. 求出最长好子序列 II(子序列优化dp,思想非常重要)
------------------

给你一个整数数组 `nums` 和一个 **非负** 整数 `k` 。如果一个整数序列 `seq` 满足在范围下标范围 `[0, seq.length - 2]` 中存在 **不超过** `k` 个下标 `i` 满足 `seq[i] != seq[i + 1]` ，那么我们称这个整数序列为 **好** 序列。

请你返回 `nums` 中 **好子序列**的最长长度

**示例 1：**

**输入：**nums = \[1,2,1,1,3\], k = 2

**输出：**4

**解释：**

最长好子序列为 `[_**1**_,_**2**_,**_1_**,_**1**_,3]` 。

**示例 2：**

**输入：**nums = \[1,2,3,4,5,1\], k = 0

**输出：**2

**解释：**

最长好子序列为 `[**_1_**,2,3,4,5,**_1_**]` 。

**提示：**

*   `1 <= nums.length <= 5 * 103`
*   `1 <= nums[i] <= 109`
*   `0 <= k <= min(50, nums.length)`

[https://leetcode.cn/problems/find-the-maximum-length-of-a-good-subsequence-ii/description/](https://leetcode.cn/problems/find-the-maximum-length-of-a-good-subsequence-ii/description/)

```java
import java.util.HashMap;

class Solution { // 直接看视频，不要看灵神的题解（别犟，千万只看视频）https://www.bilibili.com/video/BV1Tx4y1b7wk/
    public int maximumLength(int[] nums, int k) {
        HashMap<Integer, int[]> fs = new HashMap<>();
        int[] mx = new int[k + 1]; // mx[i]表示不同个数为i的子序列最大长度
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, e -> new int[k + 1]);
            for (int i = k; i >= 0; i--) {
                f[i]++;
                if (i > 0) {
                    f[i] = Math.max(f[i], mx[i - 1] + 1);
                }
                mx[i] = Math.max(mx[i], f[i]);
            }
        }
        return mx[k];
    }
}
```

100333\. 统计逆序对的数目
-----------------

给你一个整数 `n` 和一个二维数组 `requirements` ，其中 `requirements[i] = [endi, cnti]` 表示这个要求中的末尾下标和 **逆序对** 的数目。

整数数组 `nums` 中一个下标对 `(i, j)` 如果满足以下条件，那么它们被称为一个 **逆序对** ：

*   `i < j` 且 `nums[i] > nums[j]`

请你返回 `[0, 1, 2, ..., n - 1]` 的 

排列

`perm` 的数目，满足对 **所有** 的 `requirements[i]` 都有 `perm[0..endi]` 恰好有 `cnti` 个逆序对。

由于答案可能会很大，将它对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**n = 3, requirements = \[\[2,2\],\[0,0\]\]

**输出：**2

**解释：**

两个排列为：

*   `[2, 0, 1]`
    *   前缀 `[2, 0, 1]` 的逆序对为 `(0, 1)` 和 `(0, 2)` 。
    *   前缀 `[2]` 的逆序对数目为 0 个。
*   `[1, 2, 0]`
    *   前缀 `[1, 2, 0]` 的逆序对为 `(0, 2)` 和 `(1, 2)` 。
    *   前缀 `[1]` 的逆序对数目为 0 个。

**示例 2：**

**输入：**n = 3, requirements = \[\[2,2\],\[1,1\],\[0,0\]\]

**输出：**1

**解释：**

唯一满足要求的排列是 `[2, 0, 1]` ：

*   前缀 `[2, 0, 1]` 的逆序对为 `(0, 1)` 和 `(0, 2)` 。
*   前缀 `[2, 0]` 的逆序对为 `(0, 1)` 。
*   前缀 `[2]` 的逆序对数目为 0 。

**示例 3：**

**输入：**n = 2, requirements = \[\[0,0\],\[1,0\]\]

**输出：**1

**解释：**

唯一满足要求的排列为 `[0, 1]` ：

*   前缀 `[0]` 的逆序对数目为 0 。
*   前缀 `[0, 1]` 的逆序对为 `(0, 1)` 。

**提示：**

*   `2 <= n <= 300`
*   `1 <= requirements.length <= n`
*   `requirements[i] = [endi, cnti]`
*   `0 <= endi <= n - 1`
*   `0 <= cnti <= 400`
*   输入保证至少有一个 `i` 满足 `endi == n - 1` 。
*   输入保证所有的 `endi` 互不相同。

[https://leetcode.cn/problems/count-the-number-of-inversions/](https://leetcode.cn/problems/count-the-number-of-inversions/)

```java
import java.util.Arrays;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int numberOfPermutations(int n, int[][] requirements) {
        int[] req = new int[n]; // req[i] = j表示前i个数有j个逆序对
        Arrays.fill(req, -1);
        req[0] = 0;
        int m = 0;
        for (int[] p : requirements) {
            req[p[0]] = p[1];
            m = Math.max(m, p[1]);
        }
        if (req[0] > 0) {
            return 0;
        }
        int[][] memo = new int[n][m + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(n - 1, req[n - 1], req, memo);
    }

    private int dfs(int i, int j, int[] req, int[][] memo) { // 前 i 个数的逆序对为 j 时的排列个数
        if (i == 0) {
            return 1;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        int cnt = req[i - 1];
        if (cnt >= 0) {
            if (j >= cnt && j - i <= cnt) {
                res = dfs(i - 1, cnt, req, memo);
            }
        }else{
            for (int k = 0; k <= Math.min(i, j); k++) {
                res = (res + dfs(i - 1, j - k, req, memo)) % Mod;
            }
        }
        return memo[i][j] = res;
    }
}
```

1478\. 安排邮筒
-----------

给你一个房屋数组`houses` 和一个整数 `k` ，其中 `houses[i]` 是第 `i` 栋房子在一条街上的位置，现需要在这条街上安排 `k` 个邮筒。

请你返回每栋房子与离它最近的邮筒之间的距离的 **最小** 总和。

答案保证在 32 位有符号整数范围以内。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/13/sample_11_1816.png)

**输入：**houses = \[1,4,8,10,20\], k = 3
**输出：**5
**解释：**将邮筒分别安放在位置 3， 9 和 20 处。
每个房子到最近邮筒的距离和为 |3-1| + |4-3| + |9-8| + |10-9| + |20-20| = 5 。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/13/sample_2_1816.png)**

**输入：**houses = \[2,3,5,12,18\], k = 2
**输出：**9
**解释：**将邮筒分别安放在位置 3 和 14 处。
每个房子到最近邮筒距离和为 |2-3| + |3-3| + |5-3| + |12-14| + |18-14| = 9 。

**示例 3：**

**输入：**houses = \[7,4,6,1\], k = 1
**输出：**8

**示例 4：**

**输入：**houses = \[3,6,14,10\], k = 4
**输出：**0

**提示：**

*   `n == houses.length`
*   `1 <= n <= 100`
*   `1 <= houses[i] <= 10^4`
*   `1 <= k <= n`
*   数组 `houses` 中的整数互不相同。

[https://leetcode.cn/problems/allocate-mailboxes/description/](https://leetcode.cn/problems/allocate-mailboxes/description/)

```java
// 一开始以为是二分，没想到是dp
// 不过这个题就算想到dp，也比较难。我们一定要将其转化为子问题，dp[i][j]表示到第i个房子，使用j个邮筒的最小总和；然后递推公式：dp[i][j] = Math.min(dp[i - 1][p]+prefixSum[p][i]);其中的prefixSum[p][i]表示从[p,i]之间只放一个邮筒需要的距离总和因为[p,i]之间可能有很多个房子。
import java.util.Arrays;

class Solution {
    public int minDistance(int[] houses,  int k) { // 双重dp
        Arrays.sort(houses);
        int n = houses.length;
        int[][] prefixSum = new int[n][n]; // prefixSum[i][j]，表示在下标范围[i,j]的房子与邮筒距离之和最小值
        for (int i = n - 2; i >= 0; i--) { // 这里逆序遍历！我这里都想不到
            for (int j = i + 1; j < n; j++) {
                prefixSum[i][j] = prefixSum[i + 1][j - 1] + houses[j] - houses[i]; // 这里就是一重dp
            }
        }
        // dp[i][j]表示给前i个房子放置j个邮筒的最小总和
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            int maxMailboxes = Math.min(i, k);
            for (int j = 1; j <= maxMailboxes; j++) { // 枚举可用邮筒个数
                for (int p = 0; p < i; p++) { // 枚举这个邮筒的放置位置
                    dp[i][j] = Math.min(dp[i][j], dp[p][j - 1] + prefixSum[p][i - 1]);
                }
            }
        }
        return dp[n][k];
    }
}
```

1477\. 找两个和为目标值且不重叠的子数组
-----------------------

给你一个整数数组 `arr` 和一个整数值 `target` 。

请你在 `arr` 中找 **两个互不重叠的子数组** 且它们的和都等于 `target` 。可能会有多种方案，请你返回满足要求的两个子数组长度和的 **最小值** 。

请返回满足要求的最小长度和，如果无法找到这样的两个子数组，请返回 **\-1** 。

**示例 1：**

**输入：**arr = \[3,2,2,4,3\], target = 3
**输出：**2
**解释：**只有两个子数组和为 3 （\[3\] 和 \[3\]）。它们的长度和为 2 。

**示例 2：**

**输入：**arr = \[7,3,4,7\], target = 7
**输出：**2
**解释：**尽管我们有 3 个互不重叠的子数组和为 7 （\[7\], \[3,4\] 和 \[7\]），但我们会选择第一个和第三个子数组，因为它们的长度和 2 是最小值。

**示例 3：**

**输入：**arr = \[4,3,2,6,2,3,4\], target = 6
**输出：**\-1
**解释：**我们只有一个和为 6 的子数组。

**示例 4：**

**输入：**arr = \[5,5,4,4,5\], target = 3
**输出：**\-1
**解释：**我们无法找到和为 3 的子数组。

**示例 5：**

**输入：**arr = \[3,1,1,1,5,1,2,1\], target = 3
**输出：**3
**解释：**注意子数组 \[1,2\] 和 \[2,1\] 不能成为一个方案因为它们重叠了。

**提示：**

*   `1 <= arr.length <= 10^5`
*   `1 <= arr[i] <= 1000`
*   `1 <= target <= 10^8`

[https://leetcode.cn/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/description/](https://leetcode.cn/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/description/)

```java
import java.util.Arrays;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int left = 0, right = 0; // 滑窗左闭右闭
        int[] dp = new int[n]; // 使用dp(j)表示当前j以及j之前的满足条件的最小区间长度
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        int sum = 0, ans = Integer.MAX_VALUE / 2;
        while (right < n) {
            sum += arr[right];
            while (left <= right && sum > target) {
                sum -= arr[left++];
            }
            if (sum == target) {
                dp[right] = right - left + 1;
                if (left != 0) {
                    ans = Math.min(ans, dp[left - 1] + dp[right]);
                }
            }
            if (right != 0) {
                dp[right] = Math.min(dp[right], dp[right - 1]);
            }
            right++;
        }
        return ans == Integer.MAX_VALUE / 2 ? -1 : ans;
    }

}
```

3208\. 交替组 II
-------------

给你一个整数数组 `colors` 和一个整数 `k` ，`colors`表示一个由红色和蓝色瓷砖组成的环，第 `i` 块瓷砖的颜色为 `colors[i]` ：

*   `colors[i] == 0` 表示第 `i` 块瓷砖的颜色是 **红色** 。
*   `colors[i] == 1` 表示第 `i` 块瓷砖的颜色是 **蓝色** 。

环中连续 `k` 块瓷砖的颜色如果是 **交替** 颜色（也就是说除了第一块和最后一块瓷砖以外，中间瓷砖的颜色与它 **左边** 和 **右边** 的颜色都不同），那么它被称为一个 **交替** 组。

请你返回 **交替** 组的数目。

**注意** ，由于 `colors` 表示一个 **环** ，**第一块** 瓷砖和 **最后一块** 瓷砖是相邻的。

**示例 1：**

**输入：**colors = \[0,1,0,1,0\], k = 3

**输出：**3

**解释：**

![](https://assets.leetcode.com/uploads/2024/06/19/screenshot-2024-05-28-183519.png)

交替组包括：

**![](https://assets.leetcode.com/uploads/2024/05/28/screenshot-2024-05-28-182448.png)**![](https://assets.leetcode.com/uploads/2024/05/28/screenshot-2024-05-28-182844.png)**![](https://assets.leetcode.com/uploads/2024/05/28/screenshot-2024-05-28-183057.png)**

**示例 2：**

**输入：**colors = \[0,1,0,0,1,0,1\], k = 6

**输出：**2

**解释：**

![](https://assets.leetcode.com/uploads/2024/06/19/screenshot-2024-05-28-183907.png)

交替组包括：

**![](https://assets.leetcode.com/uploads/2024/06/19/screenshot-2024-05-28-184128.png)**![](https://assets.leetcode.com/uploads/2024/06/19/screenshot-2024-05-28-184240.png)

**示例 3：**

**输入：**colors = \[1,1,0,1\], k = 4

**输出：**0

**解释：**

![](https://assets.leetcode.com/uploads/2024/06/19/screenshot-2024-05-28-184516.png)

**提示：**

*   `3 <= colors.length <= 105`
*   `0 <= colors[i] <= 1`
*   `3 <= k <= colors.length`

[https://leetcode.cn/problems/alternating-groups-ii/description/](https://leetcode.cn/problems/alternating-groups-ii/description/)

```java
class Solution {
    public int numberOfAlternatingGroups(int[] colors, int k) {
        int n = colors.length, ans = 0;
        int cnt = 0;
        for (int i = 0; i < n * 2; i++) {
            if (i > 0 && colors[i % n] == colors[(i - 1) % n]) {
                cnt = 0;
            }
            cnt++;
            if (i >= n && cnt >= k) {
                ans++;
            }
        }
        return ans;
    }
}
```

1186\. 删除一次得到子数组最大和
-------------------

给你一个整数数组，返回它的某个 **非空** 子数组（连续元素）在执行一次可选的删除操作后，所能得到的最大元素总和。换句话说，你可以从原数组中选出一个子数组，并可以决定要不要从中删除一个元素（只能删一次哦），（删除后）子数组中至少应当有一个元素，然后该子数组（剩下）的元素总和是所有子数组之中最大的。

注意，删除一个元素后，子数组 **不能为空**。

**示例 1：**

**输入：**arr = \[1,-2,0,3\]
**输出：**4
**解释：**我们可以选出 \[1, -2, 0, 3\]，然后删掉 -2，这样得到 \[1, 0, 3\]，和最大。

**示例 2：**

**输入：**arr = \[1,-2,-2,3\]
**输出：**3
**解释：**我们直接选出 \[3\]，这就是最大和。

**示例 3：**

**输入：**arr = \[-1,-1,-1,-1\]
**输出：**\-1
**解释：**最后得到的子数组不能为空，所以我们不能选择 \[-1\] 并从中删去 -1 来得到 0。
我们应该直接选择 \[-1\]，或者选择 \[-1, -1\] 再从中删去一个 -1。

**提示：**

*   `1 <= arr.length <= 105`
*   `-104 <= arr[i] <= 104`

[https://leetcode.cn/problems/maximum-subarray-sum-with-one-deletion/description/?envType=daily-question&envId=2024-07-21](https://leetcode.cn/problems/maximum-subarray-sum-with-one-deletion/description/?envType=daily-question&envId=2024-07-21)

```java
class Solution {
    public int maximumSum(int[] arr) {
        int n = arr.length;
        int ans = Integer.MIN_VALUE;
        int[][] f = new int[n + 1][2];
        Arrays.fill(f[0], Integer.MIN_VALUE / 2); // 除 2 防止负数相加溢出
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = Math.max(f[i][0], 0) + arr[i];
            f[i + 1][1] = Math.max(f[i][1] + arr[i], f[i][0]);
            ans = Math.max(ans, Math.max(f[i + 1][0], f[i + 1][1]));
        }
        return ans;
    }
}

```
100396\. 单调数组对的数目 II
--------------------

给你一个长度为 `n` 的 **正** 整数数组 `nums` 。

如果两个 **非负** 整数数组 `(arr1, arr2)` 满足以下条件，我们称它们是 **单调** 数组对：

*   两个数组的长度都是 `n` 。
*   `arr1` 是单调 **非递减** 的，换句话说 `arr1[0] <= arr1[1] <= ... <= arr1[n - 1]` 。
*   `arr2` 是单调 **非递增** 的，换句话说 `arr2[0] >= arr2[1] >= ... >= arr2[n - 1]` 。
*   对于所有的 `0 <= i <= n - 1` 都有 `arr1[i] + arr2[i] == nums[i]` 。

请你返回所有 **单调** 数组对的数目。

由于答案可能很大，请你将它对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**nums = \[2,3,2\]

**输出：**4

**解释：**

单调数组对包括：

1.  `([0, 1, 1], [2, 2, 1])`
2.  `([0, 1, 2], [2, 2, 0])`
3.  `([0, 2, 2], [2, 1, 0])`
4.  `([1, 2, 2], [1, 1, 0])`

**示例 2：**

**输入：**nums = \[5,5,5,5\]

**输出：**126

**提示：**

*   `1 <= n == nums.length <= 2000`
*   `1 <= nums[i] <= 1000`

[https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-ii/description/](https://leetcode.cn/problems/find-the-count-of-monotonic-pairs-ii/description/)

```java
import java.util.Arrays;

class Solution { // 超时
    private static int Mod = (int) 1e9 + 7;
    public int countOfPairs(int[] nums) {
        int n = nums.length;
        int mx = Arrays.stream(nums).max().getAsInt();
        int[][][] dp = new int[n][mx + 1][mx + 1];
        for (int i = 0; i <= nums[0]; i++) {
            dp[0][i][nums[0] - i] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= nums[i]; j++) {
                for (int x = 0; x <= j; x++) {
                    for (int y = Math.max(nums[i - 1], nums[i]); y >= nums[i] - j; y--) {
                        dp[i][j][nums[i] - j] += dp[i - 1][x][y];
                        dp[i][j][nums[i] - j] %= Mod;
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 0; i <= mx; i++) {
            for (int j = 0; j <= mx; j++) {
                ans += dp[n - 1][i][j];
                ans %= Mod;
            }
        }
        return ans;
    }


}
```

> 其实只用定义f(i)(j)表示考虑第i个数arr1选择j的方案数，则有j' <= j，对于arr2有nums(i) - j <= nums(i  -1) - j' 移项可得j' <= j + nums(i - 1) - nums(i)，所以最后有f(i)(j) = sum(f(i - 1)(j'))，其中j' <= min(j, j + nums(i - 1) - nums(i))，然后对于求和我们可以使用前缀和优化。

```java
import java.util.Arrays;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int countOfPairs(int[] nums) {
        int n = nums.length;
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        // f(i)(j) = sum(f(i - 1)(j'))，其中j' <= min(j, j + nums(i - 1) - nums(i))
        long[][] dp = new long[n][mx + 1], g = new long[n][mx + 1];
        for (int i = 0; i <= nums[0]; i++) {
            dp[0][i] = 1;
        }
        g[0][0] = dp[0][0];
        for (int i = 1; i <= mx; i++) {
            g[0][i] = (g[0][i - 1] + dp[0][i]) % Mod;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= nums[i]; j++) {
                int lim = Math.min(j, j + nums[i - 1] - nums[i]);
                if (lim >= 0) {
                    dp[i][j] = g[i - 1][lim];
                }
            }
            // 计算前缀和
            g[i][0] = dp[i][0];
            for (int j = 1; j <= mx; j++) {
                g[i][j] = (g[i][j - 1] + dp[i][j]) % Mod;
            }
        }
        return (int) g[n - 1][mx];
    }
}
```

3117\. 划分数组得到最小的值之和
-------------------

给你两个数组 `nums` 和 `andValues`，长度分别为 `n` 和 `m`。

数组的 **值** 等于该数组的 **最后一个** 元素。

你需要将 `nums` 划分为 `m` 个 **不相交的连续**

子数组

，对于第 `ith` 个子数组 `[li, ri]`，子数组元素的按位 `AND` 运算结果等于 `andValues[i]`，换句话说，对所有的 `1 <= i <= m`，`nums[li] & nums[li + 1] & ... & nums[ri] == andValues[i]` ，其中 `&` 表示按位 `AND` 运算符。

返回将 `nums` 划分为 `m` 个子数组所能得到的可能的 **最小** 子数组 **值** 之和。如果无法完成这样的划分，则返回 `-1` 。

**示例 1：**

**输入：** nums = \[1,4,3,3,2\], andValues = \[0,3,3,2\]

**输出：** 12

**解释：**

唯一可能的划分方法为：

1.  `[1,4]` 因为 `1 & 4 == 0`
2.  `[3]` 因为单元素子数组的按位 `AND` 结果就是该元素本身
3.  `[3]` 因为单元素子数组的按位 `AND` 结果就是该元素本身
4.  `[2]` 因为单元素子数组的按位 `AND` 结果就是该元素本身

这些子数组的值之和为 `4 + 3 + 3 + 2 = 12`

**示例 2：**

**输入：** nums = \[2,3,5,7,7,7,5\], andValues = \[0,7,5\]

**输出：** 17

**解释：**

划分 `nums` 的三种方式为：

1.  `[[2,3,5],[7,7,7],[5]]` 其中子数组的值之和为 `5 + 7 + 5 = 17`
2.  `[[2,3,5,7],[7,7],[5]]` 其中子数组的值之和为 `7 + 7 + 5 = 19`
3.  `[[2,3,5,7,7],[7],[5]]` 其中子数组的值之和为 `7 + 7 + 5 = 19`

子数组值之和的最小可能值为 `17`

**示例 3：**

**输入：** nums = \[1,2,3,4\], andValues = \[2\]

**输出：** \-1

**解释：**

整个数组 `nums` 的按位 `AND` 结果为 `0`。由于无法将 `nums` 划分为单个子数组使得元素的按位 `AND` 结果为 `2`，因此返回 `-1`。

**提示：**

*   `1 <= n == nums.length <= 104`
*   `1 <= m == andValues.length <= min(n, 10)`
*   `1 <= nums[i] < 105`
*   `0 <= andValues[j] < 105`

[https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/description/?envType=daily-question&envId=2024-08-16](https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/description/?envType=daily-question&envId=2024-08-16)

```java
import java.util.Arrays;
import java.util.HashMap;

class Solution { // 超时
    private static int INF = Integer.MAX_VALUE / 2;
    int n, m;
    int[] nums, andValues;
    HashMap<String, Integer> memo = new HashMap<>();
    public int minimumValueSum(int[] nums, int[] andValues) {
        this.nums = nums;
        this.andValues = andValues;
        n = nums.length;
        m = andValues.length;
        int res = dfs(0, 0, (1 << 18) - 1, 0);
        return res == INF ? -1 : res;
    }

    private int dfs(int i, int j, int sum, int last) { // 表示到达位置i,前
        if (n - 1 - i < m - 1 - j) {
            return INF;
        }
        if (sum < andValues[j]) { // 加上这句话，超级剪枝还是超时
            return INF;
        }
        if (i == n || j == m) {
            return i == n && j == m ? last : INF;
        }
        String key = getKey(i, j, sum, last);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = INF;
        if ((sum & nums[i]) == andValues[j]) {
            res = Math.min(res, dfs(i + 1, j + 1, (1 << 18) - 1, nums[i] + last));
        }
        res = Math.min(res, dfs(i + 1, j, sum & nums[i], last));
        memo.put(key, res);
        return res;
    }

    private String getKey(int i, int j, int sum, int last) {
        return i + "_" + j + "_" + sum + "_" + last;
    }

}
```

```java
import java.util.HashMap;

class Solution {
    private static int INF = Integer.MAX_VALUE / 2;
    int n, m;
    int[] nums, andValues;
    HashMap<String, Integer> memo = new HashMap<>();
    public int minimumValueSum(int[] nums, int[] andValues) {
        this.nums = nums;
        this.andValues = andValues;
        n = nums.length;
        m = andValues.length;
        int res = dfs(0, 0, (1 << 18) - 1, 0);
        return res == INF ? -1 : res;
    }

    private int dfs(int i, int j, int sum, int last) { // 表示到达位置i,前
        
        if (n - 1 - i < m - 1 - j) {
            return INF;
        }
        if (i == n || j == m) {
            return i == n && j == m ? last : INF;
        }
        if (sum <= andValues[j]) {
            return INF;
        }
        String key = getKey(i, j, sum, last);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = INF;
        if ((sum & nums[i]) == andValues[j]) {
            res = Math.min(res, dfs(i + 1, j + 1, (1 << 18) - 1, nums[i] + last));
        }
        res = Math.min(res, dfs(i + 1, j, sum & nums[i], last));
        memo.put(key, res);
        return res;
    }

    private String getKey(int i, int j, int sum, int last) {
        return i + "_" + j + "_" + sum; // 这里降维，但是报错了
    }

}
```

```java
import java.util.HashMap;

class Solution { // AC 不把结果存入last中 873ms
    private static int INF = Integer.MAX_VALUE / 2;
    int n, m;
    int[] nums, andValues;
    HashMap<String, Integer> memo = new HashMap<>();
    public int minimumValueSum(int[] nums, int[] andValues) {
        this.nums = nums;
        this.andValues = andValues;
        n = nums.length;
        m = andValues.length;
        int res = dfs(0, 0, (1 << 18) - 1);
        return res == INF ? -1 : res;
    }

    private int dfs(int i, int j, int sum) { // 表示到达位置i,前
        if (n - 1 - i < m - 1 - j) {
            return INF;
        }
        if (i == n || j == m) {
            return i == n && j == m ? 0 : INF;
        }
        if (sum < andValues[j]) {
            return INF;
        }
        String key = getKey(i, j, sum);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = INF;
        if ((sum & nums[i]) == andValues[j]) {
            res = Math.min(res, dfs(i + 1, j + 1, (1 << 18) - 1)) + nums[i];
        }
        res = Math.min(res, dfs(i + 1, j, sum & nums[i]));
        memo.put(key, res);
        return res;
    }

    private String getKey(int i, int j, int sum) {
        return i + "_" + j + "_" + sum;
    }

}
```

```java
import java.util.HashMap;
import java.util.Map;

class Solution { // AC 使用Long存储 207ms 哈希表是真的垃圾
    public int minimumValueSum(int[] nums, int[] andValues) {
        HashMap<Long, Integer> memo = new HashMap<>();
        int ans = dfs(0, 0, -1, nums, andValues, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int j, int and, int[] nums, int[] andValues, Map<Long,Integer> memo) {
        int n = nums.length;
        int m = andValues.length;
        if (m - j > n - i) { // 剩余元素不足
            return Integer.MAX_VALUE / 2;
        }
        if (j == m) { // 分了m段
            return i == n ? 0 : Integer.MAX_VALUE / 2;
        }
        and &= nums[i];
        if (and < andValues[j]) { // 剪枝：无法等于 andValues[j]
            return Integer.MAX_VALUE / 2;
        }
        long mask = (long) i << 36 | (long) j << 32 | and; // 三个状态压缩成一个 long
        if (memo.containsKey(mask)) {
            return memo.get(mask);
        }
        int res = dfs(i + 1, j, and, nums, andValues, memo); // 不划分
        if (and == andValues[j]) { // 划分，nums[i]是这一段的最后一个数
            res = Math.min(res, dfs(i + 1, j + 1, -1, nums, andValues, memo) + nums[i]);
        }
        memo.put(mask, res);
        return res;
    }
}
```

> 这题的最优解是用单调栈优化dp

```java
class Solution {
  int inf = 0x3f3f3f3f;

    public int minimumValueSum(int[] nums, int[] andValues) {
        // 利用动态规划求解
        // 定义状态 dp[i][k+1] 代表从0-k范围内的子数组切割成i段时的最小和
        // 那么可以得出递推公式 dp[i+1][]+1]= nums[j]+min(dp[i][k]) 且满足nums[k]&nums[k+1]&。。。& nums[j]=andValues[i];
        // 因此可以求出k的一个取值范围[l,r]
        // 由于dp[i+1]至于dp[i]的状态相关，所以可以减小一个维度
        int n = nums.length;
        int[] dp = new int[n + 1];
        // 因为此时划分的子区间的个数为0，长度只要大于1就是不合理的方案
        Arrays.fill(dp, 1, n + 1, inf);
        // 因为nums的最大值为100000，最多可以形成17种子数组与和值（会进行去重处理），所以长度为18
        int[] and = new int[18];
        // 用于记录处理每个i时，k的左区间l
        int[] left = new int[18];
        // 用数组模拟单调栈
        int[] q = new int[n + 1];
        int[] nDp = new int[n + 1];
        for (int target : andValues) {
            nDp[0] = inf;
            // 用于记录and和left的长度
            int an = 0;
            // 用于记录q的左右范围
            int ql = 0, qr = 0;
            // 处理到的dp[i]的索引
            int qi = 0;
            for (int i = 0; i < n; i++) {
                // 当前处理到i
                int x = nums[i];
                // 遍历先前的子数组的与和，将其与上x，则此时所有的子数组都是从i从右往左了
                for (int j = 0; j < an; j++) {
                    and[j] &= x;
                }
                // 此时对应的最后一个子数组与和就是自己本身（单个元素）
                and[an] = x;
                // 自然对应的与和的左端点是i了（注意还没有进行去重，去重以后，如果前面已经出现过 and[an]的与和的子数组的情况下，left也会更新成对应的）
                left[an++] = i;
                // 进行去重处理,且删除小于target的and值，因为任何数和小于target的值与运算后一定不可能等于target
                // 因为and数组是单调递增的（与运算的特点）
                int last = -1;
                int r = 0;
                for (int j = 0; j < an; j++) {
                    if (and[j] >= target && and[j] != last) {
                        last = and[r] = and[j];
                        left[r++]=left[j];
                    }
                }
                // 此时重新记录去重后的and的长度
                an = r;
                if (an > 0 && and[0] == target) {
                    // 首先要有大于等于target的数，且最小的那个与和要等于target，否则因为单调递增，后续更不可能等于target了
                    int right = i;
                    if (an > 1) {
                        // 与和为target的k的范围自然是left[0]到下一个更大与和前的一个元素了
                        right = left[1] - 1;
                    }
                    // 此时处理单调递增栈，用于找出left[0]到right这个范围内的最小值
                    // 之所以可以复用前一个结果的单调栈，是因为在target不变时，不断右移i的时候，k的区间范围left[0]到right也在右移（可能不动）
                    // ，因为原先的范围是处理到i-1时的单调栈，此时多增加一个i的数进行与操作，只可能使得target的值减小或者不变，如果减小的情况下，自然就需要右移范围才可以了

                    // 采用模板来处理单调栈
                    // 右边入栈
                    while (qi <= right) {
                        while (qr > ql && dp[qi] <= dp[q[qr-1]]) {
                            qr--;
                        }
                        q[qr++] = qi++;
                    }
                    // 左边出栈
                    while (qr > ql && q[ql] < left[0]) {
                        ql++;
                    }
                    // 此时栈顶就是最小值了
                    nDp[i+1] = dp[q[ql]] + x;
                } else {
                    nDp[i+1] = inf;
                }
            }
            int[] tmp = dp;
            dp = nDp;
            nDp=tmp;
        }
        return dp[n] < inf ? dp[n] : -1;
    }
}
```

## [E - 3 Team Division 【超级模板题】](https://atcoder.jp/contests/abc375/tasks/abc375_e)

```java
	private static int N = 501;
    private static void solve() throws IOException {
        n = sc.nextInt();
        int[] a = new int[n]; // 组数
        int[] b = new int[n]; // 权重
        int sum = 0;
        for (int i = 0; i < n; i++) {
            ss = sc.nextLine().split(" ");
            a[i] = Integer.parseInt(ss[0]);
            b[i] = Integer.parseInt(ss[1]);
            sum += b[i];
        }
        if (sum % 3 != 0) {
            sc.println(-1);
            return;
        }
        int ave = sum / 3;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], N);
        }
        dp[0][0] = 0; // 表示不放任何东西进入，第一个盒子权重为0，第二个盒子权重为0的调换次数为0
        for (int i = 0; i < n; i++) {
            int[][] tmp = new int[N][N];
            for (int j = 0; j < N; j++) {
                Arrays.fill(tmp[j], INF);
            }
            int w = b[i];
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    if (a[i] == 1) {
                        if (j + w < N) {
                            tmp[j + w][k] = Math.min(tmp[j + w][k], dp[j][k]);
                        }
                        if (k + w < N) {
                            tmp[j][k + w] = Math.min(tmp[j][k + w], dp[j][k] + 1);
                        }
                        tmp[j][k] = Math.min(tmp[j][k], dp[j][k] + 1);
                    } else if (a[i] == 2) {
                        if (j + w < N) {
                            tmp[j + w][k] = Math.min(tmp[j + w][k], dp[j][k] + 1);
                        }
                        if (k + w < N) {
                            tmp[j][k + w] = Math.min(tmp[j][k + w], dp[j][k]);
                        }
                        tmp[j][k] = Math.min(tmp[j][k], dp[j][k] + 1);
                    }else{
                        if (j + w < N) {
                            tmp[j + w][k] = Math.min(tmp[j + w][k], dp[j][k] + 1);
                        }
                        if (k + w < N) {
                            tmp[j][k + w] = Math.min(tmp[j][k + w], dp[j][k] + 1);
                        }
                        tmp[j][k] = Math.min(tmp[j][k], dp[j][k]);
                    }
                }
            }
            dp = tmp;
        }
        sc.println(dp[ave][ave] == INF ? -1 : dp[ave][ave]);
    }
```

```java
package class067;

// 节点数为n高度不大于m的二叉树个数
// 现在有n个节点，计算出有多少个不同结构的二叉树
// 满足节点个数为n且树的高度不超过m的方案
// 因为答案很大，所以答案需要模上1000000007后输出
// 测试链接 : https://www.nowcoder.com/practice/aaefe5896cce4204b276e213e725f3ea
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_NodenHeightNotLargerThanm {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;
			out.println(compute3(n, m));
		}
		out.flush();
		out.close();
		br.close();
	}

	public static int MAXN = 51;

	public static int MOD = 1000000007;

	// 记忆化搜索
	public static long[][] dp1 = new long[MAXN][MAXN];

	static {
		for (int i = 0; i < MAXN; i++) {
			for (int j = 0; j < MAXN; j++) {
				dp1[i][j] = -1;
			}
		}
	}

	// 二叉树节点数为n
	// 高度不能超过m
	// 结构数返回
	// 记忆化搜索
	public static int compute1(int n, int m) {
		if (n == 0) {
			return 1;
		}
		// n > 0
		if (m == 0) {
			return 0;
		}
		if (dp1[n][m] != -1) {
			return (int) dp1[n][m];
		}
		long ans = 0;
		// n个点，头占掉1个
		for (int k = 0; k < n; k++) { 
			// 一共n个节点，头节点已经占用了1个名额
			// 如果左树占用k个，那么右树就占用i-k-1个
			ans = (ans + ((long) compute1(k, m - 1) * compute1(n - k - 1, m - 1)) % MOD) % MOD;
		}
		dp1[n][m] = ans;
		return (int) ans;
	}

	// 严格位置依赖的动态规划
	public static long[][] dp2 = new long[MAXN][MAXN];

	public static int compute2(int n, int m) {
		for (int j = 0; j <= m; j++) {
			dp2[0][j] = 1;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				dp2[i][j] = 0;
				for (int k = 0; k < i; k++) {
					// 一共i个节点，头节点已经占用了1个名额
					// 如果左树占用k个，那么右树就占用i-k-1个
					dp2[i][j] = (dp2[i][j] + dp2[k][j - 1] * dp2[i - k - 1][j - 1] % MOD) % MOD;
				}
			}
		}
		return (int) dp2[n][m];
	}

	// 空间压缩
	public static long[] dp3 = new long[MAXN];

	public static int compute3(int n, int m) {
		dp3[0] = 1;
		for (int i = 1; i <= n; i++) {
			dp3[i] = 0;
		}
		for (int j = 1; j <= m; j++) {
			// 根据依赖，一定要先枚举列
			for (int i = n; i >= 1; i--) {
				// 再枚举行，而且i不需要到达0，i>=1即可
				dp3[i] = 0;
				for (int k = 0; k < i; k++) {
					// 枚举
					dp3[i] = (dp3[i] + dp3[k] * dp3[i - k - 1] % MOD) % MOD;
				}
			}
		}
		return (int) dp3[n];
	}

}

```

## [CF1519D Maximum Sum of Products](https://www.luogu.com.cn/problem/CF1519D)

![1741527912644](assets/1741527912644.png)

![1741527947394](assets/1741527947394.png)

```java
	private static void solve() throws IOException {
        n = sc.nextInt();
        long[] a = new long[n];
        long[] b = new long[n];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(ss[i]);
        }
        long tmp = 0, sum = 0;
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            b[i] = Long.parseLong(ss[i]);
            sum += a[i] * b[i];
        }
        // 枚举以单个元素为最小区间的所有区间
        for (int i = 0; i < n; i++) {
            long tmp1 = 0;
            for (int j = i, k = i; j >= 0 && k < n; j--, k++) {
                tmp1 += (a[j] - a[k]) * (b[k] - b[j]);
                tmp = Math.max(tmp, tmp1);
            }
        }
        // 枚举以相邻的两个元素为最小区间的所有区间
        for (int i = 0; i < n; i++) {
            long tmp2 = 0;
            for (int j = i, k = i + 1; j >= 0 && k < n; j--, k++) {
                tmp2 += (a[j] - a[k]) * (b[k] - b[j]);
                tmp = Math.max(tmp, tmp2);
            }
        }
        sc.println(sum + tmp);
    }
```

> ![1741683249538](assets/1741683249538.png)
>
> 直接记录左右的最长递增字串，然后枚举中间值！

> ![1741683311285](assets/1741683311285.png)
>
> 只有abc三种字母，观察发现永远的字串abcabcabc的六种排列的一种，直接暴力枚举！

> 题意：给定k & 字符串s ，其中s是一个二进制字符串, k≤106,∣s∣≤106，输出s中包含k个1的子串的个数（我们认为两个子串不同当且仅当它们均非空并且它们出现的位置不同） 
>
> 最简单的思路就是找到一个区间，然后乘以左边0的出现次数 * 右边0的出现次数
>
> 然而还有简单做法：
>
> ```java
> 	private static void solve() throws IOException {
>         k = sc.nextInt();
>         cs = sc.next().toCharArray();
>         int n = cs.length;
>         dp = new long[n + 1]; // dp[i]是一个滚动数组，表示到当前位置，i个1出现的次数 比如有101001，那么 dp = {1, 2, 3, 1, 0, 0, 0};
>         dp[0] = 1;
>         long ans = 0;
>         int cnt = 0;
>         for (int i = 0; i < n; i++) {
>             cnt += cs[i] - '0';
>             if (cnt >= k) {
>                 ans += dp[cnt - k];
>             }
>             dp[cnt]++; // 滚动
>         }
>         // System.out.println(Arrays.toString(dp));
>         sc.println(ans);
>     }
> ```
>
> 

> 题意：给出一个序列和 x，每次可以把序列中的一个 >x 的数与 x 交换，问最少操作多少次使得整个序列升序排序，如果不能输出 `-1`。 
>
> 直接贪心，找到最后一个乱序的位置，然后从头开始替换。
>
> ```java
>     private static void solve() throws IOException {
>         n = sc.nextInt();
>         long x = sc.nextInt();
>         ss = sc.nextLine().split(" ");
>         nums = new long[n];
>         for (int i = 0; i < n; i++) {
>             nums[i] = Long.parseLong(ss[i]);
>         }
>         int id = 0;
>         for (int i = 1; i < n; i++) {
>             if (nums[i] < nums[i - 1]) {
>                 id = i;
>             }
>         }
>         if (id == 0) {
>             sc.println(0);
>             return;
>         }
>         int ans = 0;
>         for (int i = 0; i <= id; i++) {
>             if (x < nums[i]) {
>                 long tmp = nums[i];
>                 nums[i] = x;
>                 x = tmp;
>                 ans++;
>             }
>         }
>         boolean flag = true;
>         for (int i = 1; i < n; i++) {
>             if (nums[i] < nums[i - 1]) {
>                 flag = false;
>                 break;
>             }
>         }
>         sc.println(flag ? ans : -1);
>     }
> ```
>
> 

