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
            dp.put(num + 1, dp.getOrDefault(num, 0) + 1);
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
    public int minimumSubstringsInPartition(String s) { // 1:1翻译成递推同样超时，时间主要因为judge需要O(n)
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
            for (int j = i - 1; j >= 0; j--) { // i - j就是长度
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
    static {
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

