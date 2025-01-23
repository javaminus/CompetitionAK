import java.util.Arrays;

class Solution {

    private static int N = (int) 1e4 + 1;
    private static int[][] C = new int[N][N]; // 组合数
    static {
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    C[i][j] = 1;
                }else{
                    C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
                }
            }
        }
    }

    private static long Mod = (long) 1e9 + 7;
    public int minMaxSums(int[] nums, int k) {
        long res = 0;
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            long sum = 0;
            for (int j = 0; j < Math.min(k, n - 1 - i); j++) {
                sum += C[n - 1 - i][j];
                sum %= Mod;
            }
            res += sum * nums[i];
            res %= Mod;
        }
        return (int) res;
    }
}