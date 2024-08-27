import java.util.PriorityQueue;

class Solution { // Long.compare()学会了！！！
    private static int Mod = (int) 1e9 + 7;
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        if (multiplier == 1) {
            return nums;
        }
        int n = nums.length;
        int mx = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            pq.offer(new long[]{nums[i], i});
        }
        // 模拟，直到堆顶是mx
        for (; k > 0 && pq.peek()[0] < mx; k--) {
            long[] poll = pq.poll();
            poll[0] *= multiplier;
            pq.offer(poll);
        }
        for (int i = 0; i < n; i++) {
            long[] poll = pq.poll();
            nums[(int) poll[1]] = (int) (poll[0] % Mod * power(multiplier, k / n + (i < k % n ? 1 : 0)) % Mod);
        }
        return nums;
    }

    private long power(long a, long b) { // 求 (a ^ b) % p
        int p = Mod;
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans * a) % p;
            }
            a = (a * a) % p;
            b >>= 1;
        }
        return ans;
    }

}