package com.clapper;

/**
 * 对拍器
 */
public class Validator {
    private static int[] randomArr;
    public static void main(String[] args) {
        int n = 10000, MaxNum = 100;
        randomArr = new int[n];
        random(n, MaxNum);
        for (int i = 0; i < n - 1; i++) {
            int a = randomArr[i], b = randomArr[i + 1];
            if (add1(a, b) != add2(a, b)) {
                System.out.println("错误的用例"+a + " " + b);
                System.out.println("AC答案" + add1(a, b));
                System.out.println("WA答案" + add2(a, b));
            }
        }
    }

    private static void random(int n,int MaxNum) { // 生成一个长度为n,数组的范围为 [0，MaxNum)
        for (int i = 0; i < n; i++) {
            randomArr[i] = (int) (Math.random() * MaxNum);
        }
    }

    private static int add1(int a, int b) {
        return a + b;
    }

    private static int add2(int a, int b) {
        return a == 1 ? 0 : a + b; // 设置bug
    }
}
