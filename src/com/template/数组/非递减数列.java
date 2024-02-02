package com.template.数组;

/**
 * @author Minus
 * @date 2023/11/15 13:30
 */
public class 非递减数列 {

    // 最多变换一次，让nums非递减
    public boolean checkPossibility(int[] nums) {
        int cnt = 0;
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            int x = nums[i], y = nums[i + 1];
            if (x > y) {
                cnt++;
                if (cnt > 1) {
                    return false;
                }
                if (i > 0 && y < nums[i - 1]) {
                    nums[i + 1] = x;
                }
            }
        }
        return true;
    }
}
