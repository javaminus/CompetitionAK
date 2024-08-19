package com.leetcode.weekly_contest_411;

/**
 * @author Minus
 * @date 2024/8/18 23:02
 */
import java.math.BigInteger;
import java.util.Arrays;

class Solution {
    // https://leetcode.cn/problems/find-the-largest-palindrome-divisible-by-k/description/  方法一二都是打表找规律，方法三是 通用做法：建图+DFS+输出具体方案
    public String largestPalindrome_1(int n, int k) { // 使用BigInteger 2590ms
        char[] ans = new char[n];
        Arrays.fill(ans, '9');
        if (k == 2) {
            ans[0] = '8';
            if (n == 1) {
                return new String(ans);
            }
            ans[n - 1] = '8';
        } else if (k == 4) {
            if (n < 5) {
                for (int i = 0; i < n; i++) {
                    ans[i] = '8';
                }
            }else{
                ans[0] = '8';
                ans[1] = '8';
                ans[n - 1] = '8';
                ans[n - 2] = '8';
            }
        } else if (k == 5) {
            if (n == 1) {
                return Integer.toString(5);
            }
            ans[0] = '5';
            ans[n - 1] = '5';
        } else if (k == 6) {
            if (n == 1) {
                return Integer.toString(6);
            } if (n == 2) {
                return Integer.toString(66);
            }if (n == 3) {
                return "888";
            }
            if (n % 2 == 0) {
                ans[0] = '8';
                ans[n / 2 - 1] = '7';
                ans[n / 2] = '7';
                ans[n - 1] = '8';
            }else{
                ans[0] = '8';
                ans[n - 1] = '8';
                ans[n / 2] = '8';
            }
        } else if (k == 7) {
            if (n == 2) {
                return "77";
            }
            if (n == 3) {
                return "959";
            }
            Arrays.fill(ans, '9');
            if (n % 2 != 0) {
                int mid = n / 2;
                for (int i = 9; i >= 0; i--) {
                    ans[mid] = (char) ('0' + i);
                    BigInteger a = new BigInteger(new String(ans));
                    if (a.mod(new BigInteger("7")).equals(new BigInteger("0"))) {
                        return new String(ans);
                    }
                }
            } else {
                int mid = n / 2 - 1;
                for (int i = 9; i >= 0; i--) {
                    ans[mid] = (char) ('0' + i);
                    ans[mid + 1] = (char) ('0' + i);
                    BigInteger a = new BigInteger(new String(ans));
                    if (a.mod(new BigInteger("7")).equals(new BigInteger("0"))) {
                        return new String(ans);
                    }
                }
            }
        } else if (k == 8) {
            if (n < 7) {
                for (int i = 0; i < n; i++) {
                    ans[i] = '8';
                }
            }else{
                ans[0] = '8';
                ans[1] = '8';
                ans[2] = '8';
                ans[n - 1] = '8';
                ans[n - 2] = '8';
                ans[n - 3] = '8';
            }
        }
        return new String(ans);
    }
    public String largestPalindrome_2(int n, int k) { // 使用余数优化 7ms
        char[] ans = new char[n];
        Arrays.fill(ans, '9');
        if (k == 2) {
            ans[0] = '8';
            if (n == 1) {
                return new String(ans);
            }
            ans[n - 1] = '8';
        } else if (k == 4) {
            if (n < 5) {
                for (int i = 0; i < n; i++) {
                    ans[i] = '8';
                }
            }else{
                ans[0] = '8';
                ans[1] = '8';
                ans[n - 1] = '8';
                ans[n - 2] = '8';
            }
        } else if (k == 5) {
            if (n == 1) {
                return Integer.toString(5);
            }
            ans[0] = '5';
            ans[n - 1] = '5';
        } else if (k == 6) {
            if (n == 1) {
                return Integer.toString(6);
            } if (n == 2) {
                return Integer.toString(66);
            }if (n == 3) {
                return "888";
            }
            if (n % 2 == 0) {
                ans[0] = '8';
                ans[n / 2 - 1] = '7';
                ans[n / 2] = '7';
                ans[n - 1] = '8';
            }else{
                ans[0] = '8';
                ans[n - 1] = '8';
                ans[n / 2] = '8';
            }
        } else if (k == 7) {
            if (n == 2) {
                return "77";
            }
            if (n == 3) {
                return "959";
            }
            if (n % 2 != 0) {
                int midIndex = n / 2;
                int originalRemainder = 0;
                for (int i = 0; i < n; i++) {
                    originalRemainder = (originalRemainder * 10 + 9) % 7;
                }
                if (originalRemainder == 0) {
                    return new String(ans);
                }
                int midRemainder = 1;
                for (int i = n / 2; i > 0; i--) {
                    midRemainder = midRemainder * 10 % 7;
                }
                for (int digit = 8; digit >= 0; digit--) {
                    originalRemainder = (originalRemainder - midRemainder) % 7;
                    if (originalRemainder < 0) {
                        originalRemainder += 7;
                    }
                    if (originalRemainder == 0) {
                        ans[midIndex] = (char) ('0' + digit);
                        break;
                    }
                }
            } else {
                int midIndex1 = n / 2 - 1, midIndex2 = n / 2;
                int originalRemainder = 0;
                for (int i = 0; i < n; i++) {
                    originalRemainder = (originalRemainder * 10 + 9) % 7;
                }
                if (originalRemainder == 0) {
                    return new String(ans);
                }
                int midRemainder = 11;
                for (int i = n / 2 - 1; i > 0; i--) {
                    midRemainder = midRemainder * 10 % 7;
                }
                for (int digit = 8; digit >= 0; digit--) {
                    originalRemainder = (originalRemainder - midRemainder) % 7;
                    if (originalRemainder < 0) {
                        originalRemainder += 7;
                    }
                    if (originalRemainder == 0) {
                        ans[midIndex1] = (char) ('0' + digit);
                        ans[midIndex2] = (char) ('0' + digit);
                        break;
                    }
                }
            }
        } else if (k == 8) {
            if (n < 7) {
                for (int i = 0; i < n; i++) {
                    ans[i] = '8';
                }
            }else{
                ans[0] = '8';
                ans[1] = '8';
                ans[2] = '8';
                ans[n - 1] = '8';
                ans[n - 2] = '8';
                ans[n - 3] = '8';
            }
        }
        return new String(ans);
    }

    public String largestPalindrome_3(int n, int k) {
        int[] pow10 = new int[n];
        pow10[0] = 1;
        for (int i = 1; i < n; i++) {
            pow10[i] = pow10[i - 1] * 10 % k;
        }

        char[] ans = new char[n];
        int m = (n + 1) / 2;
        boolean[][] vis = new boolean[m + 1][k];
        dfs(0, 0, n, k, m, pow10, ans, vis);
        return new String(ans);
    }
    private boolean dfs(int i, int j, int n, int k, int m, int[] pow10, char[] ans, boolean[][] vis) {
        if (i == m) {
            return j == 0;
        }
        vis[i][j] = true;
        for (int d = 9; d >= 0; d--) { // 贪心：从大到小枚举
            int j2;
            if (n % 2 == 1 && i == m - 1) { // 正中间
                j2 = (j + d * pow10[i]) % k;
            } else {
                j2 = (j + d * (pow10[i] + pow10[n - 1 - i])) % k;
            }
            if (!vis[i + 1][j2] && dfs(i + 1, j2, n, k, m, pow10, ans, vis)) {
                ans[i] = ans[n - 1 - i] = (char) ('0' + d);
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-ii/description/
    // 寻找首个left[j] >= l的思路太巧妙了，[l, j - 1]的left[i]都比l小，所以[l, j - 1]的子串全部合法；
    // [j, r]的left[i]都比l大，保证合法的区间一定在[l, r]内，所以每个i - left[i] + 1都可以计算在内。
    // 本质上是枚举[l, r]内的滑窗右端点，分界点左侧的右端点窗口包含l，用滑动窗口优化；
    // 分界点右侧整个窗口都在[l, r]区间内部，暴力求和用前缀和优化。确实不好想。
    public long[] countKConstraintSubstrings(String s, int k, int[][] queries) {
        char[] cs = s.toCharArray();
        int n = s.length();
        int[] left = new int[n];
        long[] prefixSum = new long[n + 1];
        int[] cnt = new int[2];
        int l = 0;
        for (int i = 0; i < n; i++) {
            cnt[cs[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[cs[l++] & 1]--;
            }
            left[i] = l;
            // 计算 i - left[i] + 1 的前缀和
            prefixSum[i + 1] = prefixSum[i] + i - l + 1;
        }
        int m = queries.length;
        long[] ans = new long[m];
        for (int i = 0; i < m; i++) {
            int ql = queries[i][0], qr = queries[i][1];
            int j = binarySearch(left, ql, qr, ql);
            ans[i] = prefixSum[qr + 1] - prefixSum[j] + (long) (j - ql + 1) * (j - ql) / 2;
        }
        return ans;
    }
    private int binarySearch(int[] nums, int left, int right,int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return right + 1;
    }


}
