class Trie {
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
    
    public boolean search(String word) {
        Trie root = searchPrefix(word);
        return root != null && root.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

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

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */