import java.util.PriorityQueue;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.maxSlidingWindow(new int[]{1}, 1);
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int right = 0, left = 0, n = nums.length;
        int[] ans = new int[n - k + 1];
        while (right < n) {
            pq.offer(nums[right]);
            if (right + 1 - left < k) {
                right++;
            }
            if (right + 1 - left == k) {
                ans[left++] = pq.poll();
            }
        }
        return ans;
    }
}