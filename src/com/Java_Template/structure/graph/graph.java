package com.Java_Template.structure.graph;

/**
 * @author Minus
 * @date 2024/1/31 9:47
 */
public class graph {

    // 邻接矩阵 这是一种使用二维矩阵来进行存图的方式。 NOTE 适用于边数较多的稠密图使用，
    // 当边数量接近点的数量的平方，即 m ≈ n**2时，可定义为稠密图
    int N = 1000;
    int[][] g = new int[N][N];

    // 加权操作
    void add1(int a, int b, int c) {
        g[a][b] = c;
    }

    /* 邻接表
     这也是一种在图论中十分常见的存图方式，与数组存储单链表的实现一致（头插法）。
     这种存图方式又叫链式前向星存图。
     NOTE 适用于边数较少的稀疏图使用，
    当边数量接近点的数量，即 m≈n时，可定义为稀疏图*/
    int[] head = new int[N]; // 邻接表的头节点
    int[] end = new int[N]; // end数组用于存储边的终点
    int[] neighbor = new int[N]; // neighbor数组用于存储相邻边的索引
    int[] w = new int[N];   // w数组用于存储边的权重
    int idx; // 边的索引

    // 添加一条有向边
    void add2(int a, int b, int c) {
        end[idx] = b;
        neighbor[idx] = head[a];
        head[a] = idx;
        w[idx] = c;
        idx++;
    }
    // 遍历由a出发的所有边
//    for (int i = he[a]; i != -1; i = ne[i]) {
//        int b = e[i], c = w[i];  // 存在由 a 指向 b 的边，权重为 c
//    }




}
