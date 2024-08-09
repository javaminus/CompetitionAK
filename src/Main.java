import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    private final static int INF = Integer.MAX_VALUE;
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

    private static int binarySearch1(int[] nums, int target) {
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

    private static int binarySearch2(int[] nums, int target) {
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
    public static void main(String[] args) throws IOException {
        int T = 1;
        while (T-- > 0) {
            solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }


    private static void solve() throws IOException {
        String[] split = sc.nextLine().split(",");
        int n = split.length;
        int[][] nums = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] s = split[i].split(":");
            nums[i][0] = Integer.parseInt(s[0]);
            nums[i][1] = Integer.parseInt(s[1]);
        }
        Arrays.sort(nums, (a, b) -> a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]);
        int left = 0, right = 4800; // 4800:10,2:12,3:10
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums,mid)) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        System.out.println(right> 4800 ? -1 : right + 1);
    }

    private static boolean check(int[][] nums, int sum) {
        for (int i = nums.length - 1; i >= 0; i--) {
            int a = nums[i][0], b = nums[i][1];
            if (sum >= b && sum - a >= 0) {
                sum -= a;
            } else {
                return false;
            }
        }
        return true;
    }

}