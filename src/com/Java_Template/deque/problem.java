package com.Java_Template.deque;

/**
 * 双端队列
 *
 * 239. 滑动窗口最大值(https://leetcode.cn/problems/sliding-window-maximum/description/) 典中典 单调队列
 * 1696. 跳跃游戏 VI（https://leetcode.cn/problems/jump-game-vi/description/?envType=daily-question&envId=2024-02-05） 动态规划+单调队列
 */
public interface problem {
    // 239. 滑动窗口最大值
    public int[] maxSlidingWindow(int[] nums, int k);

    // 1696. 跳跃游戏 VI
    public int maxResult(int[] nums, int k);
}
