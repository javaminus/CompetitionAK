package com.template.数组;

/**
 * @author Minus
 * @date 2024/1/10 14:53
 */
public class 数组原地移除相同的元素 {
    public int removeDuplicates(int[] nums) {
        return process(nums, 2);
    }

/*   模板，保留k位
    举个🌰，我们令 k=2，假设有如下样例
    [1,1,1,1,1,1,2,2,2,2,2,2,3]
    首先我们先让前 2 位直接保留，得到 1,1
    对后面的每一位进行继续遍历，能够保留的前提是与当前位置的前面 k 个元素不同（答案中的第一个 1），因此我们会跳过剩余的 1，将第一个 2 追加，得到 1,1,2
    继续这个过程，这时候是和答案中的第 2 个 1 进行对比，因此可以得到 1,1,2,2
    这时候和答案中的第 1 个 2 比较，只有与其不同的元素能追加到答案，因此剩余的 2 被跳过，3 被追加到答案：1,1,2,2,3*/

    private int process(int[] nums, int k) {
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cnt < k || nums[cnt - k] != nums[i]) {
                nums[cnt++] = nums[i];
            }
        }
        return cnt;
    }
}
