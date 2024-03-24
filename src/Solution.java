import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

class Solution {
    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        TreeMap<Long, Integer> frequency = new TreeMap<>(Collections.reverseOrder());
        HashMap<Integer, Long> cnt = new HashMap<>();
        int n = nums.length;
        long[] ans = new long[n];
        for (int i = 0; i < n; i++) {
            if (cnt.containsKey(nums[i]) && frequency.merge(cnt.get(nums[i]), -1, Integer::sum) == 0) { // 移除出现次数为cnt.get[nums[i]]的旧数据
                frequency.remove(cnt.get(nums[i]));
            }
            frequency.merge(cnt.merge(nums[i], (long) freq[i], Long::sum), 1, Integer::sum);
            ans[i] = frequency.firstKey();
        }
        return ans;
    }
}