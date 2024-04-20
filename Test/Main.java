import java.util.*;

class Main {
    static class Pos implements Comparable<Pos> {
        long x, y;

        public Pos(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Pos p) { // 从小到大排序
            if (this.x < p.x)
                return -1;
            else if (this.x > p.x)
                return 1;
            else
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int k = sc.nextInt();
            int n = sc.nextInt();
            Pos[] a = new Pos[k + 1];
            for (int i = 1; i <= k; i++) {
                long x = sc.nextLong();
                long y = sc.nextLong();
                a[i] = new Pos(x, y);
            }
            Arrays.sort(a, 1, k + 1);

            long[] sum = new long[k + 1];
            long mi = Long.MAX_VALUE;
            for (int i = 1; i <= k; i++) {
                sum[i] = sum[i - 1] + a[i].x;
                mi = Math.min(mi, a[i].y);
            }

            long ans = Long.MAX_VALUE;
            for (int i = 1; i <= k - 1; i++) {
                int p = Arrays.binarySearch(a, 1, k + 1, new Pos(a[i].y, 0));
                p = (p >= 0) ? p : -p - 1;
                if (i <= p) {
                    if (n <= p)
                        ans = Math.min(ans, sum[n]);
                    else
                        ans = Math.min(ans, sum[p] + a[i].y * (n - p));
                } else {
                    if (n <= p)
                        ans = Math.min(ans, sum[n]);
                    else
                        ans = Math.min(ans, sum[p] + a[i].x + a[i].y * (n - p - 1));
                }
            }

            long ans1 = Long.MAX_VALUE;
            if (n >= k)
                ans1 = sum[k] + mi * (n - k);
            System.out.println(Math.min(ans1, ans));
        }
        sc.close();
    }
}
