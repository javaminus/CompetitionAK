package test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static String s;
    private static int m;
    private static long[][] memo;  // dp memo: memo[当前位][maxDigit]

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        long L = sc.nextLong();
        long R = sc.nextLong();

        long result = countSnakeNumbers(L, R);
        System.out.println(result);

        sc.close();
    }

    private static long countSnakeNumbers(long L, long R) {
        // Edge case: if L > R, return 0
        if (L > R) {
            return 0;
        }
        // Use the Digit DP approach to compute up to R, then subtract up to (L - 1)
        return countUpTo(R) - countUpTo(L - 1);
    }

    // countUpTo(N) 得到 [0, N] 范围内的所有 Snake 数数量
    private static long countUpTo(long N) {
        // 若边界小于 10，则无 Snake 数可计
        if (N < 10) {
            return 0;
        }
        s = Long.toString(N);
        m = s.length();

        // 初始化 memo，多加一列 11（maxDigit 可能为 0~10）
        memo = new long[m][11];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }

        // 从第 0 位开始，试图构造数字，此时允许使用到 s.charAt(i) 的最大值
        return dfs(0, 10, true, false);
    }

    /**
     *
     * @param i        当前正在处理的位索引
     * @param maxDigit 该位允许的最大数字 (比它小的数字才可以用)
     * @param isLimit  表示是否受 N 的当前位限制
     * @param isNum    用于判断是否已选择过至少一位数字
     * @return 在从第 i 位到结束的范围内，所有满足 Snake 条件的组合数
     */
    private static long dfs(int i, int maxDigit, boolean isLimit, boolean isNum) {
        // 如果已经处理完所有位，若 isNum == true 表示至少选过一位
        if (i == m) {
            return isNum ? 1 : 0;
        }

        // 如果不受限制 & 已经选过数字 & 有记录，直接返回
        if (!isLimit && isNum && memo[i][maxDigit] != -1) {
            return memo[i][maxDigit];
        }

        long res = 0;

        // 还没选数字时，可以跳过当前位 (不选任何 digit)
        if (!isNum) {
            // 注意下一状态对限制 isLimit 置 false，因为跳过这位不再受上界具体值的限制
            res += dfs(i + 1, 10, false, false);
        }

        // 如果当前位受限，就只能取到 s.charAt(i)；否则可以到 9
        int up = isLimit ? (s.charAt(i) - '0') : 9;

        // 如果是Num状态，则可从0开始选，否则需要从1开始 (避免选 leading zeros)
        int start = isNum ? 0 : 1;
        for (int d = start; d <= up; d++) {
            // 只有当 d < maxDigit 时才符合 "最左侧数字大于其他任意数字" 的思路
            // 因为我们将上一层传下来的 maxDigit 视为“前面更高位选的数字”。
            // 若 d >= maxDigit，就不满足“最高位 > 其他位”的要求
            if (d < maxDigit) {
                res += dfs(i + 1, isNum ? maxDigit : d, isLimit && (d == up), true);
            }
        }

        if (!isLimit && isNum) {
            memo[i][maxDigit] = res;
        }
        return res;
    }
}