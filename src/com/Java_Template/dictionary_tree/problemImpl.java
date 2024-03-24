package com.Java_Template.dictionary_tree;

import java.util.*;

/**
 *
 */
public class problemImpl implements problem {
    class Node{
        HashMap<Integer, Node> son = new HashMap<>();
        int cnt = 0;
    }
    public long countPrefixSuffixPairs(String[] words) {
        long ans = 0;
        Node root = new Node();
        for (String S : words) {
            char[] s = S.toCharArray();
            int n = s.length;
            Node cur = root;
            for (int i = 0; i < n; i++) {
                int p = (s[i] - 'a') << 5 | (s[n - 1 - i] - 'a');
                cur = cur.son.computeIfAbsent(p, k -> new Node());
                ans += cur.cnt;
            }
            cur.cnt++;
        }
        return ans;
    }

    // 212. 单词搜索 II  写法一:使用hash表构造字典树
    private int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private Set<String> ans;
    @Override
    public List<String> findWords(char[][] board, String[] words) {
        Trie root = new Trie();
        for (String word : words) {
            root.insert(word);
        }
        ans = new HashSet<>();
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, root, i, j);
            }
        }
        return new ArrayList<>(ans);
    }

    private void dfs(char[][] board, Trie root, int i, int j) {
        char ch = board[i][j];
        if (!root.children.containsKey(ch)) {
            return;
        }
        root = root.children.get(ch);
        if (!"".equals(root.word)) {
            ans.add(root.word);
        }
        board[i][j] = '#'; // 因为一个字母只能用一次
        for (int[] d : directions) {
            int newi = i + d[0], newj = j + d[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                dfs(board, root, newi, newj);
            }
        }
        board[i][j] = ch; // 回溯
    }
    class Trie{
        private String word; // 将isEnd换成word
        private Map<Character, Trie> children;

        public Trie(){
            this.word = "";
            this.children = new HashMap<Character, Trie>();
        }
        public void insert(String word) {
            Trie root = this;
            for (char c : word.toCharArray()) {
                root.children.computeIfAbsent(c, k -> new Trie()); // 等价于 if(!root.children.contains(c)) {root.children.put(c,new Trie());}
                root = root.children.get(c);
            }
            root.word = word;
        }
    }


/*  写法二:使用数组构造字典树
    private int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private Set<String> ans;
    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length, n = board[0].length;
        ans = new HashSet<>();
        Trie root = new Trie();
        for (String word : words) {
            root.insert(word);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, root, i, j);
            }
        }
        return new ArrayList<>(ans);
    }

    private void dfs(char[][] board, Trie root, int i, int j) {
        char ch = board[i][j];
        if (root.children[ch - 'a'] == null) {
            return;
        }
        root = root.children[ch - 'a'];
        if (!"".equals(root.word)) {
            ans.add(root.word);
        }
        board[i][j] = '}';
        for (int[] d : directions) {
            int newi = i + d[0], newj = j + d[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                dfs(board, root, newi, newj);
            }
        }
        board[i][j] = ch;
    }

    class Trie{
        private String word;
        private Trie[] children;

        public Trie(){
            this.word = "";
            this.children = new Trie[200];
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
            root.word = word;
        }
    }*/


    // 100268. 最长公共后缀查询 手搓字典树的快乐哈哈哈
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        Trie1 root = new Trie1();
        // 插入操作
        for (int i = 0; i < wordsContainer.length; i++) {
            char[] s = wordsContainer[i].toCharArray();
            int len = s.length;
            Trie1 cur = root;
            if (len < cur.minLen) {
                cur.minLen = len;
                cur.i = i;
            }
            for (int j = len - 1; j >= 0; j--) {
                int c = s[j] - 'a';
                if (cur.children[c] == null) {
                    cur.children[c] = new Trie1();
                }
                cur = cur.children[c];
                if (len < cur.minLen) {
                    cur.minLen = len;
                    cur.i = i;
                }
            }
        }
        int n = wordsQuery.length;
        int[] ans = new int[n];
        // 查询操作
        for (int i = 0; i < n; i++) {
            Trie1 cur = root;
            char[] s = wordsQuery[i].toCharArray();
            for (int j = s.length - 1; j >= 0 && cur.children[s[j] - 'a'] != null; j--) {
                cur = cur.children[s[j] - 'a'];
            }
            ans[i] = cur.i;
        }
        return ans;
    }

    class Trie1{
        Trie1[] children = new Trie1[26];
        int minLen = Integer.MAX_VALUE; // 最小长度
        int i; // 下标
    }

}
