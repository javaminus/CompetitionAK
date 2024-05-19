- 进制转换：直接Integer.toString(num,需要转换的进制)
- > 在一个正整数x的结尾增加尽可能少的数字，使得该数字变成495的倍数。请你给出任意一个添加方案。

  - 对于这种问题，我们找上界，因为495是三位数，即最多添加三位数。
  - 理由：因为我们可以对任何一个数*1000（除数的位数，495是3位数，就*1000），那么加上（0~999）一定可以被Mod整除！这个结论很好推理，随便举两个例子就好了

$$
\left\lceil\frac{a}{b}\right\rceil=\left\lfloor\frac{a+b-1}{b}\right\rfloor
$$

- 被进制转换狠狠的上了一课

  ```java
  import java.io.BufferedReader;
  import java.io.InputStreamReader;
  import java.util.Scanner;
  // https://www.luogu.com.cn/problem/P10510
  public class Main{
      static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
      public static void main(String[] args) {
          long V = sc.nextLong();
          int q = sc.nextInt();
          int[] a = new int[38];
          int t = 0;
          while (V != 0) {
              a[t++] = (int) (V % 3);
              V /= 3;
          }
          while (q-- > 0) {
              int op = sc.nextInt();
              int x = sc.nextInt();
              if (op <= 2) {
                  a[x] = (a[x] + op) % 3;
              } else {
                  a[x] = (3 - a[x]) % 3;
              }
              long ans = 0;
              for (int i = 37; i >= 0; i--) { // 这个就对，在0-int范围两种写法相同，但是int-long就只有这种是对的
                  ans = ans * 3 + a[i];
              }
  //            for (int j = 0; j < a.length; j++) { 这个就是错的，我真的无语。我懂了，这个超过int型就不对了，所以记住上面的写法
  //                ans += (long) Math.pow(3, j) * a[j];
  //            }
              System.out.println(ans);
          }
      }
  
  }
  ```

  