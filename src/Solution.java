import java.util.ArrayList;
import java.util.List;

class BrowserHistory {

    List<String> list = new ArrayList<>();
    int cur = 0, size = 0;
    public BrowserHistory(String homepage) {
        list.add(homepage);
        size++;
    }

    public void visit(String url) {
        size = cur + 2;
        if (list.size() < size) {
            list.add(url);
        } else {
            list.set(cur + 1, url);
        }
        cur++;
    }

    public String back(int steps) {
        cur = Math.max(0, cur - steps);
        return list.get(cur);
    }

    public String forward(int steps) {
        cur = Math.min(size - 1, cur + steps);
        return list.get(cur);
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */