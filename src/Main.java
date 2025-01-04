import java.util.*;

class TaskManager {
    private static class Task implements Comparable<Task> {
        int userId;
        int taskId;
        int priority;

        public Task(int userId, int taskId, int priority) {
            this.userId = userId;
            this.taskId = taskId;
            this.priority = priority;
        }

        @Override
        public int compareTo(Task other) {
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }
            return Integer.compare(other.taskId, this.taskId);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Task)) return false;
            Task other = (Task) obj;
            return this.taskId == other.taskId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(taskId);
        }
    }

    private TreeSet<Task> taskSet;
    private Map<Integer, Task> taskMap;
    
    public TaskManager(List<List<Integer>> tasks) {
        taskSet = new TreeSet<>();
        taskMap = new HashMap<>();
        for (List<Integer> t : tasks) {
            int userId = t.get(0);
            int taskId = t.get(1);
            int priority = t.get(2);
            Task task = new Task(userId, taskId, priority);
            taskSet.add(task);
            taskMap.put(taskId, task);
        }
    }
    public void add(int userId, int taskId, int priority) {
        Task task = new Task(userId, taskId, priority);
        taskSet.add(task);
        taskMap.put(taskId, task);
    }
    
    public void edit(int taskId, int newPriority) {
        Task task = taskMap.get(taskId);
        if (task != null) {
            taskSet.remove(task);
            task.priority = newPriority;
            taskSet.add(task);
        }
    }
    
    public void rmv(int taskId) {
        Task task = taskMap.get(taskId);
        if (task != null) {
            taskSet.remove(task);
            taskMap.remove(taskId);
        }
    }
    
    public int execTop() {
        if (taskSet.isEmpty()) {
            return -1;
        }
        Task topTask = taskSet.first();
        taskSet.remove(topTask);
        taskMap.remove(topTask.taskId);
        return topTask.userId;
    }
}