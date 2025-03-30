import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        List<Integer> list = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i == n - 1 || s.charAt(i) != s.charAt(i + 1)) {
                list.add(i - j + 1);
                j = i + 1;
            }
        }

        int ans = 0;
        for (int i = (s.charAt(0) == '0' ? 0 : 1); i + 2 < list.size(); i += 2) {
            ans = Math.max(ans, list.get(i) + list.get(i + 2));
        }
        for (char c : s.toCharArray()) {
            if (c == '1') {
                ans++;
            }
        }
        return ans;
    }
}