> 算法并不是最难的，因为这事逻辑。 真正困难的地方，是贪婪思维下的隐性决策，那是无法细节预测和探索的。

- 数组不变，区间查询：前缀和、树状数组、线段树；

- 数组单点修改，区间查询：树状数组、线段树；

- 数组区间修改，单点查询：差分、线段树；

- 数组区间修改，区间查询：线段树。

- 看到「最大化最小值」或者「最小化最大值」就要想到二分答案，这是一个固定的套路。

  为什么？一般来说，二分的值越大，越能/不能满足要求；二分的值越小，越不能/能满足要求，有单调性，可以二分。

- 无权，采用BFS;
  有权，权重均为非负，采用dijkstra;
  有权，权重包含负值，采用bellman-ford.

- 一个序列的异或和一定小于等于数值和

- 一个序列的异或和、数值和奇偶性一定相同

- 数位dp只能从高位往低位，不能从低位往高位！！！

- 比较两个数字拼接大小排序：

  ```java
  int n = nums.length;
          String[] arr = new String[n];
          for (int i = 0; i < n; i++) {
              arr[i] = "" + nums[i];
          }
          Arrays.sort(arr, (a,b)->{
              String x = a + b, y = b + a;
              return y.compareTo(x);
          });
  ```

- TreeMap<key, value> 的排序是按照`key`排序，不是按照`value`排序的；

- 48～57为0到9十个阿拉伯数字；65～90为26个大写英文字母，97～122号为26个小写英文字母

- 字符串大小写互换：c^=32

- 判断一个字符是数字：Character.isDigit(c)

- 数位公式：$y = x + (s[j] - s[i]) * (POW10[i] - POW10[j])$ 交换x的第i位与第j位 

- private static final int[] POW10 = new int[]{1, 10, 100, 1000, 10000, 100000,1000000};

- 二进制一个数是0b开头

- **int**:
  - 范围为 -2^31 到 2^31 - 1，可以表示为 -2 * 10^9 到 2 * 10^9。

- **long**:
  - 范围为 -2^63 到 2^63 - 1，可以表示为 -9 * 10^18 到 9 * 10^18。

- byte: 1 bytes
  short: 2 bytes
  int: 4 bytes
  long: 8 bytes
  char: 2 bytes
  float: 4 bytes
  double: 8 bytes
  boolean: 1 bytes (in arrays)

- 设计小数的运算一定要用`BigDecimal`，而不是用`Double`；

- ![img.png](assets/img.png)

- ![1713108404643](F:\leetcode\README.assets\1713108404643.png)

- ![1716388225535](assets/1716388225535.png)

- ![img.png](assets/img.png)

- ![img_3.png](assets/img_3.png)

- ![img_1.png](assets/img_1.png)

- ![img_2.png](assets/img_2.png)

