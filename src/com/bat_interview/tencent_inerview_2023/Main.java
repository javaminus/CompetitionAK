package com.bat_interview.tencent_inerview_2023;

/**
 * @author Minus
 * @date 2024/8/9 9:49
 * rank 17   2/5  得分：356.7 = (100+16.7+80+60+100)
 * 第二题：一定不要存值，要存下标！！！
 */

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    private final static int INF = Integer.MAX_VALUE / 2;
    private final static int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static class Read{
        BufferedReader bf;
        StringTokenizer st;
        BufferedWriter bw;
        public Read(){
            bf=new BufferedReader(new InputStreamReader(System.in));
            st=new StringTokenizer("");
            bw=new BufferedWriter(new OutputStreamWriter(System.out));
        }
        public String nextLine() throws IOException {
            return bf.readLine();
        }
        public String next() throws IOException{
            while(!st.hasMoreTokens()){
                st=new StringTokenizer(bf.readLine());
            }
            return st.nextToken();
        }
        public char nextChar() throws IOException{
            //确定下一个token只有一个字符的时候再用
            return next().charAt(0);
        }
        public int nextInt() throws IOException{
            return Integer.parseInt(next());
        }
        public long nextLong() throws IOException{
            return Long.parseLong(next());
        }
        public double nextDouble() throws IOException{
            return Double.parseDouble(next());
        }
        public float nextFloat() throws IOException{
            return Float.parseFloat(next());
        }
        public byte nextByte() throws IOException{
            return Byte.parseByte(next());
        }
        public short nextShort() throws IOException{
            return Short.parseShort(next());
        }
        public BigInteger nextBigInteger() throws IOException{
            return new BigInteger(next());
        }
        public void println(int a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
        public void print(int a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void println(String a) throws IOException{
            bw.write(a);
            bw.newLine();
            return;
        }
        public void print(String a) throws IOException{
            bw.write(a);
            return;
        }
        public void println(long a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
        public void print(long a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void println(double a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
        public void print(double a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void print(BigInteger a) throws IOException{
            bw.write(a.toString());
            return;
        }
        public void print(char a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void println(char a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
    }
    private static int binarySearch1(int[] nums, int target) { // >=target
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return right + 1;
    }
    private static int binarySearch2(int[] nums, int target) { // <=target
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left - 1;
    }

    static Read sc = new Read();
    static int T = 1;
    public static void main(String[] args) throws IOException {
        // T = sc.nextInt();
        while (T-- > 0) {
            // solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    static String[] ss;
    // https://kamacoder.com/problempage.php?pid=1231
    private static void solveA() throws IOException {
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        TreeNode root = buildTree(nums);
        ans = 0;
        dfs(root, 0, 0);
        sc.print(ans);
    }
    static int ans;
    private static void dfs(TreeNode root, int cnt0, int cnt1) {
        if (root.left == null && root.right == null) {
            if (root.val == 1) {
                cnt1++;
            }else{
                cnt0++;
            }
            ans += (cnt1 - cnt0 == 1 ? 1 : 0);
            return;
        }
        if (root.left != null) {
            if (root.val == 0) {
                dfs(root.left, cnt0 + 1, cnt1);
            }else {
                dfs(root.left, cnt0, cnt1 + 1);
            }
        }
        if (root.right != null) {
            if (root.val == 0) {
                dfs(root.right, cnt0 + 1, cnt1);
            }else {
                dfs(root.right, cnt0, cnt1 + 1);
            }
        }
    }
    public static TreeNode buildTree(int[] levelOrder) {
        if (levelOrder == null || levelOrder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < levelOrder.length) {
            TreeNode currentNode = queue.poll();

            if (levelOrder[i] != -1) {
                currentNode.left = new TreeNode(levelOrder[i]);
                queue.offer(currentNode.left);
            }
            i++;

            if (i < levelOrder.length && levelOrder[i] != -1) {
                currentNode.right = new TreeNode(levelOrder[i]);
                queue.offer(currentNode.right);
            }
            i++;
        }
        return root;
    }
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // https://kamacoder.com/problempage.php?pid=1232
    private static void solveB() throws IOException {
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] aa = new int[n];
        for (int i = 0; i < n; i++) {
            aa[i] = Integer.parseInt(ss[i]);
        }
        if (n == 1) {
            sc.print(aa[0]);
            sc.print("\n");
            return;
        }
        ss = sc.nextLine().split(" ");
        int[] bb = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            bb[i] = Integer.parseInt(ss[i]);
        }
        Integer[] ids = new Integer[n];
        Arrays.setAll(ids, i -> i);
        Arrays.sort(ids, (a, b) -> aa[a] - aa[b]);
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> aa[b] - aa[a]); // 大根堆 从大到小
        PriorityQueue<Integer> right = new PriorityQueue<>((a, b) -> aa[a] - aa[b]);  // 小根堆 从小到大
        for (int i = 0; i < n / 2; i++) {
            left.add(ids[i]);
        }
        for (int i = n / 2; i < n; i++) {
            right.add(ids[i]);
        }
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (left.size() + right.size() > 1) {
            if (left.size() > right.size()) {
                sb.append(aa[left.peek()]).append(" ");
            } else if (left.size() < right.size()) {
                sb.append(aa[right.peek()]).append(" ");
            }else{
                if ((aa[left.peek()] + aa[right.peek()]) % 2 == 0) {
                    sb.append((aa[left.peek()] + aa[right.peek()]) / 2).append(" ");
                }else{
                    double number = ((double) aa[left.peek()] + aa[right.peek()]) / 2;
                    DecimalFormat df = new DecimalFormat("#.0");
                    String formattedNumber = df.format(number);
                    sb.append(formattedNumber).append(" ");
                }
            }
            if (left.contains(bb[i])) { // 定位删除哪一边？
                left.remove(bb[i]);
            } else if (right.contains(bb[i])) {
                right.remove(bb[i]);
            }

            if (left.size() + 1 < right.size()) {
                left.add(right.poll());
            } else if (right.size() + 1 < left.size()) {
                right.add(left.poll());
            }
            i++;
        }
        if (left.size() > 0) {
            sb.append(aa[left.peek()]);
        }else if((right.size() > 0)){
            sb.append(aa[right.peek()]);
        }
        sc.print(sb.toString());
        sc.print("\n");

    }

    // https://kamacoder.com/problempage.php?pid=1233
    private static void solveC() throws IOException {
        int n = sc.nextInt();
        int k = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        HashMap<Integer, TreeSet<Integer>> cnt = new HashMap<>();
        TreeMap<Integer, HashSet<Integer>> freq = new TreeMap<>((a, b) -> b - a);
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
            cnt.computeIfAbsent(nums[i], e -> new TreeSet<>()).add(i);
            freq.computeIfAbsent(cnt.get(nums[i]).size(), e -> new HashSet<>()).add(nums[i]);
        }
        long cost = 0L;
        int ans = freq.firstKey();
        boolean[] vis = new boolean[n];
        for (Map.Entry<Integer, HashSet<Integer>> entry : freq.entrySet()) {
            int key = entry.getKey();
            HashSet<Integer> value = entry.getValue();
            if (k - (value.size()) >= 0) {
                k -= (value.size());
                for (int x : value) {
                    for (int y : cnt.get(x)) {
                        if (!vis[y]) {
                            vis[y] = true;
                            cost += y + 1;
                            break;
                        }
                    }
                }
                ans = key - 1;
            }else{
                break;
            }
        }
        sc.print(ans + " " + cost);

    }

    // https://kamacoder.com/problempage.php?pid=1234
    private static void solveD() throws IOException {
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        Arrays.sort(nums);
        long ans = 0L;
        if (n == 1) {
            sc.print(nums[0]);
            return;
        }
        ans += nums[n - 1];
        for (int i = n - 2, j = 0; i > j; i--, j++) {
            ans += (nums[i] - nums[j]);
        }
        sc.print(ans);
    }

    // https://kamacoder.com/problempage.php?pid=1235
    private static void solveE() throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();
        String s = sc.nextLine();
        long ans = 0;
        if (n == 1) {
            sc.print(0);
            return;
        }
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) != '?' && s.charAt(i - 1) != '?') {
                if (s.charAt(i) != s.charAt(i - 1)) {
                    ans++;
                }
            }
            else if (s.charAt(i) == '?' && s.charAt(i - 1) == '?') {
                if (m > 1) {
                    ans++;
                }
            }
            else if (s.charAt(i) == '?') {
                if (m > 1 || (s.charAt(i - 1) != 'a')) {
                    ans++;
                }
            }
            else if(s.charAt(i - 1)=='?'){
                if (m > 1 || (s.charAt(i) != 'a')) {
                    ans++;
                }
            }
        }
        sc.print(ans);

    }
}


