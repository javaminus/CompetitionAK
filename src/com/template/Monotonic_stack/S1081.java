package com.template.Monotonic_stack;

/**
 * @author Minus
 * @date 2023/11/7 21:27
 */
public class S1081 {
    // 贪心 + 单调栈 这是递增构建
    public String smallestSubsequence(String S) {
        char[] s = S.toCharArray();
        int[] left = new int[26];
        for (char c : s) {
            left[c - 'a']++; // 统计每个字符出现的次数
        }
        StringBuilder ans = new StringBuilder(26);
        boolean[] inAns = new boolean[26];
        for (char c : s) {
            left[c - 'a']--;
            if (inAns[c - 'a']) { // 如果c在res中
                continue;
            }
            // 设 x = ans.charAt(ans.length() - 1)，
            // 如果 c < x，且右边还有 x，那么可以把 x 去掉，因为后面可以重新把 x 加到 ans 中
            while (ans.length() != 0 && c < ans.charAt(ans.length() - 1)
                    && left[ans.charAt(ans.length() - 1) - 'a'] > 0) {
                inAns[ans.charAt(ans.length() - 1) - 'a'] = false;
                ans.deleteCharAt(ans.length() - 1);
            }
            ans.append(c);
            inAns[c - 'a'] = true;
        }
        return ans.toString();
    }
}
