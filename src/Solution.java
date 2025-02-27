class TextEditor {
    StringBuilder left = new StringBuilder();
    StringBuilder right = new StringBuilder();
    
    public TextEditor() {
        
    }

    public void addText(String text) {
        left.append(text);
    }

    public int deleteText(int k) {
        k = Math.min(k, left.length());
        left.setLength(left.length() - k);
        return k;
    }

    public String cursorLeft(int k) {
        while (k > 0 && left.length() > 0) {
            right.append(left.charAt(left.length() - 1));
            left.deleteCharAt(left.length() - 1);
            k--;
        }
        return left.substring(Math.max(left.length() - 10, 0));
    }

    public String cursorRight(int k) {
        while (k > 0 && right.length() > 0) {
            left.append(right.charAt(right.length() - 1));
            right.deleteCharAt(right.length() - 1);
            k--;
        }
        return right.substring(Math.max(left.length() - 10, 0));
    }
}

/**
 * Your TextEditor object will be instantiated and called as such:
 * TextEditor obj = new TextEditor();
 * obj.addText(text);
 * int param_2 = obj.deleteText(k);
 * String param_3 = obj.cursorLeft(k);
 * String param_4 = obj.cursorRight(k);
 */