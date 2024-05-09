3138\. 同位字符串连接的最小长度
-------------------

给你一个字符串 `s` ，它由某个字符串 `t` 和若干 `t`  的 **同位字符串** 连接而成。

请你返回字符串 `t` 的 **最小** 可能长度。

**同位字符串** 指的是重新排列一个单词得到的另外一个字符串，原来字符串中的每个字符在新字符串中都恰好只使用一次。

**示例 1：**

**输入：**s = "abba"

**输出：**2

**解释：**

一个可能的字符串 `t` 为 `"ba"` 。

**示例 2：**

**输入：**s = "cdef"

**输出：**4

**解释：**

一个可能的字符串 `t` 为 `"cdef"` ，注意 `t` 可能等于 `s` 。

**提示：**

*   `1 <= s.length <= 105`
*   `s` 只包含小写英文字母。

[https://leetcode.cn/problems/minimum-length-of-anagram-concatenation/description/](https://leetcode.cn/problems/minimum-length-of-anagram-concatenation/description/)
```java
import java.util.Arrays;

class Solution {
    public int minAnagramLength(String s) { // 数学+字符串
        int n = s.length();
        next:
        for (int k = 1; k <= n / 2; k++) { // k是字符串的长度
            if (n % k != 0) {
                continue;
            }
            int[] cnt0 = new int[26];
            for (int i = 0; i < k; i++) {
                cnt0[s.charAt(i) - 'a']++;
            }
            for (int i = k * 2; i <= n; i += k) {
                int[] cnt = new int[26];
                for (int j = i - k; j < i; j++) {
                    cnt[s.charAt(j) - 'a']++;
                }
                if (!Arrays.equals(cnt0, cnt)) {
                    continue next;
                }
            }
            return k;
        }
        return n;
    }
}
```