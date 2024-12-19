import java.util.*;

class Solution {
    class Pair{
        String son;
        Double val;

        public Pair(String son, Double val) {
            this.son = son;
            this.val = val;
        }
    }
    public double maxAmount(String initialCurrency, List<List<String>> pairs1, double[] rates1, List<List<String>> pairs2, double[] rates2) {
        Map<String, List<Pair>> g = build(pairs1, rates1);
        HashMap<String, Double> day1Amount = new HashMap<>();
        init(initialCurrency, 1, g, day1Amount);
        g = build(pairs2, rates2);
        HashSet<String> vis = new HashSet<>();
        for (Map.Entry<String, Double> e : day1Amount.entrySet()) {
            vis.clear();
            dfs(e.getKey(), e.getValue(), initialCurrency, g, vis);
        }
        return ans;
    }

    private double ans;
    private boolean dfs(String x, double curAmount, String initialCurrency, Map<String, List<Pair>> g, Set<String> vis) {
        if (x.equals(initialCurrency)) {
            ans = Math.max(ans, curAmount);
            return true;
        }
        vis.add(x);
        if (!g.containsKey(x)) {
            return false;
        }
        for (Pair p : g.get(x)) {
            if (!vis.contains(p.son) && dfs(p.son, curAmount * p.val, initialCurrency, g, vis)) {
                return true;
            }
        }
        return false;
    }
    
    
    private void init(String x, double amount, Map<String, List<Pair>> g, Map<String, Double> day1Amount) {
        day1Amount.put(x, amount);
        for (Pair p : g.get(x)) {
            if (!day1Amount.containsKey(p.son)) {
                init(p.son, amount * p.val, g, day1Amount);
            }
        }
    }
    private Map<String, List<Pair>> build(List<List<String>> pairs, double[] rates) {
        HashMap<String, List<Pair>> g = new HashMap<>();
        for (int i = 0; i < pairs.size(); i++) {
            String x = pairs.get(i).get(0), y = pairs.get(i).get(1);
            double r = rates[i];
            g.computeIfAbsent(x, e -> new ArrayList<>()).add(new Pair(y, r));
            g.computeIfAbsent(y, e -> new ArrayList<>()).add(new Pair(x, 1 / r));
        }
        return g;
    }
}