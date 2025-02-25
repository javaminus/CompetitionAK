import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        // 记录每种字母的出现位置
        List<Integer>[] pos = new ArrayList[26];
        Arrays.setAll(pos, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            pos[s.charAt(i) - 'a'].add(i);
        }

        // 构建有向图
        List<Integer>[] g = new ArrayList[26];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < 26; i++) {
            if (pos[i].isEmpty()) {
                continue;
            }
            List<Integer> p = pos[i];
            int l = p.get(0);
            int r = p.get(p.size() - 1);
            for (int j = 0; j < 26; j++) {
                if (j == i) {
                    continue;
                }
                List<Integer> q = pos[j];
                int k = lowerBound(q, l); // 返回q中大于等于l的第一个下标
                // [l, r] 包含第 j 个小写字母
                if (k < q.size() && q.get(k) <= r) {
                    g[i].add(j);
                }
            }
        }

        // 遍历有向图
        List<int[]> intervals = new ArrayList<>();
        boolean[] vis = new boolean[26];
        for (int i = 0; i < 26; i++) {
            if (pos[i].isEmpty()) {
                continue;
            }
            // 如果要包含第 i 个小写字母，最终得到的区间是什么？
            Arrays.fill(vis, false);
            l = n;
            r = 0;
            dfs(i, pos, g, vis);
            intervals.add(new int[]{l, r});
        }

        // 435. 无重叠区间
        // 直接计算所选子串
        List<String> ans = new ArrayList<>();
        intervals.sort((a, b) -> a[1] - b[1]);
        int preR = -1;
        for (int[] p : intervals) {
            int l = p[0];
            int r = p[1];
            if (l > preR) {
                ans.add(s.substring(l, r + 1));
                preR = r;
            }
        }
        return ans;
    }

    private int l, r;

    private void dfs(int x, List<Integer>[] pos, List<Integer>[] g, boolean[] vis) {
        vis[x] = true;
        List<Integer> p = pos[x];
        l = Math.min(l, p.get(0)); // 合并区间
        r = Math.max(r, p.get(p.size() - 1));
        for (int y : g[x]) {
            if (!vis[y]) {
                dfs(y, pos, g, vis);
            }
        }
    }

    // 开区间写法
    // 请看 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(List<Integer> a, int target) { // 返回大于等于target的最小下标
        // 开区间 (left, right)
        int left = -1;
        int right = a.size();
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // a[left] < target
            // a[right] >= target
            int mid = (left + right) >>> 1;
            if (a.get(mid) >= target) {
                right = mid; // 范围缩小到 (left, mid)
            } else {
                left = mid; // 范围缩小到 (mid, right)
            }
        }
        return right; // right 是最小的满足 a[right] >= target 的下标
    }
}