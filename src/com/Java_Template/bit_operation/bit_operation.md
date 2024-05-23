100282\. 数组最后一个元素的最小值
---------------------

给你两个整数 `n` 和 `x` 。你需要构造一个长度为 `n` 的 **正整数** 数组 `nums` ，对于所有 `0 <= i < n - 1` ，满足 `nums[i + 1]` **大于** `nums[i]` ，并且数组 `nums` 中所有元素的按位 `AND` 运算结果为 `x` 。

返回 `nums[n - 1]` 可能的 **最小** 值。

**示例 1：**

**输入：**n = 3, x = 4

**输出：**6

**解释：**

数组 `nums` 可以是 `[4,5,6]` ，最后一个元素为 `6` 。

**示例 2：**

**输入：**n = 2, x = 7

**输出：**15

**解释：**

数组 `nums` 可以是 `[7,15]` ，最后一个元素为 `15` 。

**提示：**

*   `1 <= n, x <= 108`

[https://leetcode.cn/problems/minimum-array-end/](https://leetcode.cn/problems/minimum-array-end/)

```java
import java.util.ArrayList;

class Solution {
    public long minEnd(int n, int x) {
        // 这里其实就是求x的二进制中，我们可以将其中的0变成1，但是不能将1变成0，求比x大的第n - 1个数
        n--;
        long ans = x;
        int i = 0, j = 0;
        while ((n >> j) > 0) {
            // x的第i个比特位是0
            if (((ans >> i) & 1) == 0) {
                // 空位填入n的第j个比特位
                ans |= (long) (n >> j & 1) << i;
                j++;
            }
            i++;
        }
        return ans;
    }
}
```
2588\. 统计美丽子数组数目
----------------

给你一个下标从 **0** 开始的整数数组`nums` 。每次操作中，你可以：

*   选择两个满足 `0 <= i, j < nums.length` 的不同下标 `i` 和 `j` 。
*   选择一个非负整数 `k` ，满足 `nums[i]` 和 `nums[j]` 在二进制下的第 `k` 位（下标编号从 **0** 开始）是 `1` 。
*   将 `nums[i]` 和 `nums[j]` 都减去 `2k` 。

如果一个子数组内执行上述操作若干次后，该子数组可以变成一个全为 `0` 的数组，那么我们称它是一个 **美丽** 的子数组。

请你返回数组 `nums` 中 **美丽子数组** 的数目。

子数组是一个数组中一段连续 **非空** 的元素序列。

**示例 1：**

**输入：**nums = \[4,3,1,2,4\]
**输出：**2
**解释：**nums 中有 2 个美丽子数组：\[4,_**3,1,2**_,4\] 和 \[_**4,3,1,2,4**_\] 。
- 按照下述步骤，我们可以将子数组 \[3,1,2\] 中所有元素变成 0 ：
  - 选择 \[_**3**_, 1, _**2**_\] 和 k = 1 。将 2 个数字都减去 21 ，子数组变成 \[1, 1, 0\] 。
  - 选择 \[_**1**_, _**1**_, 0\] 和 k = 0 。将 2 个数字都减去 20 ，子数组变成 \[0, 0, 0\] 。
- 按照下述步骤，我们可以将子数组 \[4,3,1,2,4\] 中所有元素变成 0 ：
  - 选择 \[_**4**_, 3, 1, 2, _**4**_\] 和 k = 2 。将 2 个数字都减去 22 ，子数组变成 \[0, 3, 1, 2, 0\] 。
  - 选择 \[0, _**3**_, _**1**_, 2, 0\] 和 k = 0 。将 2 个数字都减去 20 ，子数组变成 \[0, 2, 0, 2, 0\] 。
  - 选择 \[0, _**2**_, 0, _**2**_, 0\] 和 k = 1 。将 2 个数字都减去 21 ，子数组变成 \[0, 0, 0, 0, 0\] 。

**示例 2：**

**输入：**nums = \[1,10,4\]
**输出：**0
**解释：**nums 中没有任何美丽子数组。

**提示：**

*   `1 <= nums.length <= 105`
*   `0 <= nums[i] <= 106`

[https://leetcode.cn/problems/count-the-number-of-beautiful-subarrays/](https://leetcode.cn/problems/count-the-number-of-beautiful-subarrays/)

```java
import java.util.HashMap;

class Solution {
    public long beautifulSubarrays(int[] nums) {
        long ans = 0;
        int n = nums.length;
        int[] prefixSum = new int[n + 1]; // 异或前缀和
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] ^ nums[i];
        }
        // 求异或前缀和相同的两个值，使用hash表优化
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : prefixSum) {
            ans += cnt.getOrDefault(x, 0);
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }
}
```

1177\. 构建回文串检测
--------------

给你一个字符串 `s`，请你对 `s` 的子串进行检测。

每次检测，待检子串都可以表示为 `queries[i] = [left, right, k]`。我们可以 **重新排列** 子串 `s[left], ..., s[right]`，并从中选择 **最多** `k` 项替换成任何小写英文字母。 

如果在上述检测过程中，子串可以变成回文形式的字符串，那么检测结果为 `true`，否则结果为 `false`。

返回答案数组 `answer[]`，其中 `answer[i]` 是第 `i` 个待检子串 `queries[i]` 的检测结果。

注意：在替换时，子串中的每个字母都必须作为 **独立的** 项进行计数，也就是说，如果 `s[left..right] = "aaa"` 且 `k = 2`，我们只能替换其中的两个字母。（另外，任何检测都不会修改原始字符串 `s`，可以认为每次检测都是独立的）

**示例：**

**输入：**s = "abcda", queries = \[\[3,3,0\],\[1,2,0\],\[0,3,1\],\[0,3,2\],\[0,4,1\]\]
**输出：**\[true,false,false,true,true\]
**解释：**
queries\[0\] : 子串 = "d"，回文。
queries\[1\] : 子串 = "bc"，不是回文。
queries\[2\] : 子串 = "abcd"，只替换 1 个字符是变不成回文串的。
queries\[3\] : 子串 = "abcd"，可以变成回文的 "abba"。 也可以变成 "baab"，先重新排序变成 "bacd"，然后把 "cd" 替换为 "ab"。
queries\[4\] : 子串 = "abcda"，可以变成回文的 "abcba"。

**提示：**

*   `1 <= s.length, queries.length <= 10^5`
*   `0 <= queries[i][0] <= queries[i][1] < s.length`
*   `0 <= queries[i][2] <= s.length`
*   `s` 中只有小写英文字母

[https://leetcode.cn/problems/can-make-palindrome-from-substring/solutions/2309725/yi-bu-bu-you-hua-cong-qian-zhui-he-dao-q-yh5p/](https://leetcode.cn/problems/can-make-palindrome-from-substring/solutions/2309725/yi-bu-bu-you-hua-cong-qian-zhui-he-dao-q-yh5p/)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        int[][] prefixSum = new int[n + 1][26];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i].clone();
            prefixSum[i + 1][s.charAt(i) - 'a']++;
        }
        ArrayList<Boolean> ans = new ArrayList<>(queries.length); // 预分配空间
        for (int[] q : queries) {
            int left = q[0], right = q[1], k = q[2], m = 0; // m是区间奇数字母出现次数
            for (int i = 0; i < 26; i++) {
                m += (prefixSum[right + 1][i] - prefixSum[left][i]) % 2;
            }
            ans.add(m / 2 <= k);
        }
        return ans;
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

class Solution { // 封神了！！！
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int bit = 1 << (s.charAt(i) - 'a');
            prefixSum[i + 1] = prefixSum[i] ^ bit; // 该比特对应字母的奇偶性：奇数变偶数，偶数变奇数
        }
        ArrayList<Boolean> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            int left = q[0], right = q[1], k = q[2];
            int m = Integer.bitCount(prefixSum[right + 1] ^ prefixSum[left]);
            ans.add(m / 2 <= k);
        }
        return ans;
    }
}
```

1542\. 找出最长的超赞子字符串
------------------

给你一个字符串 `s` 。请返回 `s` 中最长的 **超赞子字符串** 的长度。

「超赞子字符串」需满足满足下述两个条件：

*   该字符串是 `s` 的一个非空子字符串
*   进行任意次数的字符交换后，该字符串可以变成一个回文字符串

**示例 1：**

**输入：**s = "3242415"
**输出：**5
**解释：**"24241" 是最长的超赞子字符串，交换其中的字符后，可以得到回文 "24142"

**示例 2：**

**输入：**s = "12345678"
**输出：**1

**示例 3：**

**输入：**s = "213123"
**输出：**6
**解释：**"213123" 是最长的超赞子字符串，交换其中的字符后，可以得到回文 "231132"

**示例 4：**

**输入：**s = "00"
**输出：**2

**提示：**

*   `1 <= s.length <= 10^5`
*   `s` 仅由数字组成

[https://leetcode.cn/problems/find-longest-awesome-substring/description/?envType=daily-question&envId=2024-05-20](https://leetcode.cn/problems/find-longest-awesome-substring/description/?envType=daily-question&envId=2024-05-20)

```java
import java.util.HashMap;

class Solution {
    public int longestAwesome(String s) {
        int n = s.length();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int ans = 1, cur = 0;
        for (int i = 0; i < n; i++) {
            int x = s.charAt(i) - '0';
            cur = (1 << x) ^ cur;
            if (map.containsKey(cur)) {
                ans = Math.max(ans, i - map.get(cur)); // 说明构成的回文串是偶数长度
            }else{
                map.put(cur, i);
            }
            // 这里讨论奇数回文串的情况
            for (int j = 0; j < 10; j++) {
                int find = (1 << j) ^ cur; // 当第j位为奇数时
                if (map.containsKey(find)) {
                    ans = Math.max(ans, i - map.get(find));
                }
            }
        }
        return ans;
    }
}
```

# 与或（AND/OR）的性质

> AND 的数越多，结果越小。OR 的数越多，结果越大。 移除元素可以num ^= x;

2871\. 将数组分割成最多数目的子数组
---------------------

给你一个只包含 **非负** 整数的数组 `nums` 。

我们定义满足 `l <= r` 的子数组 `nums[l..r]` 的分数为 `nums[l] AND nums[l + 1] AND ... AND nums[r]` ，其中 **AND** 是按位与运算。

请你将数组分割成一个或者更多子数组，满足：

*   **每个** 元素都 **只** 属于一个子数组。
*   子数组分数之和尽可能 **小** 。

请你在满足以上要求的条件下，返回 **最多** 可以得到多少个子数组。

一个 **子数组** 是一个数组中一段连续的元素。

**示例 1：**

**输入：**nums = \[1,0,2,0,1,2\]
**输出：**3
**解释：**我们可以将数组分割成以下子数组：
- \[1,0\] 。子数组分数为 1 AND 0 = 0 。
- \[2,0\] 。子数组分数为 2 AND 0 = 0 。
- \[1,2\] 。子数组分数为 1 AND 2 = 0 。
  分数之和为 0 + 0 + 0 = 0 ，是我们可以得到的最小分数之和。
  在分数之和为 0 的前提下，最多可以将数组分割成 3 个子数组。所以返回 3 。

**示例 2：**

**输入：**nums = \[5,7,1,3\]
**输出：**1
**解释：**我们可以将数组分割成一个子数组：\[5,7,1,3\] ，分数为 1 ，这是可以得到的最小总分数。
在总分数为 1 的前提下，最多可以将数组分割成 1 个子数组。所以返回 1 。

**提示：**

*   `1 <= nums.length <= 105`
*   `0 <= nums[i] <= 106`

[https://leetcode.cn/problems/split-array-into-maximum-number-of-subarrays/description/](https://leetcode.cn/problems/split-array-into-maximum-number-of-subarrays/description/)

```java
class Solution {
    public int maxSubarrays(int[] nums) {
        int ans = 0;
        int a = -1; // -1 就是 111...1，和任何数 AND 都等于那个数
        for (int x : nums) {
            a &= x;
            if (a == 0) {
                ans++; // 分割
                a = -1;
            }
        }
        return Math.max(ans, 1); // 如果 ans=0 说明所有数的 and>0，答案为 1
    }
}
```

2401\. 最长优雅子数组
--------------

给你一个由 **正** 整数组成的数组 `nums` 。

如果 `nums` 的子数组中位于 **不同** 位置的每对元素按位 **与（AND）**运算的结果等于 `0` ，则称该子数组为 **优雅** 子数组。

返回 **最长** 的优雅子数组的长度。

**子数组** 是数组中的一个 **连续** 部分。

**注意：**长度为 `1` 的子数组始终视作优雅子数组。

**示例 1：**

**输入：**nums = \[1,3,8,48,10\]
**输出：**3
**解释：**最长的优雅子数组是 \[3,8,48\] 。子数组满足题目条件：
- 3 AND 8 = 0
- 3 AND 48 = 0
- 8 AND 48 = 0
  可以证明不存在更长的优雅子数组，所以返回 3 。

**示例 2：**

**输入：**nums = \[3,1,5,11,13\]
**输出：**1
**解释：**最长的优雅子数组长度为 1 ，任何长度为 1 的子数组都满足题目条件。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 109`

[https://leetcode.cn/problems/longest-nice-subarray/description/](https://leetcode.cn/problems/longest-nice-subarray/description/)

```java
class Solution {
        // 2401. 最长优雅子数组
    public int longestNiceSubarray(int[] nums) {
        // 方法二 滑动窗口
        int ans = 0;
        for (int left = 0, right = 0, or = 0; right < nums.length; right++) {
            while ((or & nums[right]) > 0) { // 有交集
                or ^= nums[left++]; // 从 or 中去掉集合 nums[left]
            }
            or |= nums[right];
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
```

3097\. 或值至少为 K 的最短子数组 II(or模板)
------------------------

给你一个 **非负** 整数数组 `nums` 和一个整数 `k` 。

如果一个数组中所有元素的按位或运算 `OR` 的值 **至少** 为 `k` ，那么我们称这个数组是 **特别的** 。

请你返回 `nums` 中 **最短特别非空** 

子数组

的长度，如果特别子数组不存在，那么返回 `-1` 。

**示例 1：**

**输入：**nums = \[1,2,3\], k = 2

**输出：**1

**解释：**

子数组 `[3]` 的按位 `OR` 值为 `3` ，所以我们返回 `1` 。

**示例 2：**

**输入：**nums = \[2,1,8\], k = 10

**输出：**3

**解释：**

子数组 `[2,1,8]` 的按位 `OR` 值为 `11` ，所以我们返回 `3` 。

**示例 3：**

**输入：**nums = \[1,2\], k = 0

**输出：**1

**解释：**

子数组 `[1]` 的按位 `OR` 值为 `1` ，所以我们返回 `1` 。

**提示：**

*   `1 <= nums.length <= 2 * 105`
*   `0 <= nums[i] <= 109`
*   `0 <= k <= 109`

[https://leetcode.cn/problems/shortest-subarray-with-or-at-least-k-ii/description/](https://leetcode.cn/problems/shortest-subarray-with-or-at-least-k-ii/description/)

```java
class Solution {
    public int minimumSubarrayLength(int[] nums, int k) {
        int ans = Integer.MAX_VALUE;
        List<int[]> ors = new ArrayList<>(); // 保存 (右端点为 i 的子数组 OR, 该子数组左端点的最大值)
        for (int i = 0; i < nums.length; i++) {
            ors.add(new int[]{0, i});
            int j = 0;
            for (int[] or : ors) {
                or[0] |= nums[i];
                if (or[0] >= k) {
                    ans = Math.min(ans, i - or[1] + 1);
                }
                if (ors.get(j)[0] == or[0]) {
                    ors.get(j)[1] = or[1]; // 原地去重：合并相同值，左端点取靠右的
                } else {
                    ors.set(++j, or);
                }
            }
            ors.subList(j + 1, ors.size()).clear(); // 去重：移除多余元素
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
```

2680\. 最大或值
-----------

给你一个下标从 **0** 开始长度为 `n` 的整数数组 `nums` 和一个整数 `k` 。每一次操作中，你可以选择一个数并将它乘 `2` 。

你最多可以进行 `k` 次操作，请你返回 `nums[0] | nums[1] | ... | nums[n - 1]` 的最大值。

`a | b` 表示两个整数 `a` 和 `b` 的 **按位或** 运算。

**示例 1：**

**输入：**nums = \[12,9\], k = 1
**输出：**30
**解释：**如果我们对下标为 1 的元素进行操作，新的数组为 \[12,18\] 。此时得到最优答案为 12 和 18 的按位或运算的结果，也就是 30 。

**示例 2：**

**输入：**nums = \[8,1,2\], k = 2
**输出：**35
**解释：**如果我们对下标 0 处的元素进行操作，得到新数组 \[32,1,2\] 。此时得到最优答案为 32|1|2 = 35 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 109`
*   `1 <= k <= 15`

[https://leetcode.cn/problems/maximum-or/description/](https://leetcode.cn/problems/maximum-or/description/)

```java
class Solution {
       // 2680. 最大或值
    public long maximumOr(int[] nums, int k) {
        int n = nums.length;
        long[] suffix = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] | nums[i];
        }
        long ans = 0, pre = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, pre | ((long) nums[i] << k) | suffix[i + 1]);
            pre |= nums[i];
        }
        return ans;
    }
}
```

2411\. 按位或最大的最小子数组长度
--------------------

给你一个长度为 `n` 下标从 **0** 开始的数组 `nums` ，数组中所有数字均为非负整数。对于 `0` 到 `n - 1` 之间的每一个下标 `i` ，你需要找出 `nums` 中一个 **最小** 非空子数组，它的起始位置为 `i` （包含这个位置），同时有 **最大** 的 **按位或****运算值** 。

*   换言之，令 `Bij` 表示子数组 `nums[i...j]` 的按位或运算的结果，你需要找到一个起始位置为 `i` 的最小子数组，这个子数组的按位或运算的结果等于 `max(Bik)` ，其中 `i <= k <= n - 1` 。

一个数组的按位或运算值是这个数组里所有数字按位或运算的结果。

请你返回一个大小为 `n` 的整数数组 `answer`，其中 `answer[i]`是开始位置为 `i` ，按位或运算结果最大，且 **最短** 子数组的长度。

**子数组** 是数组里一段连续非空元素组成的序列。

**示例 1：**

**输入：**nums = \[1,0,2,1,3\]
**输出：**\[3,3,2,2,1\]
**解释：**
任何位置开始，最大按位或运算的结果都是 3 。
- 下标 0 处，能得到结果 3 的最短子数组是 \[1,0,2\] 。
- 下标 1 处，能得到结果 3 的最短子数组是 \[0,2,1\] 。
- 下标 2 处，能得到结果 3 的最短子数组是 \[2,1\] 。
- 下标 3 处，能得到结果 3 的最短子数组是 \[1,3\] 。
- 下标 4 处，能得到结果 3 的最短子数组是 \[3\] 。
  所以我们返回 \[3,3,2,2,1\] 。

**示例 2：**

**输入：**nums = \[1,2\]
**输出：**\[2,1\]
**解释：**
下标 0 处，能得到最大按位或运算值的最短子数组长度为 2 。
下标 1 处，能得到最大按位或运算值的最短子数组长度为 1 。
所以我们返回 \[2,1\] 。

**提示：**

*   `n == nums.length`
*   `1 <= n <= 105`
*   `0 <= nums[i] <= 109`

[https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/description/](https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/description/)

```java
class Solution {
       public int[] smallestSubarrays(int[] nums) {
        List<int[]> ors = new ArrayList<>();
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            ors.add(new int[]{0, i});
            int k = 0;
            for (int[] or : ors) {
                or[0] |= nums[i];
                // 值不变，选取更小的下标
                if (or[0] == ors.get(k)[0]) {
                    ors.get(k)[1] = or[1];
                } else {    // 值发生改变，更新
                    ors.set(++k, or);
                }
            }
            ors = ors.subList(0, k + 1);
            ans[i] = ors.get(0)[1];
        }
        return ans;
    }
}

```

3108\. 带权图里旅途的最小代价
------------------

给你一个 `n` 个节点的带权无向图，节点编号为 `0` 到 `n - 1` 。

给你一个整数 `n` 和一个数组 `edges` ，其中 `edges[i] = [ui, vi, wi]` 表示节点 `ui` 和 `vi` 之间有一条权值为 `wi` 的无向边。

在图中，一趟旅途包含一系列节点和边。旅途开始和结束点都是图中的节点，且图中存在连接旅途中相邻节点的边。注意，一趟旅途可能访问同一条边或者同一个节点多次。

如果旅途开始于节点 `u` ，结束于节点 `v` ，我们定义这一趟旅途的 **代价** 是经过的边权按位与 `AND` 的结果。换句话说，如果经过的边对应的边权为 `w0, w1, w2, ..., wk` ，那么代价为`w0 & w1 & w2 & ... & wk` ，其中 `&` 表示按位与 `AND` 操作。

给你一个二维数组 `query` ，其中 `query[i] = [si, ti]` 。对于每一个查询，你需要找出从节点开始 `si` ，在节点 `ti` 处结束的旅途的最小代价。如果不存在这样的旅途，答案为 `-1` 。

返回数组 `answer` ，其中 `answer[i]` 表示对于查询 `i` 的 **最小** 旅途代价。

**示例 1：**

**输入：**n = 5, edges = \[\[0,1,7\],\[1,3,7\],\[1,2,1\]\], query = \[\[0,3\],\[3,4\]\]

**输出：**\[1,-1\]

**解释：**

![](https://assets.leetcode.com/uploads/2024/01/31/q4_example1-1.png)

第一个查询想要得到代价为 1 的旅途，我们依次访问：`0->1`（边权为 7 ）`1->2` （边权为 1 ）`2->1`（边权为 1 ）`1->3` （边权为 7 ）。

第二个查询中，无法从节点 3 到节点 4 ，所以答案为 -1 。

**示例 2：**

**输入：**n = 3, edges = \[\[0,2,7\],\[0,1,15\],\[1,2,6\],\[1,2,1\]\], query = \[\[1,2\]\]

**输出：**\[0\]

**解释：**

![](https://assets.leetcode.com/uploads/2024/01/31/q4_example2e.png)

第一个查询想要得到代价为 0 的旅途，我们依次访问：`1->2`（边权为 1 ），`2->1`（边权 为 6 ），`1->2`（边权为 1 ）。

**提示：**

*   `1 <= n <= 105`
*   `0 <= edges.length <= 105`
*   `edges[i].length == 3`
*   `0 <= ui, vi <= n - 1`
*   `ui != vi`
*   `0 <= wi <= 105`
*   `1 <= query.length <= 105`
*   `query[i].length == 2`
*   `0 <= si, ti <= n - 1`

[https://leetcode.cn/problems/minimum-cost-walk-in-weighted-graph/description/](https://leetcode.cn/problems/minimum-cost-walk-in-weighted-graph/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        List[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        int[] ids = new int[n]; // 记录每个点所在连通块的编号
        Arrays.fill(ids, -1);
        ArrayList<Integer> ccAnd = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (ids[i] < 0) {
                ccAnd.add(dfs(ids, ccAnd.size(), g, i));
            }
        }
        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int from = query[i][0], to = query[i][1];
            ans[i] = from == to ? 0 : ids[from] == ids[to] ? ccAnd.get(ids[from]) : -1;
        }
        return ans;

    }

    private int dfs(int[] ids, int curId, List<int[]>[] g, int x) {
        ids[x] = curId;
        int and = -1;
        for (int[] y : g[x]) {
            and &= y[1];
            if (ids[y[0]] < 0) { // 没有被访问
                and &= dfs(ids, curId, g, y[0]);
            }
        }
        return and;
    }
}
```

898\. 子数组按位或操作
--------------

我们有一个非负整数数组 `arr` 。

对于每个（连续的）子数组 `sub = [arr[i], arr[i + 1], ..., arr[j]]` （ `i <= j`），我们对 `sub` 中的每个元素进行按位或操作，获得结果 `arr[i] | arr[i + 1] | ... | arr[j]` 。

返回可能结果的数量。 多次出现的结果在最终答案中仅计算一次。

**示例 1：**

**输入：**arr = \[0\]
**输出：**1
**解释：**
只有一个可能的结果 0 。

**示例 2：**

**输入：**arr = \[1,1,2\]
**输出：**3
**解释：**
可能的子数组为 \[1\]，\[1\]，\[2\]，\[1, 1\]，\[1, 2\]，\[1, 1, 2\]。
产生的结果为 1，1，2，1，3，3 。
有三个唯一值，所以答案是 3 。

**示例 3：**

**输入：**arr = \[1,2,4\]
**输出：**6
**解释：**
可能的结果是 1，2，3，4，6，以及 7 。

**提示：**

*   `1 <= nums.length <= 5 * 104`
*   `0 <= nums[i] <= 109`​​​​​​​

[https://leetcode.cn/problems/bitwise-ors-of-subarrays/description/](https://leetcode.cn/problems/bitwise-ors-of-subarrays/description/)

```java
import java.util.ArrayList;
import java.util.HashSet;

class Solution {
    public int subarrayBitwiseORs(int[] nums) {
        int n = nums.length;
        ArrayList<int[]> ors = new ArrayList<int[]>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = n - 1; i >= 0; i--) {
            ors.add(new int[]{0, i});
            int k = 0;
            for (int[] or : ors) {
                or[0] |= nums[i];
                if (ors.get(k)[0] == or[0]) {
                    ors.get(k)[1] = or[1];
                }else{
                    ors.set(++k, or);
                }
                set.add(or[0]);
            }
            ors.subList(k + 1, ors.size()).clear();
        }
        return set.size();
    }
}
```

1521\. 找到最接近目标值的函数值
-------------------

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/07/19/change.png)

Winston 构造了一个如上所示的函数 `func` 。他有一个整数数组 `arr` 和一个整数 `target` ，他想找到让 `|func(arr, l, r) - target|` 最小的 `l` 和 `r` 。

请你返回 `|func(arr, l, r) - target|` 的最小值。

请注意， `func` 的输入参数 `l` 和 `r` 需要满足 `0 <= l, r < arr.length` 。

**示例 1：**

**输入：**arr = \[9,12,3,7,15\], target = 5
**输出：**2
**解释：**所有可能的 \[l,r\] 数对包括 \[\[0,0\],\[1,1\],\[2,2\],\[3,3\],\[4,4\],\[0,1\],\[1,2\],\[2,3\],\[3,4\],\[0,2\],\[1,3\],\[2,4\],\[0,3\],\[1,4\],\[0,4\]\]， Winston 得到的相应结果为 \[9,12,3,7,15,8,0,3,7,0,0,3,0,0,0\] 。最接近 5 的值是 7 和 3，所以最小差值为 2 。

**示例 2：**

**输入：**arr = \[1000000,1000000,1000000\], target = 1
**输出：**999999
**解释：**Winston 输入函数的所有可能 \[l,r\] 数对得到的函数值都为 1000000 ，所以最小差值为 999999 。

**示例 3：**

**输入：**arr = \[1,2,4,8,16\], target = 0
**输出：**0

**提示：**

*   `1 <= arr.length <= 10^5`
*   `1 <= arr[i] <= 10^6`
*   `0 <= target <= 10^7`

[https://leetcode.cn/problems/find-a-value-of-a-mysterious-function-closest-to-target/description/](https://leetcode.cn/problems/find-a-value-of-a-mysterious-function-closest-to-target/description/)

```java
import java.util.ArrayList;

class Solution {
    public int closestToTarget(int[] nums, int target) {
        int n = nums.length;
        ArrayList<int[]> ands = new ArrayList<>();
        int ans = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            ands.add(new int[]{nums[i], i});
            int k = 0;
            for (int[] and : ands) {
                and[0] &= nums[i];
                if (and[0] == ands.get(k)[0]) {
                    ands.get(k)[1] = and[1];
                }else{
                    ands.set(++k, and);
                }
                ans = Math.min(ans, Math.abs(and[0] - target));
            }
            ands.subList(k + 1, ands.size()).clear();
        }
        return ans;
    }
}
```

3117\. 划分数组得到最小的值之和
-------------------

给你两个数组 `nums` 和 `andValues`，长度分别为 `n` 和 `m`。

数组的 **值** 等于该数组的 **最后一个** 元素。

你需要将 `nums` 划分为 `m` 个 **不相交的连续** 子数组，对于第 `ith` 个子数组 `[li, ri]`，子数组元素的按位`AND`运算结果等于 `andValues[i]`，换句话说，对所有的 `1 <= i <= m`，`nums[li] & nums[li + 1] & ... & nums[ri] == andValues[i]` ，其中 `&` 表示按位`AND`运算符。

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

[https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/description/](https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/description/)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
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

# 异或（XOR）的性质

2429\. 最小异或
-----------

给你两个正整数 `num1` 和 `num2` ，找出满足下述条件的正整数 `x` ：

*   `x` 的置位数和 `num2` 相同，且
*   `x XOR num1` 的值 **最小**

注意 `XOR` 是按位异或运算。

返回整数 `x` 。题目保证，对于生成的测试用例， `x` 是 **唯一确定** 的。

整数的 **置位数** 是其二进制表示中 `1` 的数目。

**示例 1：**

**输入：**num1 = 3, num2 = 5
**输出：**3
**解释：**
num1 和 num2 的二进制表示分别是 0011 和 0101 。
整数 **3** 的置位数与 num2 相同，且 `3 XOR 3 = 0` 是最小的。

**示例 2：**

**输入：**num1 = 1, num2 = 12
**输出：**3
**解释：**
num1 和 num2 的二进制表示分别是 0001 和 1100 。
整数 **3** 的置位数与 num2 相同，且 `3 XOR 1 = 2` 是最小的。

**提示：**

*   `1 <= num1, num2 <= 109`

[https://leetcode.cn/problems/minimize-xor/description/](https://leetcode.cn/problems/minimize-xor/description/)

```java
class Solution {
    public int minimizeXor(int num1, int num2) {
        int c1 = Integer.bitCount(num1);
        int c2 = Integer.bitCount(num2);
        for (; c2 < c1; ++c2) num1 &= num1 - 1; // 最低的 1 变成 0
        for (; c2 > c1; --c2) num1 |= num1 + 1; // 最低的 0 变成 1
        return num1;
    }
}
```

