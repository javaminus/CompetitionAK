import java.util.HashMap;
import java.util.HashSet;

class Solution {
    public int[] findOriginalArray(int[] changed) {
        int n = changed.length;
        if (n % 2 == 1) {
            return new int[0];
        }
        int[] ans = new int[n / 2];
        int ansId = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : changed) {
            cnt.merge(x, 1, Integer::sum);
        }
        int cnt0 = cnt.getOrDefault(0, 0);
        if (cnt0 % 2 == 1) {
            return new int[0];
        }
        cnt.remove(0);
        HashSet<Integer> done = new HashSet<>();
        for (int x : cnt.keySet()) {
            if (done.contains(x) || x % 2 == 0 && cnt.containsKey(x / 2)) {
                continue;
            }
            while (cnt.containsKey(x)) {
                done.add(x);
                int cntX = cnt.get(x);
                int cnt2x = cnt.getOrDefault(x * 2, 0);
                if (cntX > cnt2x) {
                    return new int[0];
                }
                for (int i = 0; i < cntX; i++) {
                    if (ansId > ans.length) {
                        return new int[0];
                    }
                    ans[ansId++] = x;
                }
                if (cnt2x > cntX) {
                    cnt.put(x * 2, cnt2x - cntX);
                    x *= 2;
                }else{
                    done.add(x * 2);
                    x *= 4;
                }

            }
        }
        return ans;

    }
}