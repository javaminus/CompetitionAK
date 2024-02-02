package com.Java_Template.bit_operation;

/**
 * @author Minus
 * @date 2024/1/20 19:54
 */
public class problemImpl implements problem {

    // LCR 190. 加密运算  位运算实现加法
    @Override
    public int encryptionCalculate(int a, int b) {
        // 非进位使用异或^ , 进位就左移<<
        while (b != 0) {
            int t = (a & b) << 1; // 求进位
            a ^= b; // 异或求非进位
            b = t;
        }
        return a;
    }

    // Problem: LCR 003. 比特位计数
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // ans[i] = Integer.bitCount(i);
            ans[i] = ans[i >> 1] + (i & 1); // 偶数ans[i] = ans[i>>1], 奇数就加1
//            if (i % 2 == 1) {
//                ans[i] = ans[i / 2] + 1;
//            }else{
//                ans[i] = ans[i / 2];
//            }
        }
        return ans;
    }


    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        // 方法1，使用api
        // return Integer.reverse(n);

        // 方法2， 每次把 res 左移，把 n 的二进制末尾数字，拼接到结果 res 的末尾。然后把 n 右移。
//        int res = 0;
//        for (int i = 0; i < 32; i++) {
//            res |= (n & 1) << (n - i);
//            n >>>= 1;
//        }
//        return res;
        // 方法3 分治
        int M1 = 0x55555555; // 01010101010101010101010101010101
        int M2 = 0x33333333; // 00110011001100110011001100110011
        int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
        int M8 = 0x00ff00ff; // 00000000111111110000000011111111

        n = n >>> 1 & M1 | (n & M1) << 1;
        n = n >>> 2 & M2 | (n & M2) << 2;
        n = n >>> 4 & M4 | (n & M4) << 4;
        n = n >>> 8 & M8 | (n & M8) << 8;
        return n >>> 16 | n << 16;
    }

}
