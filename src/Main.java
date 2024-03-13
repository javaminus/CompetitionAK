// 1:无需package
// 2: 类名必须Main, 不可修改

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] nums = new int[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                nums[i][j] = scanner.nextInt();
            }
        }
        solve(nums, a, b, n);

    }

    private static int solve(int[][] nums, int a, int b, int n) { // 滑动窗口+单调队列
        int[][] t = new int[a][b];
        for (int i = 0; i < a; i++) {
            LinkedList<Integer> queMax = new LinkedList<>();
            LinkedList<Integer> queMin = new LinkedList<>();
            int left = 0, right = 0, index = 0;
            while (right < b) { // 遍历列
                while (!queMax.isEmpty() && queMax.peekLast() < nums[i][right]) {
                    queMax.pollLast();
                }
                while (!queMin.isEmpty() && queMin.peekLast() > nums[i][right]) {
                    queMin.pollLast();
                }
                queMax.offerLast(nums[i][right]);
                queMin.offerLast(nums[i][right]);
                while (!queMax.isEmpty() && !queMin.isEmpty() && right - left > n) {
                    if (queMax.peekFirst() == nums[i][left]) {
                        queMax.pollFirst();
                    }
                    if (queMin.peekFirst() == nums[i][left]) {
                        queMin.pollFirst();
                    }
                    left++;
                }
                t[i][index++] = queMax.peekLast() - queMin.peekLast();
                right++;
            }
        }
        System.out.println(Arrays.deepToString(t));
        return 0;
    }
}