package test;

import java.io.*;
import java.util.*;

public class MaxSubarraySum {
    private static final int maxn = (500000 + 10) * 4;
    private static long[] num = new long[maxn];
    private static long[] addval = new long[maxn];
    private static Node[] tree = new Node[maxn];

    private static class Node {
        int l, r;
        long lx, sum, rx, ans;

        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    private static void build(int k, int left, int right) {
        tree[k] = new Node(left, right);
        if (left == right) {
            tree[k].sum = tree[k].lx = tree[k].rx = tree[k].ans = num[left];
            return;
        }
        int mid = (left + right) >>> 1;
        build(k * 2, left, mid);
        build(k * 2 + 1, mid + 1, right);
        tree[k].sum = tree[k * 2].sum + tree[k * 2 + 1].sum;
        tree[k].lx = Math.max(tree[k * 2].lx, tree[k * 2].sum + tree[k * 2 + 1].lx);
        tree[k].rx = Math.max(tree[k * 2 + 1].rx, tree[k * 2 + 1].sum + tree[k * 2].rx);
        tree[k].ans = Math.max(Math.max(tree[k * 2].ans, tree[k * 2 + 1].ans), tree[k * 2].rx + tree[k * 2 + 1].lx);
    }

    private static Node query(int k, int lt, int rt) {
        if (lt <= tree[k].l && rt >= tree[k].r) return tree[k];
        Node a = new Node(-1, -1), b = new Node(-1, -1), ans = new Node(-1, -1);
        a.lx = a.rx = a.ans = a.ans = Long.MIN_VALUE;
        b.lx = b.rx = b.ans = b.ans = Long.MIN_VALUE;
        a.sum = b.sum = 0;
        ans.ans = Long.MIN_VALUE;
        ans.sum = 0;
        int mid = (tree[k].l + tree[k].r) >>> 1;
        if (lt <= mid) {
            a = query(k * 2, lt, rt);
            ans.sum += a.sum;
        }
        if (rt >= mid + 1) {
            b = query(k * 2 + 1, lt, rt);
            ans.sum += b.sum;
        }
        ans.ans = Math.max(a.rx + b.lx, Math.max(a.ans, b.ans));
        ans.lx = Math.max(a.lx, a.sum + b.lx);
        ans.rx = Math.max(b.rx, b.sum + a.rx);
        if (lt > mid) {
            ans.lx = Math.max(ans.lx, b.lx);
        }
        if (rt < mid) {
            ans.rx = Math.max(ans.rx, a.rx);
        }
        return ans;
    }

    private static void modify(int k, int lt, int rt, int qx, long val) {
        if (qx < lt || qx > rt) return;
        else if (lt == qx && rt == qx) {
            tree[k].ans = tree[k].lx = tree[k].rx = tree[k].sum = val;
            return;
        } else {
            int mid = lt + (rt - lt) / 2;
            modify(k * 2, lt, mid, qx, val);
            modify(k * 2 + 1, mid + 1, rt, qx, val);
            tree[k].sum = tree[k * 2].sum + tree[k * 2 + 1].sum;
            tree[k].lx = Math.max(tree[k * 2].lx, tree[k * 2].sum + tree[k * 2 + 1].lx);
            tree[k].rx = Math.max(tree[k * 2 + 1].rx, tree[k * 2 + 1].sum + tree[k * 2].rx);
            tree[k].ans = Math.max(Math.max(tree[k * 2].ans, tree[k * 2 + 1].ans), tree[k * 2].rx + tree[k * 2 + 1].lx);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            num[i] = Long.parseLong(st.nextToken());
        }
        build(1, 1, n);
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int pre = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (pre == 2) {
                modify(1, 1, n, x, y);
            } else {
                if (x > y) {
                    int temp = x;
                    x = y;
                    y = temp;
                }
                Node a = query(1, x, y);
                System.out.println(a.ans);
            }
        }
    }
}