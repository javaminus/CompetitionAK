package com.template.二分查找;

/**
 * @author Minus
 * @date 2023/11/10 16:23
 */
public class BinarySearch {
    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid; // 找到目标元素，返回索引
            } else if (nums[mid] < target) {
                left = mid + 1; // 目标元素在右半部分，更新左边界
            } else {
                right = mid - 1; // 目标元素在左半部分，更新右边界
            }
        }

        return -1; // 未找到目标元素
    }
}
