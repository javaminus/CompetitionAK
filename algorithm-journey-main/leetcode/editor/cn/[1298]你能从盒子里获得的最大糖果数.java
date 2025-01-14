import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

class Solution {
    public static void main(String[] args) {
        new Solution().maxCandies(new int[]{1, 0, 1, 0}, new int[]{7, 5, 4, 100}, new int[][]{{}, {}, {1}, {}}, new int[][]{{1, 2}, {3}, {}, {}}, new int[]{0});
    }
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        Deque<Integer> q = new LinkedList<>();
        HashSet<Integer> keySet = new HashSet<>();
        int ans = 0;
        for (int x : initialBoxes) {
            // 当前盒子可以打开
            for (int k : keys[x]) {
                keySet.add(k); // 加入钥匙
            }
            for (int c : containedBoxes[x]) {
                q.offerLast(c); // 加入新盒子
            }
            // 盒子已经打开，直接拿糖果
            ans += candies[x];
        }
        int cnt = 0;
        while (!q.isEmpty()) {
            Integer x = q.pollFirst();
            if (status[x] == 1 || keySet.contains(x)) {
                // 当前盒子可以打开
                for (int k : keys[x]) {
                    keySet.add(k); // 加入钥匙
                }
                for (int c : containedBoxes[x]) {
                    q.offerLast(c); // 加入新盒子
                }
                // 盒子已经打开，直接拿糖果
                ans += candies[x];
                cnt = 0;
            }else{
                q.offerLast(x);
                cnt++;
            }
            if (cnt >= q.size()) {
                break;
            }
        }
        return ans;
    }
}