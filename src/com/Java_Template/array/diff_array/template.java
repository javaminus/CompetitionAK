package com.Java_Template.array.diff_array;

/**
 * 差分数组
 */
public class template {

}
/**
 * 二维差分（https://www.luogu.com.cn/problem/P3397）
 *
 * 1.import java.io.BufferedReader;
 * 2.import java.io.IOException;
 * 3.import java.io.InputStreamReader;
 * 4.
 * 5.public class carpet {//差分——地毯
 * 6.    public static void main() throws IOException {
 * 7.
 * 8.        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 * 9.        //Scanner sc =new Scanner(System.in);太慢了，BR缓冲区快一倍
 * 10.        String[] temp = br.readLine().split(" ");
 * 11.        int[][] D =new int[1050][1050];
 * 12.        //int a[5000][5000];   //原数组，不定义也行
 * 13.        int n=Integer.parseInt(temp[0]);
 * 14.        int m=Integer.parseInt(temp[1]);
 * 15.
 * 16.        while(m!=0) {
 * 17.            m--;
 * 18.            String[] str =br.readLine().split(" ");
 * 19.            int x1=Integer.parseInt(str[0]);
 * 20.            int y1=Integer.parseInt(str[1]);
 * 21.            int x2=Integer.parseInt(str[2]);
 * 22.            int y2=Integer.parseInt(str[3]);
 * 23.
 * 24.            D[x1][y1]     += 1;        //计算差分数组
 * 25.            D[x2+1][y1]   -= 1;
 * 26.            D[x1][y2+1]   -= 1;
 * 27.            D[x2+1][y2+1] += 1;
 * 28.        }
 * 29.        for(int i=1;i<=n;i++) {
 * 30.            for(int j=1;j<=n;j++) {
 * 31.                //a[i][j] = D[i][j] + a[i-1][j] + a[i][j-1] - a[i-1][j-1];
 * 32.                //printf("%d ",a[i][j]);  //这两行和下面两行的效果一样
 * 33.                D[i][j] += D[i-1][j]+D[i][j-1]-D[i-1][j-1];
 * 34.                System.out.print(D[i][j]);
 * 35.            }
 * 36.            System.out.println();
 * 37.        }
 * 38.        UI.main();
 * 39.    }
 * 40.}
 */