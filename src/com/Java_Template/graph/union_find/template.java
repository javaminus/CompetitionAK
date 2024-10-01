package com.Java_Template.graph.union_find;

/**
 * 并查集 (合并，查询)
 */
public class template {

    public void union(int[] p, int x, int y) {
        p[find(p, x)] = find(p, y);
    }

    public int find(int[] p, int x) {
        if (p[x] != x) {
            p[x] = find(p, p[x]);
        }
        return p[x];
    }

}


