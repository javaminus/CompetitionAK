package com.Java_Template.sliding_window;

/**
 * @author Minus
 * @date 2024/2/5 14:50
 */
public class problemImpl implements problem {

    @Override
    public int numberOfSubarrays(int[] nums, int k) {
        int left = 0, right = 0, oddCnt = 0, ans = 0, n = nums.length;
        while (right < n) {
            if ((nums[right++] & 1) == 1) {
                oddCnt++;
            }
            if (oddCnt == k) {
                int temp = right;
                while (right < n && (nums[right] & 1) == 0) {
                    right++;
                }
                int rightSum = right - temp;
                int leftSum = 0;
                while ((nums[left] & 1) == 0) {
                    leftSum++;
                    left++;
                }
                ans += (leftSum + 1) * (rightSum + 1);
                left++;
                oddCnt--;
            }
        }
        return ans;
    }
}
