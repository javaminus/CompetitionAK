package com.Java_Template.string;

import com.Java_Template.string.template.KMP;

import java.util.List;

/**
 * @author Minus
 * @date 2024/2/11 13:10
 */
public class ProblemImpl implements problem {

    @Override
    public int countMatchingSubarrays(int[] nums, int[] pattern) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                sb.append("1");
            }
            if (nums[i] == nums[i - 1]) {
                sb.append("0");
            }
            if (nums[i] < nums[i - 1]) {
                sb.append("2");
            }
        }
        StringBuilder pSb = new StringBuilder();
        for (int i = 0; i < pattern.length; i++) {
            pSb.append(pattern[i] == -1 ? 2 : pattern[i]);
        }
        List<Integer> res = new KMP().search(sb.toString().toCharArray(), pSb.toString().toCharArray());
        return res.size();
    }

    /**
     * @param arr
     * @return
     * 3076. 数组中的最短非公共子字符串
     */
    @Override
    public String[] shortestSubstrings(String[] arr) {
        int n = arr.length;
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            int m = arr[i].length();
            String t = "";
            for (int size = 1; size < m && t.isEmpty(); size++) {
                for (int j = 0; j <= m - size; j++) {
                    String sub = arr[i].substring(j, j + size);
                    if ((t.isEmpty() || sub.compareTo(t) < 0) && check(arr, i, sub)) {
                        t = sub;
                    }
                }
            }
            ans[i] = t;
        }
        return ans;
    }

    private boolean check(String[] arr, int k, String sub) {
        for (int i = 0; i < arr.length; i++) {
            if (i != k && arr[i].contains(sub)) {
                return false;
            }
        }
        return true;
    }

    // 796. 旋转字符串
    public boolean rotateString(String s, String goal) {
        return s.length() == goal.length() && (s + s).contains(goal);
    }

    // 2565. 最少得分子序列
    public int minimumScore(String s, String t) {
        int m = s.length(), n = t.length();
        int[] suf = new int[m + 1];
        suf[m] = n;
        int j = n - 1;
        for (int i = m - 1; i >= 0; i--) {
            if (s.charAt(i) == t.charAt(j)) {
                j--;
            }
            if (j < 0) {
                return 0;
            }
            suf[i] = j + 1;
        }
        int ans = suf[0];
        j = 0;
        for (int i = 0; i < m; i++) {
            if (s.charAt(i) == t.charAt(j)) {
                j++;
                ans = Math.min(ans, suf[i + 1] - j);
            }
        }
        return ans;
    }
}
