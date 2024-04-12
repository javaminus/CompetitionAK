package com.Java_Template.dp.base;

/**
 * 100205. 修改数组后最大化数组中的连续元素数目(https://leetcode.cn/problems/maximize-consecutive-elements-in-an-array-after-modification/description/)
 * 2767. 将字符串分割为最少的美丽子字符串(https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/description/) 5的倍数不一定是5的幂，比如75是5的倍数，但是不是5的幂
 * 大富翁游戏（https://ac.nowcoder.com/acm/contest/75771/D）
 * 2312. 卖木头块(https://leetcode.cn/problems/selling-pieces-of-wood/description/?envType=daily-question&envId=2024-03-15)
 * 2266. 统计打字方案数（https://leetcode.cn/problems/count-number-of-texts/description/）
 * 213. 打家劫舍 II(https://leetcode.cn/problems/house-robber-ii/description/)
 * 2320. 统计放置房子的方式数(https://leetcode.cn/problems/count-number-of-ways-to-place-houses/description/)
 * <p>
 * <p>
 * <p>
 * -----------------------子数组和---------------------------------------------------------------------------------------------------------------------------------------------
 * 53. 最大子数组和(https://leetcode.cn/problems/maximum-subarray/description/)
 * 1749. 任意子数组和的绝对值的最大值(https://leetcode.cn/problems/maximum-absolute-sum-of-any-subarray/description/)
 * 363. 矩形区域不超过 K 的最大数值和(https://leetcode.cn/problems/max-sum-of-rectangle-no-larger-than-k/description/) 二维前缀和
 * 1289. 下降路径最小和 II(https://leetcode.cn/problems/minimum-falling-path-sum-ii/)
 */
public interface problem {

    public int maxSelectedElements(int[] nums);

    // 大富翁游戏
    public void niuKe1();

    // 2312. 卖木头块
    public long sellingWood(int m, int n, int[][] prices);

    // 2266. 统计打字方案数
    public int countTexts(String s);

    // 213. 打家劫舍 II
    public int rob(int[] nums);

    // 2320. 统计放置房子的方式数
    public int countHousePlacements(int n);

    // 53. 最大子数组和
    public int maxSubArray(int[] nums);

    // 1749. 任意子数组和的绝对值的最大值
    public int maxAbsoluteSum(int[] nums);

    // 363. 矩形区域不超过 K 的最大数值和
    public int maxSumSubmatrix(int[][] matrix, int k);

    // 152. 乘积最大子数组
    public int maxProduct(int[] nums);

    // 1289. 下降路径最小和 II
    public int minFallingPathSum(int[][] grid);

    // 1594. 矩阵的最大非负积
    public int maxProductPath(int[][] grid);
}
