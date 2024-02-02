import java.util.Arrays;

class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        int ans = 0;
        Arrays.sort(idx, (i, j) -> aliceValues[j] + bobValues[j] - aliceValues[i] - bobValues[i]);
        for (int i = 0; i < n; i++) {
            ans += i % 2 == 0 ? aliceValues[idx[i]] : -bobValues[idx[i]];
        }
        return Integer.compare(ans, 0);
    }
}