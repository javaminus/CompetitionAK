//给你一个整数数组 digits，你可以通过按 任意顺序 连接其中某些数字来形成 3 的倍数，请你返回所能得到的最大的 3 的倍数。
//
// 由于答案可能不在整数数据类型范围内，请以字符串形式返回答案。如果无法得到答案，请返回一个空字符串。返回的结果不应包含不必要的前导零。
//
//
//
// 示例 1：
//
//
//输入：digits = [8,1,9]
//输出："981"
//
//
// 示例 2：
//
//
//输入：digits = [8,6,7,1,0]
//输出："8760"
//
//
// 示例 3：
//
//
//输入：digits = [1]
//输出：""
//
//
// 示例 4：
//
//
//输入：digits = [0,0,0,0,0,0]
//输出："0"
//
//
//
//
// 提示：
//
//
// 1 <= digits.length <= 10^4
// 0 <= digits[i] <= 9
//
//
// Related Topics 贪心 数组 动态规划 👍 93 👎 0


import java.util.Arrays;
import java.util.PriorityQueue;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public static void main(String[] args) {
        new Solution().largestMultipleOfThree(new int[]{0,0,0,0,0,1});
    }
    public String largestMultipleOfThree(int[] digits) {
        long d = 0, n = digits.length;
        Arrays.sort(digits);
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Integer> pq1 = new PriorityQueue<>();
        PriorityQueue<Integer> pq2 = new PriorityQueue<>();
        for (int x : digits) {
            d += x;
            if (x % 3 == 1) {
                pq1.offer(x);
            }
            if (x % 3 == 2) {
                pq2.offer(x);
            }
            sb.append(x);
        }
        if (d % 3 == 1) {
            if (!pq1.isEmpty()) {
                int x = pq1.poll();
                for (int i = 0; i < n; i++) {
                    if (sb.charAt(i) - '0' == x) {
                        sb.setCharAt(i, '#');
                        break;
                    }
                }
            } else if (pq2.size() >= 2) {
                int x1 = pq2.poll(), x2 = pq2.poll();
                int cnt = 0;
                for (int i = 0; i < n; i++) {
                    if (sb.charAt(i) - '0' == x1 || sb.charAt(i) - '0' == x2) {
                        sb.setCharAt(i, '#');
                        cnt++;
                    }
                    if (cnt == 2) {
                        break;
                    }
                }
            }else{
                return "";
            }
        } else if (d % 3 == 2) {
            if (!pq2.isEmpty()) {
                int x = pq2.poll();
                for (int i = 0; i < n; i++) {
                    if (sb.charAt(i) - '0' == x) {
                        sb.setCharAt(i, '#');
                        break;
                    }
                }
            } else if (pq1.size() >= 2) {
                int x1 = pq1.poll(), x2 = pq1.poll();
                int cnt = 0;
                for (int i = 0; i < n; i++) {
                    if (sb.charAt(i) - '0' == x1 || sb.charAt(i) - '0' == x2) {
                        sb.setCharAt(i, '#');
                        cnt++;
                    }
                    if (cnt == 2) {
                        break;
                    }
                }
            }else{
                return "";
            }
        }
        String res = sb.reverse().toString().replace("#", "");
        if(res.length()==0){
            return "";
        }
        if (res.charAt(0) == '0') {
            return "0";
        } else {
            return res;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
