package com.songw;


public class Package {
    public int max_weight = 150;
    public static int[] weight = new int[] {35,30,60,50,40,10,25};
    public static int[] value = new int[] {10,40,30,50,35,40,30};
    public void greedyPackage(int capacity, int[] weight , int[] value) {
        int n = weight.length;//总个数
        double[] price = new double[n];//性价比数组
        int count[] = new int[n];//序号数组

        //求性价比
        for (int i = 0; i < n; i++) {
            price[i] = (double)value[i] / weight[i];
            count[i] = i;
        }

        //性价比排序
        for (int i = 0; i < n - 1; i++) {
            for (int j = i; j < n - 1; j++) {
                if (price[j] < price[j + 1]) {
                    double tmp = price[j];
                    price[j] = price[j + 1];
                    price[j + 1] = tmp;
                    //交换性价比排序后，再吧序号交换,方便之后取数
                    int a = count[j];
                    count[j] = count[j + 1];
                    count[j + 1] = a;
                }
            }
        }

        //把质量和价值也按照性价比的排序顺序对应好，存到新数组里
        int newWeight[] = new int[n];
        int newValue[] = new int[n];
        for (int i = 0; i < n; i++) {
            newValue[i] = value[count[i]];
            newWeight[i] = weight[count[i]];
        }

        double maxValue = 0;
        //装东西，优先拿性价比高的
        for (int i = 0; i < n; i++) {
            if (capacity > newWeight[i]) {
                capacity -= newWeight[i];
                maxValue += newValue[i];
            }
        }

        System.out.print("共放下了" + (max_weight - capacity) +"kg重的东西\n");
        System.out.print("总价值" + maxValue);
    }

    public static void main(String[] args) {
        Package greedyPackage = new Package();
        greedyPackage.greedyPackage(greedyPackage.max_weight, weight, value);
    }
}


