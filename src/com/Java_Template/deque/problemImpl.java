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


    @Override
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        ArrayDeque<Integer> queue = new ArrayDeque<>(); // 小 ->大
        int right = 0, ans = n + 1;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        while (right <= n) {
            while (!queue.isEmpty() && prefix[right] <= prefix[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(right);
            while (!queue.isEmpty() && prefix[right] - prefix[queue.peekFirst()] >= k) {
                ans = Math.min(ans, right - queue.pollFirst());
            }
            right++;
        }
        return ans == n + 1 ? -1 : ans;
    }

    @Override
    public int constrainedSubsetSum(int[] nums, int k) { // 超时
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int ans = dp[0];
        for (int i = 0; i < n; i++) {
            dp[i] = nums[i];
            for (int j = Math.max(0, i - k); j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j] + nums[i]);
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int constrainedSubsetSum1(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int ans = dp[0];
        ArrayDeque<int[]> queue = new ArrayDeque<>(); // 大 -> 小
        for (int i = 1; i < n; i++) {
            while (!queue.isEmpty() && queue.peekLast()[1] <= dp[i - 1]) {
                queue.pollLast();
            }
            queue.offerLast(new int[]{i - 1, dp[i - 1]});
            while (!queue.isEmpty() && queue.peekFirst()[0] < i - k) {
                queue.pollFirst();
            }
            dp[i] = Math.max(0, queue.peekFirst()[1]) + nums[i];
            ans = Math.max(dp[i], ans);
        }
        return ans;
    }
}
