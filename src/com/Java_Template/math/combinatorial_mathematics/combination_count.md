# §2.2 组合计数

1359\. 有效的快递序列数目
----------------

给你 `n` 笔订单，每笔订单都需要快递服务。

计算所有有效的 取货 / 交付 可能的顺序，使 delivery(i) 总是在 pickup(i) 之后。

由于答案可能很大，请返回答案对 10^9 + 7 取余的结果。

**示例 1：**

**输入：**n = 1
**输出：**1
**解释：**只有一种序列 (P1, D1)，物品 1 的配送服务（D1）在物品 1 的收件服务（P1）后。

**示例 2：**

**输入：**n = 2
**输出：**6
**解释：**所有可能的序列包括：
(P1,P2,D1,D2)，(P1,P2,D2,D1)，(P1,D1,P2,D2)，(P2,P1,D1,D2)，(P2,P1,D2,D1) 和 (P2,D2,P1,D1)。
(P1,D2,P2,D1) 是一个无效的序列，因为物品 2 的收件服务（P2）不应在物品 2 的配送服务（D2）之后。

**示例 3：**

**输入：**n = 3
**输出：**90

**提示：**

*   `1 <= n <= 500`

[https://leetcode.cn/problems/count-all-valid-pickup-and-delivery-options/description/](https://leetcode.cn/problems/count-all-valid-pickup-and-delivery-options/description/)

```java
class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int countOrders(int n) {
        long ans = 1;
        for (long i = 2; i <= n; i++) {
            ans = ans * (2*i - 1)  % Mod * i % Mod;
        }
        return (int) ans;
    }
}
```

2400\. 恰好移动 k 步到达某一位置的方法数目
--------------------------

给你两个 **正** 整数 `startPos` 和 `endPos` 。最初，你站在 **无限** 数轴上位置 `startPos` 处。在一步移动中，你可以向左或者向右移动一个位置。

给你一个正整数 `k` ，返回从 `startPos` 出发、**恰好** 移动 `k` 步并到达 `endPos` 的 **不同** 方法数目。由于答案可能会很大，返回对 `109 + 7` **取余** 的结果。

如果所执行移动的顺序不完全相同，则认为两种方法不同。

**注意：**数轴包含负整数**。**

**示例 1：**

**输入：**startPos = 1, endPos = 2, k = 3
**输出：**3
**解释：**存在 3 种从 1 到 2 且恰好移动 3 步的方法：
- 1 -> 2 -> 3 -> 2.
- 1 -> 2 -> 1 -> 2.
- 1 -> 0 -> 1 -> 2.
  可以证明不存在其他方法，所以返回 3 。

**示例 2：**

**输入：**startPos = 2, endPos = 5, k = 10
**输出：**0
**解释：**不存在从 2 到 5 且恰好移动 10 步的方法。

**提示：**

*   `1 <= startPos, endPos, k <= 1000`

[https://leetcode.cn/problems/number-of-ways-to-reach-a-position-after-exactly-k-steps/description/](https://leetcode.cn/problems/number-of-ways-to-reach-a-position-after-exactly-k-steps/description/)

```java
import java.util.HashMap;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    private HashMap<String, Integer> memo;
    public int numberOfWays(int startPos, int endPos, int k) { // 只会dfs哈哈哈
        if (k < Math.abs(endPos - startPos)) {
            return 0;
        }
        memo = new HashMap();
        return dfs(startPos, endPos, k);
    }

    private int dfs(int curPos, int endPos,int surplus) {
        if (curPos == endPos && surplus == 0) {
            return 1;
        }
        if (surplus == 0) {
            return 0;
        }
        String key = curPos + " " + surplus;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int ans = (dfs(curPos + 1, endPos, surplus - 1) + dfs(curPos - 1, endPos, surplus - 1)) % Mod;
        memo.put(key, ans);
        return ans;
    }
}
```

> $$
> 组合数递推公式：C_{i}^{j}=C_{i-1}^{j}+C_{i-1}^{j-1}
> $$
>

```java
class Solution {
    private static final int Mod = (int) 1e9 + 7;
    private static final int MX = 1001;
    private static final int[][] c = new int[MX][MX];
    static {
        for (int i = 0; i < MX; i++) {
            c[i][0] = c[i][i] = 1;
            for (int j = 1; j < i; j++) {
                c[i][j] = (c[i - 1][j - 1] + c[i - 1][j]) % Mod;
            }
        }
    }
    public int numberOfWays(int startPos, int endPos, int k) {
        int d = Math.abs(endPos - startPos);
        if ((k + d) % 2 == 1) {
            return 0;
        }
        return c[k][(k + d) / 2];
    }
}
```

100298\. 到达第 K 级台阶的方案数
----------------------

给你有一个 **非负** 整数 `k` 。有一个无限长度的台阶，**最低** 一层编号为 0 。

虎老师有一个整数 `jump` ，一开始值为 0 。虎老师从台阶 1 开始，虎老师可以使用 **任意** 次操作，目标是到达第 `k` 级台阶。假设虎老师位于台阶 `i` ，一次 **操作** 中，虎老师可以：

*   向下走一级到 `i - 1` ，但该操作 **不能** 连续使用，如果在台阶第 0 级也不能使用。
*   向上走到台阶 `i + 2jump` 处，然后 `jump` 变为 `jump + 1` 。

请你返回虎老师到达台阶 `k` 处的总方案数。

**注意** ，虎老师可能到达台阶 `k` 处后，通过一些操作重新回到台阶 `k` 处，这视为不同的方案。

**示例 1：**

**输入：**k = 0

**输出：**2

**解释：**

2 种到达台阶 0 的方案为：

*   虎老师从台阶 1 开始。
    *   执行第一种操作，从台阶 1 向下走到台阶 0 。
*   虎老师从台阶 1 开始。
    *   执行第一种操作，从台阶 1 向下走到台阶 0 。
    *   执行第二种操作，向上走 20 级台阶到台阶 1 。
    *   执行第一种操作，从台阶 1 向下走到台阶 0 。

**示例 2：**

**输入：**k = 1

**输出：**4

**解释：**

4 种到达台阶 1 的方案为：

*   虎老师从台阶 1 开始，已经到达台阶 1 。
*   虎老师从台阶 1 开始。
    *   执行第一种操作，从台阶 1 向下走到台阶 0 。
    *   执行第二种操作，向上走 20 级台阶到台阶 1 。
*   虎老师从台阶 1 开始。
    *   执行第二种操作，向上走 20 级台阶到台阶 2 。
    *   执行第一种操作，向下走 1 级台阶到台阶 1 。
*   虎老师从台阶 1 开始。
    *   执行第一种操作，从台阶 1 向下走到台阶 0 。
    *   执行第二种操作，向上走 20 级台阶到台阶 1 。
    *   执行第一种操作，向下走 1 级台阶到台阶 0 。
    *   执行第二种操作，向上走 21 级台阶到台阶 2 。
    *   执行第一种操作，向下走 1 级台阶到台阶 1 。

**提示：**

*   `0 <= k <= 109`

[https://leetcode.cn/problems/find-number-of-ways-to-reach-the-k-th-stair/solutions/2782792/liang-chong-fang-fa-ji-yi-hua-sou-suo-zu-j227/](https://leetcode.cn/problems/find-number-of-ways-to-reach-the-k-th-stair/solutions/2782792/liang-chong-fang-fa-ji-yi-hua-sou-suo-zu-j227/)

```java
import java.util.HashMap;

class Solution {
    HashMap<String, Integer> memo = new HashMap<>();
    public int waysToReachStair(int k) {
        if (k == 0) {
            return 2;
        }
        return dfs(1, 0, 0, k);
    }

    private int dfs(int i, int isUsed, int jump, int k) {
        if (i < 0) {
            return 0;
        }
        if ((i == k + 1 && isUsed == 0)) {
            return 1;
        }
        if (i > k + 1) {
            return 0;
        }
        String key = i + " " + " " + isUsed + " " + jump;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = dfs(i + (int) Math.pow(2, jump), 0, jump + 1, k);
        if (i == k) {
            res++;
        }
        if (isUsed == 0 && i != 0) {
            res += dfs(i - 1, 1, jump, k);
        }
        memo.put(key, res);
        return res;
    }
}
```

![1716116464969](assets/1716116464969.png)

```java
class Solution {
    private static int MX = 31;
    private static int[][] c = new int[MX][MX];
    static {
        for (int i = 0; i < MX; i++) {
            c[i][i] = c[i][0] = 1;
            for (int j = 1; j < i; j++) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
            }
        }
    }
    public int waysToReachStair(int k) {
        int ans = 0;
        for (int j = 0; j < 30; j++) {
            int m = (1 << j) - k;
            if (0 <= m && m <= j + 1) {
                ans += c[j + 1][m];
            }
        }
        return ans;
    }
}
```

2514\. 统计同位异构字符串数目(费马小定理+存在相同值的全排列)
------------------

https://leetcode.cn/circle/discuss/mDfnkW/

给你一个字符串 `s` ，它包含一个或者多个单词。单词之间用单个空格 `' '` 隔开。

如果字符串 `t` 中第 `i` 个单词是 `s` 中第 `i` 个单词的一个 **排列** ，那么我们称字符串 `t` 是字符串 `s` 的同位异构字符串。

*   比方说，`"acb dfe"` 是 `"abc def"` 的同位异构字符串，但是 `"def cab"` 和 `"adc bef"` 不是。

请你返回 `s` 的同位异构字符串的数目，由于答案可能很大，请你将它对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**s = "too hot"
**输出：**18
**解释：**输入字符串的一些同位异构字符串为 "too hot" ，"oot hot" ，"oto toh" ，"too toh" 以及 "too oht" 。

**示例 2：**

**输入：**s = "aa"
**输出：**1
**解释：**输入字符串只有一个同位异构字符串。

**提示：**

*   `1 <= s.length <= 105`
*   `s` 只包含小写英文字母和空格 `' '` 。
*   相邻单词之间由单个空格隔开。

[https://leetcode.cn/problems/count-anagrams/](https://leetcode.cn/problems/count-anagrams/)

![1716119020712](assets/1716119020712.png)

![1716117668390](assets/1716117668390.png)

```java
import java.util.Arrays;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int countAnagrams(String S) {
        char[] s = S.toCharArray();
        long ans = 1L, mul = 1L;
        int[] cnt = new int[26];
        for (int i = 0, j = 0; i < s.length; i++) {
            if (s[i] == ' ') {
                Arrays.fill(cnt, 0);
                j = 0;
            }else{
                mul = mul * ++cnt[s[i] - 'a'] % Mod;
                ans = ans * ++j % Mod;
            }
        }
        // BigInteger m = new BigInteger(Long.toString(mul));
        // BigInteger mod = new BigInteger(Integer.toString(Mod));
        // BigInteger pow = m.modPow(new BigInteger("-1"), mod);
        // String res = new BigInteger(Long.toString(ans)).multiply(pow).mod(mod).toString();
        // return Integer.parseInt(res); 这样也行，利用java的BigInteger
        return (int) (ans * pow(mul, Mod - 2) % Mod); // 这里就是我们求逆元，但是java可以用BigInteger
    }
    // 最后是a / b (mod p)
    private long pow(long x, int n) {
        long res = 1L;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % Mod;
            }
            x = x * x % Mod;
        }
        return res;
    }
}
```

1643\. 第 K 条最小指令
----------------

Bob 站在单元格 `(0, 0)` ，想要前往目的地 `destination` ：`(row, column)` 。他只能向 **右** 或向 **下** 走。你可以为 Bob 提供导航 **指令** 来帮助他到达目的地 `destination` 。

**指令** 用字符串表示，其中每个字符：

*   `'H'` ，意味着水平向右移动
*   `'V'` ，意味着竖直向下移动

能够为 Bob 导航到目的地 `destination` 的指令可以有多种，例如，如果目的地 `destination` 是 `(2, 3)`，`"HHHVV"` 和 `"HVHVH"` 都是有效 **指令** 。

然而，Bob 很挑剔。因为他的幸运数字是 `k`，他想要遵循 **按字典序排列后的第 `k` 条最小指令** 的导航前往目的地 `destination` 。`k`  的编号 **从 1 开始** 。

给你一个整数数组 `destination` 和一个整数 `k` ，请你返回可以为 Bob 提供前往目的地 `destination` 导航的 **按字典序排列后的第 `k` 条最小指令** 。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/11/01/ex1.png)

**输入：**destination = \[2,3\], k = 1
**输出：**"HHHVV"
**解释：**能前往 (2, 3) 的所有导航指令 **按字典序排列后** 如下所示：
\["HHHVV", "HHVHV", "HHVVH", "HVHHV", "HVHVH", "HVVHH", "VHHHV", "VHHVH", "VHVHH", "VVHHH"\].

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/11/01/ex2.png)**

**输入：**destination = \[2,3\], k = 2
**输出：**"HHVHV"

**示例 3：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/11/01/ex3.png)**

**输入：**destination = \[2,3\], k = 3
**输出：**"HHVVH"

**提示：**

*   `destination.length == 2`
*   `1 <= row, column <= 15`
*   `1 <= k <= nCr(row + column, row)`，其中 `nCr(a, b)` 表示组合数，即从 `a` 个物品中选 `b` 个物品的不同方案数。

[https://leetcode.cn/problems/kth-smallest-instructions/description/](https://leetcode.cn/problems/kth-smallest-instructions/description/)

```java
class Solution {
    private static int MX = 31;
    private static int[][] c = new int[MX][MX];
    static {
        for (int i = 0; i < MX; i++) {
            c[i][i] = c[i][0] = 1;
            for (int j = 1; j < i; j++) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
            }
        }
    }
    public String kthSmallestPath(int[] destination, int k) {
        int row = destination[0], col = destination[1];
        int total = row + col;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total && col > 0; i++) {
            int count = c[row + col - 1][col - 1]; // 如果当前位置填H的方案数，如果k<=count，那么就是填H；如果k>count，那么当前位置就应该填V,并且有k-=count,将当前位置填H的所有方案减去
            if (k <= count) {
                sb.append('H');
                col--;
            }else{
                sb.append('V');
                row--;
                k -= count;
            }
        }
        while (row > 0) {
            sb.append('V');
            row--;
        }
        return sb.toString();
    }
}
```

## [3463. 判断操作后字符串中的数字是否相等 II](https://leetcode.cn/problems/check-if-digits-are-equal-in-string-after-operations-ii/)

> 题意：给你一个由数字组成的字符串 `s` 。重复执行以下操作，直到字符串恰好包含 **两个** 数字：
>
> - 从第一个数字开始，对于 `s` 中的每一对连续数字，计算这两个数字的和 **模** 10。
> - 用计算得到的新数字依次替换 `s` 的每一个字符，并保持原本的顺序。
>
> 如果 `s` 最后剩下的两个数字相同，则返回 `true` 。否则，返回 `false`。

```java
class Solution {
	// 这题分析出每一位就是乘以c(n, i)的组合数很简单，难的是如何将组合数学模10，由于10是一个合数，所以不能用费马小定理：如果mod是质数，则 a ^ (Mod) = a(% Mod) 既 a ^ (Mod - 1) = 1(% Mod)，两边再除以a，有 a ^ (Mod - 2) = 1^(-a)(% Mod)，也就是最常见的费马小定理求逆元：1/a的逆元就是pow(a, mod - 2) (% Mod)
    // 但是这个题的Mod = 10，不是质数，所以不能使用费马小定理；还有一个定理可以求逆元，欧拉定理：如果a与Mod互质，有a ^ (Mod的欧拉函数) = 1 （% Mod）其中Mod的欧拉函数就是小于Mod的质数的个数，所以10的欧拉函数就是4，然后等式两边同时除以a，有a ^ (3) = 1/a (% Mod)，这样就有invF(mx) = pow(f(mx),3),然后使用递推式：invF(i) = invF(i + 1) * i
    private static final int Mod = 10;
    private static final int MX = (int) 1e5;
    private static final int[] f = new int[MX + 1]; // i的阶乘
    private static final int[] invF = new int[MX + 1];  // i的阶乘的逆元
    private static final int[] p2 = new int[MX + 1]; // i中2因子的个数
    private static final int[] p5 = new int[MX + 1]; // i中5因子的个数
    
    static {
        f[0] = 1;
        for (int i = 1; i <= MX; i++) {
            int x = i;
            int e2 = Integer.numberOfTrailingZeros(x); // 计算x末尾0的个数
            x >>= e2;
            int e5 = 0;
            while (x % 5 == 0) {
                e5++;
                x /= 5;
            }
            f[i] = f[i - 1] * x % Mod;
            p2[i] = p2[i - 1] + e2;
            p5[i] = p5[i - 1] + e5;
        }
        invF[MX] = qpow(f[MX], 3);
        for (int i = MX; i > 0; i--) {
            int x = i;
            x >>= Integer.numberOfTrailingZeros(x);
            while (x % 5 == 0) {
                x /= 5;
            }
            invF[i - 1] = invF[i] * x % Mod;
        }
    }

    private static int qpow(int a, int b) {
        int ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans * a) % Mod;
            }
            b >>= 1;
            a = (a * a) % Mod;
        }
        return ans;
    }

    private int comb(int n, int k) {
        // 由于每项都 < 10，所以无需中途取模
        return f[n] * invF[k] * invF[n - k] * qpow(2, p2[n] - p2[k] - p2[n - k]) * qpow(5, p5[n] - p5[k] - p5[n - k]) % Mod;
    }
    
    public boolean hasSameDigits(String s) {
        return solve(s.substring(0, s.length() - 1)) == solve(s.substring(1));
    }

    private int solve(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += comb(s.length() - 1, i) * (s.charAt(i) - '0');
        }
        return res % Mod;
    }
}
```

> 这个题可以学到太多东西了，一定让你豁然开朗！！！

题目：给你一个排列（1，2，3，...，n），输出第k小的排列。

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 和 PrintWriter 提高输入输出效率
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        // 读取测试用例数量
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            // 读取 n 和 k，注意 k 为 1-indexed
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            // 预先计算阶乘，fact[i] 表示 i!；n 较大时可能溢出，但一般题目中 n 较小
            long[] fact = new long[n + 1];
            fact[0] = 1;
            boolean overflow = false;
            for (int i = 1; i <= n; i++) {
                // 检查乘法是否可能溢出，若溢出则停止计算
                if (Long.MAX_VALUE / fact[i - 1] < i) {
                    overflow = true;
                    break;
                }
                fact[i] = fact[i - 1] * i;
            }

            // 如果 n 较大或者 k 超出所有排列数量，则输出 -1
            if (!overflow && k > fact[n]) {
                out.println("-1");
                continue;
            } else if (overflow) {
                // 当发生溢出时，题目数据可以保证不会超过范围（通常 n 会比较小）
                // 若出现溢出，这里简单处理为不输出结果
                out.println("-1");
                continue;
            }

            // 转换为 0 索引方便计算
            k--;
            // 初始化存放可选数字的列表，数字为 1, 2, ..., n
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                numbers.add(i);
            }

            // 使用贪心算法构造第 k 小的排列
            StringBuilder sb = new StringBuilder();
            for (int i = n; i >= 1; i--) {
                int index = (int) (k / fact[i - 1]);
                sb.append(numbers.get(index)).append(" ");
                numbers.remove(index); // 删除下标
                k %= fact[i - 1];
            }

            out.println(sb.toString().trim());
        }

        // 刷新并关闭输出流
        out.flush();
        out.close();
        br.close();
    }
}

```

![1741940999432](assets/1741940999432.png)

非常典型的 codeforces 小注意力题。

首先，我们发现当排列为 1 到 n 的正序数列时，S 可以取到最大值。此时，$S(p)=1×n+2×(n−1)+3×(n−2)⋯+n×1$。这是因为区间 [1,1],[1,2],[1,3],…[1,n] 的最小值为 1，共 n 个，区间 [2,2],[2,3],…[2,n] 的最小值为 2，共 n−1 个，以此类推。

我们考虑如下构造过程。假设现在已经构造好了 x+1 到 n 的排列方式，考虑 x 可以插入到哪些位置。将 x 插入到已排好的内容中间，将会导致原先的许多区间贡献变得更小，不如将 x 放在头尾的位置更优。因此，最优排列的构造方案一定是从大往小，每次将当前的数放在头或尾的位置。

一共需决策 n−1 个数，每个数都有可能放在首尾两个位置，因此一共有 2n−1 中方案。超出可以直接输出 `-1`。否则，对 k 进行二进制拆分，根据当前位的值决定该数应该放在头还是尾。可以直接 deque 维护。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;

public class Main { // 代码可能有问题，但是思路没问题
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            String[] input = br.readLine().split(" ");
            int n = Integer.parseInt(input[0]);
            long k = Long.parseLong(input[1]) - 1;
            if (n - 1 < 64 && k >= (1L << (n - 1))) {
                System.out.println("-1");
                continue;
            }
            Deque<Integer> ans = new LinkedList<>();
            ans.add(n);
            for (int i = n - 1; i > 0; --i) {
                if ((k & 1) == 1) {
                    ans.addLast(i);
                } else {
                    ans.addFirst(i);
                }
                k >>= 1;
            }
            for (int i : ans) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
```

