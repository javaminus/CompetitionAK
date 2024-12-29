package test;

import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int T = sc.nextInt();
        while (T-- > 0) {
            solve();
        }
    }

    public static void solve() {
        int n = sc.nextInt();
        int m = sc.nextInt();

        // 使用1-based索引，额外留出空间
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }

        // 复制数组b
        int[] b = Arrays.copyOf(a, n + 1);

        // 检查重复元素
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            if (mp.containsKey(a[i])) {
                System.out.println("NO");
                return;
            }
            mp.put(a[i], 1);
        }

        // 对b进行排序（从下标1开始）
        Arrays.sort(b, 1, n + 1);

        // 将排序后的数字做映射：to[b[i]] = b[i+m-1]
        Map<Integer, Integer> to = new HashMap<>();
        for (int i = 1; i + m - 1 <= n; i++) {
            to.put(b[i], b[i + m - 1]);
        }

        // 计算前缀数组pre，用来检查单调递增
        int[] pre = new int[n + 5];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + (i > 1 && a[i] > a[i - 1] ? 1 : 0);
        }

        // 检查长度为m的连续片段是否满足单调递增
        for (int i = 1; i + m - 1 <= n; i++) {
            // 如果该片段单调递增，则 pre[i+m-1] - pre[i] == m - 1
            if (pre[i + m - 1] - pre[i] == m - 1) {
                // 检查映射是否匹配
                if (to.containsKey(a[i]) && to.get(a[i]) == a[i + m - 1]) {
                    System.out.println("YES");
                    return;
                }
            }
        }

        // 计算后缀数组suf，用来检查单调递增（从后往前）
        int[] suf = new int[n + 5];
        for (int i = n; i >= 1; i--) {
            suf[i] = suf[i + 1] + (i < n && a[i] > a[i + 1] ? 1 : 0);
        }

        // 同理检查从后往前的单调递增
        for (int i = 1; i + m - 1 <= n; i++) {
            // 如果该片段单调递增，则 suf[i] - suf[i+m-1] == m - 1
            if (suf[i] - suf[i + m - 1] == m - 1) {
                // 检查映射是否匹配（反向）
                if (to.containsKey(a[i + m - 1]) && to.get(a[i + m - 1]) == a[i]) {
                    System.out.println("YES");
                    return;
                }
            }
        }

        System.out.println("NO");
    }
}