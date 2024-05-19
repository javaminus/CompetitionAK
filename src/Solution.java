class Solution {
    public int getWinner(int[] arr, int k) {
        int mx = arr[0];
        int cnt = 0, x = arr[0];
        for (int i = 1; i < arr.length; i++) {
            mx = Math.max(mx, arr[i]);
            if (x > arr[i]) {
                cnt++;
            }else{
                x = arr[i];
                cnt = 1;
            }
            if (cnt == k) {
                return x;
            }
        }
        return mx;
    }
}