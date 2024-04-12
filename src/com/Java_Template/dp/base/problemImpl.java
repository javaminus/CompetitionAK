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


    // 2266. 统计打字方案数
    // 把相同字符分为一组，每组内只有一种字符
    private static final int MX = (int) 1e5 + 1, Mod = (int) 1e9 + 7;
    static int[] f = new int[MX];
    static int[] g = new int[MX];
    static {
        f[0] = g[0] = 1;
        f[1] = g[1] = 1;
        f[2] = g[2] = 2;
        f[3] = g[3] = 4;
        for (int i = 4; i < MX; i++) {
            // 注意：这里不能这样写 (long) (f[i - 1] + f[i - 2] + f[i - 3])
            //      只能写成 (long) f[i - 1] + f[i - 2] + f[i - 3]，因为三个数先加再转为long毫无意义，只能先转，再加
            f[i] = (int) (((long) f[i - 1] + f[i - 2] + f[i - 3]) % Mod);
            g[i] = (int) (((long) g[i - 1] + g[i - 2] + g[i - 3] + g[i - 4]) % Mod);
        }
    }
    public int countTexts(String s) {
        int ans = 1;
        int cnt = 0;
        for (int i = 0; i < s.length(); i++) {
            cnt++;
            char c = s.charAt(i);
            if (i == s.length() - 1 || c != s.charAt(i + 1)) {
                ans = (int) ((long) ans * (c == '7' || c == '9' ? g[cnt] : f[cnt]) % Mod);
                cnt = 0;
            }
        }
        return ans;
    }

    // 213. 打家劫舍 II
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        // 偷nums[0]，那么只能偷nums[2]到nums[n - 2]
        int[] ans1 = new int[n];
        ans1[2] = nums[2];
        for (int i = 3; i < n - 1; i++) {
            ans1[i] = Math.max(ans1[i - 1], ans1[i - 2] + nums[i]);
        }
        ans1[n - 2] += nums[0];
        // 不偷nums[0]，那么就是偷nums[1]到nums[n - 1]
        int[] ans2 = new int[n];
        ans2[1] = nums[1];
        for (int i = 2; i < n; i++) {
            ans2[i] = Math.max(ans2[i - 1], ans2[i - 2] + nums[i]);
        }
        return Math.max(ans1[n - 2], ans2[n - 1]);
    }


    // 2320. 统计放置房子的方式数
    /* 一条街道上共有 n * 2 个 地块 ，街道的两侧各有 n 个地块。每一边的地块都按从 1 到 n 编号。每个地块上都可以放置一所房子。

现要求街道同一侧不能存在两所房子相邻的情况，请你计算并返回放置房屋的方式数目。由于答案可能很大，需要对 109 + 7 取余后再返回。

注意，如果一所房子放置在这条街某一侧上的第 i 个地块，不影响在另一侧的第 i 个地块放置房子。 */
    // private static final int MX = (int) 1e4 + 1, Mod = (int) 1e9 + 7;
    // private static int[] f = new int[MX];
    static {
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < MX; i++) {
            f[i] = (f[i - 1] + f[i - 2]) % Mod;
        }
    }
    public int countHousePlacements(int n) {
        return (int) ((long) f[n] * f[n] % Mod);
    }


    // 53. 最大子数组和
    /* 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

       子数组 是数组中的一个连续部分。 */
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int preSum = 0;
        int minPre = 0;
        for (int num : nums) {
            preSum += num;
            ans = Math.max(ans, preSum - minPre);
            minPre = Math.min(minPre, preSum);
        }
        return ans;
    }

    // 1749. 任意子数组和的绝对值的最大值
    /* 给你一个整数数组 nums 。一个子数组 [numsl, numsl+1, ..., numsr-1, numsr] 的 和的绝对值 为 abs(numsl + numsl+1 + ... + numsr-1 + numsr) 。

        请你找出 nums 中 和的绝对值 最大的任意子数组（可能为空），并返回该 最大值 。
    */
    public int maxAbsoluteSum(int[] nums) {
        int mn = 0, mx = 0, preSum = 0;
        for (int num : nums) {
            preSum += num;
            if (preSum > mx) {
                mx = preSum;
            }
            if (preSum < mn) {
                mn = preSum;
            }
        }
        return mx - mn;
    }



    /*  363. 矩形区域不超过 K 的最大数值和
            已解答
            算术评级: 8
            困难
            给你一个 m x n 的矩阵 matrix 和一个整数 k ，找出并返回矩阵内部矩形区域的不超过 k 的最大数值和。

            题目数据保证总会存在一个数值和不超过 k 的矩形区域。 */
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int m = matrix.length, n = matrix[0].length;
        int[][] preSum = new int[m + 1][n + 1];
        // 二维前缀和
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                preSum[i + 1][j + 1] = preSum[i + 1][j] + preSum[i][j + 1] - preSum[i][j] + matrix[i][j];
                if (preSum[i + 1][j + 1] == k) {
                    return k;
                }
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int x = i; x < m; x++) {
                    for (int y = j; y < n; y++) {
                        int area = preSum[x + 1][y + 1] + preSum[i][j] - preSum[i][y + 1] - preSum[x + 1][j];
                        // int area = sum[x][y] - (i - 1 < 0 ? 0 : sum[i - 1][y]) - (j - 1 < 0 ? 0 : sum[x][j - 1]) + (i - 1 < 0 || j - 1 < 0 ? 0 :sum[i - 1][j - 1]);
                        // System.out.println(area);
                        if (area == k) {
                            return k;
                        }
                        if (area < k) {
                            ans = Math.max(ans, area);
                        }
                    }
                }
            }
        }
        return ans;
    }


    /* 152. 乘积最大子数组
        给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组
        （该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

        测试用例的答案是一个 32-位 整数。 */
    public int maxProduct(int[] nums) {
        /* 标签：动态规划
            遍历数组时计算当前最大值，不断更新
            令imax为当前最大值，则当前最大值为 imax = max(imax * nums[i], nums[i])
            由于存在负数，那么会导致最大的变最小的，最小的变最大的。因此还需要维护当前最小值imin，imin = min(imin * nums[i], nums[i])
            当负数出现时则imax与imin进行交换再进行下一步计算
            时间复杂度：O(n) */
        int ans = Integer.MIN_VALUE, iMax = 1, iMin = 1;
        for (int num : nums) {
            if (num < 0) {
                int temp = iMax;
                iMax = iMin;
                iMin = temp;
            }
            iMax = Math.max(iMax * num, num);
            iMin = Math.min(iMin * num, num);
            ans = Math.max(iMax, ans);
        }
        return ans;
    }

    /* 1289. 下降路径最小和 II

        给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
        非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列 */
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = grid[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (j == k) {
                        continue;
                    }
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + grid[i][j]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }
        return ans;
    }

    /* 1594. 矩阵的最大非负积
        给你一个大小为 m x n 的矩阵 grid 。最初，你位于左上角 (0, 0) ，每一步，你可以在矩阵中 向右 或 向下 移动。
        在从左上角 (0, 0) 开始到右下角 (m - 1, n - 1) 结束的所有路径中，找出具有 最大非负积 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。
        返回 最大非负积 对 109 + 7 取余 的结果。如果最大积为 负数 ，则返回 -1 。
        注意，取余是在得到最大积之后执行的。 */
    @Override
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length, Mod = (int) 1e9 + 7;
        long[][] dpMax = new long[m][n];
        long[][] dpMin = new long[m][n];
        dpMax[0][0] = grid[0][0];
        dpMin[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dpMax[i][0] = dpMax[i - 1][0] * grid[i][0];
            dpMin[i][0] = dpMin[i - 1][0] * grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dpMax[0][i] = dpMax[0][i - 1] * grid[0][i];
            dpMin[0][i] = dpMin[0][i - 1] * grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] > 0) {
                    dpMax[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] < 0) {
                    dpMax[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] == 0) {
                    dpMax[i][j] = 0;
                    dpMin[i][j] = 0;
                }
            }
        }
        return (int) (Math.max(dpMax[m - 1][n - 1], -1) % Mod);
    }
}
