- 进制转换：直接Integer.toString(num,需要转换的进制)
- > 在一个正整数x的结尾增加尽可能少的数字，使得该数字变成495的倍数。请你给出任意一个添加方案。

  - 对于这种问题，我们找上界，因为495是三位数，即最多添加三位数。
  - 理由：因为我们可以对任何一个数*1000（除数的位数，495是3位数，就*1000），那么加上（0~999）一定可以被Mod整除！这个结论很好推理，随便举两个例子就好了

