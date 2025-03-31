class Solution {
    public int closestToTarget(int[] arr, int target) {
        int n = arr.length;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int x = arr[i];
            ans = Math.min(ans, Math.abs(arr[i] - target));
            for (int j = i - 1; j >= 0 && (arr[j] | x) != arr[j]; j--) {
                arr[j] |= x;
                ans = Math.min(ans, Math.abs(arr[j] - target));
            }
        }
        return ans;
    }
}