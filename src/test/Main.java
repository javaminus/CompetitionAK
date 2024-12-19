package test;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        generateRandomStrings(100);
    }

    private static void generateRandomStrings(int count) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(10000000) + 1; // Generate a number between 1 and 10
            int value = random.nextInt(10000000) + 1; // Generate a value between 1 and 5
            sb.append(num).append("(").append(value).append("),");
        }
        sb.deleteCharAt(sb.length() - 1); // Remove the last comma
        sb.append("]");
        System.out.println(sb.toString());
    }
}
