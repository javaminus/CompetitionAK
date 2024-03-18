import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    int Mod = (int) 1e9 + 7;
    public int waysToReachTarget(int target, int[][] types) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        ArrayList<Integer> list = new ArrayList<>();
        for (int[] type : types){
            int count = type[0], marks = type[1];
            for (int i = 0; i < count; i++) {
                list.add(marks);
            }
        }
        for (int i = target; i >= 0; i--) {
        for (int num : list) {
            // 背包容量
            if (i >= num) {

                dp[i] = (dp[i] + dp[i - num]) % Mod;
            }
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[target];
    }
}