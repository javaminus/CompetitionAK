import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    // 使用两个不同的 MOD 来降低哈希碰撞概率
    static final long MOD1 = 1000000007L;
    static final long MOD2 = 1000000009L;
    static final long BASE = 137L;

    // 方法：判断是否能通过在字符串 S 头部添加任意多个 { 'l', 'q', 'b' }
    // 将 S 转换为回文串
    public static boolean canTransform(String s) {
        int n = s.length();
        // 预处理后缀 allowed 数组：allowedSuffix[k] 表示 S 的后 k 个字符是否均属于允许范围
        boolean[] allowedSuffix = new boolean[n + 1];
        allowedSuffix[0] = true;
        for (int k = 1; k <= n; k++) {
            char c = s.charAt(n - k);
            allowedSuffix[k] = allowedSuffix[k - 1] && (c == 'l' || c == 'q' || c == 'b');
        }

        // 预处理哈希：计算 S 的前缀哈希
        long[] f1 = new long[n + 1];
        long[] f2 = new long[n + 1];
        long[] p1 = new long[n + 1];
        long[] p2 = new long[n + 1];
        p1[0] = 1; p2[0] = 1;
        for (int i = 0; i < n; i++) {
            int val = s.charAt(i) - 'a' + 1;
            f1[i+1] = (f1[i] * BASE + val) % MOD1;
            f2[i+1] = (f2[i] * BASE + val) % MOD2;
            p1[i+1] = (p1[i] * BASE) % MOD1;
            p2[i+1] = (p2[i] * BASE) % MOD2;
        }

        // 构造 s 的反转字符串 sr，并计算其前缀哈希
        String sr = new StringBuilder(s).reverse().toString();
        long[] r1 = new long[n + 1];
        long[] r2 = new long[n + 1];
        for (int i = 0; i < n; i++) {
            int val = sr.charAt(i) - 'a' + 1;
            r1[i+1] = (r1[i] * BASE + val) % MOD1;
            r2[i+1] = (r2[i] * BASE + val) % MOD2;
        }

        // 枚举 k 从 0 到 n，检查是否存在一种 k 能满足条件：
        // S 的后 k 个字符全部合法，且 S 的前 (n - k) 个字符是回文串
        // 注意：当 (n - k) = L 时，s[0...L-1] 的反转等于 sr[n-L...n-1]
        for (int k = 0; k <= n; k++) {
            if (!allowedSuffix[k]) {
                continue;
            }
            int L = n - k;  // S 的前 L 个字符需构成回文串
            if (isPalindromeHash(f1, f2, r1, r2, p1, p2, n, L)) {
                return true;
            }
        }
        return false;
    }

    // 辅助方法：通过哈希判断 s[0...L-1] 是否是回文串
    // 其中 sr 为 s 的反转字符串，通过 precomputed r1, r2 得到 sr 子串的哈希
    private static boolean isPalindromeHash(long[] f1, long[] f2, long[] r1, long[] r2,
                                            long[] p1, long[] p2, int n, int L) {
        // 哈希 s[0...L-1]
        long hashS1 = f1[L];
        long hashS2 = f2[L];

        // 从 sr 中提取 s[0...L-1] 的反转对应部分: sr segment [n - L, n - 1]
        long hashR1 = (r1[n] - (r1[n - L] * p1[L]) % MOD1 + MOD1) % MOD1;
        long hashR2 = (r2[n] - (r2[n - L] * p2[L]) % MOD2 + MOD2) % MOD2;

        return hashS1 == hashR1 && hashS2 == hashR2;
    }

    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 提高大数据量的输入效率
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        // 对每组数据进行判断
        for (int t = 0; t < T; t++) {
            String s = br.readLine().trim();
            sb.append(canTransform(s) ? "Yes" : "No").append("\n");
        }
        System.out.print(sb);
    }
}