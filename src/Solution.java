import java.util.HashMap;

class Solution {
    public int minimumOperations(int[] nums) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int n = nums.length;
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (cnt.size() == n - i) {
                return res;
            }
            res++;
            if (i + 3 >= n) {
                return res;
            }
            for (int j = 0; j < 3; j++) {
                Integer v = cnt.get(nums[i + j]) - 1;
                if (v == 0) {
                    cnt.remove(nums[i + j]);
                }else{
                    cnt.put(nums[i + j], v);
                }
            }
            i += 2;
        }
        return (n + 2) / 3;
    }
}