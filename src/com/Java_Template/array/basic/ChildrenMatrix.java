package com.Java_Template.array.basic;

import java.io.*;
import java.math.BigInteger;
 
/*
原题连接：https://www.dotcpp.com/oj/problem3161.html
题目描述:
给定一个 n × m （n 行 m 列）的矩阵。
设一个矩阵的价值为其所有数中的最大值和最小值的乘积。
求给定矩阵的所有大小为 a × b （a 行 b 列）的子矩阵的价值的和。
答案可能很大，你只需要输出答案对 998244353 取模后的结果。
 
输入格式:
输入的第一行包含四个整数分别表示 n, m, a, b ，相邻整数之间使用一个空格分隔。
接下来 n 行每行包含 m 个整数，相邻整数之间使用一个空格分隔，表示矩阵中的每个数 Ai, j 。
输出格式
输出一行包含一个整数表示答案。
 
样例输入:
2 3 1 2
1 2 3
4 5 6
 
样例输出:
58
 
提示
1×2+2×3+4×5+5×6 = 58 。
*/
public class ChildrenMatrix {
    static BigInteger N = new BigInteger("998244353");
    static int n, m, a, b;
    static int matrix[][];
    static int colMaxMatrix[][];
    static int colMinMatrix[][]; // 每个值为 一行b列 矩阵中的最小值
     
    static int rowNum;
    static int colNum;
 
    static int childMaxMatrix[][]; // 每个值为 一个 a行b列矩阵中的最大值
    static int childMinMatrix[][]; // 每个值为 一个 a行b列矩阵中的最小值
 
    static BigInteger sum = new BigInteger("0");
    static int max;
    static int min;
 
    // 输入流
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // 分割输入流
    static StreamTokenizer st = new StreamTokenizer(br);
    // 输出流
    static PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
 
    static int nextInt() throws Exception {
        st.nextToken(); // 开始遍历输入流中的数据, 默认标记中 数据为0与null, 以" "或者"\n"为分隔符查找到下一个标记, 然后返回之前的数值
        // nval:
        // 如果当前标记是一个数值，则nval属性将存储该数值的值。如果当前标记不是一个数值，或者如果没有下一个标记可用，则nval将被设置为Double.NaN。
        // sval:
        // 如果当前标记是一个字符串，则sval属性将存储该字符串的值。如果当前标记不是一个字符串，或者如果当前标记不是一个普通标记，则sval将为null。
        return (int) st.nval;
    }
 
    public static void main(String[] args) throws Exception {
        // 1. 接收尺寸输入
        n = nextInt();
        m = nextInt();
        a = nextInt();
        b = nextInt();
 
        // 2. 接收矩阵数据输入
        matrix = new int[n][m];
        InputMatrix();
 
        // 3. 核心: 
        GetColMatrix();
        GetChildMatrix();
        GetSum();
         
        pw.println(sum.mod(N));
        pw.flush(); // 刷新输出流，确保内容被输出到控制台
    }
 
    private static void InputMatrix() throws Exception {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                matrix[i][j] = nextInt();
            }
        }
 
    }
 
    // 先求每行b列, 得到对应的最大最小
    // 例如: n = m = 5, a = b = 3
    /*
        3 5 1 6 1
        1 2 4 7 6
        9 4 8 4 6
        1 3 7 1 3
        4 5 8 9 2
    */
     
    // 第一行为: 3 5 1 6 1. 滑块宽度为3
    // 3 5 1 中最大为5, 最小为1
    // colMaxMatrix[1][0]: 5
    // 滑块移动: 5 1 6, 最大为6
    // colMaxMatrix[1][1]: 6
    // 滑块移动: 1 6 1, 最大为6
    // colMaxMatrix[1][2]: 6
    // 滑块无法移动, colMaxMatrix 第一行结束
     
    // 换句话说, colMaxMatrix 每一行与 原 Matrix 一一对应, 列为 每b列的浓缩
    private static void GetColMatrix() {
        // 初始化最大与最小矩阵
        colMaxMatrix = new int[n][m];
        colMinMatrix = new int[n][m];
         
        // 由于是压缩列数, 因此行的数量没有发生改变
        for (int i = 0; i < n; ++i) {
            int col = 0; //  
            for (int j = 0; j + b - 1 < m; ++j) {
                // 每个滑块的最大值与最小值的默认都是第一个
                max = matrix[i][j];
                min = matrix[i][j];
                 
                // 在滑块内寻找最大与最小值
                for (int z = j; z < j + b; ++z) {
                    if (max < matrix[i][z]) {
                        max = matrix[i][z];
                    }
                    if (min > matrix[i][z]) {
                        min = matrix[i][z];
                    }
                }
 
                // 填入最大值与最小值
                colMaxMatrix[i][col] = max;
                colMinMatrix[i][col] = min;
                col++;     
            }
            colNum = col; // 确定新矩阵的有效列数
        }
    }
 
    // 再结合行数, 方法与之前相同, 只是压缩的方向变为了行, 而不是列, 以此得到的矩阵的对应值 就是 每个a*b矩阵的最大值
    // 例如: 得到的新矩阵: childMaxMatrix[0][0]对应值: 原矩阵中的 MaxMatrix[0-(a-1)][0-(b-1)]中的最大值
    private static void GetChildMatrix() {
        childMaxMatrix = new int[n][m];
        childMinMatrix = new int[n][m];
        // 这次是结合 行数, 因此是从列数开始遍历
        for (int j = 0; j < colNum; ++j) {
            int row = 0;
            for (int i = 0; i + a - 1 < n; ++i) {
                // 填入最大值与最小值记得回归默认值
                max = colMaxMatrix[i][j];
                min = colMinMatrix[i][j];
                for(int z = i; z < i+a; ++z) {
                    if (max < colMaxMatrix[z][j]) {
                        max = colMaxMatrix[z][j];
                    }
                    if (min > colMinMatrix[z][j]) {
                        min = colMinMatrix[z][j];
                    }
                }
                 
                // 填入最大值与最小值
                childMaxMatrix[row][j] = max;
                childMinMatrix[row][j] = min;
                row++;
            }
            rowNum = row;
        }
    }
 
    // 最大与最小一一对应相乘, 相乘最开始越阈了, 使用 BigInteger解决问题
    private static void GetSum() {
        for(int i = 0; i< rowNum; ++i)
        {
            for(int j = 0; j<colNum; ++j)
            {
                BigInteger num1 = new BigInteger(String.valueOf(childMaxMatrix[i][j]));
                BigInteger num2 = new BigInteger(String.valueOf(childMinMatrix[i][j]));
                sum = sum.add(num1.multiply(num2));  
            }
        }
    }
}