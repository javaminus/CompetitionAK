import java.util.HashMap;

class Solution {
    public int maximumLength(int[] nums, int k) {
        HashMap<Integer, int[]> fs = new HashMap<>();
        int[] mx = new int[k + 1];
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, e -> new int[k + 1]);
            for (int i = k; i >= 0; i--) {
                f[i]++;
                if (i > 0) {
                    f[i] = Math.max(f[i], mx[i - 1] + 1);
                }
                mx[i] = Math.max(mx[i], f[i]);
            }
        }
        return mx[k];
    }
}