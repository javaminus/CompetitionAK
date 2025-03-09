package com.Java_Template.dp.package_dp;

import java.util.ArrayList;
import java.util.List;

public class _具体方案选择 {
    public static int knapsack(int[] weights, int[] values, int n, int W, List<Integer> items) {
        int[][] dp = new int[n + 1][W + 1]; // 前i个物品最大容量为j可以得到的最大价值
        boolean[][] keep = new boolean[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    if (values[i - 1] + dp[i - 1][w - weights[i - 1]] > dp[i - 1][w]) {
                        dp[i][w] = values[i - 1] + dp[i - 1][w - weights[i - 1]];
                        keep[i][w] = true;
                    } else {
                        dp[i][w] = dp[i - 1][w];
                    }
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int w = W;
        for (int i = n; i > 0; i--) {
            if (keep[i][w]) {
                items.add(i - 1);
                w -= weights[i - 1];
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 4};
        int[] values = {10, 20, 30, 40};
        int W = 5;
        int n = weights.length;
        List<Integer> items = new ArrayList<>();

        int maxValue = knapsack(weights, values, n, W, items);
        System.out.println("Maximum value in Knapsack = " + maxValue);
        System.out.println("Items selected: " + items);
    }
}
