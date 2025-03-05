class Solution {
    public String breakPalindrome(String s) {
        int n = s.length();
        if (n == 1) {
            return "";
        }
        char[] cs = s.toCharArray();
        for (int i = 0; i < n / 2; i++) {
            if (cs[i] != 'a') {
                cs[i] = 'a';
                return new String(cs);
            }
        }
        cs[n - 1] = 'b';
        return new String(cs);
    }
}