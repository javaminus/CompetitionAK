package com.Java_Template.util;

/**
 * @author Minus
 * @date 2024/1/30 14:33
 */
public class cal_mode {
    // Boyer-Moore投票算法 求众数
    public static int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;

        // 遍历数组
        for (int num : nums) {
            // 如果计数为0，将当前元素设为候选众数
            if (count == 0) {
                candidate = num;
            }

            // 如果当前元素等于候选众数，增加计数，否则减少计数
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }
}
