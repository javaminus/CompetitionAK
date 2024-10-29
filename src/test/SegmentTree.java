package test;

import java.util.Scanner;

public class SegmentTree {
    static int N = 2000001;
    static Node[] nodes = new Node[N];
    static int n, m;
    static int[] nums;

    static class Node {
        int l, r;
        long maxLeft, maxRight, sum, ans;

        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    static void build(int l, int r, int p) {
        nodes[p] = new Node(l, r);
        if (l == r) {
            nodes[p].sum = nums[l];
            nodes[p].maxLeft = nodes[p].maxRight = nodes[p].ans = nodes[p].sum;
            return;
        }
        int mid = (l + r) / 2;
        build(l, mid, p * 2);
        build(mid + 1, r, p * 2 + 1);
        putIn(p);
    }

    static void putIn(int p) {
        nodes[p].sum = nodes[p * 2].sum + nodes[p * 2 + 1].sum;
        nodes[p].maxLeft = Math.max(nodes[p * 2].maxLeft, nodes[p * 2].sum + nodes[p * 2 + 1].maxLeft);
        nodes[p].maxRight = Math.max(nodes[p * 2 + 1].maxRight, nodes[p * 2].maxRight + nodes[p * 2 + 1].sum);
        nodes[p].ans = Math.max(Math.max(nodes[p * 2].ans, nodes[p * 2 + 1].ans), nodes[p * 2].maxRight + nodes[p * 2 + 1].maxLeft);
    }

    static Node query(int p, int pl, int pr) {
        if (pl <= nodes[p].l && pr >= nodes[p].r) {
            return nodes[p];
        }
        int mid = (nodes[p].l + nodes[p].r) / 2;
        if (pr <= mid) {
            return query(p * 2, pl, pr);
        } else if (nodes[p].l > mid) {
            return query(p * 2 + 1, pl, pr);
        } else {
            Node node = new Node(0, 0);
            Node a = query(p * 2, pl, pr);
            Node b = query(p * 2 + 1, pl, pr);
            node.maxLeft = Math.max(a.maxLeft, a.sum + b.maxLeft);
            node.maxRight = Math.max(b.maxRight, b.sum + a.maxRight);
            node.ans = Math.max(a.maxRight + b.maxLeft, Math.max(a.ans, b.ans));
            return node;
        }
    }

    static void change(int p, int x, int y) {
        if (nodes[p].l == nodes[p].r) {
            nodes[p].maxLeft = nodes[p].maxRight = nodes[p].ans = nodes[p].sum = y;
            return;
        }
        int mid = (nodes[p].l + nodes[p].r) / 2;
        if (x <= mid) {
            change(p * 2, x, y);
        } else {
            change(p * 2 + 1, x, y);
        }
        putIn(p);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        nums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            nums[i + 1] = scanner.nextInt();
        }
        build(1, n, 1);
        while (m-- > 0) {
            int choose = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (choose == 1) {
                if (x > y) {
                    int temp = x;
                    x = y;
                    y = temp;
                }
                System.out.println(query(1, x, y).ans);
            } else {
                change(1, x, y);
            }
        }
    }
}