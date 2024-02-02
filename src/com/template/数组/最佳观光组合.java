package com.template.数组;

/**
 * @author Minus
 * @date 2023/11/15 13:58
 */
public class 最佳观光组合 {
    public int maxScoreSightseeingPair(int[] values) {
        int n = values.length;
        int res = 0, start = values[0];
        for (int i = 1; i < n; i++) {
            res = Math.max(res, start + values[i] - i);
            start = Math.max(start, values[i] + i);
        }
        return res;
    }
}
