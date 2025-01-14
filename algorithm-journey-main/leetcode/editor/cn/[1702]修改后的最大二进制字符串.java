class Solution {
    public String maximumBinaryString(String binary) {
        int i = binary.indexOf('0');
        if (i < 0) {
            return binary; // 全是1
        }
        char[] s = binary.toCharArray();
        int cnt1 = 0;
        for (i++; i < s.length; i++) {
            cnt1 += s[i] - '0';
        }
        return "1".repeat(s.length - 1 - cnt1) + "0" + "1".repeat(cnt1);
    }
}