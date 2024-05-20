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

