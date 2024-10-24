import java.util.Arrays;

class Solution {
    public long maxProduct(String s) {
        int n = s.length();
        char[] t = new char[n * 2 + 3];
        Arrays.fill(t, '#');
        t[0] = '^';
        for (int i = 0; i < n; i++) {
            t[i * 2 + 2] = s.charAt(i);
        }
        t[n * 2 + 2] = '$';
        int[] halfLen = new int[t.length - 2];
        halfLen[1] = 1;
        int boxM = 0, boxR = 0, maxI = 0;
        for (int i = 2; i < halfLen.length; i++) {
            int hl = 1;
            if (i < boxR) {
                hl = Math.min(halfLen[boxM * 2 - i], boxR - i);
            }
            while (t[i - hl] == t[i + hl]) {
                hl++;
                boxM = i;
                boxR = i + hl;
            }
            halfLen[i] = hl;
            if (hl > halfLen[maxI]) {
                maxI = i;
            }
        }

        int[] startPL = new int[n]; // 表示以 s[i] 为首字母的最长奇回文子串的长度
        int[] endPL = new int[n]; // 表示以 s[i] 为尾字母的最长奇回文子串的长度
        for (int i = 2; i < t.length - 2; i += 2) {
            int hl = halfLen[i];
            int left = (i - hl) / 2;
            int right = (i + hl) / 2 - 2;
            startPL[left] = Math.max(startPL[left], hl - 1);
            endPL[right] = Math.max(endPL[right], hl - 1);
        }

        for (int i = 1; i < n; i++) {
            startPL[i] = Math.max(startPL[i], startPL[i - 1] - 2);
        }
        for (int i = n - 2; i >= 0; i--) {
            startPL[i] = Math.max(startPL[i], startPL[i + 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            endPL[i] = Math.max(endPL[i], endPL[i + 1] - 2);
        }
        for (int i = 1; i < n; i++) {
            endPL[i] = Math.max(endPL[i], endPL[i - 1]);
        }

        long ans = 0;
        for (int i = 1; i < n; i++) {
            ans = Math.max(ans, (long) endPL[i - 1] * startPL[i]);
        }
        return ans;
    }
}