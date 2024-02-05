package com.Java_Template.graph.union_find;

import java.util.*;

/**
 *
 */
public class problemImpl implements problem {

    // 684. 冗余连接
    @Override
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
//        for (int i = 1; i <= n; i++) {
//            parent[i] = i;
//        }
        Arrays.setAll(parent, a -> a);
        for (int i = 0; i < n; i++) {
            int[] edge = edges[i];
            int x = edge[0], y = edge[1];
            if (find(parent, x) != find(parent, y)) {
                union(parent, x, y);
            }else{
                return edge;
            }
        }
        return new int[0];
    }


    // 1722. 执行交换操作后的最小汉明距离
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) { // 进来学api
        int n = source.length, ans = 0;
        int[] parent = new int[n];
        Arrays.setAll(parent, i -> i);
        for (int[] allowedSwap : allowedSwaps) {
            union(parent, allowedSwap[0], allowedSwap[1]);
        }
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (source[i] != target[i]) {
                map.computeIfAbsent(target[i], e -> new ArrayList<>()).add(i);
            }
        }
        for (int i = 0; i < n; i++) {
            if(source[i]==target[i]) continue;
            if(!map.containsKey(source[i])){ ans++;}
            else{
                List<Integer> list = map.get(source[i]);
                ans++;
                for (int j = 0; j < list.size(); j++) {
                    if (find(parent, i) == find(parent, list.get(j))) {
                        ans--;
                        list.remove(j);
                        break;
                    }
                }
            }
        }
        return ans;
    }


    // 1202. 交换字符串中的元素
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n = s.length();
        int[] parent = new int[n];
        Arrays.setAll(parent, a -> a);
        for (List<Integer> pair : pairs) {
            union(parent, pair.get(0), pair.get(1));
        }
        HashMap<Integer, PriorityQueue<Character>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(find(parent, i), e -> new PriorityQueue<Character>()).offer(s.charAt(i));
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n; i++) {
            ans.append(map.get(find(parent, i)).poll());
        }
        return ans.toString();
    }



    // 839. 相似字符串组
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int[] parent = new int[n];
        Arrays.setAll(parent, a -> a);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (find(parent, i) == find(parent, j)) {
                    continue;
                }
                if (checkIsSimilar(strs[i], strs[j])) {
                    union(parent, i, j);
                }
            }
        }
//        HashSet<Integer> set = new HashSet<>(); note 思考为什么这样写不对？
//        for (int p : parent) {
//            set.add(p);
//        }
//        return set.size();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) { // 只要被合并就不用单独分组
                ans++;
            }
        }
        return ans;
    }

    private boolean checkIsSimilar(String s1, String s2) {
        if (s1.equals(s2)) {
            return true;
        }
        int ans = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                ans++;
            }
            if (ans > 2) {
                return false;
            }
        }
        return true;
    }


    // 765. 情侣牵手
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        int[] parent = new int[n/2];
        Arrays.setAll(parent, a -> a);
        for (int i = 0; i < n; i += 2) {
            union(parent, row[i] / 2, row[i + 1] / 2);
        }
        int ans = 0;
        for (int i = 0; i < n / 2; i++) {
            if (parent[i] != i) {
                ans++;
            }
        }
        return ans;
    }











    public void union(int[] parent, int index1, int index2) {
        parent[find(parent, index1)] = find(parent, index2);
    }

    public int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }





}
