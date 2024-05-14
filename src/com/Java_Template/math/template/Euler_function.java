package com.Java_Template.math.template;

import java.util.Scanner;
  
/*
  定义一个测试类  给定a, b,求1 ≤ x < a^b 中有多少个x与a^b互质。由于答案可能很大,你只需要输出答案对998244353取模的结果。
  欧拉函数（得到互质个数）
 */
public class Euler_function {
    // 定义一个常量，表示取模的值  
    static int mod = 998244353;  
  
    // 主函数，程序的入口  
    public static void main(String[] args) {  
        // 创建Scanner对象，用于读取用户输入  
        Scanner sc = new Scanner(System.in);  
        // 读取第一个长整型输入a  
        long a = sc.nextLong();  
        // 读取第二个长整型输入b  
        long b = sc.nextLong();  
        // 如果a等于1，则直接输出0，因为任何数的0次方都是1  
        if(a == 1) System.out.println(0);  
        // 初始化结果res为a  
        long res = a,x = a;  

        // 循环，从2开始到x的平方根，检查x的因子  
        for(int i = 2; i <= x / i; i ++) {  
            // 如果i是x的因子  
            if(x % i == 0) {  
                // 不断除以i，直到x不能被i整除  
                while(x % i == 0) x/= i;  
                // 根据欧拉定理，将res中所有i的因子替换为i-1  
                res = res / i * (i - 1);  
            }  
        }  
        // 如果x还有大于1的因子，重复上述操作  
        if(x > 1) res = res / x * (x - 1);  

        // 输出结果，为res乘以a的b-1次方，并对mod取模  
        System.out.println(res * qmi(a,b - 1) % mod);
    }  
  
    // 快速幂运算方法，用于计算a的b次方模mod的值  
    private static long qmi(long a,long b) {  
        // 初始化结果为1模mod的值  
        long res = 1 % mod;  
        // 当b大于0时循环  
        for(;b > 0; b >>= 1) {  
            // 如果b的二进制表示当前位为1，则累乘a  
            if((b & 1) == 1) res = res * a % mod;  
            // a自乘，相当于计算a的2次方  
            a = a * a % mod;  
        }  
        // 返回结果模mod的值  
        return res % mod;  
    }  
}