class SegmentTree{
    int[] tree;
    int[] nums;
    int n;
    public SegmentTree(int[] nums) {
        n = nums.length;
        this.nums = nums;
        tree = new int[n * 4];
        build(1, 0, n - 1);
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        }else{
            int mid = start + (end - start) / 2;
            int leftChild = node * 2;
            int rightChild = node * 2 + 1;
            build(leftChild, start, mid);
            build(rightChild, mid + 1, end);
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    private int queryTree(int node, int start, int end, int left, int right) {
        if (right < start || left > end) {
            return 0;
        }
        if (left <= start && right >= end) {
            return tree[node];
        }
        int mid = start + (end - start) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;
        int leftSum = queryTree(leftChild, start, mid, left, right);
        int rightSum = queryTree(rightChild, mid + 1, end, left, right);
        return leftSum + rightSum;
    }

    private void updateTree(int node, int start, int end, int index, int value) {
        if (start == end) {
            tree[node] = value;
            nums[index] = value;
        }else{
            int mid = start + (end - start) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            if (index >= start && index <= mid) {
                updateTree(leftChild, start, mid, index, value);
            }else{
                updateTree(rightChild, mid + 1, end, index, value);
            }
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    private void updateRangeeTree(int node, int start, int end, int left, int right, int delta) {
        if (start > end || start > right || end < left) {
            return;
        }
        if (start == end) {
            nums[start] += delta;
            tree[node] += delta;
        }else{
            int mid = start + (end - start) / 2;
            int leftChild = 2 * node;
            int rightChid = 2 * node + 1;
            updateRangeeTree(leftChild, start, mid, left, right, delta);
            updateRangeeTree(rightChid, mid + 1, end, left, right, delta);
            tree[node] = tree[leftChild] + tree[rightChid];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 11};
        int n = nums.length;
        SegmentTree segmentTree = new SegmentTree(nums);
        System.out.println(segmentTree.queryTree(1, 0, n - 1, 1, 4));
        segmentTree.updateTree(1, 0, n - 1, 2, 6);
        System.out.println(segmentTree.queryTree(1, 0, n - 1, 1, 4));
        segmentTree.updateRangeeTree(1, 0, n - 1, 1, 3, 2);
        System.out.println(segmentTree.queryTree(1, 0, n - 1, 1, 4));
    }



}