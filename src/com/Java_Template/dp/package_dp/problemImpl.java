package com.Java_Template.dp.package_dp;

import java.util.*;

/**
 * @author Minus
 * @date 2024/3/17 15:52
 */
public class problemImpl implements problem {

    /**
     * 和为目标值的最长子序列的长度 0-1背包问题（选与不选）
     */
    @Override
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int n = nums.size();
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int num = nums.get(i - 1);
            // 选择num对dp的影响
            for (int j = target; j >= num; j--) { // 这里j>=num,因为当背包容量j小于num时肯定装不了num
                if (dp[j - num] < 0) { // 也就是dp[j - num] = -1的时候，表示背包没有剩余容量为j - num的时候
                    continue;
                }
                dp[j] = Math.max(dp[j], dp[j - num] + 1);
            }
        }
        return dp[target];
    }

    /**
     * 416. 分割等和子集
     */
    @Override
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 == 1) {
            return false;
        }
        sum /= 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        int n = nums.length;
        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = sum; j >= num; j--) {
                if (dp[j - num]) {
                    dp[j] = true;
                }
            }
        }
        return dp[sum];
    }


    /**
     * 零钱兑换 （完全背包问题）
     */
    @Override
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) { // 能放进银币coin
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE / 2 ? -1 : dp[amount];
    }

    // 2585. 获得分数的方法数 多重背包
    int Mod = (int) 1e9 + 7;
    public int waysToReachTarget(int target, int[][] types) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int[] type : types) {
            int count = type[0], marks = type[1];
            for (int i = target; i >= 0; i--) { // 背包容量
                for (int j = 1; j <= count && j * marks <= i; j++) {
                    dp[i] = (dp[i] + dp[i - j * marks]) % Mod;
                }
            }
        }
        return dp[target];
    }


    // 474. 一和零
    public int findMaxForm(String[] strs, int m, int n) {
        int len = strs.length;
        int[][][] dp = new int[len + 1][m + 1][n + 1]; // 表示在前len个字符串中选最大不超过m和n的最长子集
        for (int i = 1; i <= len; i++) {
            String str = strs[i - 1];
            int[] t = f(str);
            int zero = t[0], one = t[1];
            for (int j = 0; j <=m ; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[i][j][k] = dp[i - 1][j][k]; // 不选当前字符串
                    if (zero <= j && one <= k) {
                        // dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j - zero][k - one] + 1); 这样就会报错
                        // 其实是dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zero][k - one] + 1),如果要选当前值，那么也是dp[i - 1]+1呀
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - zero][k - one] + 1);
                    }
                }
            }
        }
        return dp[len][m][n];
    }

    // 滚动数组优化
    public int findMaxForm1(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] dp = new int[m + 1][n + 1]; // 表示在前len个字符串中选最大不超过m和n的最长子集
        for (int i = 1; i <= len; i++) {
            String str = strs[i - 1];
            int[] t = f(str);
            int zero = t[0], one = t[1];
            for (int j = m; j >= zero; j--) { // 倒叙遍历，因为是01背包
                for (int k = n; k >= one; k--) {
                    dp[j][k] = Math.max(dp[j][k], dp[j - zero][k - one] + 1);
                }
            }
        }
        return dp[m][n];
    }
    private int[] f(String s) {
        int[] res = new int[2];
        for (int i = 0; i < s.length(); i++) {
            res[s.charAt(i) - '0']++;
        }
        return res;
    }

    // 1155. 掷骰子等于目标和的方法数
    @Override
    public int numRollsToTarget(int n, int k, int target) {
        if (target < n || target > n * k) {
            return 0;
        }
        int[][] dp = new int[n + 1][target + 1 - n]; // dp[i][j]表示前i个骰子和为 target+1-n 的数量  差分
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = target - n; j >= 0; j--) {
                for (int x = 0; x < k && j - x >= 0; x++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - x]) % Mod;
                }
            }
        }
        return dp[n][target - n];
    }


    // 1049. 最后一块石头的重量 II
    @Override
    public int lastStoneWeightII(int[] stones) {
        // 转换成背包问题真的不好想
        int sum1 = Arrays.stream(stones).sum();
        int sum = sum1 / 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        // 01背包
        for (int stone : stones) {
            for (int i = sum; i >= stone; i--) {
                if (dp[i - stone]) {
                    dp[i] = true;
                }
            }
        }
        for (int i = sum; i >= 0; i--) {
            if (dp[i]) {
                return sum1 - sum * 2;
            }
        }
        return 0;
    }

    /**
     * 494. 目标和
     */
    @Override
    public int findTargetSumWays(int[] nums, int target) {
        int sum = Arrays.stream(nums).sum();
        if ((sum - target) % 2 == 1 || sum - target < 0) {
            return 0;
        }
        target = (sum - target) / 2;
        int n = nums.length;
        int[] dp = new int[target + 1];
        Arrays.fill(dp, 0);
        dp[0] = 1; // 表示方案数为1，即一个负数都不选
        for (int i = 1; i <= n; i++) {
            int num = nums[i - 1];
            for (int j = target; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[target];
    }

    // 518. 零钱兑换 II
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        /*
         交换循环顺序会报错，因为如果背包的容量在外层，那么对于amount = 5,coins = {1,2,5}有 1，2，2是一组，2，1，2是一组，这样就会统计重复的答案
         for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] += dp[i - coin];
                }
            }
        }*/
        /* 这个也会报错，这里主要是因为可以重复选择的原因，这样写就是没有利用重复的元素，如果要重复，那么就要从小到大
         * for (int coin : coins) {
            for (int i = amount; i >= coin; i++) {
                dp[i] += dp[i - coin];
            }
        }
         */
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }


    // 1449. 数位成本和为目标值的最大数字
    @Override
    public String largestNumber(int[] cost, int target) {
        /**
         * 完全背包问题
         */
        int[][] dp = new int[10][target + 1]; // dp[i][j]表示在前i个数，最多可以选几个数，也就是最长数位
        Arrays.fill(dp[0], Integer.MIN_VALUE);
        dp[0][0] = 0; // 选0个数的数位长度为0
        for (int i = 1; i <=9 ; i++) {
            for (int j = 1; j <= target; j++) { // 正序，因为是完全背包，可以选重复的元素
                if (cost[i - 1] > j) {
                    // dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]); 这里这么写都不行！！！
                    dp[i][j] = dp[i - 1][j];
                }else{
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - cost[i - 1]] + 1);
                }
            }
        }
        if (dp[9][target] < 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        int digit = 9, remain = target;
        while (digit > 0) {
            if (remain >= cost[digit - 1] && dp[digit][remain] == dp[digit][remain - cost[digit - 1]] + 1) {
                // 选择了当前数字
                sb.append(digit);
                remain -= cost[digit - 1];
            }else{
                digit--;
            }
        }
        return sb.toString();
    }
    // 滚动数组优化空间
    public String largestNumber1(int[] cost, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= 9; i++) {
            for (int j = cost[i - 1]; j <= target; j++) {
                dp[j] = Math.max(dp[j], dp[j - cost[i - 1]] + 1);
            }
        }
        if (dp[target] < 0) {
            return "0";
        }
        StringBuffer sb = new StringBuffer();
        int digit = 9, remain = target;
        while (digit > 0) {
            if (remain >= cost[digit - 1] && dp[remain] == dp[remain - cost[digit - 1]] + 1) {
                remain -= cost[digit - 1];
                sb.append(digit);
            }else{
                digit--;
            }
        }
        return sb.toString();
    }

    // 879. 盈利计划
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        // 其中 dp[i][j][k]表示前 i 种工作的子集当中成员总数至多为 j 且总利润至少为 k 的计划数。
        int m = group.length;
        int[][][] dp = new int[m + 1][n + 1][minProfit + 1];
        for (int i = 0; i <= n; i++) {
            dp[0][i][0] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int k = 0; k <= minProfit; k++) {
                    if (j < group[i - 1]) {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }else{
                        dp[i][j][k] = (dp[i - 1][j][k] + dp[i - 1][j - group[i - 1]][Math.max(k - profit[i - 1], 0)]) % Mod;
                    }
                }
            }
        }
        return dp[m][n][minProfit];
    }
    // 滚动数组优化
    public int profitableSchemes1(int n, int minProfit, int[] group, int[] profit) {
        int m = group.length;
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int j = 0; j <= n; j++) {
            dp[j][0] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = n; j >= group[i - 1]; j--) { // 倒叙
                for (int k = minProfit; k >= 0; k--) {
                    dp[j][k] = (dp[j][k] + dp[j - group[i - 1]][Math.max(k - profit[i - 1], 0)]) % Mod;
                }
            }
        }
        return dp[n][minProfit];
    }

    /*  2787. 将一个数字表示成幂的和的方案数
        给你两个 正 整数 n 和 x 。
        请你返回将 n 表示成一些 互不相同 正整数的 x 次幂之和的方案数。换句话说，你需要返回互不相同整数 [n1, n2, ..., nk] 的集合数目，满足 n = n1x + n2x + ... + nkx 。
        由于答案可能非常大，请你将它对 109 + 7 取余后返回。
        比方说，n = 160 且 x = 3 ，一个表示 n 的方法是 n = 23 + 33 + 53 。*/
    public int numberOfWays(int n, int x) {
        int Mod = (int) 1e9 + 7;
        ArrayList<Long> candidates = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            long candidate = (long) Math.pow(i, x);
            if (candidate > n) {
                break;
            }
            candidates.add(candidate);
        }
        // 0-1 背包 思考先遍历背包容量还是遍历物品? 0-1背包不能重复选，所以遍历物品
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (long c : candidates) {
            for (int i = n; i >= c; i--) {
                dp[i] = (dp[i] + dp[(int) (i - c)]) % Mod;
            }
        }
        return (int) dp[n];
    }


    /*  1774. 最接近目标价格的甜点成本 (真复杂)
        你打算做甜点，现在需要购买配料。目前共有 n 种冰激凌基料和 m 种配料可供选购。而制作甜点需要遵循以下几条规则：
        必须选择 一种 冰激凌基料。
        可以添加 一种或多种 配料，也可以不添加任何配料。
        每种类型的配料 最多两份 。
        给你以下三个输入：
        baseCosts ，一个长度为 n 的整数数组，其中每个 baseCosts[i] 表示第 i 种冰激凌基料的价格。
        toppingCosts，一个长度为 m 的整数数组，其中每个 toppingCosts[i] 表示 一份 第 i 种冰激凌配料的价格。
        target ，一个整数，表示你制作甜点的目标价格。
        你希望自己做的甜点总成本尽可能接近目标价格 target 。
        返回最接近 target 的甜点成本。如果有多种方案，返回 成本相对较低 的一种。*/
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int mn = Arrays.stream(baseCosts).min().getAsInt();
        if (mn >= target) {
            return mn;
        }
        boolean[] can = new boolean[target + 1];
        int res = target * 2 - mn; // 大于target的最小值
        for (int b : baseCosts) {
            if (b <= target) {
                can[b] = true;
            }else{
                res = Math.min(res, b);
            }
        }
        for (int t : toppingCosts) {
            for (int i = 0; i < 2; i++) {
                for (int j = target; j > 0; j--) {
                    if (can[j] && j + t > target) {
                        res = Math.min(res, j + t);
                    }
                    if (j - t > 0) {
                        can[j] = can[j] | can[j - t];
                    }
                }
            }
        }
        for (int i = 0; i <= res - target; i++) {
            if (can[target - i]) {
                return target - i;
            }
        }
        return res;
    }

    /*  3082. 求出所有子序列的能量和 2300
        题解：https://leetcode.cn/problems/find-the-sum-of-the-power-of-all-subsequences/solutions/2691661/liang-chong-fang-fa-er-wei-yi-wei-0-1-be-2e47/
        给你一个长度为 n 的整数数组 nums 和一个 正 整数 k 。
        一个整数数组的 能量 定义为和 等于 k 的子序列的数目。
        请你返回 nums 中所有子序列的 能量和 。
        由于答案可能很大，请你将它对 109 + 7 取余 后返回。*/
    public int sumOfPower(int[] nums, int k) {
        int Mod = (int) 1e9 + 7;
        long[] dp = new long[k + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int i = k; i >= 0; i--) {
                dp[i] = (dp[i] * 2 + (i >= num ? dp[i - num] : 0)) % Mod;
            }
        }
        return (int) dp[k];
    }

    /*  956. 最高的广告牌 2381
        你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
        你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
        返回 广告牌的最大可能安装高度 。如果没法安装广告牌，请返回 0 。*/
    public int tallestBillboard(int[] rods) {
        // 初始化时dp={0:0},表示和为0时的最大长度为0
        // 那么最后只需要求dp[0]的最大值就ok辣
        // 对每根钢筋都有三种处理方式：加，减，丢 （对应乘以1，-1或0）
        HashMap<Integer, Integer> dp = new HashMap<>();
        dp.put(0, 0);
        for (int r : rods) {
            // 这里需要new HashMap<>(dp)，因为这里的dp在变，而迭代一个动态的map会出问题，这里就像clone一样
            for (Map.Entry<Integer, Integer> entry : new HashMap<>(dp).entrySet()) {
                int k = entry.getKey(), v = entry.getValue();
                dp.put(k + r, Math.max(dp.getOrDefault(k + r, 0), v + r));
                dp.put(k - r, Math.max(dp.getOrDefault(k - r, 0), v));
            }
        }
        return dp.get(0);
    }

    /*  2518. 好分区的数目
        给你一个正整数数组 nums 和一个整数 k 。
        分区 的定义是：将数组划分成”两个“有序的 组 ，并满足每个元素 恰好 存在于 某一个 组中。如果分区中每个组的元素和都大于等于 k ，则认为分区是一个好分区。
        返回 不同 的好分区的数目。由于答案可能很大，请返回对 109 + 7 取余 后的结果。
        如果在两个分区中，存在某个元素 nums[i] 被分在不同的组中，则认为这两个分区不同。*/
    public int countPartitions(int[] nums, int k) {
        int Mod = (int) 1e9 + 7;
        long numsSum = 0L;
        for (int x : nums) { // 这里求和不能使用Arrays.stream.sum();因为这个是int类型的返回值，会超数据范围
            numsSum += x;
        }
        if (numsSum < k * 2L) {
            return 0;
        }
        int[] dp = new int[k];
        dp[0] = 1;
        int ans = 1;
        for (int num : nums) {
            ans = ans * 2 % Mod;
            for (int i = k - 1; i >= num; i--) {
                dp[i] = (dp[i] + dp[i - num]) % Mod;
            }
        }
        for (int d : dp) {
            ans = (ans - d * 2 % Mod + Mod) % Mod;
        }
        return ans;
    }

    /*  2742. 给墙壁刷油漆
        给你两个长度为 n 下标从 0 开始的整数数组 cost 和 time ，分别表示给 n 堵不同的墙刷油漆需要的开销和时间。你有两名油漆匠：
        一位需要 付费 的油漆匠，刷第 i 堵墙需要花费 time[i] 单位的时间，开销为 cost[i] 单位的钱。
        一位 免费 的油漆匠，刷 任意 一堵墙的时间为 1 单位，开销为 0 。但是必须在付费油漆匠 工作 时，免费油漆匠才会工作。
        请你返回刷完 n 堵墙最少开销为多少。*/
    // 记忆化搜索写法，后面是0-1背包写法
    public int paintWalls1(int[] cost, int[] time) {
        HashMap<Integer, Integer> memo = new HashMap<>();
        return dfs(memo, cost, time, cost.length - 1, 0);
    }
    // 定义dfs(i,j)，返回刷i面墙的开销，然后j是付费刷墙数 - 免费刷墙数，也就是可用的免费刷墙数
    private int dfs(HashMap<Integer, Integer> memo, int[] cost, int[] time, int i, int j) {
        // 下面这两个if不能调换顺序！！！
        if (j > i) { // 剩余的墙可以免费刷
            return 0;
        }
        if (i < 0) {
            return Integer.MAX_VALUE / 2;
        }
        if (memo.containsKey((i << 10) + j)) {
            return memo.get((i << 10) + j);
        }
        int ans = Math.min(dfs(memo, cost, time, i - 1, j + time[i]) + cost[i], dfs(memo, cost, time, i - 1, j - 1));
        memo.put((i << 10) + j, ans);
        return ans;
    }
    // 0-1背包写法
    public int paintWalls(int[] cost, int[] time) {
        //付费刷墙个数+免费刷墙个数=n
        //付费刷墙时间之和>= n-免费刷墙个数
        //移项可得:"付费刷墙时间+1"之和>=n
        //把time[i]+1看作体积,从cost[i]看作物品价值
        //问题变成:从n个物品中选择体积和至少为n的物品价值和最小是多少?
        int n = cost.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int c = cost[i], t = time[i] + 1;
            /*这样写是不对的：for (int j = n; j >= t; j--) {
                dp[j] = Math.min(dp[j], dp[j - t] + c);
               }
               因为可以浪费刷墙面数
            }*/
            for (int j = n; j >= 0; j--) {
                dp[j] = Math.min(dp[j], dp[Math.max(j - t, 0)] + c);
            }
        }
        return dp[n];
    }

    /*  LCP 47. 入场安检
        「力扣挑战赛」 的入场仪式马上就要开始了，由于安保工作的需要，设置了可容纳人数总和为 M 的 N 个安检室，capacities[i] 记录第 i 个安检室可容纳人数。安检室拥有两种类型：
        先进先出：在安检室中的所有观众中，最早进入安检室的观众最先离开
        后进先出：在安检室中的所有观众中，最晚进入安检室的观众最先离开

        恰好 M+1 位入场的观众（编号从 0 开始）需要排队依次入场安检， 入场安检的规则如下：
        观众需要先进入编号 0 的安检室
        当观众将进入编号 i 的安检室时（0 <= i < N)，
        若安检室未到达可容纳人数上限，该观众可直接进入；
        若安检室已到达可容纳人数上限，在该观众进入安检室之前需根据当前安检室类型选择一位观众离开后才能进入；
        当观众离开编号 i 的安检室时 （0 <= i < N-1)，将进入编号 i+1 的安检室接受安检。
        若可以任意设定每个安检室的类型，请问有多少种设定安检室类型的方案可以使得编号 k 的观众第一个通过最后一个安检室入场。

        注意：

        观众不可主动离开安检室，只有当安检室容纳人数达到上限，且又有新观众需要进入时，才可根据安检室的类型选择一位观众离开；
        由于方案数可能过大，请将答案对 1000000007 取模后返回。*/

    /*  本题需要注意到

        先进先出的安检室并不会影响入场顺序
        后进先出的安检室, 会将 capacity−1capacity - 1capacity−1 个人留住， 即在后面的所有观众可以提前 capacity−1capacity - 1capacity−1 位入场
        题目可以理解为：在默认全部为先进先出的情况下，通过将安检室改变为后进先出可以使入场顺序提前 k−1k - 1k−1 个的背包问题*/
    public int securityCheck(int[] capacities, int k) {
        int Mod = (int) 1e9 + 7;
        int[] dp = new int[k + 1];
        dp[0] = 1;
        for (int c : capacities) {
            for (int i = k; i >= c - 1; i--) {
                dp[i] = (dp[i] + dp[i - (c - 1)]) % Mod;
            }
        }
        return dp[k];
    }


    /*  2902. 和带限制的子多重集合的数目  多重背包（2759） 看不懂，跳过咯
        给你一个下标从 0 开始的非负整数数组 nums 和两个整数 l 和 r 。
        请你返回 nums 中子多重集合的和在闭区间 [l, r] 之间的 子多重集合的数目 。
        由于答案可能很大，请你将答案对 109 + 7 取余后返回。
        子多重集合 指的是从数组中选出一些元素构成的 无序 集合，每个元素 x 出现的次数可以是 0, 1, ..., occ[x] 次，其中 occ[x] 是元素 x 在数组中的出现次数。
        注意：
        如果两个子多重集合中的元素排序后一模一样，那么它们两个是相同的 子多重集合 。
        空 集合的和是 0 。*/
    public int countSubMultisets(List<Integer> nums, int l, int r) {
        final int MOD = 1_000_000_007;
        int total = 0;
        HashMap<Integer,Integer> cnt = new HashMap<Integer, Integer>();
        for (int x : nums) {
            total += x;
            cnt.merge(x, 1, Integer::sum);
        }
        if (l > total) {
            return 0;
        }

        r = Math.min(r, total);
        int[] f = new int[r + 1];
        f[0] = cnt.getOrDefault(0, 0) + 1;
        cnt.remove(0);

        int sum = 0;
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            int x = e.getKey(), c = e.getValue();
            sum = Math.min(sum + x * c, r);
            for (int j = x; j <= sum; j++) {
                f[j] = (f[j] + f[j - x]) % MOD; // 原地计算同余前缀和
            }
            for (int j = sum; j >= x * (c + 1); j--) {
                f[j] = (f[j] - f[j - x * (c + 1)] + MOD) % MOD; // 两个同余前缀和的差
            }
        }

        int ans = 0;
        for (int i = l; i <= r; ++i) {
            ans = (ans + f[i]) % MOD;
        }
        return ans;
    }

    /*  1981. 最小化目标值与所选元素的差
        给你一个大小为 m x n 的整数矩阵 mat 和一个整数 target 。
        从矩阵的 每一行 中选择一个整数，你的目标是 最小化 所有选中元素之 和 与目标值 target 的 绝对差 。
        返回 最小的绝对差 。
        a 和 b 两数字的 绝对差 是 a - b 的绝对值*/
    // 这里每行一定要选一个数
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length, n = mat[0].length;
        int sum = 0;
        for (int i = 0; i < m; i++) {
            int mx = 0;
            for (int j = 0; j < n; j++) {
                mx = Math.max(mx, mat[i][j]);
            }
            sum += mx;
        }
        boolean[][] dp = new boolean[m][sum + 1];
        for (int j : mat[0]) {
            dp[0][j] = true;
        }
        for (int i = 1; i < m; i++) {
            for (int j = sum; j >= 0; j--) {
                for (int k = 0; k < n; k++) {
                    if (j >= mat[i][k]) {
                        dp[i][j] = dp[i][j] | dp[i - 1][j - mat[i][k]];
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= sum; i++) {
            if (dp[m - 1][i] && Math.abs(i - target) < ans) {
                ans = Math.abs(i - target);
                if (ans == 0) {
                    return ans;
                }
            }
        }
        return ans;
    }


}
