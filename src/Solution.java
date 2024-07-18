import java.util.Arrays;
import java.util.HashMap;

class Solution {
    private static long Mod = (int) 1e9 + 7;
    HashMap<String, Long> memo = new HashMap<>();
    public int sumOfPowers(int[] nums, int k) {
        Arrays.sort(nums);
        return (int) dfs(nums.length - 1 , k , Integer.MAX_VALUE / 2 , Integer.MAX_VALUE / 2 , nums);
    }

    private long dfs(int i, int rest, int pre, int min, int[] nums) {
        if (i + 1 < rest) {
            return 0;
        }
        if (rest == 0) {
            return min;
        }
        String key = i + "_" + rest + "_" + pre + "_" + min;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        long res1 = dfs(i - 1, rest, pre, min, nums) % Mod;
        long res2 = dfs(i - 1, rest - 1, nums[i], Math.min(min, nums[i] - pre), nums) % Mod;
        memo.put(key, (res1 + res2) % Mod);
        return (res1 + res2) % Mod;
    }
}