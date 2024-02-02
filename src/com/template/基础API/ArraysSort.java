package com.template.基础API;

import java.util.ArrayList;
import java.util.Arrays;

public class ArraysSort {

    // Arrays.sort(intervals, (x, y) -> x[1] - y[1]); 返回值大于0就交换，否则不交换！
    //
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (x, y) -> x[0] == y[0] ? x[1] - y[1] : x[0] - y[0]);
        ArrayList<int[]> ans = new ArrayList<>();
        for (int[] p : people) ans.add(p[1], p);
        return ans.toArray(new int[0][0]);
    }
    public void arraysSort(int[] nums) {
        // Arrays.sort(nums, (a, b) -> a - b);
    }
}
