package com.Java_Template.util;

/**
 * 数组原地去重算法
 */
public class remove_weight_in_place {
    private void remove_weight_in_place(int[] nums) {
        int k = 1, n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[k++] = nums[i];
            }
        }
        // return nums;
    }
}
