package com.Java_Template.binary_search.template;

import java.util.List;
import java.util.function.Predicate;

/**
 * 二分查找模板
 * 作者：Minus
 * 日期：2024/1/17 10:40
 */
public class template {

    /**
     * 查找满足条件的最大整数（查找小于等于target的最大整数下标）
     * 输入{1，2，3，4，5}，3
     * 返回 2
     */
    // note 在这个例子中，目标值为2，数组为{1, 2, 2, 2, 3, 4, 4, 5}。程序输出目标值2的下界索引，即数组中第一个不小于2的元素的索引。
    private int binarySearch(List<Integer> list,int target) { // 大于等于target的最小值
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= target) {  // note 当前元素大于等于目标值，向左搜索
                right = mid - 1;
            } else { // note 当前元素小于目标值，向右搜索
                left = mid + 1;
            }
        }
        return left;
    }

    // 二分查找数组上界的方法
    static int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) { // note {1, 2, 2, 2, 3, 4, 4, 5} target = 2，结果为4，如果改为arr[mid] >= target ，结果为2
                // 当前元素大于目标值，向左搜索
                right = mid - 1;
            } else {
                // 当前元素小于等于目标值，向右搜索
                left = mid + 1;
            }
        }
        return left; // 返回找到的上界元素的索引
    }

    private int lowerBound(int[] nums, int target) { // 左开右开
        int left = -1, right = nums.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) { // 这里也可以取小于等于
                left = mid;
            }else{
                right = mid;
            }
        }
        return right;
    }

    /**
     * 查找满足条件的最小整数 x
     *
     * @param low    查找范围的下界
     * @param high   查找范围的上界
     * @param check  判断条件的函数接口
     * @return 满足条件的最小整数 x
     */
    public static int findIntLeft(int low, int high, Predicate<Integer> check) {
        while (low < high - 1) {
            int mid = low + (high - low) / 2;
            if (check.test(mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return check.test(low) ? low : high;
    }

    /**
     * 查找满足条件的最大整数 x
     *
     * @param low    查找范围的下界
     * @param high   查找范围的上界
     * @param check  判断条件的函数接口
     * @return 满足条件的最大整数 x
     */
    public static int findIntRight(int low, int high, Predicate<Integer> check) {
        while (low < high - 1) {
            int mid = low + (high - low) / 2;
            if (check.test(mid)) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return check.test(high) ? high : low;
    }

    /**
     * 查找满足条件的最小浮点数 x
     *
     * @param low    查找范围的下界
     * @param high   查找范围的上界
     * @param check  判断条件的函数接口
     * @param error  误差范围
     * @return 满足条件的最小浮点数 x
     */
    public static double findFloatLeft(double low, double high, Predicate<Double> check, double error) {
        while (low < high - error) {
            double mid = low + (high - low) / 2;
            if (check.test(mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return check.test(low) ? low : high;
    }

    /**
     * 查找满足条件的最大浮点数 x
     *
     * @param low    查找范围的下界
     * @param high   查找范围的上界
     * @param check  判断条件的函数接口
     * @param error  误差范围
     * @return 满足条件的最大浮点数 x
     */
    public static double findFloatRight(double low, double high, Predicate<Double> check, double error) {
        while (low < high - error) {
            double mid = low + (high - low) / 2;
            if (check.test(mid)) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return check.test(high) ? high : low;
    }
}