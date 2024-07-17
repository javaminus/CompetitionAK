import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int ans = 0;
        HashMap<String, Integer> dp = new HashMap<String, Integer>();
        for (String s : words) {
            int res = 0;
            for (int i = 0; i < s.length(); i++) { // 枚举去掉 s[i]
                String t = s.substring(0, i) + s.substring(i + 1);
                res = Math.max(res, dp.getOrDefault(t, 0));
            }
            dp.put(s, res + 1);
            ans = Math.max(ans, res + 1);
        }
        return ans;
    }
}