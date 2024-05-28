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