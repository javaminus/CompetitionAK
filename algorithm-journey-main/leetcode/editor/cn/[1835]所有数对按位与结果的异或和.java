class Solution {
    public int getXORSum(int[] arr1, int[] arr2) {
        int m = arr1.length, n = arr2.length, ans = 0;
        if ((m & 1) != 0) {
            for (int x : arr1) {
                ans ^= x;
            }
        }
        if ((n & 1) != 0) {
            for (int x : arr2) {
                ans ^= x;
            }
        }
        return ans;
    }
}