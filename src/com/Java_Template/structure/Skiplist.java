package com.Java_Template.structure;

class Skiplist {
    static final int LEVEL_LIMIT = 32;
    static final int FACTOR = 2;
    Node pseudoHead;
    int maxLevel;

    public Skiplist() {
        pseudoHead = new Node(-1, LEVEL_LIMIT);
        maxLevel = -1;
    }

    public boolean search(int target) {
        Node node = pseudoHead;
        for (int i = maxLevel; i >= 0; i--) {
            node = findNodeBefore(i, node, target);
            if (node.next[i] != null && node.next[i].val == target) {
                return true;
            }
        }
        return false;
    }

    public void add(int num) {
        int curLevel = getRandomLevel();
        maxLevel = Math.max(maxLevel, curLevel);
        Node addNode = new Node(num, curLevel + 1);
        Node node = pseudoHead;
        for (int i = maxLevel; i > curLevel; i--) {
            node = findNodeBefore(i, node, num);
        }
        for (int i = curLevel; i >= 0; i--) {
            node = findNodeBefore(i, node, num);
            if (node.next[i] != null) {
                addNode.next[i] = node.next[i];
            }
            node.next[i] = addNode;
        }
    }

    public boolean erase(int num) {
        boolean erased = false;
        Node node = pseudoHead;
        for (int i = maxLevel; i >= 0; i--) {
            node = findNodeBefore(i, node, num);
            if (node.next[i] != null && node.next[i].val == num) {
                node.next[i] = node.next[i].next[i];
                erased = true;
            }
        }
        return erased;
    }

    private Node findNodeBefore(int level, Node start, int target) {
        Node node = start;
        while (node.next[level] != null && node.next[level].val < target) {
            node = node.next[level];
        }
        return node;
    }

    private int getRandomLevel() {
        int maxLevel = 0;
        while (maxLevel < LEVEL_LIMIT - 1 && (int) (Math.random() * FACTOR) == 0) {
            maxLevel++;
        }
        return maxLevel;
    }
}

class Node {
    int val;
    Node[] next;

    public Node(int val, int size) {
        this.val = val;
        next = new Node[size];
    }
}