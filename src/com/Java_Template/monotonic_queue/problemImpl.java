package com.Java_Template.monotonic_queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Minus
 * @date 2024/3/13 19:49
 */
public class problemImpl implements problem {

    /**
     * @param nums
     * @param limit
     * @return
     * 1438. 绝对差不超过限制的最长连续子数组
     */
    @Override
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> queMax = new LinkedList<>();
        Deque<Integer> queMin = new LinkedList<>();
        int n = nums.length;
        int left = 0, right = 0;
        int ans = 0;
        while (right < n) {
            while (!queMax.isEmpty() && queMax.peekLast() < nums[right]) {
                queMax.pollLast();
            }
            while (!queMin.isEmpty() && queMin.peekLast() > nums[right]) {
                queMin.pollLast();
            }
            queMax.offerLast(nums[right]);
            queMin.offerLast(nums[right]);
            while (!queMax.isEmpty() && !queMin.isEmpty() && queMax.peekFirst() - queMin.peekFirst() > limit) {
                if (nums[left] == queMax.peekFirst()) {
                    queMax.pollFirst();
                }
                if (nums[left] == queMin.peekFirst()) {
                    queMin.pollFirst();
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }
}
