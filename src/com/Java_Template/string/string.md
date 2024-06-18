## KMP算法

```java
package com.Java_Template.string.template;

public class MyKmp {
    private static int[] getNext(String pattern) { // 求next数组
        int n = pattern.length();
        int[] next = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    private static int find(String s, String pattern, int[] next) { // 返回主串匹配模式串的第一个下标
        int n = s.length();
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && s.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1]; //下一个匹配位为next数组的第j-1位
            }
            if (s.charAt(i) == pattern.charAt(j)) {
                j++; //主串通过i进行加1，模式串通过j加1
            }
            if (j == pattern.length()) {
                return i - j + 1; //返回匹配位置
            }
        }
        return -1;
    }

    public static void printNext(String s){
        System.out.println("********************");
        int[] nextI = getNext(s);
        System.out.print("模式串：'"+s+"'的next[]数组为：[");
        for(int i = 0; i < (nextI.length); i++){
            System.out.print(nextI[i]+" ");
        }
        System.out.println("]");
        System.out.println("模式串长度为："+nextI.length);
    }

    public static void main(String[] args){
        String s = "CDFGFABABAFABABAAAQWEDC";
        String t = "ABABAA";
        int[] next = getNext(t);
        int res = find(s, t, next);
        if (res!=-1){
            System.out.println("起始位置为："+res);
        }
        else System.out.println("主串中不包含字符串："+t);
        printNext("ABCDABD");
        printNext("ABABAA");
        printNext("ABAABCAC");
    }

    /*起始位置为：11
    ********************
    模式串：'ABCDABD'的next[]数组为：[0 0 0 0 1 2 0 ]
    模式串长度为：7
    ********************
    模式串：'ABABAA'的next[]数组为：[0 0 1 2 3 1 ]
    模式串长度为：6
    ********************
    模式串：'ABAABCAC'的next[]数组为：[0 0 1 1 2 0 1 0 ]
    模式串长度为：8*/

}
```

## [马拉车](https://leetcode.cn/problems/longest-palindromic-substring/solutions/1304330/zui-chang-hui-wen-zi-chuan-by-qin-fen-de-u233/)

> 马拉车算法（Manacher's Algorithm）是一种高效的字符串处理算法，其核心作用是**在给定的字符串中寻找最长的回文子串并确定其长度**。该算法由一个叫Manacher的人在1975年发明，它的主要贡献在于将寻找最长回文子串的时间复杂度降低到了线性级别，即O(N)，其中N是字符串的长度。 

392\. 判断子序列
-----------

给定字符串 **s** 和 **t** ，判断 **s** 是否为 **t** 的子序列。

字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，`"ace"`是`"abcde"`的一个子序列，而`"aec"`不是）。

**进阶：**

如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？

**致谢：**

特别感谢 [@pbrother](https://leetcode.com/pbrother/) 添加此问题并且创建所有测试用例。

**示例 1：**

**输入：**s = "abc", t = "ahbgdc"
**输出：**true

**示例 2：**

**输入：**s = "axc", t = "ahbgdc"
**输出：**false

**提示：**

*   `0 <= s.length <= 100`
*   `0 <= t.length <= 10^4`
*   两个字符串都只由小写字符组成。

[https://leetcode.cn/problems/is-subsequence/description/](https://leetcode.cn/problems/is-subsequence/description/)

```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        // && 操作符的短路性质，当s.charAt(i) = c 时，i会加一
        // 也不用考虑i是否会越界
        // 坏处是需要特判，如果s不为空的话，用这个会简洁很多
        if (s.isEmpty()) {
            return true;
        }
        int i = 0;
        for(char c : t.toCharArray()){
            if(s.charAt(i) == c && ++i == s.length()){
                return true;
            }
        }
        return false;
    }
}
```

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
2982\. 找出出现至少三次的最长特殊子字符串 II
---------------------------

给你一个仅由小写英文字母组成的字符串 `s` 。

如果一个字符串仅由单一字符组成，那么它被称为 **特殊** 字符串。例如，字符串 `"abc"` 不是特殊字符串，而字符串 `"ddd"`、`"zz"` 和 `"f"` 是特殊字符串。

返回在 `s` 中出现 **至少三次** 的 **最长特殊子字符串** 的长度，如果不存在出现至少三次的特殊子字符串，则返回 `-1` 。

**子字符串** 是字符串中的一个连续 **非空** 字符序列。

**示例 1：**

**输入：**s = "aaaa"
**输出：**2
**解释：**出现三次的最长特殊子字符串是 "aa" ：子字符串 "_**aa**_aa"、"a_**aa**_a" 和 "aa_**aa**_"。
可以证明最大长度是 2 。

**示例 2：**

**输入：**s = "abcdef"
**输出：**\-1
**解释：**不存在出现至少三次的特殊子字符串。因此返回 -1 。

**示例 3：**

**输入：**s = "abcaba"
**输出：**1
**解释：**出现三次的最长特殊子字符串是 "a" ：子字符串 "_**a**_bcaba"、"abc_**a**_ba" 和 "abcab_**a**_"。
可以证明最大长度是 1 。

**提示：**

*   `3 <= s.length <= 5 * 105`
*   `s` 仅由小写英文字母组成。

[https://leetcode.cn/problems/find-longest-special-substring-that-occurs-thrice-ii/description/](https://leetcode.cn/problems/find-longest-special-substring-that-occurs-thrice-ii/description/)

```java
import java.util.*;

class Solution {
	public int maximumLength(String S) {
		PriorityQueue<Integer>[] pqList = new PriorityQueue[26];
		Arrays.setAll(pqList, e -> new PriorityQueue<Integer>((a, b) -> b - a));
		char[] s = S.toCharArray();
		int n = s.length;
		for (int i = 0; i < n; i++) {
			int i0 = i;
			while (i + 1 < n && s[i] == s[i + 1]) {
				i++;
			}
			pqList[s[i0] - 'a'].offer(i - i0 + 1);
		}
		int ans = 0;
		for (int i = 0; i < 26; i++) {
			PriorityQueue<Integer> pq = pqList[i];
			pq.offer(0);
			pq.offer(0);
			pq.offer(0);
			int a = pq.poll(), b = pq.poll(), c = pq.poll();
			ans = Math.max(ans, Math.max(c, Math.max(a - 2, Math.min(a - 1, b))));
		}
		return ans == 0 ? -1 : ans;
	}
}
```

2288\. 价格减免(保留两位小数)
-----------

**句子** 是由若干个单词组成的字符串，单词之间用单个空格分隔，其中每个单词可以包含数字、小写字母、和美元符号 `'$'` 。如果单词的形式为美元符号后跟着一个非负实数，那么这个单词就表示一个 **价格** 。

*   例如 `"$100"`、`"$23"` 和 `"$6"` 表示价格，而 `"100"`、`"$"` 和 `"$1e5` 不是。

给你一个字符串 `sentence` 表示一个句子和一个整数 `discount` 。对于每个表示价格的单词，都在价格的基础上减免 `discount%` ，并 **更新** 该单词到句子中。所有更新后的价格应该表示为一个 **恰好保留小数点后两位** 的数字。

返回表示修改后句子的字符串。

注意：所有价格 **最多** 为  `10` 位数字。

**示例 1：**

**输入：**sentence = "there are $1 $2 and 5$ candies in the shop", discount = 50
**输出：**"there are $0.50 $1.00 and 5$ candies in the shop"
**解释：**
表示价格的单词是 "$1" 和 "$2" 。 
- "$1" 减免 50% 为 "$0.50" ，所以 "$1" 替换为 "$0.50" 。
- "$2" 减免 50% 为 "$1" ，所以 "$1" 替换为 "$1.00" 。

**示例 2：**

**输入：**sentence = "1 2 $3 4 $5 $6 7 8$ $9 $10$", discount = 100
**输出：**"1 2 $0.00 4 $0.00 $0.00 7 8$ $0.00 $10$"
**解释：**
任何价格减免 100% 都会得到 0 。
表示价格的单词分别是 "$3"、"$5"、"$6" 和 "$9"。
每个单词都替换为 "$0.00"。

**提示：**

*   `1 <= sentence.length <= 105`
*   `sentence` 由小写英文字母、数字、`' '` 和 `'$'` 组成
*   `sentence` 不含前导和尾随空格
*   `sentence` 的所有单词都用单个空格分隔
*   所有价格都是 **正** 整数且不含前导零
*   所有价格 **最多** 为  `10` 位数字
*   `0 <= discount <= 100`

[https://leetcode.cn/problems/apply-discount-to-prices/description/?envType=daily-question&envId=2024-06-18](https://leetcode.cn/problems/apply-discount-to-prices/description/?envType=daily-question&envId=2024-06-18)

```java
public class Solution {
    public String discountPrices(String sentence, int discount) {
        double d = 1 - discount / 100.0;
        String[] a = sentence.split(" ");
        for (int i = 0; i < a.length; i++) {
            if (check(a[i])) {
                // 保留两位小数
                a[i] = String.format("$%.2f", Long.parseLong(a[i].substring(1)) * d);
            }
        }
        return String.join(" ", a); // 插入" "
    }

    private boolean check(String S) {
        if (S.length() == 1 || S.charAt(0) != '$') {
            return false;
        }
        char[] s = S.toCharArray();
        for (int i = 1; i < s.length; i++) {
            if (!Character.isDigit(s[i])) {
                return false;
            }
        }
        return true;
    }
}
```

