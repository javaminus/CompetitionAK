import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int N = 100005;
    private static long[] a = new long[N];
    private static long[] b = new long[N];
    private static long ans;

    private static long read(BufferedReader br) throws IOException {
        long ans = 0;
        int f = 1;
        int c = br.read();
        while (c > '9' || c < '0') {
            if (c == '-') f = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            ans = ans * 10 + c - '0';
            c = br.read();
        }
        return ans * f;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        for (int i = 1; i <= n; ++i) {
            a[i] = read(br);
        }
        for (int i = n; i >= 1; --i) {
            b[i] = gcd(b[i + 1], a[i]);
        }
        for (int i = 1; i <= n; ++i) {
            ans = gcd(ans, a[i] * b[i + 1]);
        }
        System.out.println(ans / b[1]);
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}