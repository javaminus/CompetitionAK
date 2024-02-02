package com.songw;

public class LeetCode44 {
    class Solution {
        public boolean isMatch(String s, String p) {
            int sRight = s.length(), pRight = p.length();
            while (sRight > 0 && pRight > 0 && p.charAt(pRight - 1) != '*') {
                if (charMatch(s.charAt(sRight - 1), p.charAt(pRight - 1))) {
                    --sRight;
                    --pRight;
                } else {
                    return false;
                }
            }

            if (pRight == 0) {
                return sRight == 0;
            }

            int sIndex = 0, pIndex = 0;
            int sRecord = -1, pRecord = -1;

            while (sIndex < sRight && pIndex < pRight) {
                if (p.charAt(pIndex) == '*') {
                    ++pIndex;
                    sRecord = sIndex;
                    pRecord = pIndex;
                } else if (charMatch(s.charAt(sIndex), p.charAt(pIndex))) {
                    ++sIndex;
                    ++pIndex;
                } else if (sRecord != -1 && sRecord + 1 < sRight) {
                    ++sRecord;
                    sIndex = sRecord;
                    pIndex = pRecord;
                } else {
                    return false;
                }
            }

            return allStars(p, pIndex, pRight);
        }

        public boolean allStars(String str, int left, int right) {
            for (int i = left; i < right; ++i) {
                if (str.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        public boolean charMatch(char u, char v) {
            return u == v || v == '?';
        }
    }
}
