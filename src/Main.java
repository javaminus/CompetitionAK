import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for (int _ = 0; _ < t; _++) {
            String sStr = br.readLine().trim();
            int n = sStr.length();
            int[] s = new int[n];
            for (int i = 0; i < n; i++) {
                s[i] = sStr.charAt(i) - '0';
            }
            int m = Integer.parseInt(br.readLine());
            String l = br.readLine();
            String r = br.readLine();
            int mx = 0;
            for (int i = 0; i < m; i++) {
                int li = l.charAt(i) - '0';
                int ri = r.charAt(i) - '0';
                int nmx = mx;
                for (int c = li; c <= ri; c++) {
                    int cur = mx;
                    while (cur < n && s[cur] != c) {
                        cur++;
                    }
                    nmx = Math.max(nmx, cur);
                }
                mx = nmx + 1;
            }
            System.out.println(mx > n ? "YES" : "NO");
        }
    }
}
