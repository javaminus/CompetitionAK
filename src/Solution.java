import java.util.HashMap;

class Solution {
    static HashMap<Character, Integer> map = new HashMap<>();
    static {
        map.put('a', 0);
        map.put('e', 1);
        map.put('i', 2);
        map.put('o', 3);
        map.put('u', 4);
    }
    public long countOfSubstrings(String word, int k) {
        return f(word, k) - f(word, k + 1);
    }

    private long f(String word, int k) { // 辅音出现k次及以上的方案数
        int left = 0, cntFu = 0;
        int[] cnt = new int[5];
        long ans = 0;
        for (int right = 0; right < word.length(); right++) {
            if (map.containsKey(word.charAt(right))) {
                cnt[map.get(word.charAt(right))]++;
            }else{
                cntFu++;
            }

            while (left <= right && judge(cnt) && cntFu >= k) {
                if (map.containsKey(word.charAt(left))) {
                    cnt[map.get(word.charAt(left))]--;
                }else{
                    cntFu--;
                }
                left++;
            }

            ans += left;
        }
        return ans;
    }

    private boolean judge(int[] cnt) {
        for (int i = 0; i < 5; i++) {
            if (cnt[i] == 0) {
                return false;
            }
        }
        return true;
    }
}