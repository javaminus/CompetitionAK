import java.util.*;

public class Solution {
    public int[] longestCommonPrefix(String[] words, int k) {
        Trie trie = new Trie();
        trie.setK(k);
        for (String s : words) {
            trie.insert(s);
        }
        int[] res = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            trie.delete(words[i]);
            res[i] = trie.query();
            trie.insert(words[i]);
        }
        return res;
    }

    class Trie {
        int cnt;             // How many strings pass through (or end in) this node.
        int dp;              // Cached maximum prefix chain length from this node.
        Trie[] sons;         // Children nodes.
        int K;               // The threshold for valid node counts.

        public Trie(){
            cnt = 0;
            dp = 0;
            sons = new Trie[26];
        }

        public void setK(int k) {
            this.K = k;
        }

        public void insert(String s) {
            List<Trie> path = new ArrayList<>();
            Trie node = this;
            path.add(node);
            for (char c : s.toCharArray()) {
                int d = c - 'a';
                if (node.sons[d] == null) {
                    node.sons[d] = new Trie();
                    node.sons[d].setK(this.K);
                }
                node = node.sons[d];
                node.cnt++;
                path.add(node);
            }
            updatePath(path);
        }

        public void delete(String s) {
            List<Trie> path = new ArrayList<>();
            Trie node = this;
            path.add(node);
            for (char c : s.toCharArray()) {
                int d = c - 'a';
                node = node.sons[d];
                node.cnt--;
                path.add(node);
            }
            updatePath(path);
        }

        public int query() {
            return this.dp;
        }

        private void updatePath(List<Trie> path) {
            for (int i = path.size() - 1; i >= 0; i--) {
                Trie cur = path.get(i);
                int newdp = 0;
                for (int j = 0; j < 26; j++) {
                    if (cur.sons[j] != null && cur.sons[j].cnt >= K) {
                        newdp = Math.max(newdp, 1 + cur.sons[j].dp);
                    }
                }
                cur.dp = newdp;
            }
        }
    }
}