class Solution {
    public int countCompleteSubstrings(String word, int k) {
        int n = word.length();
        int ans = 0;
        for (int i = 0; i < n;) { // 没有i++
            int i0 = i;
            for (i++; i < n && Math.abs(word.charAt(i) - word.charAt(i - 1)) <= 2; i++);
            ans += f(word.substring(i0, i), k);
        }
        return ans;
    }

    private int f(String S, int k) {
        char[] s = S.toCharArray();
        int res = 0;
        for (int m = 1; m <= 26 && m * k <= s.length; m++) {
            int[] cnt = new int[26];
            for (int right = 0; right < s.length; right++) {
                cnt[s[right] - 'a']++;
                int left = right + 1 - k * m;
                if (left >= 0) {
                    boolean flag = true;
                    for (int i = 0; i < 26; i++) {
                        if (cnt[i] > 0 && cnt[i] != k) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        res++;
                    }
                    cnt[s[left] - 'a']--;
                }
            }
        }
        return res;
    }
}