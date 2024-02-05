package com.Java_Template.binary_search.template;

public class LowerBound {
    public static int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 4, 5};
        int target = 2;
        int result = lowerBound(nums, target);
        System.out.println("Lower Bound: " + result);
    }
}
