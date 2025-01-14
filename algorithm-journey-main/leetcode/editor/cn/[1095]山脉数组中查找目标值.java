/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */

class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        // 二分搜索找到 peak
        int n = mountainArr.length();
        int peak = findPeak(0, n - 1, mountainArr);
        int leftRes = binarySearchLeft(target, 0, peak, mountainArr);
        if (leftRes != -1) return leftRes;  // 左半边有结果直接返回
        // 左半边搜不到，再看右半边
        int rightRes = binarySearchRight(target, peak + 1, n - 1, mountainArr);
        return rightRes;
    }

    // 先找到山顶的索引位置
    public int findPeak(int left, int right, MountainArray mountainArr){
        while (left < right){
            int mid = left + (right - left) / 2;
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) // 当前 mid 比右边小，mid 一定不是 peak
                left = mid + 1;
            else right = mid;
        }
        // 退出的时候 left = right
        return left;
    }
    // 左半边有序数组 找 target，最简单的二分搜索 while 里面的条件是 left <= right
    public int binarySearchLeft(int target, int left, int right, MountainArray mountainArr){
        while (left <= right){
            int mid = left + (right - left) / 2;
            if (target == mountainArr.get(mid)) return mid;
            else if (target < mountainArr.get(mid)) right = mid - 1;
            else left = mid + 1;
        }
        return -1;
    }
    // 右半边有序数组 找 target
    public int binarySearchRight(int target, int left, int right, MountainArray mountainArr){
        while (left <= right){
            int mid = left + (right - left) / 2;
            if (target == mountainArr.get(mid)) return mid;
            else if (target < mountainArr.get(mid)) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
