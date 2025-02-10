import java.util.List;

public class TaskSorter {
    private TaskSortingStrategy strategy;

    public void setStrategy(TaskSortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void sortTasks(List<Task> tasks) {
        if (strategy != null) {
            strategy.sort(tasks);
        } else {
            System.out.println("No sorting strategy set!");
        }
    }
}