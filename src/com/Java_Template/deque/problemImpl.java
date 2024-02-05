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
}
