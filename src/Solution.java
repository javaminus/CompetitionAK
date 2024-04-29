import java.util.ArrayList;
import java.util.List;

class Solution {
    public int minimumOperations(List<Integer> nums) {
        ArrayList<Integer> g = new ArrayList<>();
        for (int x : nums) {
            int i = binarySearch(g, x);
            if (i == g.size()) {
                g.add(x);
            } else {
                g.set(i, x);
            }
        }
        return nums.size() - g.size();
    }
    private int binarySearch(List<Integer> nums, int target) {
        int left = 0, right = nums.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }

}