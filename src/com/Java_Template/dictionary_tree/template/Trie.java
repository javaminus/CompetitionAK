package com.Java_Template.dictionary_tree.template;

/**
 * 字典树 （前缀树）
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 *
 * 请你实现 Trie 类：
 *
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
 *
 * search与startWith的区别：“abcd”与“abcde”的search与startWith都是true
 *                         “abc”与“abd”search为false,startWith是true
 */
public class Trie {
    private Trie[] children;
    private boolean isEnd;
    public Trie() {
        children = new Trie[26]; // 这里只统计小写字母，如果要大小写字母的话，可以写成26+32+26
        isEnd = false;
    }

    public void insert(String word) {
        Trie root = this; // 得到树的根节点
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (root.children[index] == null) {
                root.children[index] = new Trie();
            }
            //每一次生成字母都移动指针到下一个字母节点
            root = root.children[index];
        }
        root.isEnd = true;
    }


    public boolean search(String word) {
        Trie node = searchPrefix(word);
        //只有当该单词在字典树中存在并且最后一个字母节点为最后一个字母，才返回true
        return node != null && node.isEnd;
    }


    public boolean startsWith(String prefix) {
        //只要前缀匹配存在于字典树中就返回true
        return searchPrefix(prefix) != null;
    }

    //前缀搜索 还是 全文搜索都是调用此方法，区别在于前缀搜索只要前缀匹配就返回true，全文搜索则要匹配到最后一个字母才返回true，所以这里返回的是最后一个字母节点
    public Trie searchPrefix(String word){
        Trie root = this;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (root.children[index] != null) {
                root = root.children[index];
            }else{
                return null;
            }
        }
        return root;
    }
}
