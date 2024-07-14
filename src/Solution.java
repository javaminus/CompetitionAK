import java.util.Arrays;

class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        // 1.切一个m×n的蛋糕需要m×n-1刀
        // 2.越后面切，需要的cost倍数越高，所以要从cost高往低切
        // 3.横切刀的cost是cost[i]*(1+已切的竖刀次数)，竖切刀的情况类似
        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);
        long ans = 0;
        int i = m - 2;
        int j = n - 2;
        int cntH = 1;
        int cntV = 1;
        while (i >= 0 || j >= 0) {
            if (j < 0 || i >= 0 && horizontalCut[i] > verticalCut[j]) {
                ans += (long) horizontalCut[i--] * cntH;
                cntV++;
            }else{
                ans += (long) verticalCut[j--] * cntV;
                cntH++;
            }
        }
        return ans;
    }
}