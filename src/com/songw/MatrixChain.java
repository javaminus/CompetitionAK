package com.songw;
import org.junit.Test;
//矩阵连乘问题
public class MatrixChain {
        /**
         * 求解最优值
         //* @param p: 矩阵维数信息数组
         //* @param m: 存放最优值数组, 上三角形式
         //* @param s: 存放分割位置下标的数组
         //* @return 返回最优值
         **/
    @Test
    public void Test(){
        int[] p = new int[]{5, 7, 4, 3, 5};
        int[][] m = new int[p.length][p.length];
        int[][] s = new int[p.length][p.length];
        System.out.println("最优值为： "+matrixChain(p, m, s));
        traceback(1, p.length-1, s);
    }

        public static int matrixChain(int[] p, int[][] m, int[][] s) {
            int n = p.length - 1;
            for (int i = 1; i <= n; i++)
                // 本身为0
                m[i][i] = 0;  // 初始化二维数组
            for (int r = 2; r <= n; r++) {
                for (int i = 1; i <= n - r + 1; i++) {
                    int j = i + r - 1;
                    // 先以i进行划分
                    m[i][j] = m[i + 1][j] + p[i - 1] * p[i] * p[j];  // 求出Ai到Aj的连乘
                    s[i][j] = i;  // 记录划分位置
                    for (int k = i + 1; k < j; k++) {
                        // 寻找是否有可优化的分割点
                        int t = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];  // 公式
                        if (t < m[i][j]) {
                            m[i][j] = t;
                            s[i][j] = k;
                        }
                    }
                }
            }
            return m[1][n];
        }

        /**
         * 输出 A[i:j] 的最优计算次序
         * @param i、j: 连乘矩阵下标
         * @param s: 存放分割位置下标的数组
         **/
        public static void traceback(int i, int j, int[][] s) {
            // 输出A[i:j] 的最优计算次序
            if (i == j) {
                // 递归出口
                System.out.print("A"+i);
                return;
            } else {
                System.out.print("(");
                // 递归输出左边
                traceback(i, s[i][j], s);
                // 递归输出右边
                traceback(s[i][j] + 1, j, s);
                System.out.print(")");
            }
        }


}
