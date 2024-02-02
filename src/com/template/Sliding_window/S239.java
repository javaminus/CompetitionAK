package com.template.Sliding_window;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Minus
 * @date 2023/11/7 16:26
 */
public class S239 {
    /**
     * 使用双端队列，并且维护队列的单调性，单调递减
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] resArr = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // 初始化双端队列
        for (int i = 0; i < n; i++) {
            // step1、入
            while (!deque.isEmpty() && nums[deque.getLast()] <= nums[i]) {
                deque.removeLast(); // 维护队列的单调性  大 --> 小
            }
            deque.addLast(i);
            // 出
            if (i - deque.getFirst() >= k) {
                deque.removeFirst();
            }
            // 记录答案
            if (i >= k - 1) {
                resArr[i - k + 1] = nums[deque.getFirst()];
            }

        }
        return resArr;

    }
}
