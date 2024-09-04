import java.util.Arrays;
import java.util.List;

class Solution {
    public int countWays(List<Integer> nums) {
        // 排序后：
        //
        //如果选了 nums[i]，那么比 nums[i] 更小的学生也要选。
        //如果不选 nums[i]，那么比 nums[i] 更大的学生也不选。
        int[] a = nums.stream().mapToInt(i -> i).toArray();
        Arrays.sort(a);
        int ans = a[0] > 0 ? 1 : 0; // 一个都不选
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] < i && i < a[i]) { // 选择i个学生，也就是选择第[0, i - 1]的所有学生
                ans++;
            }
        }
        // 所有学生一定可以选择
        return ans + 1;
    }
}