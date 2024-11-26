import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int maxRemoval(int[] nums, int[][] queries) {
        int n = nums.length;
        Arrays.sort(queries, (a, b) -> a[0] - b[0]);
        int[] diff = new int[n + 1];
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int sumD = 0;
        int j = 0; // queries的下标
        for (int i = 0; i < n; i++) {
            sumD += diff[i];
            while (j < queries.length && queries[j][0] <= i) {
                pq.offer(queries[i][1]);
                j++;
            }
            while (sumD < nums[i] && !pq.isEmpty() && pq.peek() >= i) {
                sumD++;
                diff[pq.poll() + 1]--;
            }
            if (sumD < nums[i]) {
                return -1;
            }
        }
        return pq.size();
    }
}