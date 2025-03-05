import java.io.*;
import java.util.*;

public class Standard {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n + 2];
        int[] dp1 = new int[n + 2];
        int[] dp2 = new int[n + 2];
        int ans = 0;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            if (a[i] > a[i - 1]) {
                dp1[i] = dp1[i - 1] + 1;
            } else {
                dp1[i] = 1;
            }
        }

        for (int i = n; i >= 1; i--) {
            if (a[i] < a[i + 1]) {
                dp2[i] = dp2[i + 1] + 1;
            } else {
                dp2[i] = 1;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (a[i - 1] < a[i + 1]) {
                ans = Math.max(ans, dp1[i - 1] + dp2[i + 1]);
            }
            ans = Math.max(ans, Math.max(dp1[i], dp2[i]));
        }

        System.out.println(ans);
    }
}