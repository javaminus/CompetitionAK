package com.clapper;

import java.util.Random;

/**
 * @author Minus
 * @date 2024/3/9 20:28
 */
public class random {
    public static void main(String[] args) {
        System.out.println(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(10);
            System.out.print(num + " ");
        }
    }
}
