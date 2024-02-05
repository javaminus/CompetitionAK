/*
import java.util.Arrays;

class Solution {
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MIN_VALUE;
            for (int j = 1; j <= k; j++) {
                if (i - j < 0) {
                    continue;
                }
                dp[i] = Math.max(nums[i] + dp[i - j], dp[i]);
            }
        }
        return dp[n - 1];
    }
}*/

import java.util.ArrayDeque;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // step1:入
            while (!deque.isEmpty() && nums[deque.getLast()] <= nums[i]) {
                deque.removeLast(); // 维护队列的单调性  大 --> 小
            }
            deque.addLast(i); // 添加到队尾
            // 出
            if (i - deque.getFirst() >= k) {
                deque.removeFirst();
            }
            // 记录答案
            if (i >= k - 1) {
                ans[i - k + 1] = nums[deque.getFirst()];
            }
        }
        return ans;
    }
}