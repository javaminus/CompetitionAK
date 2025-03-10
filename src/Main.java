import java.io.*;
import java.util.*;

public class Main {
    static int cnt = 0;
    static boolean[] vis = new boolean[300];
    static String s, t;
    static List<Integer>[] v = new ArrayList[300];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            Arrays.fill(vis, false);
            for (char i = 'a'; i <= 'z'; ++i) {
                v[i] = new ArrayList<>();
            }
            cnt = 1; // Initialize, note cnt starts from 1
            s = br.readLine();
            t = br.readLine();
            int n = s.length(), m = t.length();
            s = "+" + s;
            t = "+" + t; // Small habit, can be ignored, just to make the index start from 1
            for (int i = 1; i <= n; ++i) {
                vis[s.charAt(i)] = true;
                v[s.charAt(i)].add(i); // Store positions
            }
            boolean flag = false;
            for (int i = 1; i <= m; ++i) { // Check for no solution
                if (!vis[t.charAt(i)]) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                System.out.println("-1");
                continue;
            }
            int lst = 0;
            for (int i = 1; i <= m; ++i) { // Binary search for position
                int it = Collections.binarySearch(v[t.charAt(i)], lst + 1);
                if (it < 0) it = -it - 1;
                if (it >= v[t.charAt(i)].size()) {
                    lst = v[t.charAt(i)].get(0);
                    ++cnt; // If not found, start over
                } else {
                    lst = v[t.charAt(i)].get(it); // If found, continue
                }
            }
            System.out.println(cnt);
        }
    }
}