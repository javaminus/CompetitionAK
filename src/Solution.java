import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Tuple[] a = new Tuple[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            a[i] = new Tuple(interval.get(0), interval.get(1), interval.get(2), i);
        }
        Arrays.sort(a, (p, q) -> p.r - q.r);
        Pair[][] dp = new Pair[n + 1][5];
        Arrays.setAll(dp[0], e -> new Pair(0, new ArrayList<>()));
        for (int i = 0; i < n; i++) {
            Tuple t = a[i];
            int k = search(a, i, t.l);
            dp[i + 1][0] = new Pair(0, new ArrayList<>());
            for (int j = 1; j < 5; j++) {
                long s1 = dp[i][j].sum;
                long s2 = dp[k + 1][j - 1].sum + t.weight;
                if (s1 > s2) {
                    dp[i + 1][j] = dp[i][j];
                    continue;
                }
                // List<Integer> newId = dp[k + 1][j - 1].ids; 这样写会报错
                List<Integer> newId = new ArrayList<>(dp[k + 1][j - 1].ids);
                newId.add(t.i);
                Collections.sort(newId);
                if (s1 == s2 && compareLists(dp[i][j].ids, newId) < 0) {
                    newId = dp[i][j].ids;
                }
                dp[i + 1][j] = new Pair(s2, newId);
            }
        }
        return dp[n][4].ids.stream().mapToInt(v -> v).toArray();
    }

    // 比较两个 List 的字典序
    private int compareLists(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) - b.get(i);
            }
        }
        return a.size() - b.size();
    }

    private int search(Tuple[] a, int right, int upper) {
        int left = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (a[mid].r < upper) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left - 1;
    }

    class Tuple{
        int l, r, weight, i;
        public Tuple(int l, int r, int weight, int i) {
            this.l = l;
            this.r = r;
            this.weight = weight;
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return l == tuple.l && r == tuple.r && weight == tuple.weight && i == tuple.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(l, r, weight, i);
        }
    }

    class Pair{
        long sum;
        List<Integer> ids;

        public Pair(long sum, List<Integer> ids) {
            this.sum = sum;
            this.ids = ids;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return sum == pair.sum && Objects.equals(ids, pair.ids);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sum, ids);
        }
    }

}