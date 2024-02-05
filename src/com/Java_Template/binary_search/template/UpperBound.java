package com.Java_Template.binary_search.template;

public class UpperBound {
    public static int upperBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 3, 4, 5};
        int target = 3;
        int result = upperBound(nums, target);
        System.out.println("Upper Bound: " + result);
    }
}
