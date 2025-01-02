class MyCalendar {
    Node root = new Node();
    int N = (int) 1e9;
    public MyCalendar() {
        // update(root, 0, N, 0, N, 0);
    }

    public boolean book(int start, int end) {
        update(root, 0, N, start, end - 1, 1);
        int ans = query(root, 0, N, start, end - 1);
        if (ans < 2) {
            return true;
        }else{
            update(root, 0, N, start, end - 1, -1);
            return false;
        }
    }

    class Node{
        Node leftNode, rightNode;
        int val, add;
    }

    void update(Node node, int leftChild, int rightChild, int left, int right, int delta) {
        if (left <= leftChild && right >= rightChild) {
            node.val += delta;
            node.add += delta;
            return;
        }
        pushdown(node);
        int mid = leftChild + (rightChild - leftChild) / 2;
        if (left <= mid) {
            update(node.leftNode, leftChild, mid, left, right, delta);
        }
        if (right > mid) {
            update(node.rightNode, mid + 1, rightChild, left, right, delta);
        }
        pushup(node);
    }

    int query(Node node, int leftChild, int rightChild, int left, int right) {
        if (left <= leftChild && right >= rightChild) {
            return node.val;
        }
        int mid = leftChild + (rightChild - leftChild) / 2, ans = 0;
        if (left <= mid) {
            ans = query(node.leftNode, leftChild, mid, left, right);
        }
        if (right > mid) {
            ans = Math.max(ans, query(node.rightNode, mid + 1, rightChild, left, right));
        }
        return ans;
    }

    void pushdown(Node node) {
        if (node.leftNode == null) {
            node.leftNode = new Node();
        }
        if (node.rightNode == null) {
            node.rightNode = new Node();
        }
        if (node.add != 0) {
            int add = node.add;
            node.leftNode.val += add;
            node.rightNode.val += add;
            node.leftNode.add += add;
            node.rightNode.add += add;
            node.add = 0;
        }
    }

    void pushup(Node node) {
        node.val = Math.max(node.leftNode.val, node.rightNode.val);
    }

}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */