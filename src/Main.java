import java.util.Arrays;

public class Main {
    public static int minSum(int[] nums) {
        Arrays.sort(nums); // 对数组进行排序
        int sum = 0;       // 初始化总和
        for (int num : nums) {
            sum += num;     // 计算原始总和
        }
        while (nums.length > 1) { // 当数组中至少有两个元素时
            int maxIndex = nums.length - 1; // 找到最大值的下标
            sum -= nums[maxIndex];          // 从总和中减去最大值
            nums = Arrays.copyOf(nums, nums.length - 1); // 移除最大值
        }
        return sum; // 返回最终的总和
    }

    public static void main(String[] args) {
        int[] nums = {5, 3, 8, 4}; // 示例数组
        System.out.println(minSum(nums)); // 输出最小化后的总和
    }
}
