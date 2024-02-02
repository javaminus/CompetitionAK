public class NearestPalindromeFinder {

    public static void main(String[] args) {
        int[] nums = {123, 456, 789}; // 你可以替换成你的数组
        int nearestPalindrome = findNearestPalindrome(nums);
        System.out.println("距离数组中所有数字最近的回文数是：" + nearestPalindrome);
    }

    public static int findNearestPalindrome(int[] nums) {
        int minDiff = Integer.MAX_VALUE;
        int nearestPalindrome = 0;

        for (int num : nums) {
            int currentDiff = Math.abs(num - findNearestPalindrome(num));
            if (currentDiff < minDiff) {
                minDiff = currentDiff;
                nearestPalindrome = findNearestPalindrome(num);
            }
        }

        return nearestPalindrome;
    }

    private static int findNearestPalindrome(int num) {
        int smallerPalindrome = findSmallerPalindrome(num - 1);
        int largerPalindrome = findLargerPalindrome(num + 1);

        if (Math.abs(num - smallerPalindrome) <= Math.abs(num - largerPalindrome)) {
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
