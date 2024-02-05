package com.Java_Template.deque;

import java.util.ArrayDeque;

public class problemImpl implements problem {

    @Override
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

    @Override
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        ArrayDeque<Integer> deque = new ArrayDeque<>(); // 维护从大到小的双端队列 new LinkedList<>()也可以
        for (int i = 1; i < n; i++) {
            // 进
            while (!deque.isEmpty() && dp[i - 1] >= dp[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i - 1);
            // 出
            if (deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            dp[i] = dp[deque.peekFirst()] + nums[i];
        }
        return dp[n - 1];
    }
}
