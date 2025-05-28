import java.io.*;
import java.util.*;

public class c {
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 进行快速输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 使用 PrintWriter 输出结果
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        
        // 读取整数 N，即排列 P 的长度
        int N = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");
        int[] P = new int[N];
        for (int i = 0; i < N; i++) {
            P[i] = Integer.parseInt(parts[i]);
        }
        
        // 子数组要求长度至少为 4
        // 我们讨论 P 的连续子数组是否呈 tilde 形，定义为：
        // 1. 子数组的第一个数小于第二个数，即差值为正。
        // 2. 子数组内恰好有一个局部最大值和一个局部最小值。
        // 观察相邻差值可以将问题转化为：在该子数组对应的差分序列 D 中，
        // 必须严格具有如下形态：一段正差 (+)，接着一段负差 (-)，再接着一段正差 (+)。
        // 且每一段至少包含一个元素。
        // 注意：若子数组对应的差分序列满足该形态，则原子数组 A 满足：
        //   A[1] < A[2]，且恰有一个局部最大值（从正转负）和一个局部最小值（从负转正）。

        // 构造差分数组 D (长度为 N-1)，定义 D[i] = +1 若 P[i+1] > P[i]，否则为 -1（由于排列中不可能有相等的情况）
        int lenD = N - 1;
        int[] D = new int[lenD];
        for (int i = 0; i < lenD; i++) {
            D[i] = (P[i+1] > P[i] ? 1 : -1);
        }
        
        // 对 D 进行跑长编码（RLE），将连续相同的符号合并
        // 每个段记录：符号值、起始下标、结束下标、段长
        List<Segment> segments = new ArrayList<>();
        int i = 0;
        while(i < lenD){
            int curVal = D[i];
            int start = i;
            while(i < lenD && D[i] == curVal){
                i++;
            }
            int end = i - 1;
            int length = end - start + 1;
            segments.add(new Segment(curVal, start, end, length));
        }
        
        // 统计满足模式：+, -, + 的三段组合
        // 对于每一个连续三个段，如果它们的符号依次为 +, -, +，
        // 可选的区间数为： (#个选取子区间起点在第一段) * (#个选取子区间终点在第三段)
        // 因为子数组对应的差分区间必须跨越这三个段，不允许包含额外的段，否则局部极值个数会增加。
        long ans = 0;
        for (int j = 0; j + 2 < segments.size(); j++) {
            Segment seg1 = segments.get(j);
            Segment seg2 = segments.get(j+1);
            Segment seg3 = segments.get(j+2);
            if (seg1.val == 1 && seg2.val == -1 && seg3.val == 1) {
                // 对于合法的三段组合，子数组差分的选择：
                // 起点必须取在 seg1 内（任意位置都可以选），终点必须取在 seg3 内（任意位置都可选）。
                // 中间 seg2 将必然被完全包含，所以至少有一个元素。
                ans += (long) seg1.len * seg3.len;
            }
        }
        
        // 输出结果
        out.println(ans);
        out.flush();
        out.close();
    }
    
    // 定义 Segment 类来存储跑长编码的每一段信息
    static class Segment {
        int val;    // 符号值，+1 表示正差，-1 表示负差
        int start;  // 该段在 D 中的起始下标
        int end;    // 该段在 D 中的结束下标
        int len;    // 段长
        public Segment(int val, int start, int end, int len) {
            this.val = val;
            this.start = start;
            this.end = end;
            this.len = len;
        }
    }
}