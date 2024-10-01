class Solution {
    public int minSpeedOnTime(int[] dist, double hour) {
        int n = dist.length;
        int left = 1, right = (int) 1e7 + 10;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (judge(dist, mid, hour)) {
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return right + 1 >= (int) 1e7 + 10 ? -1 : right + 1;
    }

    private boolean judge(int[] dist, double speed, double hour) {
        double ans = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            // ans += (dist[i] + speed - 1) / speed;
            ans += Math.ceil(dist[i] / speed);
        }
        return ans + (dist[dist.length - 1] / speed) <= hour;
    }
}