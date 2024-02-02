import java.util.PriorityQueue;

class Solution {
    public int[] numsGame(int[] nums) {
        final int Mod = (int) 1e9 + 7;
        int n = nums.length;
        int[] ans = new int[n];
        // 用一个大根堆 left 维护较小的一半，其元素和为 leftSum；
        // 用一个小根堆 right 维护较大的一半，其元素和为 rightSum
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a); // 大根堆 从大到小
        PriorityQueue<Integer> right = new PriorityQueue<>();  // 小根堆 从小到大
        long leftSum = 0L, rightSum = 0L;
        for (int i = 0; i < n; i++) {
            int b = nums[i] - i;
            if (i % 2 == 0) { // 前缀和为奇数
                // 维护两个堆 left < right
                if (!left.isEmpty() && left.peek() > b) {
                    leftSum += b - left.peek();
                    left.offer(b);
                    b = left.poll();
                }
                right.offer(b);
                rightSum += b;
                ans[i] = (int) ((rightSum - leftSum - right.peek()) % Mod);
            }else{ // 前缀和为偶数
                if (right.peek() < b) {
                    rightSum += b - right.peek();
                    right.offer(b);
                    b = right.poll();
                }
                leftSum += b;
                left.offer(b);
                ans[i] = (int) ((rightSum - leftSum) % Mod);
            }
        }
        return ans;
    }
}