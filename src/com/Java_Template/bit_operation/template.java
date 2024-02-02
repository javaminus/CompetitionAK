package com.Java_Template.bit_operation;

/**
 * java中n>>1与n>>>1 的区别？
 *
 * 在Java中，n >> 1 和 n >>> 1 都是右移操作符，用于将二进制表示的整数向右移动指定的位数。它们之间的区别在于符号位的处理方式。
 *
 * n >> 1: 这是带符号右移操作符，它将整数的二进制表示向右移动指定的位数，同时保持最高位的符号位不变。在右移过程中，最高位是由原来的最高位填充的，即如果最高位是1，则右移后继续用1填充，如果最高位是0，则右移后继续用0填充。
 *
 * 例如，如果 n 是正整数，带符号右移会在左侧用0填充，如果 n 是负整数，带符号右移会在左侧用1填充。
 *
 * n >>> 1: 这是无符号右移操作符，它将整数的二进制表示向右移动指定的位数，同时用0填充最高位。无符号右移不考虑符号位，始终在左侧用0填充。
 *
 * 总的来说，带符号右移 (>>) 会根据原来的符号位填充左侧，而无符号右移 (>>>) 则始终用0填充左侧。选择使用哪个操作符取决于你对符号位的处理需求。
 */
import java.util.ArrayList;
import java.util.List;

class BitOperation {

    // 计算异或和
    public static int sumXor(int n) {
        // 如果 n 能被 4 整除
        if (n % 4 == 0) {
            return n;
        }
        // 如果 n 除以 4 的余数为 1
        else if (n % 4 == 1) {
            return 1;  // n^(n-1)
        }
        // 如果 n 除以 4 的余数为 2
        else if (n % 4 == 2) {
            return n + 1;  // n^(n-1)^(n-2)
        }
        return 0;  // n^(n-1)^(n-2)^(n-3)
    }

    // 计算异或和另一种实现
    public static int sumXor2(int n) {
        // 如果 n 能被 4 整除
        if (n % 4 == 0) {
            return n;
        }
        // 如果 n 除以 4 的余数为 1
        else if (n % 4 == 1) {
            return n ^ (n - 1);
        }
        // 如果 n 除以 4 的余数为 2
        else if (n % 4 == 2) {
            return n ^ (n - 1) ^ (n - 2);
        }
        return n ^ (n - 1) ^ (n - 2) ^ (n - 3);
    }

    // 将格雷码转换为整数
    public static int grayCodeToInteger(String grayCode) {
        int grayCodeLen = grayCode.length();
        StringBuilder binary = new StringBuilder();
        binary.append(grayCode.charAt(0));
        for (int i = 1; i < grayCodeLen; i++) {
            if (grayCode.charAt(i) == binary.charAt(i - 1)) {
                binary.append('0');
            } else {
                binary.append('1');
            }
        }
        return Integer.parseInt(binary.toString(), 2);
    }

    // 将整数转换为格雷码
    public static String integerToGrayCode(int integer) {
        String binary = Integer.toBinaryString(integer);
        StringBuilder grayCode = new StringBuilder();
        grayCode.append(binary.charAt(0));
        int binaryLen = binary.length();
        for (int i = 1; i < binaryLen; i++) {
            if (binary.charAt(i - 1) == binary.charAt(i)) {
                grayCode.append('0');
            } else {
                grayCode.append('1');
            }
        }
        return grayCode.toString();
    }

    // 获取格雷码列表
    public static List<Integer> getGrayCode(int n) {
        List<Integer> code = new ArrayList<>();
        code.add(0);
        code.add(1);
        for (int i = 1; i < n; i++) {
            for (int j = code.size() - 1; j >= 0; j--) {
                code.add((1 << i) + code.get(j));
            }
        }
        return code;
    }

    public static void main(String[] args) {
        // 示例用法
        int n = 3;
        List<Integer> grayCodes = getGrayCode(n);
        System.out.println("格雷码列表：" + grayCodes);
    }
}

