// 1:无需package
// 2: 类名必须Main, 不可修改

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static int Mod = 998244353;

    public static void main(String[] args) {
        int N = sc.nextInt();
        int Q = sc.nextInt();
        int[] nums = new int[N];
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < N; i++) {
            nums[i] = sc.nextInt();
            cnt.put(nums[i], cnt.getOrDefault(nums[i], 0) + 1);
        }
        Arrays.sort(nums);
        int minDiss = -1;
        for (int i = 0; i <= nums[N - 1] + 1; i++) {
            if (!cnt.containsKey(i)) {
                minDiss = i;
                break;
            }
        }
        long[] predixSum = new long[minDiss + 1];
        predixSum[0] = 1;
        for (int i = 0; i < minDiss; i++) {
            predixSum[i + 1] = predixSum[i] * cnt.get(i) * (cnt.get(i) + 1) / 2 % Mod;
        }
        while (Q-- > 0) {
            int q = sc.nextInt();
            if (q > minDiss) {
                System.out.println(0);
            } else {
                int i = 0;
                for (; i < N; i++) {
                    if (nums[i] > q) {
                        break;
                    }
                }
                System.out.println(predixSum[q] * (long) Math.pow(2, N - i) % Mod);
            }
        }
    }
}