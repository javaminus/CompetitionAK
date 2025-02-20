import java.util.ArrayList;
import java.util.List;

class Solution {
    public int shortestMatchingSubstring(String s, String p) {
        String[] sp = p.split("\\*", -1);
        char[] p1 = sp[0].toCharArray();
        char[] p2 = sp[1].toCharArray();
        char[] p3 = sp[2].toCharArray();
        char[] cs = s.toCharArray();

        List<Integer> pos1 = kmp(cs, p1);
        List<Integer> pos2 = kmp(cs, p2);
        List<Integer> pos3 = kmp(cs, p3);

        int ans = Integer.MAX_VALUE, i = 0, k = 0;
        for(int j: pos2){
            while(k < pos3.size() && pos3.get(k) < j + p2.length){
                k++;
            }
            if(k==pos3.size()){
                break;
            }
            while(i < pos1.size() && pos1.get(i) + p1.length < j){
                i++;
            }
            if (i > 0 && i <= pos1.size()) {
                ans = Math.min(ans, pos3.get(k) + p3.length - pos1.get(i - 1));
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 在文本串 s 中查找模式串 p，返回所有成功匹配的位置（p[0] 在 s 中的下标）
    private List<Integer> kmp(char[] s, char[] p) {
        ArrayList<Integer> list = new ArrayList<>();
        if (p.length == 0) {
            for (int i = 0; i <= s.length; i++) {
                list.add(i);
            }
            return list;
        }
        int[] next = getNext(new String(p));
        for (int i = 0, j = 0; i < s.length; i++) {
            while (j > 0 && s[i] != p[j]) {
                j = next[j - 1];
            }
            if (s[i] == p[j]) {
                j++;
            }
            if (j == p.length) {
                list.add(i - p.length + 1);
                j = next[j - 1]; // 不同点;
            }
        }
        return list;
    }
    private int[] getNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static void main(String[] args) {
        new Solution().shortestMatchingSubstring("abaacbaecebce", "ba*c*ce");
    }
}