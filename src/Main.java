import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Main {
    static final long MOD = 1000000007;
    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine().trim();
        String[] parts = line.split("],");
        if(parts.length < 2){
            System.out.println(0);
            return;
        }
        String numStr = parts[0].substring(1);
        String[] numsStr = numStr.split(",");
        int n = numsStr.length;
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(numsStr[i].trim());
        }
        String colorStr = parts[1].trim();
        if(colorStr.startsWith("\"") && colorStr.endsWith("\""))
            colorStr = colorStr.substring(1, colorStr.length()-1);
        int preRedMod = 0;
        int countOdd = 0;
        int countEven = 0;
        for(int i = 0; i < n; i++){
            char c = colorStr.charAt(i);
            if(c == 'R'){
                preRedMod = (preRedMod + arr[i]) % 2;
            } else {
                if(arr[i] % 2 == 0){
                    countEven++;
                } else {
                    countOdd++;
                }
            }
        }
        long waysEven, waysOdd;
        long evenSubsets = modPow(2, countEven); // 偶数选择的个数
        if(countOdd > 0){
            waysEven = modPow(2, countOdd - 1); // 有一个必选，然后剩下n - 1个选与不选
            waysOdd = waysEven;
        } else {
            waysEven = 1;
            waysOdd = 0;
        }
        int required = (2 - preRedMod) % 2;
        long whiteWays = (required == 0 ? waysEven : waysOdd);
        long ans = (evenSubsets * whiteWays) % MOD;
        System.out.println(ans);
    }
    static long modPow(long base, int exp){
        long res = 1;
        base = base % MOD;
        while(exp > 0){
            if((exp & 1) == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return res;
    }
}