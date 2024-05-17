import java.util.HashMap;

class Solution {
    public int minSubarray(int[] nums, int p) {
        int n = nums.length, ans = n;
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = (prefixSum[i] + nums[i]) % p;
        }
        int x = prefixSum[n];
        if (x == 0) {
            return 0;
        }
        HashMap<Integer, Integer> last = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            last.put(prefixSum[i], i);
            // 如果不存在，-n 可以保证 i-j >= n
            int j = last.getOrDefault((prefixSum[i] - x + p) % p, -n);
            ans = Math.min(ans, i - j);
        }
        return ans < n ? ans : -1;
    }
}