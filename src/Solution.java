import java.util.Arrays;

public class Solution {

    public int findMinDifference(int[] primes) {
        int n = primes.length;
        // 如果数组中只有一个素数，或者没有素数，则无法进行操作，极差为0
        if (n <= 1) return 0;

        // 将素数数组排序
        Arrays.sort(primes);

        // 只留下最小的和最大的的素数，其余的素数两两配对合并
        int minPrime = primes[0];
        int maxPrime = primes[n - 1];

        // maxProduct表示合并操作后的最大合数，初始化为最大素数自身
        long maxProduct = maxPrime;

        // 从第二小的素数到倒数第二大的素数，进行合并操作
        for (int i = 1; i < n - 1; i += 2) {
            long product = (long) primes[i] * primes[i + 1];
            maxProduct = Math.max(maxProduct, product);
        }

        // 返回合并操作后的极差
        return (int) (maxProduct - minPrime);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] primes = {2, 3, 5, 7}; // 示例素数数组
        int result = solution.findMinDifference(primes);
        System.out.println("Minimum Difference: " + result);
    }
}