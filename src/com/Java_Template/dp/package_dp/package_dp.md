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

3181. 执行操作可获得的最大总奖励 II（bitset/bigint 优化 0-1 背包 + 两数之和优化 ）
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

