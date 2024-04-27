class Solution {
    public long numberOfWays(String s) {
        // 根据题意，只有两种情况：
        //010：对每个 1，统计左边 0 的个数和右边 0 的个数；
        //101：对每个 0，统计左边 1 的个数和右边 1 的个数。
        long ans = 0;
        int n = s.length(), cnt0 = 0, cnt1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                cnt0++;
            }else{
                cnt1++;
            }
        }
        int curCnt0 = 0, curCnt1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                curCnt0++;
                ans += (long) curCnt1 * (cnt1 - curCnt1);
            }else{
                curCnt1++;
                ans += (long) curCnt0 * (cnt0 - curCnt0);
            }
        }
        return ans;
    }
}