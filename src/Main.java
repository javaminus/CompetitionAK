import java.util.Scanner;

public class Main { // https://codeforces.com/problemset/problem/546/D
    static final int MAX = 5000001;  // 最大值，定义求解上限
    static int[] prime = new int[MAX];  // 用于存储质数数组
    static int num = 0;  // 质数数量计数器
    static int[] sum = new int[MAX];  // 前缀和数组，存储每个数的质因子个数累计和
    static boolean[] isp = new boolean[MAX];  // 布尔数组，用于标记质数候选，true表示可能为质数

    // 基本筛法和前缀和计算方法
    static void getprime(int n) {
        // 初始化：将2到n-1的所有数标记为质数候选
        // 在 Java 中，boolean 数组默认值为 false，这里将 true 表示可能为质数
        for (int i = 2; i < n; i++) {
            isp[i] = true;
        }
        // 筛法过程：计数每个数的不同质因子数量
        for (int i = 2; i < n; i++) {
            if (isp[i]) {
                // 如果 i 是质数，则存入质数数组
                prime[++num] = i;
                // 对于质数，其质因子个数为 1
                sum[i] = 1;
            }
            for (int j = 1; j <= num; j++) {
                // 如果 i 与 prime[j] 的乘积超出范围，则结束循环
                if ((long)i * prime[j] >= n) {
                    break;
                }
                int index = i * prime[j];
                // 将该数标记为合数
                isp[index] = false;
                // 计算质因子个数：为 i 的质因子个数加 1
                sum[index] = sum[i] + 1;
                // 若 i 能被 prime[j] 整除，则跳出循环避免重复计数
                if (i % prime[j] == 0) {
                    break;
                }
            }
        }
        // 计算前缀和：对于每个 i，sum[i] 为 1 到 i 的质因子个数的累计和
        for (int i = 1; i < n; i++) {
            sum[i] += sum[i - 1];
        }
    }

    public static void main(String[] args) {
        // 预处理并构建筛法至 MAX
        getprime(MAX);

        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        while (T-- > 0) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            // 输出 a 与 b 之间前缀和的差值
            System.out.println(sum[a] - sum[b]);
        }
        scanner.close();
    }
}