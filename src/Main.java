import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static class Read {
        BufferedReader bf;
        StringTokenizer st;
        BufferedWriter bw;
        public Read() {
            bf = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }
        public String nextLine() throws IOException {
            return bf.readLine();
        }
    }
    static Read sc = new Read();
    private static int T = 1;
    public static void main(String[] args) throws IOException {
        // int T = sc.nextInt();
        while (T-- > 0) {
            solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    static int m, n;
    private static void solve() throws IOException {
        String[] ss = sc.nextLine().split(" ");
        m = Integer.parseInt(ss[0]);
        n = Integer.parseInt(ss[1]);
        int[] nums1 = new int[m];
        int[] nums2 = new int[n];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < m; i++) {
            nums1[i] = Integer.parseInt(ss[i]);
        }
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums2[i] = Integer.parseInt(ss[i]);
        }
        int k = Integer.parseInt(sc.nextLine());
        dfs(0, 0, nums1, nums2, k);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ans.length(); i++) {
            res.append(ans.charAt(i));
            if (i != ans.length() - 1) {
                res.append(" ");
            }
        }
        System.out.println(res);
    }

    static StringBuilder sb = new StringBuilder();
    private static void dfs(int i, int j, int[] nums1, int[] nums2, int k) {
        if (sb.length() == k) {
            exe();
            return;
        }
        if (j != n) {
            sb.append(nums2[j]);
            dfs(i, j + 1, nums1, nums2, k);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (i != m) {
            sb.append(nums1[i]);
            dfs(i + 1, j, nums1, nums2, k);
            sb.deleteCharAt(sb.length() - 1);
        }

    }

    static StringBuilder ans = new StringBuilder();
    private static void exe() {
        if (ans.length() == 0) {
            ans = new StringBuilder(sb);
        }else{
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) > ans.charAt(i)) {
                    ans = new StringBuilder(sb);
                } else if (sb.charAt(i) < ans.charAt(i)) {
                    return;
                }
            }
        }
    }
}