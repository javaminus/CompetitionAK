import java.util.List;

class Solution {
    public int minLengthAfterRemovals(List<Integer> nums) {
        int n = nums.size();
        int x = nums.get(n / 2);
        int mxCnt = binarySearch(nums, x + 1) - binarySearch(nums, x);
        System.out.println(binarySearch(nums, x + 1));
        System.out.println(binarySearch(nums, x));
        return Math.max(n % 2, mxCnt * 2 - n);
    }

    private int binarySearch(List<Integer> nums, int tar) {
        int l = 0, r = nums.size() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums.get(mid) <= tar) {
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return l - 1;
    }
}