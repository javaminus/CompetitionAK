import java.io.*;
import java.util.Arrays;
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
    private static void solve() throws IOException {
        int N = 200;
        int V = 1000;
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = (int) (Math.random() * V);
        }
        nim2(nums);
    }

    public static void nim2(int[] nums) throws IOException {
        int mx = Arrays.stream(nums).max().getAsInt();
        int[] sg = new int[mx + 1];
        boolean[] vis = new boolean[mx + 1];
        for (int i = 1; i <= mx; i++) {
            Arrays.fill(vis, false);
            for (int j = 0; j < i; j++) {
                vis[j] = true;
            }
            for (int s = 0; s <= mx; s++) {
                if (!vis[s]) {
                    sg[i] = s;
                    break;
                }
            }
        }
        for (int i = 0; i < mx + 1; i++) {
            sc.bw.write(i + " " + sg[i] + "\n");
        }
    }
}