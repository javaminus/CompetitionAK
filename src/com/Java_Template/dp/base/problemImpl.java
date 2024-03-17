package com.Java_Template.dp.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 */
public class problemImpl implements problem {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num + 1, map.getOrDefault(num, 0) + 1); // 当前数加一dp的结果
            map.put(num, map.getOrDefault(num - 1, 0) + 1); // 当前数不变dp的结果
        }
        int ans = 0;
        for (int x : map.values()) {
            ans = Math.max(ans, x);
        }
        return ans;
    }


    public void niuKe1() {
        // 大富翁游戏（https://ac.nowcoder.com/acm/contest/75771/D）
        // 记录每一轮可以到达的位置，然后计算最后一次的位置是否为初始位置
        // 我最开始的思路是加法，累加看是否为target，显然这种思路不好
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt(); // 轮数
        int[] steps = new int[m];
        for (int i = 0; i < m; i++) {
            steps[i] = scanner.nextInt() % n; // 细节
        }
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int i = 0; i < m; i++) {
            boolean[] list = new boolean[n]; // 依次遍历每一个位置
            for (int j = 0; j < n; j++) {
                if (dp[j]) {
                    list[(j + steps[i]) % n] = true;
                    list[(j - steps[i] + n) % n] = true;
                }
            }
            dp = list; // 动态规划
        }
        System.out.println(dp[0] ? "YES" : "NO");
        scanner.close();
    }

    public long sellingWood(int m, int n, int[][] prices) {
        long[][] dp = new long[m + 1][n + 1];
        for (int[] price : prices) {
            dp[price[0]][price[1]] = price[2];
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k < j/2; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - k] + dp[i][k]); // 垂直切割
                }
                for (int k = 1; k < i / 2; k++) {
                    // 水平切割
                    dp[i][j] = Math.max(dp[i][j], dp[i - k][j] + dp[k][j]);
                }
            }
        }
        return dp[m][n];
    }
}
