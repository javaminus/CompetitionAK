package com.template.动态规划;

import java.util.HashMap;

/**
 * @author Minus
 * @date 2023/12/1 19:56
 */
public class 哈希dp {
    public int longestSubsequence(int[] arr, int difference) {
        int ans = 0;
        HashMap<Integer, Integer> dp = new HashMap<>();
        for (int v : arr) {
            dp.put(v, dp.getOrDefault(v - difference, 0) + 1);
            ans = Math.max(ans, dp.get(v));
        }
        return ans;
    }
}
