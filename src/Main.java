import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static int[] memo;
    public static void main(String[] args) {
        int[] cnt = new int[26];
        memo = new int[26];
        Arrays.fill(memo, -1);
        for (int i = 0; i < 26; i++) {
            cnt[i] = sc.nextInt();
        }
        int ans = dfs(0, cnt);
        System.out.println(ans);
        sc.close();
    }

    private static int dfs(int i, int[] cnt) { // 代表到第i个数，可以得到的最大结果
        if (i == 26) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        // 不合成
        int ans = Math.min(Math.min(cnt[1], cnt[19]), Math.min(cnt[4], cnt[24]));
        ans = Math.max(ans, dfs(i + 1, cnt));
        // 合
        if (cnt[i] >= 2) {
            int[] temp = cnt.clone();
            temp[i] -= 2;
            temp[i + 1]++;
            ans = Math.max(ans, dfs(i, temp));
        }
        return memo[i] = ans;
    }
}