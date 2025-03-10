class Solution {
    public int divisorSubstrings(int num, int k) {
        String s = Integer.toString(num);
        int ans = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            if (sb.length() > k) {
                sb.deleteCharAt(0);
            }
            if (Integer.parseInt(sb.toString()) != 0 && sb.length() == k && num % Integer.parseInt(sb.toString()) == 0) {
                ans++;
            }
        }
        return ans;
    }
}