3287\. 求出数组中最大序列值
-----------------

给你一个整数数组 `nums` 和一个 **正** 整数 `k` 。

定义长度为 `2 * x` 的序列 `seq` 的 **值** 为：

*   `(seq[0] OR seq[1] OR ... OR seq[x - 1]) XOR (seq[x] OR seq[x + 1] OR ... OR seq[2 * x - 1])`.

请你求出 `nums` 中所有长度为 `2 * k` 的

子序列

的 **最大值** 。

**示例 1：**

**输入：**nums = \[2,6,7\], k = 1

**输出：**5

**解释：**

子序列 `[2, 7]` 的值最大，为 `2 XOR 7 = 5` 。

**示例 2：**

**输入：**nums = \[4,2,5,6,7\], k = 2

**输出：**2

**解释：**

子序列 `[4, 5, 6, 7]` 的值最大，为 `(4 OR 5) XOR (6 OR 7) = 2` 。

**提示：**

*   `2 <= nums.length <= 400`
*   `1 <= nums[i] < 27`
*   `1 <= k <= nums.length / 2`

[https://leetcode.cn/problems/find-the-maximum-sequence-value-of-array/description/](https://leetcode.cn/problems/find-the-maximum-sequence-value-of-array/description/)

> 看到从中间划分，一般就是枚举中间
>
> 看到子序列，直接想dp，没什么贪心可以想的
>
> 要求选K个数，那么就是背包容量为k

```java
class Solution {
    public int maxValue(int[] nums, int k) {
        int n = nums.length;
        int mx = 1 << 7;
        boolean[][][] f = new boolean[n + 1][k + 1][mx]; // 表示从前i(不包括i)个数选出j个数，结果是否为k
        f[0][0][0] = true;
        boolean[][][] g = new boolean[n + 1][k + 1][mx]; // 表示从i(包括i)到n - 1选出j个数，结果是否为k
        g[n][0][0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int x = 0; x < mx; x++) {
                    if (f[i][j][x]) {
                        f[i + 1][j][x] = true;
                        if (j < k) {
                            f[i + 1][j + 1][x | nums[i]] = true;
                        }
                    }
                }
            }
        }
        for (int i = n; i > 0; i--) {
            for (int j = 0; j <= k; j++) {
                for (int x = 0; x < mx; x++) {
                    if (g[i][j][x]) {
                        g[i - 1][j][x] = true;
                        if (j < k) {
                            g[i - 1][j + 1][x | nums[i - 1]] = true;
                        }
                    }
                }
            }
        }
        // 枚举中间的元素
        int ans = 0;
        for (int i = k; i + k <= n; i++) {
            for (int x = 0; x < mx; x++) {
                for (int y = 0; y < mx; y++) {
                    if (f[i][k][x] && g[i][k][y]) {
                        ans = Math.max(ans, x ^ y);
                    }
                }
            }
        }
        return ans;
    }
}
```



## [B.Cidoai的平均数对（平均值01背包）](https://ac.nowcoder.com/acm/contest/88880/B)

![1726289139228](assets/1726289139228.png)

![1726289113199](assets/1726289113199.png)

```java
public class Main{ // 平均值不要想着总数/数量，对于每一个物品想它的正负贡献就好了
    private static void solve() throws IOException {
        n = sc.nextInt();
        k = sc.nextInt();
        nums = new int[n][2];
        for (int i = 0; i < n; i++) {
            nums[i][0] = sc.nextInt();
            nums[i][1] = sc.nextInt();
        }
        int sum = 0, target = 0;
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (nums[i][1] <= k) {
                sum += nums[i][0];
                target += k - nums[i][1];
            }else{
                list.add(new int[]{nums[i][0], nums[i][1]});
            }
        }
        int[] dp = new int[target + 1];
        Arrays.fill(dp, sum);
        for (int[] pair : list) {
            int a = pair[0], b = pair[1] -k;
            for (int i = target; i >= b; i--) {
                dp[i] = Math.max(dp[i], dp[i - b] + a);
            }
        }
        sc.println(dp[target]);
    }
}
```

 



3180\. 执行操作可获得的最大总奖励 I
----------------------

给你一个整数数组 `rewardValues`，长度为 `n`，代表奖励的值。

最初，你的总奖励 `x` 为 0，所有下标都是 **未标记** 的。你可以执行以下操作 **任意次** ：

*   从区间 `[0, n - 1]` 中选择一个 **未标记** 的下标 `i`。
*   如果 `rewardValues[i]` **大于** 你当前的总奖励 `x`，则将 `rewardValues[i]` 加到 `x` 上（即 `x = x + rewardValues[i]`），并 **标记** 下标 `i`。

以整数形式返回执行最优操作能够获得的 **最大** 总奖励。

**示例 1：**

**输入：**rewardValues = \[1,1,3,3\]

**输出：**4

**解释：**

依次标记下标 0 和 2，总奖励为 4，这是可获得的最大值。

**示例 2：**

**输入：**rewardValues = \[1,6,4,3,2\]

**输出：**11

**解释：**

依次标记下标 0、2 和 1。总奖励为 11，这是可获得的最大值。

**提示：**

*   `1 <= rewardValues.length <= 2000`
*   `1 <= rewardValues[i] <= 2000`

[https://leetcode.cn/problems/maximum-total-reward-using-operations-i/description/](https://leetcode.cn/problems/maximum-total-reward-using-operations-i/description/)

```java
// 首先用的是Hash表做记忆化搜索,可惜直接超时
import java.util.Arrays;
import java.util.HashMap;

class Solution {
    int n;
    int mx;
    int tol;
    HashMap<Integer,Integer> memo;
    public int maxTotalReward(int[] rewardValues) {
        Arrays.sort(rewardValues);
        for (int x : rewardValues) {
            mx = Math.max(mx, x);
            tol += x;
        }
        n = rewardValues.length;
        memo = new HashMap<>();
        return dfs(0, 0, rewardValues);
    }

    private int dfs(int i, int sum, int[] rewardValues) {
        if (i == n || mx <= sum || sum > tol / 2) {
            return sum;
        }
        Integer key = ( i << 11) + sum;
        if (memo.containsKey(key)) {
            // System.out.println(memo.get(key));
            return memo.get(key);
        }
        int res = 0;
        if (rewardValues[i] > sum) {
            res = dfs(i + 1, sum + rewardValues[i], rewardValues);
        }
        res = Math.max(res, dfs(i + 1, sum, rewardValues));
        memo.put(key, res);
        return res;
    }
}
```

```java
// 我们可以从题目发现，其实不会选两个相同的数字，我们可以先做去重处理，去重之后可以通过，525ms
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

class Solution {
    int n;
    int mx;
    HashMap<Integer,Integer> memo;
    public int maxTotalReward(int[] rewardValues) {
        HashSet<Integer> set = new HashSet<>();
        for (int x : rewardValues) {
            set.add(x);
            mx = Math.max(mx, x);
        }
        n = set.size();
        int[] nums = new int[n];
        int i = 0;
        for (int x : set) {
            nums[i++] = x;
        }
        Arrays.sort(nums);
        memo = new HashMap<>();
        return dfs(0, 0, nums);
    }

    private int dfs(int i, int sum, int[] nums) {
        if (i == n || mx <= sum) {
            return sum;
        }
        Integer key = ( i << 11) + sum;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = 0;
        if (nums[i] > sum) {
            res = dfs(i + 1, sum + nums[i], nums);
        }
        res = Math.max(res, dfs(i + 1, sum, nums));
        memo.put(key, res);
        return res;
    }
}
```

```java
// 当然比赛中我也想过用数组记忆化，但是爆内存了
import java.util.Arrays;

class Solution {
    int n;
    int mx;
    int tol;
    int[][] memo;
    public int maxTotalReward(int[] rewardValues) {
        Arrays.sort(rewardValues);
        for (int x : rewardValues) {
            mx = Math.max(mx, x);
            tol += x;
        }
        n = rewardValues.length;
        memo = new int[n][tol / 2 + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, rewardValues);
    }

    private int dfs(int i, int sum, int[] rewardValues) {
        if (i == n || mx <= sum) {
            return sum;
        }
        long key = ((long) i << 32) + sum;
        if (memo[i][sum] != -1) {;
            return memo[i][sum];
        }
        int res = 0;
        if (rewardValues[i] > sum) {
            res = dfs(i + 1, sum + rewardValues[i], rewardValues);
        }
        res = Math.max(res, dfs(i + 1, sum, rewardValues));
        return memo[i][sum] = res;
    }
}
```

```java
// 我们可以在上面代码做降重，然后数组最大开到mx*2就行了，77ms
import java.util.Arrays;
import java.util.HashSet;

class Solution {
    int n;
    int mx;
    int[][] memo;
    public int maxTotalReward(int[] rewardValues) {
        HashSet<Integer> set = new HashSet<>();
        for (int x : rewardValues) {
            set.add(x);
            mx = Math.max(mx, x);
        }
        n = set.size();
        int[] nums = new int[n];
        int i = 0;
        for (int x : set) {
            nums[i++] = x;
        }
        Arrays.sort(nums);
        memo = new int[n][mx * 2];
        for (int j = 0; j < n; j++) {
            Arrays.fill(memo[j], -1);
        }
        return dfs(0, 0, nums);
    }

    private int dfs(int i, int sum, int[] rewardValues) {
        if (i == n || mx <= sum) {
            return sum;
        }
        if (memo[i][sum] != -1) {
            return memo[i][sum];
        }
        int res = 0;
        if (rewardValues[i] > sum) {
            res = dfs(i + 1, sum + rewardValues[i], rewardValues);
        }
        res = Math.max(res, dfs(i + 1, sum, rewardValues));
        return memo[i][sum] = res;
    }
}
```

> 其实这个题到这里的55ms已经很好了，但是这个还是不够，无论是内存还是时间，我们其实浪费了一个维度，我们dfs的返回值是sum,然后传参也有sum，其实这就是一个01背包问题，我们返回值只需要写boolean.

```java
// 翻译成01背包问题，19ms
import java.util.Arrays;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        // `Arrays.stream(rewardValues).distinct().sorted().toArray()` 是Java中的一段代码，它的意思是：
        //
        //1. `Arrays.stream(rewardValues)`：将数组 `rewardValues` 转换为一个流（Stream）。
        //2. `.distinct()`：从流中去除重复的元素。
        //3. `.sorted()`：对流中的元素进行排序。
        //4. `.toArray()`：将流转换回数组。
        //
        //所以，这段代码的作用是对数组 `rewardValues` 进行去重和排序操作，并将结果存储在一个新的数组中。
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        int n = nums.length;
        int mx = nums[n - 1];
        boolean[] dp = new boolean[mx * 2 + 1]; // 表示前n个数可以取到的最大值
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            for (int j = mx * 2; j >= nums[i]; j--) {
                if (dp[j - nums[i]] && j < nums[i] * 2) {
                    dp[j] = true;
                }
            }
        }
        // 找到最大的dp[i] = true中的i
        for (int i = dp.length - 1; i >= 0; i--) {
            if (dp[i]) {
                return i;
            }
        }
        return 0;
    }
}
```

## 3181. 执行操作可获得的最大总奖励 II（bitset/bigint 优化 0-1 背包 + 两数之和优化 ）

-----------------------

给你一个整数数组 `rewardValues`，长度为 `n`，代表奖励的值。

最初，你的总奖励 `x` 为 0，所有下标都是 **未标记** 的。你可以执行以下操作 **任意次** ：

*   从区间 `[0, n - 1]` 中选择一个 **未标记** 的下标 `i`。
*   如果 `rewardValues[i]` **大于** 你当前的总奖励 `x`，则将 `rewardValues[i]` 加到 `x` 上（即 `x = x + rewardValues[i]`），并 **标记** 下标 `i`。

以整数形式返回执行最优操作能够获得的 **最大** 总奖励。

**示例 1：**

**输入：**rewardValues = \[1,1,3,3\]

**输出：**4

**解释：**

依次标记下标 0 和 2，总奖励为 4，这是可获得的最大值。

**示例 2：**

**输入：**rewardValues = \[1,6,4,3,2\]

**输出：**11

**解释：**

依次标记下标 0、2 和 1。总奖励为 11，这是可获得的最大值。

**提示：**

*   `1 <= rewardValues.length <= 5 * 104`
*   `1 <= rewardValues[i] <= 5 * 104`

[https://leetcode.cn/problems/maximum-total-reward-using-operations-ii/description/](https://leetcode.cn/problems/maximum-total-reward-using-operations-ii/description/)

> 不难发现，这个题的数据范围陡增

```java
// 使用BigInteger提速64倍，2525ms
import java.math.BigInteger;
import java.util.Arrays;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        // `Arrays.stream(rewardValues).distinct().sorted().toArray()` 是Java中的一段代码，它的意思是：
        //
        //1. `Arrays.stream(rewardValues)`：将数组 `rewardValues` 转换为一个流（Stream）。
        //2. `.distinct()`：从流中去除重复的元素。
        //3. `.sorted()`：对流中的元素进行排序。
        //4. `.toArray()`：将流转换回数组。
        //
        //所以，这段代码的作用是对数组 `rewardValues` 进行去重和排序操作，并将结果存储在一个新的数组中。
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        BigInteger f = BigInteger.ONE;
        for (int x : nums) {
            // 条件 x <= j < x*2 ， f |= (f & ((1 << v) - 1)) << v
            BigInteger mask = BigInteger.ONE.shiftLeft(x).subtract(BigInteger.ONE);
            f = f.or(f.and(mask).shiftLeft(x));
        }
        return f.bitLength() - 1;
    }
}
```

> 设 m=max⁡(rewardValues)，如果数组中包含 m−1，则答案为 2m−1（因为这是答案的上界），无需计算 DP。

```java
import java.math.BigInteger; // 728ms
import java.util.Arrays;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        // `Arrays.stream(rewardValues).distinct().sorted().toArray()` 是Java中的一段代码，它的意思是：
        //
        //1. `Arrays.stream(rewardValues)`：将数组 `rewardValues` 转换为一个流（Stream）。
        //2. `.distinct()`：从流中去除重复的元素。
        //3. `.sorted()`：对流中的元素进行排序。
        //4. `.toArray()`：将流转换回数组。
        //
        //所以，这段代码的作用是对数组 `rewardValues` 进行去重和排序操作，并将结果存储在一个新的数组中。
        int m = 0;
        for (int x : rewardValues) {
            m = Math.max(m, x);
        }
        for (int x : rewardValues) {
            if (x == m - 1) {
                return m * 2 - 1;
            }
        }
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        BigInteger f = BigInteger.ONE;
        for (int x : nums) {
            // 条件 x <= j < x*2 ， f |= (f & ((1 << v) - 1)) << v
            BigInteger mask = BigInteger.ONE.shiftLeft(x).subtract(BigInteger.ONE);
            f = f.or(f.and(mask).shiftLeft(x));
        }
        return f.bitLength() - 1;
    }
}
```

```java
// 如果有两个不同元素之和等于 m−1，也可以直接返回 2m−1。
// 由于在随机数据下，几乎 100% 的概率有两数之和等于 m−1，而力扣又喜欢出随机数据，所以在大多数情况下，本题就是一道 1. 两数之和。 15ms
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        // `Arrays.stream(rewardValues).distinct().sorted().toArray()` 是Java中的一段代码，它的意思是：
        //
        //1. `Arrays.stream(rewardValues)`：将数组 `rewardValues` 转换为一个流（Stream）。
        //2. `.distinct()`：从流中去除重复的元素。
        //3. `.sorted()`：对流中的元素进行排序。
        //4. `.toArray()`：将流转换回数组。
        //
        //所以，这段代码的作用是对数组 `rewardValues` 进行去重和排序操作，并将结果存储在一个新的数组中。
        int mx = 0;
        for (int x : rewardValues) {
            mx = Math.max(mx, x);
        }
        Set<Integer> set = new HashSet<>();
        for (int x : rewardValues) {
            if (x == mx - 1) {
                return mx * 2 - 1;
            }
            if (set.contains(x)) {
                continue;
            }
            if (set.contains(mx - 1 - x)) {
                return mx * 2 - 1;
            }
            set.add(x);
        }
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        BigInteger f = BigInteger.ONE;
        for (int x : nums) {
            // 条件 x <= j < x*2 ， f |= (f & ((1 << v) - 1)) << v
            BigInteger mask = BigInteger.ONE.shiftLeft(x).subtract(BigInteger.ONE);
            f = f.or(f.and(mask).shiftLeft(x));
        }
        return f.bitLength() - 1;
    }
}
```

2742\. 给墙壁刷油漆
-------------

给你两个长度为 `n` 下标从 **0** 开始的整数数组 `cost` 和 `time` ，分别表示给 `n` 堵不同的墙刷油漆需要的开销和时间。你有两名油漆匠：

*   一位需要 **付费** 的油漆匠，刷第 `i` 堵墙需要花费 `time[i]` 单位的时间，开销为 `cost[i]` 单位的钱。
*   一位 **免费** 的油漆匠，刷 **任意** 一堵墙的时间为 `1` 单位，开销为 `0` 。但是必须在付费油漆匠 **工作** 时，免费油漆匠才会工作。

请你返回刷完 `n` 堵墙最少开销为多少。

**示例 1：**

**输入：**cost = \[1,2,3,2\], time = \[1,2,3,2\]
**输出：**3
**解释：**下标为 0 和 1 的墙由付费油漆匠来刷，需要 3 单位时间。同时，免费油漆匠刷下标为 2 和 3 的墙，需要 2 单位时间，开销为 0 。总开销为 1 + 2 = 3 。

**示例 2：**

**输入：**cost = \[2,3,4,2\], time = \[1,1,1,1\]
**输出：**4
**解释：**下标为 0 和 3 的墙由付费油漆匠来刷，需要 2 单位时间。同时，免费油漆匠刷下标为 1 和 2 的墙，需要 2 单位时间，开销为 0 。总开销为 2 + 2 = 4 。

**提示：**

*   `1 <= cost.length <= 500`
*   `cost.length == time.length`
*   `1 <= cost[i] <= 106`
*   `1 <= time[i] <= 500`

[https://leetcode.cn/problems/painting-the-walls/description/?envType=daily-question&envId=2024-06-28](https://leetcode.cn/problems/painting-the-walls/description/?envType=daily-question&envId=2024-06-28)

```java
import java.util.Arrays;

class Solution { // 我喜欢从i = 0开始走
    int n;
    int[][] memo;
    int[] cost, time;
    public int paintWalls(int[] cost, int[] time) {
        n = cost.length;
        this.cost = cost;
        this.time = time;
        memo = new int[n][n * 2 + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0);
    }

    private int dfs(int i, int j) { // 表示刷到第i堵墙，付费时间 - 免费时间 = j
        if (j >= n - i) { // 剩余的墙都可以免费刷
            return 0;
        }
        if (i >= n) {
            return Integer.MAX_VALUE / 2;
        }
        int k = j + n; // 加上偏移量，防止出现负数，只有在memo里面才需要使用k
        if (memo[i][k] != -1) {
            return memo[i][k];
        }
        int res = dfs(i + 1, j + time[i]) + cost[i];
        res = Math.min(res, dfs(i + 1, j - 1)); // 核心：主要是j的值可以暂时为负数！！！
        return memo[i][k] = res;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    // 把 time[i]+1 看成物品体积，cost[i] 看成物品价值，问题变成：
	// 从 n 个物品中选择体积和至少为 n 的物品，价值和最小是多少？
    public int paintWalls(int[] cost, int[] time) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int c = cost[i], t = time[i] + 1;
            for (int j = n; j >= 0; j--) {
                dp[j] = Math.min(dp[j], dp[Math.max(j - t, 0)] + c);
            }
        }
        return dp[n];
    }
}
```

[CF1526C1 Potions (Easy Version)](https://codeforces.com/problemset/problem/1526/C1)

> 给你一个长度为 n 的序列 A，要求你找出最长的一个子序列使得这个子序列任意前缀和都非负。
>
> 其中 n≤2×103，−109≤ai≤109。

```java
// 背包解法 O(n^2)  横看成岭侧成峰
	private static void solve() throws IOException {
        n = sc.nextInt();
        int[] nums = new int[n];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        int[] dp = new int[n + 1]; // 表示长度为i的最大和
        Arrays.fill(dp, -INF);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = n; j > 0; j--) {
                if (dp[j - 1] + nums[i] < 0) {
                    continue;
                }
                dp[j] = Math.max(dp[j], dp[j - 1] + nums[i]);
            }
        }
        for (int i = n; i >= 0; i--) {
            if (dp[i] >= 0) {
                sc.println(i);
                return;
            }
        }
    }
```

```java
// 反悔贪心 O(nlogn)
	private static void solve() throws IOException {
        n = sc.nextInt();
        int[] nums = new int[n];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> a - b);
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            pq.offer(nums[i]);
            while (sum < 0) {
                sum -= pq.poll();
            }
        }
        sc.println(pq.size());
    }
```

[E. Tetrahedron ](https://codeforces.com/problemset/problem/166/E)

> - 一只蚂蚁站在一个四面体的某个顶点上，求走过 n 条棱后回到原顶点的方案总数。
> - 答案对 109+7 取模。
> - Data Range:1≤n≤107。
>
> 
>
> 题解：
>
> 设计状态，fi表示走了i步目前在顶端的方案数，gi表示走了i步目前不在顶端的方案数
>
> 方程fi=3gi−1,gi=fi−1+2gi−1
>
> 发现还可以滚动掉，爽啊

```java
	private static void solve() throws IOException {
        n = sc.nextInt();
        long f = 0, g = 1;
        for (int i = 2; i <= n; i++) {
            long tmp = g * 3;
            g = (f + g * 2) % Mod;
            f = tmp % Mod;
        }
        sc.println(f);
    } // 还可以快速幂优化
```

[C. Mortal Kombat Tower ](https://codeforces.com/problemset/problem/1418/C)

给定 n 个数，你的朋友和你轮流操作，你的朋友先手，从头一次取出 1 个或 2 个数，求你的朋友取出 1 的数量的最小值。 

```java
	// dp  
	private static void solve() throws IOException {
        // dp[i][0] = min(dp[i - 1][1], dp[i - 2][1]) 我
        // dp[i][1] = min(dp[i - 1][0] + w[i], dp[i - 2][0] + w[i - 1] + w[i]) 朋友
        n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }
        if (n == 1) {
            sc.println(nums[0]);
            return;
        }
        dp[0][1] = nums[0];
        dp[1][1] = nums[0] + nums[1];
        dp[1][0] = dp[0][1];
        for (int i = 2; i < n; i++) {
            dp[i][0] = min(dp[i - 1][1], dp[i - 2][1]);
            dp[i][1] = min(dp[i - 1][0] + nums[i], dp[i - 2][0] + nums[i - 1] + nums[i]);
        }
        sc.println(min(dp[n - 1][0], dp[n - 1][1]));
    }
```



