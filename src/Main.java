import java.io.*;
import java.util.*;

public class Main {
    static class FenwickTree {
        private int[] tree;
        private int n;

        public FenwickTree(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        // Increase value at index i (1-indexed) by delta.
        public void update(int i, int delta) {
            while (i <= n) {
                tree[i] += delta;
                i += i & -i;
            }
        }

        // Query sum from 1 to i (inclusive)
        public int query(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }

        // Query sum in range [l, r]
        public int query(int l, int r) {
            return query(r) - query(l - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int n = Integer.parseInt(line.trim());
        int[] a = new int[n];
        String[] parts = br.readLine().trim().split("\\s+");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i]);
        }

        int[] prefix = new int[n];
        int[] suffix = new int[n];
        HashMap<Integer, Integer> freqPrefix = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int cnt = freqPrefix.getOrDefault(a[i], 0) + 1;
            freqPrefix.put(a[i], cnt);
            prefix[i] = cnt;
        }
        HashMap<Integer, Integer> freqSuffix = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            int cnt = freqSuffix.getOrDefault(a[i], 0) + 1;
            freqSuffix.put(a[i], cnt);
            suffix[i] = cnt;
        }
        FenwickTree bit = new FenwickTree(n);
        long answer = 0;
        for (int j = 0; j < n; j++) {
            if (suffix[j] < n) {
                answer += bit.query(suffix[j] + 1, n);
            }
            bit.update(prefix[j], 1);
        }

        System.out.println(answer);
    }
}