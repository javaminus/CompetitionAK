package com.Java_Template.binary_search;

import java.util.List;

/**
 * 算法：二分查找
 * 功能：利用单调性确定最优选择，通常可以使用SortedList、Bisect，还可以使用精度控制二分
 * 题目：xx（xx）
 * ===================================力扣===================================
 * 410. 分割数组的最大值(https://leetcode.cn/problems/split-array-largest-sum/description/) 贪心+二分
 * 4. 寻找两个正序数组的中位数（https://leetcode.cn/problems/median-of-two-sorted-arrays/）经典二分思想查找题
 * 81. 搜索旋转排序数组 II（https://leetcode.cn/problems/search-in-rotated-sorted-array-ii/）经典有重复数字的旋转数组
 * 153. 寻找旋转排序数组中的最小值(https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/)
 * 154. 寻找旋转排序数组中的最小值 II（https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array-ii/）经典有重复数字的旋转数组
 * 162. 寻找峰值（https://leetcode.cn/problems/find-peak-element/）经典二分思想查找题
 * 1901. 寻找峰值 II(https://leetcode.cn/problems/find-a-peak-element-ii/description/) 二维 + 二分
 * 2426. 满足不等式的数对数目（https://leetcode.cn/problems/number-of-pairs-satisfying-inequality/）根据不等式变换和有序集合进行二分查找
 * 2179. 统计数组中好三元组数目（https://leetcode.cn/problems/count-good-triplets-in-an-array/）维护区间范围内的个数
 * 2141. 同时运行 N 台电脑的最长时间（https://leetcode.cn/problems/maximum-running-time-of-n-computers/）贪心选择最大的 N 个电池作为基底，然后二分确定在其余电池的加持下可以运行的最长时间
 * 2102. 序列顺序查询（https://leetcode.cn/problems/sequentially-ordinal-rank-tracker/）使用有序集合维护优先级姓名实时查询
 * 2563. 统计公平数对的数目（https://leetcode.cn/problems/count-the-number-of-fair-pairs/）使用二分查找确定范围个数
 * 2604. 吃掉所有谷子的最短时间（https://leetcode.cn/problems/minimum-time-to-eat-all-grains/）二分加指针贪心 check
 * 1201. 丑数 III（https://leetcode.cn/problems/ugly-number-iii/）二分加容斥原理计数
 * 1739. 放置盒子（https://leetcode.cn/problems/building-boxes/）可推公式二分也可数学方法计算
 * 1889. 装包裹的最小浪费空间（https://leetcode.cn/problems/minimum-space-wasted-from-packaging/）排序加前缀和预处理与贪心二分
 * 2071. 你可以安排的最多任务数目（https://leetcode.cn/problems/maximum-number-of-tasks-you-can-assign/）经典二分加贪心
 * 2594. 修车的最少时间（https://leetcode.cn/problems/minimum-time-to-repair-cars/）经典二分
 * 2517. 礼盒的最大甜蜜度（https://leetcode.cn/problems/maximum-tastiness-of-candy-basket/）经典二分
 * 1482. 制作 m 束花所需的最少天数（https://leetcode.cn/problems/minimum-number-of-days-to-make-m-bouquets/）经典二分
 * 2528. 最大化城市的最小供电站数目（https://leetcode.cn/problems/maximize-the-minimum-powered-city/description/）经典二分使用前缀和差分数组贪心验证
 * 2560. 打家劫舍 IV（https://leetcode.cn/problems/house-robber-iv/）经典二分DP来check
 * 2234. 花园的最大总美丽值（https://leetcode.cn/problems/maximum-total-beauty-of-the-gardens/description/）前缀和加二分枚举
 * 2861. 最大合金数(https://leetcode.cn/problems/maximum-number-of-alloys/description/?envType=daily-question&envId=2024-01-27)
 * 2601. 质数减法运算(https://leetcode.cn/problems/prime-subtraction-operation/description/)
 * 2602. 使数组元素全部相等的最少操作次数（https://leetcode.cn/problems/minimum-operations-to-make-all-array-elements-equal/description/）
 * 100200. 标记所有下标的最早秒数 I(https://leetcode.cn/problems/earliest-second-to-mark-indices-i/description/)
 * 2386. 找出数组的第 K 大和(https://leetcode.cn/problems/find-the-k-sum-of-an-array/description/?envType=daily-question&envId=2024-03-09) 二分+回溯，难死人（2648）
 */
public interface problem {
    // 4. 寻找两个正序数组的中位数
    public double findMedianSortedArrays(int[] A, int[] B);

    // 81. 搜索旋转排序数组 II
    public boolean search(int[] nums, int target);

    // 153. 寻找旋转排序数组中的最小值
    public int findMin(int[] nums);

    // 154. 寻找旋转排序数组中的最小值 II ,有重复数字
    public int findMin1(int[] nums);

    // 162. 寻找峰值
    public int findPeakElement(int[] nums);

    // 1901. 寻找峰值 II
    public int[] findPeakGrid(int[][] mat);

    // 410. 分割数组的最大值
    public int splitArray(int[] nums, int k);

    // 878. 第 N 个神奇数字
    public int nthMagicalNumber(int n, int a, int b);

    // 2861. 最大合金数
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost);

    // 2601. 质数减法运算
    public boolean primeSubOperation(int[] nums);

    // 2602. 使数组元素全部相等的最少操作次数
    public List<Long> minOperations(int[] nums, int[] queries);

    // 100200. 标记所有下标的最早秒数 I
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices);

    // 2386. 找出数组的第 K 大和
    public long kSum(int[] nums, int k);
}
