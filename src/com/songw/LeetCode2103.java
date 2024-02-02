package com.songw;

/**
 * @author Minus
 * @date 2023/11/2 21:52
 */
public class LeetCode2103 {

    public static void main(String[] args) {
        LeetCode2103 leetCode2103 = new LeetCode2103();
        String s = "zzz";
        char c = s.charAt(0);

        leetCode2103.countPoints("B0B6G0R6R0R6G9");
    }
    public int countPoints(String rings) {
        int res = 0;
        int n = rings.length();
        int[][] matrix = new int[10][3];
        int i = 0;
        while(i<n){
            if(i%2==0){
                char c = rings.charAt(i + 1);
                if(rings.charAt(i)=='R'){
                    matrix[rings.charAt(i+1)-'0'][0]=1;
                }
                if(rings.charAt(i)=='G'){
                    matrix[rings.charAt(i+1)-'0'][1]=1;
                }
                if(rings.charAt(i)=='B'){
                    matrix[rings.charAt(i+1)-'0'][2]=1;
                }
            }
            i+=2;
        }
        int j = 9;
        while(j>=0){
            if (matrix[j][0] == 1 && matrix[j][1] == 1 && matrix[j][2] == 1) {
                res++;
            }
        }
        return res;
    }
}
