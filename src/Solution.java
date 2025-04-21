class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] res = new long[k];
        for (int i = 0; i < k; i++) {
            res[i] = fun(nums, k, i);
        }
        return res;
    }

    public static void main(String[] args) {
        new Solution().fun(new int[]{1, 2, 3, 4, 5}, 3, 1);
    }
    
    private long fun(int[] nums, int k, int d){
        int n = nums.length;
        int[][] dp1 = new int[n][k];
        int[][] dp2 = new int[n][k];
        dp1[0][nums[0] % k] = 1;
        dp2[n - 1][nums[n - 1] % k] = 1;
        for (int i = 1; i < n; i++) {
            dp1[i][nums[i] % k] = 1;
            for (int j = 0; j < k; j++) {
                dp1[i][j*nums[i]%k] += dp1[i - 1][j];
            }
        }
        for (int i = n - 2; i >=0; i--) {
            dp2[i][nums[i] % k] = 1;
            for (int j = 0; j < k; j++) {
                dp2[i][j*nums[i]%k] += dp2[i + 1][j];
            }
        }
        long res = dp1[n - 1][d] + dp2[0][d];
        for (int i = 1; i < n - 1; i++) {
            int x = nums[i] % k; // 表示要nums[i]
            if (x == d) {
                res++;
            }
            for (int p = 0; p < k; p++) {
                for (int q = 0; q < k; q++) {
                    if (x * p * q % k == d) {
                        res += (long) dp1[i - 1][p] * dp2[i + 1][q];
                    }
                }
            }
        }
        return res;
    }
}