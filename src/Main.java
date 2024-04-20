import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
// 1:无需package
// 2: 类名必须Main, 不可修改

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        //在此输入您的代码...
        int T = sc.nextInt();
        while (T-- > 0) {
            int k = sc.nextInt();
            int n = sc.nextInt();
            int[][] nums = new int[k - 1][2];
            int minY = Integer.MAX_VALUE, minY_xIndex = -1;
            long sumX = 0;
            for (int i = 0; i < k - 1; i++) {
                nums[i][0] = sc.nextInt();
                sumX += nums[i][0];
                nums[i][1] = sc.nextInt();
                if (nums[i][1] < minY) {
                    minY = nums[i][1];
                    minY_xIndex = nums[i][0];
                }
            }
            int king_x = sc.nextInt();
            int king_y = sc.nextInt();
            Arrays.sort(nums, (a, b) -> a[0] - b[0]);
            // 不挑战兽王
            long ans = minY_xIndex + (long) minY * (n - 1);
            long t = nums[0][0], minYY = nums[0][1];
            int i = 1;
            for (; i < k - 1 && i < n; i++) {
                if (nums[i][0] <= minYY) {
                    t += nums[i][0];
                    minYY = Math.min(minYY, nums[i][0]);
                }else{
                    break;
                }
            }
            ans = Math.min(ans, t + minYY * (n - i));
            // 挑战兽王
            sumX += king_x;
            sumX += (long) Math.min(minY, king_y) * (n - k + 1);
            ans = Math.min(ans, sumX);
            System.out.println(ans);
        }
        sc.close();
    }
}