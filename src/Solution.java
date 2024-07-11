// 最快的做法 二分+贪心
class Solution {
    // 贪心+二分
    public int splitArray(int[] nums, int k) {
        int left = 0, right = 0; // left是nums中最大元素，right = Math.sum(nums), 代表二分的上下界
        for (int num : nums) {
            if (left < num) {
                left = num;
            }
            right += num;
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid, k)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right + 1;
    }

    private boolean check(int[] nums, int limit, int k) {
        int sum = 0;
        int cnt = 1; // 划分的子数组数量
        for (int num : nums) {
            if (sum + num > limit) {
                sum = num;
                cnt++;
            }else{
                sum += num;
            }
        }
        return cnt <= k; // 贪心思想
    }
}