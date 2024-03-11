package com.Java_Template.array.diff_array;

/**
 * 差分
 * 1094. 拼车(https://leetcode.cn/problems/car-pooling/description/)
 * 1109. 航班预订统计(https://leetcode.cn/problems/corporate-flight-bookings/description/)
 * 2406. 将区间分为最少组数(https://leetcode.cn/problems/divide-intervals-into-minimum-number-of-groups/description/)
 * 2381. 字母移位 II(https://leetcode.cn/problems/shifting-letters-ii/description/)
 * 2772. 使数组中的所有元素都等于零(https://leetcode.cn/problems/apply-operations-to-make-all-array-elements-equal-to-zero/description/)
 * 2528. 最大化城市的最小电量(https://leetcode.cn/problems/maximize-the-minimum-powered-city/description/)
 */
public interface problem {
    // 1094. 拼车
    public boolean carPooling(int[][] trips, int capacity);

    // 1109. 航班预订统计
    public int[] corpFlightBookings(int[][] bookings, int n);

    // 2406. 将区间分为最少组数
    public int minGroups(int[][] intervals);

    // 2381. 字母移位 II
    public String shiftingLetters(String s, int[][] shifts);

    // 2772. 使数组中的所有元素都等于零
    public boolean checkArray(int[] nums, int k);

    // 2528. 最大化城市的最小电量
    public long maxPower(int[] stations, int r, int k);
}
