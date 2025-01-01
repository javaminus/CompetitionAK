import java.util.Arrays;

class Solution {
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);
        long ans = 0;
        int i = m - 2, j = n - 2, cntH = 1, cntV = 1;
        while (i >= 0 && j >= 0) {
            if (horizontalCut[i] > verticalCut[j]) {
                ans += (long) horizontalCut[i] * cntV;
                cntH++;
                i--;
            }else{
                ans += (long) verticalCut[j] * cntH;
                cntV++;
                j--;
            }
        }
        while (i >= 0) {
            ans += (long) horizontalCut[i] * cntV;
            i--;
        }
        while (j >= 0) {
            ans += (long) verticalCut[j] * cntH;
            j--;
        }
        return ans;
    }
}