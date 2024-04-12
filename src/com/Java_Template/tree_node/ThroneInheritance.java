package com.Java_Template.tree_node;

import java.util.*;

/**
 * @author Minus
 * @date 2024/4/8 9:41
 *
 * 1600. 王位继承顺序(https://leetcode.cn/problems/throne-inheritance/description/) 多叉树的先序遍历
 */
public class ThroneInheritance {
    Map<String, List<String>> edges;
    Set<String> dead;
    String king;
    public ThroneInheritance(String kingName) {
        edges = new HashMap<>();
        dead = new HashSet<>();
        king = kingName;
    }

    public void birth(String parentName, String childName) {
        List<String> children = edges.getOrDefault(parentName, new ArrayList<String>());
        children.add(childName);
        edges.put(parentName, children);
    }

    public void death(String name) {
        dead.add(name);
    }

    public List<String> getInheritanceOrder() {
        ArrayList<String> ans = new ArrayList<>();
        preOrder(ans, king);
        return ans;
    }

    private void preOrder(List<String> ans, String name) {
        if (!dead.contains(name)) {
            ans.add(name);
        }
        List<String> children = edges.getOrDefault(name, new ArrayList<>());
        for (String child : children) {
            preOrder(ans, child);
        }
    }
}
