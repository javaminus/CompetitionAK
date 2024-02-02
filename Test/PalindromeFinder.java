import java.util.ArrayList;
import java.util.List;

public class PalindromeFinder {

    public static void main(String[] args) {
        int a = 100; // 范围起始值
        int b = 1000; // 范围结束值

        PalindromeResult result = findAllPalindromesAndNearest(a, b);

        System.out.println("在范围 [" + a + ", " + b + "] 之间的所有回文数是：" + result.getPalindromeList());
        System.out.println("小于 " + a + " 的最近的回文数是：" + result.getNearestSmallerPalindrome());
        System.out.println("大于 " + b + " 的最近的回文数是：" + result.getNearestLargerPalindrome());
    }

    public static PalindromeResult findAllPalindromesAndNearest(int a, int b) {
        List<Integer> palindromeList = new ArrayList<>();

        if (a < 0 || b < 0 || a > b) {
            System.out.println("无效的输入范围");
            return new PalindromeResult(palindromeList, -1, -1);
        }

        for (int i = a; i <= b; i++) {
            if (isPalindrome(i)) {
                palindromeList.add(i);
            }
        }

        int nearestSmallerPalindrome = findNearestSmallerPalindrome(a);
        int nearestLargerPalindrome = findNearestLargerPalindrome(b);

        return new PalindromeResult(palindromeList, nearestSmallerPalindrome, nearestLargerPalindrome);
    }

    private static boolean isPalindrome(int num) {
        String numStr = String.valueOf(num);
        int left = 0;
        int right = numStr.length() - 1;

        while (left < right) {
            if (numStr.charAt(left) != numStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    private static int findNearestSmallerPalindrome(int num) {
        while (num >= 0) {
            if (isPalindrome(num)) {
                return num;
            }
            num--;
        }
        return -1; // 没有找到小于num的回文数
    }

    private static int findNearestLargerPalindrome(int num) {
        while (true) {
            if (isPalindrome(num)) {
                return num;
            }
            num++;
        }
    }
}

class PalindromeResult {
    private List<Integer> palindromeList;
    private int nearestSmallerPalindrome;
    private int nearestLargerPalindrome;

    public PalindromeResult(List<Integer> palindromeList, int nearestSmallerPalindrome, int nearestLargerPalindrome) {
        this.palindromeList = palindromeList;
        this.nearestSmallerPalindrome = nearestSmallerPalindrome;
        this.nearestLargerPalindrome = nearestLargerPalindrome;
    }

    public List<Integer> getPalindromeList() {
        return palindromeList;
    }

    public int getNearestSmallerPalindrome() {
        return nearestSmallerPalindrome;
    }

    public int getNearestLargerPalindrome() {
        return nearestLargerPalindrome;
    }
}
