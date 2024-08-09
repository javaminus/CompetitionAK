package com.clapper;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

class Main{

    static int[] aa;
    static int[] bb;
    static int n = 100;
    public static void main(String[] args) throws IOException {
        aa = new int[n+1000];
        bb = new int[n +1000];
        int nn = 10000; // 需要生成的不重复数字的数量
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= nn; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < n; i++) {
            aa[i] = numbers.get(i);
        }
        List<Integer> numbersb = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbersb.add(i);
        }
        Collections.shuffle(numbersb);
        for (int i =0; i < numbersb.size()-1; i++) {
            aa[i] = numbers.get(i);
        }
        solve();
    }
    private static void solve() throws IOException {
        Arrays.sort(aa);
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a); // 大根堆 从大到小
        PriorityQueue<Integer> right = new PriorityQueue<>();  // 小根堆 从小到大
        for (int i = 0; i < n / 2; i++) {
            left.add(aa[i]);
        }
        for (int i = n / 2; i < n; i++) {
            right.add(aa[i]);
        }
        int i = 0;
        while (left.size() + right.size() > 1) {
            if (left.size() > right.size()) {
                sc.print(left.peek()+" ");
            } else if (left.size() < right.size()) {
                sc.print(right.peek()+" ");
            }else{
                if ((left.peek() + right.peek()) % 2 == 0) {
                    sc.print(((long) (left.peek() + right.peek()) / 2)+" ");
                }else{
                    double number = (left.peek() + right.peek()) / 2;
                    DecimalFormat df = new DecimalFormat("#.0");
                    String formattedNumber = df.format(number);
                    sc.print(formattedNumber+" ");
                }
            }
            int r = aa[bb[i]];
            if (left.contains(r)) {
                left.remove(r);
            } else if (right.contains(r)) {
                right.remove(r);
            }
            if (left.size() + 1 < right.size()) {
                left.add(right.poll());
            } else if (right.size() + 1 < left.size()) {
                right.add(left.poll());
            }
            i++;
        }
        if (left.size() > 0) {
            sc.print(left.peek());
        }else if((right.size() > 0)){
            sc.print(right.peek());
        }
        sc.print("\n");

    }

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
    static Read sc = new Read();
}

