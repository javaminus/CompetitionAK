import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }
    
    void run() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            solve(br, out);
        }
        out.flush();
    }
    
    void solve(BufferedReader br, PrintWriter out) throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int tot = n + m + 1;
        // 为了直接使用 1-indexed，下标范围 1..tot，额外留出 tot+2, tot+3 的空间
        long[] a   = new long[tot + 3];
        long[] b   = new long[tot + 3];
        long[] sum = new long[tot + 3]; // sum[i] = sum[i-1] + (a[i] > b[i] ? 1 : 0)
        long[] pre = new long[tot + 3]; // pre[i] = pre[i-1] + max(a[i], b[i])
        long[] A   = new long[tot + 3]; // A[i] = A[i+1] + a[i]
        long[] B   = new long[tot + 3]; // B[i] = B[i+1] + b[i]
        
        // 读入数组 a 和 b，注意下标从1开始
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= tot; i++) {
            a[i] = Long.parseLong(st.nextToken());
        }
        
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= tot; i++) {
            b[i] = Long.parseLong(st.nextToken());
        }
                
        // 计算 sum[i] = sum[i-1] + (a[i] > b[i] ? 1 : 0)
        for (int i = 1; i <= tot; i++) {
            sum[i] = sum[i - 1] + (a[i] > b[i] ? 1 : 0);
        }
        
        // pre[i] = pre[i-1] + max(a[i], b[i])
        for (int i = 1; i <= tot; i++) {
            pre[i] = pre[i - 1] + Math.max(a[i], b[i]);
        }
        
        // 初始化 A 和 B，先设置 A[tot+1]，B[tot+1] 为0
        A[tot + 1] = B[tot + 1] = 0;
        for (int i = tot; i >= 1; i--) {
            A[i] = A[i + 1] + a[i];
            B[i] = B[i + 1] + b[i];
        }
        
        // 对于每个候选人被移除的情况，二分查找一个分界点 pos
        for (int i = 1; i <= tot; i++) {
            int l = 0, r = tot, pos = -1;
            int P = 0, Q = 0;
            while (l <= r) {
                int mid = (l + r) >> 1;  // 等同于 (l + r) / 2
                int p = (int) (mid >= 0 ? sum[mid] : 0); // 候选人更适合程序员的人数
                int q = mid - p; // 剩下的人数即更适合测试员的人数
                
                if (mid >= i) {
                    // 如果当前考察区间包含被移除的候选人 i，则扣除其贡献
                    if (a[i] > b[i]) {
                        p--;
                    } else {
                        q--;
                    }
                }
                
                // 如果某一角色已经达到要求，则记录分界点 pos
                if (p >= n || q >= m) {
                    P = p;
                    Q = q;
                    pos = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            
            // 计算答案
            long res = pre[pos] - (pos >= i ? Math.max(a[i], b[i]) : 0);
            // 根据 Q 是否等于 m 判断测试员已满
            if (Q == m) {
                res += A[pos + 1] - ((pos + 1 <= i) ? a[i] : 0);
            } else {
                res += B[pos + 1] - ((pos + 1 <= i) ? b[i] : 0);
            }
            out.print(res + " ");
        }
        out.println();
    }
}