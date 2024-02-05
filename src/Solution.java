import java.util.ArrayDeque;

class Solution {
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i < n; i++) {
            while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[i - 1]) {
                deque.pollLast();
            }
            deque.offerLast(i - 1);
            if (deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            dp[i] = dp[deque.peekFirst()] + nums[i];
        }
        return dp[n - 1];
    }
}