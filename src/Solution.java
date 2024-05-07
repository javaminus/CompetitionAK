import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];

        // 读取矩阵
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int maxSum = findMaxSubRectangle(matrix);
        System.out.println(maxSum);
    }

    // 计算最大子矩阵的和
    private static int findMaxSubRectangle(int[][] matrix) {
        int n = matrix.length;
        int maxSum = Integer.MIN_VALUE;

        // 遍历所有可能的上边界
        for (int top = 0; top < n; top++) {
            int[] temp = new int[n];

            // 遍历所有可能的下边界
            for (int bottom = top; bottom < n; bottom++) {
                // 更新 temp 数组，累加每一列的值
                for (int i = 0; i < n; i++) {
                    temp[i] += matrix[bottom][i];
                }

                // 使用 Kadane's Algorithm 计算一维数组的最大子数组和
                int maxCurrent = temp[0];
                int maxGlobal = temp[0];
                for (int i = 1; i < n; i++) {
                    maxCurrent = Math.max(temp[i], maxCurrent + temp[i]);
                    maxGlobal = Math.max(maxGlobal, maxCurrent);
                }

                // 更新最大子矩阵的和
                maxSum = Math.max(maxSum, maxGlobal);
            }
        }

        return maxSum;
    }
}
