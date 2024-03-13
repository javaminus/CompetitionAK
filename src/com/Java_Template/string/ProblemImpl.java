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
}
