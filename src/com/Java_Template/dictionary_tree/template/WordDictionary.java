package com.Java_Template.dictionary_tree.template;

/**
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
 *
 * 实现词典类 WordDictionary ：
 *
 * WordDictionary() 初始化词典对象
 * void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
 * bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
 *
 */
class WordDictionary {
    Trie root;
    public WordDictionary(){
        root = new Trie();
    }

    public void addWord(String word) {
        root.insert(word);
    }

    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int index, Trie root) {
        if (index == word.length()) {
            return root.isEnd;
        }
        char c = word.charAt(index);
        if (Character.isLetter(c)) {
            int childIndex = c - 'a';
            Trie child = root.children[childIndex];
            if (child != null && dfs(word, index + 1, child)) {
                return true;
            }
        }else{
            for (int i = 0; i < 26; i++) {
                Trie child = root.children[i]; // 随便拿一个不为空的
                if (child != null && dfs(word, index + 1, child)) {
                    return true;
                }
            }
        }
        return false;
    }

    class Trie{
        private Trie[] children;
        private boolean isEnd;
        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie root = this;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (root.children[index] == null) {
                    root.children[index] = new Trie();
                }
                root = root.children[index];
            }
            root.isEnd = true;
        }

    }
}

