class Solution {
    public double nthPersonGetsNthSeat(int n) {
        double ans = 1;
        while (n-- > 0) {
            double x = 1.0 / n;
            ans *= x;
        }
        return ans;
    }
}