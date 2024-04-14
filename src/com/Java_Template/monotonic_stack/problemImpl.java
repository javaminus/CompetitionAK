package com.Java_Template.monotonic_stack;

import java.util.*;

/**
 * @author Minus
 * @date 2024/1/24 13:32
 */
public class problemImpl implements problem {

    // Problem: 2866. 美丽塔 II
    @Override
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        // 如果i就是峰值，最大的前后缀  前后缀+动态规划
        int n = maxHeights.size();
        long ans = 0;
        long[] prefix = new long[n];
        long[] suffix = new long[n];
        ArrayDeque<Integer> stack1 = new ArrayDeque<>();
        ArrayDeque<Integer> stack2 = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack1.isEmpty() && maxHeights.get(i) < maxHeights.get(stack1.peek())) {
                stack1.pop();
            }
            if (stack1.isEmpty()) { // 思考什么时候栈为空？ 答：当前元素比它前面的元素逗笑的时候或则i==0时，例如"5，4，3，2，1"
                prefix[i] = (long) (i + 1) * maxHeights.get(i);
            }else{ // !stack1.isEmpty()
                prefix[i] = prefix[stack1.peek()] + (long) (i - stack1.peek()) * maxHeights.get(i);
            }
            stack1.push(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && maxHeights.get(i) < maxHeights.get(stack2.peek())) {
                stack2.pop();
            }
            if (stack2.isEmpty()) {
                suffix[i] = (long) (n - i) * maxHeights.get(i);
            }else{
                suffix[i] = suffix[stack2.peek()] + (long) (stack2.peek() - i) * maxHeights.get(i);
            }
            stack2.push(i);
            ans = Math.max(ans, prefix[i] + suffix[i] - maxHeights.get(i));
        }
        return ans;
    }

    // Problem: 496. 下一个更大元素 I
    @Override
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] ans = new int[m];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums2[stack.peek()] < nums2[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                map.put(nums2[i], -1);
            }else{
                map.put(nums2[i], nums2[stack.peek()]);
            }
            stack.push(i);
        }
        for (int i = 0; i < m; i++) {
            ans[i] = map.get(nums1[i]);
        }
        return ans;
    }

    /**劳累一天为+1，不劳累一天为-1【前缀和】
     【单调栈】栈中存在可能的最长子数组的左端端点，从栈底到栈顶是单调递减的，当s[x]<栈顶时入栈
     */
    @Override
    public int longestWPI(int[] hours) {
        int n=hours.length;
        int[] s=new int[n+1]; //前缀和
        Deque<Integer> st=new LinkedList<>(); //栈存放下标
        st.push(0);
        for(int i=1;i<=n;++i){
            s[i]=s[i-1]+(hours[i-1]>8?1:-1);
            if(s[i]<s[st.peek()]) st.push(i);
        }
        //倒序遍历前缀和，如果s[i]比栈顶大，栈顶出栈并更新答案
        int res=0;
        for(int i=n;i>0;--i){
            while(!st.isEmpty()&&s[i]>s[st.peek()]){
                res=Math.max(res,i-st.pop()); //[栈顶,i)可能是最长子数组
            }
        }
        return res;
    }

    // 42. 接雨水
    @Override
    public int trap(int[] height) {
        int n = height.length;
        int ans = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                Integer pop = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int curWidth = i - left - 1; // 当前的宽度
                int curHeight = Math.min(height[stack.peek()], height[i]) - height[pop]; // 当前的长度
                ans += curWidth * curHeight;
            }
            stack.push(i);
        }
        return ans;
    }

    /*  100273. 边界元素是最大值的子数组数目
        给你一个 正 整数数组 nums 。
        请你求出 nums 中有多少个子数组，满足子数组中 第一个 和 最后一个 元素都是这个子数组中的 最大 值。
 */
    public long numberOfSubarrays(int[] nums) {
        int n = nums.length;
        long ans = n;
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.offerLast(new int[]{Integer.MAX_VALUE, 1}); // 添加哨兵，可以不用判空
        for (int x : nums) {
            while (x > stack.peekLast()[0]) { // 如果当前元素大于栈底元素，那么前面的元素就没用了
                stack.pollLast();
            }
            if (x == stack.peekLast()[0]) {
                ans += stack.peekLast()[1]++;
            }else{ // 小于栈顶
                stack.offerLast(new int[]{x, 1});
            }
        }
        return ans;
    }


}
