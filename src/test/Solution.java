package test;

import java.util.Arrays;

public class Solution {
    public String longestPalindrome(String s) {
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
        int hl = halfLen[maxI];
        return s.substring((maxI - hl) / 2, (maxI + hl) / 2 - 1);
    }
}
