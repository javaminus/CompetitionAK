1960\. 两个回文子字符串长度的最大乘积
----------------------

给你一个下标从 **0** 开始的字符串 `s` ，你需要找到两个 **不重叠****的回文** 子字符串，它们的长度都必须为 **奇数** ，使得它们长度的乘积最大。

更正式地，你想要选择四个整数 `i` ，`j` ，`k` ，`l` ，使得 `0 <= i <= j < k <= l < s.length` ，且子字符串 `s[i...j]` 和 `s[k...l]` 都是回文串且长度为奇数。`s[i...j]` 表示下标从 `i` 到 `j` 且 **包含** 两端下标的子字符串。

请你返回两个不重叠回文子字符串长度的 **最大** 乘积。

**回文字符串** 指的是一个从前往后读和从后往前读一模一样的字符串。**子字符串** 指的是一个字符串中一段连续字符。

**示例 1：**

**输入：**s = "ababbb"
**输出：**9
**解释：**子字符串 "aba" 和 "bbb" 为奇数长度的回文串。乘积为 3 \* 3 = 9 。

**示例 2：**

**输入：**s = "zaaaxbbby"
**输出：**9
**解释：**子字符串 "aaa" 和 "bbb" 为奇数长度的回文串。乘积为 3 \* 3 = 9 。

**提示：**

*   `2 <= s.length <= 105`
*   `s` 只包含小写英文字母。

[https://leetcode.cn/problems/maximum-product-of-the-length-of-two-palindromic-substrings/solutions/928047/go-ma-la-che-suan-fa-fu-xiang-xi-zhu-shi-98xx/](https://leetcode.cn/problems/maximum-product-of-the-length-of-two-palindromic-substrings/solutions/928047/go-ma-la-che-suan-fa-fu-xiang-xi-zhu-shi-98xx/)

```java
import java.util.Arrays;

class Solution {
    public long maxProduct(String s) {
        int n = s.length();
        char[] t = new char[n * 2 + 3];
        Arrays.fill(t, '#');
        t[0] = '^';
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = s.charAt(i);
        }
        t[n * 2 + 2] = '$';
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;
        int boxM = 0, boxR = 0, maxI = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = 1;
            if (i < boxR) {
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }
            halfLen[i] = hl;
            if (hl > halfLen[maxI]) {
                maxI = i;
            }
        }

        int[] startPL = new int[n]; // 表示以 s[i] 为首字母的最长奇回文子串的长度
        int[] endPL = new int[n]; // 表示以 s[i] 为尾字母的最长奇回文子串的长度
        for (int i = 2; i < t.length - 2; i += 2) {
            int hl = halfLen[i];
            int left = (i - hl) / 2;
            int right = (i + hl) / 2 - 2;
            startPL[left] = Math.max(startPL[left], hl - 1);
            endPL[right] = Math.max(endPL[right], hl - 1);
        }

        for (int i = 1; i < n; i++) {
            startPL[i] = Math.max(startPL[i], startPL[i - 1] - 2);
        }
        for (int i = n - 2; i >= 0; i--) {
            startPL[i] = Math.max(startPL[i], startPL[i + 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            endPL[i] = Math.max(endPL[i], endPL[i + 1] - 2);
        }
        for (int i = 1; i < n; i++) {
            endPL[i] = Math.max(endPL[i], endPL[i - 1]);
        }

        long ans = 0;
        for (int i = 1; i < n; i++) {
            ans = Math.max(ans, (long) endPL[i - 1] * startPL[i]);
        }
        return ans;
    }
}
```

