3045\. 统计前后缀下标对 II
------------------

给你一个下标从 **0** 开始的字符串数组 `words` 。

定义一个 **布尔** 函数 `isPrefixAndSuffix` ，它接受两个字符串参数 `str1` 和 `str2` ：

* 当 `str1` 同时是 `str2` 的前缀（

    prefix

    ）和后缀（

    suffix

    ）时，`isPrefixAndSuffix(str1, str2)` 返回 `true`，否则返回 `false`。

例如，`isPrefixAndSuffix("aba", "ababa")` 返回 `true`，因为 `"aba"` 既是 `"ababa"` 的前缀，也是 `"ababa"` 的后缀，但是 `isPrefixAndSuffix("abc", "abcd")` 返回 `false`。

以整数形式，返回满足 `i < j` 且 `isPrefixAndSuffix(words[i], words[j])` 为 `true` 的下标对 `(i, j)` 的 **数量** 。

**示例 1：**

**输入：**words = \["a","aba","ababa","aa"\]
**输出：**4
**解释：**在本示例中，计数的下标对包括：
i = 0 且 j = 1 ，因为 isPrefixAndSuffix("a", "aba") 为 true 。
i = 0 且 j = 2 ，因为 isPrefixAndSuffix("a", "ababa") 为 true 。
i = 0 且 j = 3 ，因为 isPrefixAndSuffix("a", "aa") 为 true 。
i = 1 且 j = 2 ，因为 isPrefixAndSuffix("aba", "ababa") 为 true 。
因此，答案是 4 。

**示例 2：**

**输入：**words = \["pa","papa","ma","mama"\]
**输出：**2
**解释：**在本示例中，计数的下标对包括：
i = 0 且 j = 1 ，因为 isPrefixAndSuffix("pa", "papa") 为 true 。
i = 2 且 j = 3 ，因为 isPrefixAndSuffix("ma", "mama") 为 true 。
因此，答案是 2 。

**示例 3：**

**输入：**words = \["abab","ab"\]
**输出：**0
**解释：**在本示例中，唯一有效的下标对是 i = 0 且 j = 1 ，但是 isPrefixAndSuffix("abab", "ab") 为 false 。
因此，答案是 0 。

**提示：**

*   `1 <= words.length <= 105`
*   `1 <= words[i].length <= 105`
*   `words[i]` 仅由小写英文字母组成。
*   所有 `words[i]` 的长度之和不超过 `5 * 105` 。

[https://leetcode.cn/problems/count-prefix-and-suffix-pairs-ii/description/](https://leetcode.cn/problems/count-prefix-and-suffix-pairs-ii/description/)

```java
import java.util.HashMap;

class Solution {
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
}
```

212\. 单词搜索 II
-------------

给定一个 `m x n` 二维字符网格 `board` 和一个单词（字符串）列表 `words`， _返回所有二维网格上的单词_ 。

单词必须按照字母顺序，通过 **相邻的单元格** 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。

**示例 1：**

![](https://assets.leetcode.com/uploads/2020/11/07/search1.jpg)

**输入：**board = \[\["o","a","a","n"\],\["e","t","a","e"\],\["i","h","k","r"\],\["i","f","l","v"\]\], words = \["oath","pea","eat","rain"\]
**输出：**\["eat","oath"\]

**示例 2：**

![](https://assets.leetcode.com/uploads/2020/11/07/search2.jpg)

**输入：**board = \[\["a","b"\],\["c","d"\]\], words = \["abcb"\]
**输出：**\[\]

**提示：**

*   `m == board.length`
*   `n == board[i].length`
*   `1 <= m, n <= 12`
*   `board[i][j]` 是一个小写英文字母
*   `1 <= words.length <= 3 * 104`
*   `1 <= words[i].length <= 10`
*   `words[i]` 由小写英文字母组成
*   `words` 中的所有字符串互不相同

[https://leetcode.cn/problems/word-search-ii/description/?envType=study-plan-v2&envId=top-interview-150](https://leetcode.cn/problems/word-search-ii/description/?envType=study-plan-v2&envId=top-interview-150)

```java
	// 212. 单词搜索 II  写法一:使用hash表构造字典树
class Solution {    
	private int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private Set<String> ans;
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
}
```

```java
// 写法二:使用数组构造字典树
class Solution {
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
    }
}
```

3093\. 最长公共后缀查询
---------------

给你两个字符串数组 `wordsContainer` 和 `wordsQuery` 。

对于每个 `wordsQuery[i]` ，你需要从 `wordsContainer` 中找到一个与 `wordsQuery[i]` 有 **最长公共后缀** 的字符串。如果 `wordsContainer` 中有两个或者更多字符串有最长公共后缀，那么答案为长度 **最短** 的。如果有超过两个字符串有 **相同** 最短长度，那么答案为它们在 `wordsContainer` 中出现 **更早** 的一个。

请你返回一个整数数组 `ans` ，其中 `ans[i]`是 `wordsContainer`中与 `wordsQuery[i]` 有 **最长公共后缀** 字符串的下标。

**示例 1：**

**输入：**wordsContainer = \["abcd","bcd","xbcd"\], wordsQuery = \["cd","bcd","xyz"\]

**输出：**\[1,1,1\]

**解释：**

我们分别来看每一个 `wordsQuery[i]` ：

*   对于 `wordsQuery[0] = "cd"` ，`wordsContainer` 中有最长公共后缀 `"cd"` 的字符串下标分别为 0 ，1 和 2 。这些字符串中，答案是下标为 1 的字符串，因为它的长度为 3 ，是最短的字符串。
*   对于 `wordsQuery[1] = "bcd"` ，`wordsContainer` 中有最长公共后缀 `"bcd"` 的字符串下标分别为 0 ，1 和 2 。这些字符串中，答案是下标为 1 的字符串，因为它的长度为 3 ，是最短的字符串。
*   对于 `wordsQuery[2] = "xyz"` ，`wordsContainer` 中没有字符串跟它有公共后缀，所以最长公共后缀为 `""` ，下标为 0 ，1 和 2 的字符串都得到这一公共后缀。这些字符串中， 答案是下标为 1 的字符串，因为它的长度为 3 ，是最短的字符串。

**示例 2：**

**输入：**wordsContainer = \["abcdefgh","poiuygh","ghghgh"\], wordsQuery = \["gh","acbfgh","acbfegh"\]

**输出：**\[2,0,2\]

**解释：**

我们分别来看每一个 `wordsQuery[i]` ：

*   对于 `wordsQuery[0] = "gh"` ，`wordsContainer` 中有最长公共后缀 `"gh"` 的字符串下标分别为 0 ，1 和 2 。这些字符串中，答案是下标为 2 的字符串，因为它的长度为 6 ，是最短的字符串。
*   对于 `wordsQuery[1] = "acbfgh"` ，只有下标为 0 的字符串有最长公共后缀 `"fgh"` 。所以尽管下标为 2 的字符串是最短的字符串，但答案是 0 。
*   对于 `wordsQuery[2] = "acbfegh"` ，`wordsContainer` 中有最长公共后缀 `"gh"` 的字符串下标分别为 0 ，1 和 2 。这些字符串中，答案是下标为 2 的字符串，因为它的长度为 6 ，是最短的字符串。

**提示：**

*   `1 <= wordsContainer.length, wordsQuery.length <= 104`
*   `1 <= wordsContainer[i].length <= 5 * 103`
*   `1 <= wordsQuery[i].length <= 5 * 103`
*   `wordsContainer[i]` 只包含小写英文字母。
*   `wordsQuery[i]` 只包含小写英文字母。
*   `wordsContainer[i].length` 的和至多为 `5 * 105` 。
*   `wordsQuery[i].length` 的和至多为 `5 * 105` 。

[https://leetcode.cn/problems/longest-common-suffix-queries/description/](https://leetcode.cn/problems/longest-common-suffix-queries/description/)

```java
class Solution {
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        Trie root = new Trie();
        // 插入操作
        for (int i = 0; i < wordsContainer.length; i++) {
            char[] s = wordsContainer[i].toCharArray();
            int len = s.length;
            Trie cur = root;
            if (len < cur.minLen) {
                cur.minLen = len;
                cur.i = i;
            }
            for (int j = len - 1; j >= 0; j--) {
                int c = s[j] - 'a';
                if (cur.children[c] == null) {
                    cur.children[c] = new Trie();
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
            Trie cur = root;
            char[] s = wordsQuery[i].toCharArray();
            for (int j = s.length - 1; j >= 0 && cur.children[s[j] - 'a'] != null; j--) {
                cur = cur.children[s[j] - 'a'];
            }
            ans[i] = cur.i;
        }
        return ans;
    }

    class Trie{
        Trie[] children = new Trie[26];
        int minLen = Integer.MAX_VALUE; // 最小长度
        int i; // 下标
    }
}
```

