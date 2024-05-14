import java.util.ArrayList;

class Solution {
    static int MX = (int) 1e6;
    static boolean[] pn = new boolean[MX + 1]; // pn[i]==false为素数，pn[i]==true不是素数
    static ArrayList<Integer> list = new ArrayList<Integer>(); // 预处理质数表
    static { // 埃氏筛，时间复杂度MX*log(logMX)不算太高
        pn[0] = pn[1] = true;
        for (int i = 2; i * i <= MX; i++) {
            if (!pn[i]) {
                for (int j = i; j <= MX / i; j++) {
                    pn[j * i] = true;
                }
            }
        }
        for (int i = 2; i <= MX; i++) {
            if (!pn[i]) {
                list.add(i);
            }
        }
    }
    public boolean primeSubOperation(int[] nums) {
        int pre = 0; // pre 是上一个减完后的数字
        for (int num : nums) {
            if (num <= pre) {
                return false;
            }
            int idx = binarySearch(num - pre);
            if (idx < 0) {
                pre = num;
                continue;
            }
            pre = num - list.get(idx);
        }
        return true;
    }

    private int binarySearch(int target) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left - 1;
    }


}