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