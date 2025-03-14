import java.io.*;
import java.util.*;

public class Standard {
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 和 PrintWriter 提高输入输出效率
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        // 读取测试用例数量
        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            // 读取 n 和 k，注意 k 为 1-indexed
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());

            // 预先计算阶乘，fact[i] 表示 i!；n 较大时可能溢出，但一般题目中 n 较小
            long[] fact = new long[n + 1];
            fact[0] = 1;
            boolean overflow = false;
            for (int i = 1; i <= n; i++) {
                // 检查乘法是否可能溢出，若溢出则停止计算
                if (Long.MAX_VALUE / fact[i - 1] < i) {
                    overflow = true;
                    break;
                }
                fact[i] = fact[i - 1] * i;
            }

            // 如果 n 较大或者 k 超出所有排列数量，则输出 -1
            if (!overflow && k > fact[n]) {
                out.println("-1");
                continue;
            } else if (overflow) {
                // 当发生溢出时，题目数据可以保证不会超过范围（通常 n 会比较小）
                // 若出现溢出，这里简单处理为不输出结果
                out.println("-1");
                continue;
            }

            // 转换为 0 索引方便计算
            k--;
            // 初始化存放可选数字的列表，数字为 1, 2, ..., n
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                numbers.add(i);
            }

            // 使用贪心算法构造第 k 小的排列
            StringBuilder sb = new StringBuilder();
            for (int i = n; i >= 1; i--) {
                int index = (int) (k / fact[i - 1]);
                sb.append(numbers.get(index)).append(" ");
                numbers.remove(index);
                k %= fact[i - 1];
            }

            out.println(sb.toString().trim());
        }

        // 刷新并关闭输出流
        out.flush();
        out.close();
        br.close();
    }
}