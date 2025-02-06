import java.util.Arrays;

class Solution {
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        int ans = 0;
        String[] cur = new String[n];
        for (int i = 0; i < n; i++) {
            String[] cur2 = Arrays.copyOf(cur, n);
            for (int j = 0; j < n; j++) {
                cur2[j] += strs[j].charAt(i);
            }
            if (isSorted(cur2)) {
                cur = cur2;
            }else{
                ans++;
            }
        }

        return ans;
    }

    public boolean isSorted(String[] strs) {
        for (int i = 0; i < strs.length - 1; ++i)
            if (strs[i].compareTo(strs[i+1]) > 0)
                return false;

        return true;
    }
}