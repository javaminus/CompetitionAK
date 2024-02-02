public class ClosestPalindromeFinder {

    public static void main(String[] args) {
        int x = 9; // 你可以替换成你想要的整数
        int closestPalindrome = findClosestPalindrome(x);
        System.out.println("最接近的回文数字是：" + closestPalindrome);
    }

    public static int findClosestPalindrome(int x) {
        if (x < 0) {
            return -1; // 负数没有回文数字
        }

        int smallerPalindrome = findSmallerPalindrome(x - 1);
        int largerPalindrome = findLargerPalindrome(x + 1);

        if (Math.abs(x - smallerPalindrome) <= Math.abs(x - largerPalindrome)) {
            return smallerPalindrome;
        } else {
            return largerPalindrome;
        }
    }

    private static int findSmallerPalindrome(int num) {
        while (!isPalindrome(num)) {
            num--;
        }
        return num;
    }

    private static int findLargerPalindrome(int num) {
        while (!isPalindrome(num)) {
            num++;
        }
        return num;
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
}
