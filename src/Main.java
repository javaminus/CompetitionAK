import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        Arrays.sort(nums);
        int cnt = 0, ans = 0, left = 0;
        for (int right = 0; right < n; right++) {
            cnt++; // 移入窗口
            while (nums[right] - nums[left] >= m) {
                if (nums[left] > 0) {
                    cnt--;
                }
                left++;
            }
            if (cnt == k) {
                nums[right] = 0;
                cnt--;
                ans++;
            }
        }
        System.out.println(ans);
    }
}